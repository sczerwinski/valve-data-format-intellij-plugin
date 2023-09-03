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

import com.intellij.lang.Language
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.openapi.options.colors.AttributesDescriptor
import com.intellij.openapi.options.colors.ColorDescriptor
import com.intellij.openapi.options.colors.RainbowColorSettingsPage
import com.intellij.psi.codeStyle.DisplayPriority
import com.intellij.psi.codeStyle.DisplayPrioritySortable
import it.czerwinski.ide.plugins.valve.ValveBundle
import it.czerwinski.ide.plugins.valve.ValveIcons
import it.czerwinski.ide.plugins.valve.lang.VdfAttributeKeys
import it.czerwinski.ide.plugins.valve.lang.VdfLanguage
import it.czerwinski.ide.plugins.valve.lang.VdfSyntaxHighlighter
import javax.swing.Icon

/**
 * Color settings page for Valve Data Format files.
 */
class VdfColorSettingsPage : RainbowColorSettingsPage, DisplayPrioritySortable {

    /**
     * Returns attribute descriptors for Valve Data Format files.
     */
    override fun getAttributeDescriptors(): Array<AttributesDescriptor> = arrayOf(
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_KEY"),
            VdfAttributeKeys.attrKey
        ),
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_STRING"),
            VdfAttributeKeys.attrString
        ),
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_VALID_ESCAPE"),
            VdfAttributeKeys.attrValidEscape
        ),
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_INVALID_ESCAPE"),
            VdfAttributeKeys.attrInvalidEscape
        ),
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_SPECIAL_CHAR"),
            VdfAttributeKeys.attrSpecialCharacter
        ),
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_REFERENCE"),
            VdfAttributeKeys.attrReference
        ),
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_BRACES"),
            VdfAttributeKeys.attrBraces
        ),
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_COMMENT"),
            VdfAttributeKeys.attrComment
        ),
        AttributesDescriptor(
            ValveBundle.message(key = "fileType.vdf.settings.colors.VDF_BAD_CHARACTER"),
            VdfAttributeKeys.attrBadCharacter
        )
    )

    /**
     * Returns an empty array.
     */
    override fun getColorDescriptors(): Array<ColorDescriptor> =
        ColorDescriptor.EMPTY_ARRAY

    /**
     * Returns display name for Valve Data Format.
     */
    override fun getDisplayName(): String =
        requireNotNull(ValveBundle.message(key = "fileType.vdf.name"))

    /**
     * Returns icon for Valve Data Format.
     */
    override fun getIcon(): Icon =
        ValveIcons.VdfFile

    /**
     * Returns syntax highlighter for Valve Data Format files.
     */
    override fun getHighlighter(): SyntaxHighlighter =
        VdfSyntaxHighlighter()

    /**
     * Returns demo text for Valve Data Format file.
     */
    override fun getDemoText(): String =
        """
            <vdfKey>"Root"</vdfKey>
            {
                <vdfKey>"Path"</vdfKey>
                {
                    <vdfKey>"name"</vdfKey>              "#PathName"
                    <vdfKey>"linux_path"</vdfKey>        "/usr/local/bin"
                    <vdfKey>"windows_path"</vdfKey>      "\"C:\\Program Files\\Steam\""
                    <vdfKey>"invalid_path"</vdfKey>      "\/usr\/local\/bin"
                }
                <vdfKey>"Array"</vdfKey>     // Arrays are not supported in VDF
                [
                ]
                <vdfKey>"localization"</vdfKey>
                {
                    <vdfKey>"english"</vdfKey>
                    {
                        <vdfKey>"PathName"</vdfKey>      "Some path"
                    }
                }
            }
        """.trimIndent()

    /**
     * Returns map of additional highlighting tags to descriptors.
     */
    override fun getAdditionalHighlightingTagToDescriptorMap(): MutableMap<String, TextAttributesKey> =
        mutableMapOf("vdfKey" to VdfAttributeKeys.attrKey)

    /**
     * Returns display priority of this settings page.
     */
    override fun getPriority(): DisplayPriority =
        DisplayPriority.LANGUAGE_SETTINGS

    /**
     * Returns `true` if given [type] is one of types .
     */
    override fun isRainbowType(type: TextAttributesKey?): Boolean =
        type in VdfAttributeKeys.rainbowAttrs

    /**
     * Returns Valve Data Format language.
     */
    override fun getLanguage(): Language =
        VdfLanguage
}
