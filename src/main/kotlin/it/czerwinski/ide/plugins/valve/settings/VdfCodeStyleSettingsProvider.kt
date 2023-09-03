/*
 * Copyright 2023 Slawomir Czerwinski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.czerwinski.ide.plugins.valve.settings

import com.intellij.application.options.CodeStyleAbstractConfigurable
import com.intellij.application.options.CodeStyleAbstractPanel
import com.intellij.application.options.TabbedLanguageCodeStylePanel
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.CodeStyleConfigurable
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.codeStyle.CodeStyleSettingsProvider
import com.intellij.psi.codeStyle.CustomCodeStyleSettings
import it.czerwinski.ide.plugins.valve.lang.VdfLanguage

/**
 * Provider of code style settings for Valve Data Format files.
 */
class VdfCodeStyleSettingsProvider : CodeStyleSettingsProvider() {

    /**
     * Creates custom settings with given [settings] as a container.
     */
    override fun createCustomSettings(settings: CodeStyleSettings): CustomCodeStyleSettings =
        VdfCodeStyleSettings(settings)

    /**
     * Creates a new configurable.
     */
    override fun createConfigurable(
        settings: CodeStyleSettings,
        modelSettings: CodeStyleSettings
    ): CodeStyleConfigurable =
        MyConfigurable(settings, modelSettings)

    /**
     * Returns display name of the configurable.
     */
    override fun getConfigurableDisplayName(): String =
        VdfLanguage.displayName

    /**
     * Returns Valve Data Format language.
     */
    override fun getLanguage(): Language =
        VdfLanguage

    private class MyConfigurable(
        settings: CodeStyleSettings,
        cloneSettings: CodeStyleSettings
    ) : CodeStyleAbstractConfigurable(settings, cloneSettings, VdfLanguage.displayName) {

        override fun createPanel(settings: CodeStyleSettings): CodeStyleAbstractPanel =
            MyPanel(currentSettings, settings)
    }

    private class MyPanel(
        currentSettings: CodeStyleSettings,
        settings: CodeStyleSettings
    ) : TabbedLanguageCodeStylePanel(VdfLanguage, currentSettings, settings) {

        override fun initTabs(settings: CodeStyleSettings?) {
            addIndentOptionsTab(settings)
        }
    }
}
