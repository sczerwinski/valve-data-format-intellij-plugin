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

import com.intellij.application.options.IndentOptionsEditor
import com.intellij.application.options.SmartIndentOptionsEditor
import com.intellij.lang.Language
import com.intellij.psi.codeStyle.LanguageCodeStyleSettingsProvider
import it.czerwinski.ide.plugins.valve.lang.VdfLanguage

/**
 * Language code style settings provider for Valve Data Format files.
 */
class VdfLanguageCodeStyleSettingsProvider : LanguageCodeStyleSettingsProvider() {

    /**
     * Returns Valve Data Format language.
     */
    override fun getLanguage(): Language =
        VdfLanguage

    /**
     * Returns smart indent options editor.
     */
    override fun getIndentOptionsEditor(): IndentOptionsEditor {
        return SmartIndentOptionsEditor()
    }

    /**
     * Returns Valve Data Format code sample for code style settings preview.
     */
    override fun getCodeSample(settingsType: SettingsType): String =
        """
            "Root"
            {
                "Path"
                {
                    "name"              "#PathName"
                    "linux_path"        "/usr/local/bin"
                    "windows_path"      "\"C:\\Program Files\\Steam\""
                }

                "localization"
                {
                    "english"
                    {
                        "PathName"      "Some path"
                    }
                }
            }
        """.trimIndent()
}
