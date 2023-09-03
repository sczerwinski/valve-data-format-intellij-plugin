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

import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey

/**
 * Attribute keys for Valve Data Format.
 */
object VdfAttributeKeys {

    /**
     * Property key.
     */
    internal val attrKey: TextAttributesKey =
        createTextAttributesKey("VDF_KEY", DefaultLanguageHighlighterColors.INSTANCE_FIELD)

    /**
     * String literal value.
     */
    internal val attrString: TextAttributesKey =
        createTextAttributesKey("VDF_STRING", DefaultLanguageHighlighterColors.STRING)

    /**
     * Valid escape sequence inside string literal.
     */
    internal val attrValidEscape: TextAttributesKey =
        createTextAttributesKey("VDF_VALID_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE)

    /**
     * Invalid escape sequence inside string literal.
     */
    internal val attrInvalidEscape: TextAttributesKey =
        createTextAttributesKey("VDF_INVALID_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE)

    /**
     * Special character inside string literal.
     */
    internal val attrSpecialCharacter: TextAttributesKey =
        createTextAttributesKey("VDF_SPECIAL_CHAR", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE)

    /**
     * Reference.
     */
    internal val attrReference: TextAttributesKey =
        createTextAttributesKey("VDF_REFERENCE", DefaultLanguageHighlighterColors.IDENTIFIER)

    /**
     * Braces.
     */
    internal val attrBraces: TextAttributesKey =
        createTextAttributesKey("VDF_BRACES", DefaultLanguageHighlighterColors.BRACES)

    /**
     * Comment.
     */
    internal val attrComment: TextAttributesKey =
        createTextAttributesKey("VDF_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)

    /**
     * Bad character.
     */
    internal val attrBadCharacter: TextAttributesKey =
        createTextAttributesKey("VDF_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)

    /**
     * Rainbow text attributes.
     */
    internal val rainbowAttrs: List<TextAttributesKey> =
        listOf(attrKey, attrString, attrBraces)
}
