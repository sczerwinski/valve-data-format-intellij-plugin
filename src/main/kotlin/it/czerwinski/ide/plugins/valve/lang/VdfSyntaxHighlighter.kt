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

import com.intellij.lexer.LayeredLexer
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import it.czerwinski.ide.plugins.valve.lang.psi.VdfStringLiteralTypes
import it.czerwinski.ide.plugins.valve.lang.psi.VdfTypes

/**
 * Syntax highlighter for Valve Data Format files.
 */
class VdfSyntaxHighlighter : SyntaxHighlighter {

    /**
     * Returns a lexer used for highlighting.
     */
    override fun getHighlightingLexer(): Lexer =
        LayeredLexer(VdfLexerAdapter()).apply {
            registerLayer(VdfStringLiteralLexer(), VdfTypes.STRING)
        }

    /**
     * Returns text attributes for given [tokenType].
     */
    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> =
        when (tokenType) {
            VdfTypes.STRING ->
                arrayOf(VdfAttributeKeys.attrString)

            VdfStringLiteralTypes.VALID_ESCAPE_TOKEN ->
                arrayOf(VdfAttributeKeys.attrValidEscape)

            VdfStringLiteralTypes.INVALID_ESCAPE_TOKEN ->
                arrayOf(VdfAttributeKeys.attrInvalidEscape)

            VdfStringLiteralTypes.REFERENCE_CHARACTER ->
                arrayOf(VdfAttributeKeys.attrSpecialCharacter)

            VdfStringLiteralTypes.REFERENCE ->
                arrayOf(VdfAttributeKeys.attrReference)

            VdfTypes.LBRACE,
            VdfTypes.RBRACE ->
                arrayOf(VdfAttributeKeys.attrBraces)

            VdfTypes.COMMENT ->
                arrayOf(VdfAttributeKeys.attrComment)

            TokenType.BAD_CHARACTER ->
                arrayOf(VdfAttributeKeys.attrBadCharacter)

            else ->
                emptyArray()
        }
}
