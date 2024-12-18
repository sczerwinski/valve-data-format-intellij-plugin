import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.changelog.Changelog
import org.jetbrains.changelog.markdownToHTML
import org.jetbrains.grammarkit.tasks.GenerateLexerTask
import org.jetbrains.grammarkit.tasks.GenerateParserTask

fun properties(key: String) = project.findProperty(key).toString()

plugins {
    // Java support
    id("java")
    // Kotlin support
    id("org.jetbrains.kotlin.jvm") version "2.0.20"
    // Gradle IntelliJ Plugin
    id("org.jetbrains.intellij") version "1.17.4"
    // Gradle Grammar-Kit Plugin
    id("org.jetbrains.grammarkit") version "2022.3.2.2"
    // gradle-changelog-plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
    id("org.jetbrains.changelog") version "2.2.1"
    // detekt linter - read more: https://detekt.github.io/detekt/kotlindsl.html
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
}

group = properties("pluginGroup")
version = properties("pluginVersion")

// Configure project's dependencies
repositories {
    mavenCentral()
    maven {
        name = "JogAmp"
        url = uri(path = "https://www.jogamp.org/deployment/maven/")
    }
}

// Set the JVM language level used to compile sources and generate files - Java 17 is required since 2022.2
kotlin {
    jvmToolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.23.7")
}

// Generate parsers and lexers before Kotlin compilation.
// Read more: https://github.com/JetBrains/gradle-grammar-kit-plugin
fun generateParserTask(suffix: String, config: GenerateParserTask.() -> Unit = {}) =
    task<GenerateParserTask>("generateParser${suffix.replaceFirstChar { it.uppercaseChar() }}") {
        sourceFile.set(file("src/main/grammar/${suffix.replaceFirstChar { it.uppercaseChar() }}.bnf"))
        targetRootOutputDir.set(file("${project.layout.buildDirectory.get()}/generated/source/parser/$suffix"))
        pathToParser.set("it/czerwinski/ide/plugins/valve/lang/parser/${suffix.replaceFirstChar { it.uppercaseChar() }}Parser.java")
        pathToPsiRoot.set("it/czerwinski/ide/plugins/valve/lang/psi")
        purgeOldFiles.set(true)
        config()
    }

fun generateLexerTask(suffix: String, config: GenerateLexerTask.() -> Unit = {}) =
    task<GenerateLexerTask>("generateLexer${suffix.replaceFirstChar { it.uppercaseChar() }}") {
        sourceFile.set(file("src/main/grammar/${suffix.replaceFirstChar { it.uppercaseChar() }}.flex"))
        targetOutputDir.set(file("${project.layout.buildDirectory.get()}/generated/source/lexer/$suffix/it/czerwinski/intellij/wavefront/lang"))
        purgeOldFiles.set(true)
        config()
    }

val generateParserVdf = generateParserTask("vdf")
val generateLexerVdf = generateLexerTask("vdf")

val compileKotlin = tasks.named("compileKotlin") {
    dependsOn(generateParserVdf, generateLexerVdf)
}

// Configure gradle-intellij-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-intellij-plugin
intellij {
    pluginName.set(properties("pluginName"))
    version.set(properties("platformVersion"))
    type.set(properties("platformType"))
    downloadSources.set(properties("platformDownloadSources").toBoolean())
    updateSinceUntilBuild.set(true)

    // Plugin Dependencies. Uses `platformPlugins` property from the gradle.properties file.
    plugins.set(properties("platformPlugins").split(',').map(String::trim).filter(String::isNotEmpty))
}

// Configure gradle-changelog-plugin plugin.
// Read more: https://github.com/JetBrains/gradle-changelog-plugin
changelog {
    version.set(properties("pluginVersion"))
}

// Configure detekt plugin.
// Read more: https://detekt.github.io/detekt/kotlindsl.html
detekt {
    config.setFrom(files("./detekt-config.yml"))
    buildUponDefaultConfig = true
}

tasks {

    withType<JavaCompile> {
        sourceSets["main"].java.srcDirs(
            "${project.projectDir}/src/main/kotlin",
            "${project.layout.buildDirectory.get()}/generated/source/lexer/vdf",
            "${project.layout.buildDirectory.get()}/generated/source/parser/vdf"
        )
    }

    withType<Detekt> {
        // Configure detekt reports.
        // Read more: https://detekt.github.io/detekt/kotlindsl.html
        reports {
            html.required.set(false)
            xml {
                required.set(true)
                outputLocation.set(file("build/reports/detekt.xml"))
            }
            txt.required.set(false)
            sarif {
                required.set(true)
                outputLocation.set(file("build/reports/detekt.sarif.json"))
            }
        }
    }

    wrapper {
        gradleVersion = properties("gradleVersion")
    }

    patchPluginXml {
        version.set(properties("pluginVersion"))
        sinceBuild.set(properties("pluginSinceBuild"))
        untilBuild.set(properties("pluginUntilBuild"))

        // Extract the <!-- Plugin description --> section from README.md and provide for the plugin's manifest
        pluginDescription.set(
            projectDir.resolve("README.md").readText().lines().run {
                val start = "<!-- Plugin description -->"
                val end = "<!-- Plugin description end -->"

                if (!containsAll(listOf(start, end))) {
                    throw GradleException("Plugin description section not found in README.md:\n$start ... $end")
                }
                subList(indexOf(start) + 1, indexOf(end))
            }.joinToString("\n").run { markdownToHTML(this) }
        )

        // Get the change notes from the changelog file
        changeNotes.set(
            provider {
                changelog.renderItem(
                    changelog.run {
                        getOrNull(properties("pluginVersion")) ?: getUnreleased()
                    }.withHeader(false),
                    Changelog.OutputType.HTML
                )
            }
        )
    }

    runPluginVerifier {
        ideVersions.set(properties("pluginVerifierIdeVersions").split(',').map(String::trim).filter(String::isNotEmpty))
    }

    signPlugin {
        certificateChain.set(System.getenv("SIGNING_CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("SIGNING_PRIVATE_KEY"))
        password.set(System.getenv("SIGNING_PASSWORD"))
    }

    publishPlugin {
        dependsOn("patchChangelog")
        token.set(System.getenv("PUBLISH_TOKEN"))
        // pluginVersion is based on the SemVer (https://semver.org) and supports pre-release labels, like 2.1.7-alpha.3
        // Specify pre-release label to publish the plugin in a custom Release Channel automatically. Read more:
        // https://plugins.jetbrains.com/docs/intellij/deployment.html#specifying-a-release-channel
        channels.set(listOf(properties("pluginVersion").split('-').getOrElse(1) { "default" }.split('.').first()))
    }
}
