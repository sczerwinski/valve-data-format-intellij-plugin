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

package it.czerwinski.ide.plugins.valve.lang

import com.intellij.openapi.fileTypes.LanguageFileType
import it.czerwinski.ide.plugins.valve.ValveIcons
import javax.swing.Icon

/**
 * Valve Data Format file type.
 */
object VdfFileType : LanguageFileType(VdfLanguage) {

    /**
     * Returns the name of this file type.
     */
    override fun getName(): String = "Valve Data Format"

    /**
     * Returns the description of this file type.
     */
    override fun getDescription(): String = "Valve data format"

    /**
     * Returns the default extensions of the files of this type.
     */
    override fun getDefaultExtension(): String = "vdf"

    /**
     * Returns the icon of this file type.
     */
    override fun getIcon(): Icon = ValveIcons.VdfFile
}
