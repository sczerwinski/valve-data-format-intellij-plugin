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

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import it.czerwinski.ide.plugins.valve.lang.parser.VdfParser
import it.czerwinski.ide.plugins.valve.lang.psi.VdfFile
import it.czerwinski.ide.plugins.valve.lang.psi.VdfTypes

/**
 * Valve Data Format parser definition.
 */
class VdfParserDefinition : ParserDefinition {

    /**
     * Creates a new Valve Data Format lexer.
     */
    override fun createLexer(project: Project?): Lexer =
        VdfLexerAdapter()

    /**
     * Creates a new Valve Data Format parser.
     */
    override fun createParser(project: Project?): PsiParser =
        VdfParser()

    /**
     * Returns a Valve Data Format file node element type.
     */
    override fun getFileNodeType(): IFileElementType =
        IFileElementType(VdfLanguage)

    /**
     * Returns a set of valid Valve Data Format comment tokens.
     */
    override fun getCommentTokens(): TokenSet =
        TokenSet.create(VdfTypes.COMMENT)

    /**
     * Returns a set of valid Valve Data Format string literals.
     */
    override fun getStringLiteralElements(): TokenSet =
        TokenSet.create(VdfTypes.STRING)

    /**
     * Creates a new Valve Data Format PSI element from given [node].
     */
    override fun createElement(node: ASTNode?): PsiElement =
        VdfTypes.Factory.createElement(node)

    /**
     * Creates a new Valve Data Format file.
     */
    override fun createFile(viewProvider: FileViewProvider): PsiFile =
        VdfFile(viewProvider)

    /**
     * Returns space requirements between [left] and [right] node.
     */
    override fun spaceExistenceTypeBetweenTokens(left: ASTNode?, right: ASTNode?): ParserDefinition.SpaceRequirements {
        val spaceRequiredTypes = listOf(VdfTypes.KEY, VdfTypes.LITERAL, VdfTypes.STRING)
        return if (left?.elementType in spaceRequiredTypes && right?.elementType in spaceRequiredTypes) {
            ParserDefinition.SpaceRequirements.MUST
        } else {
            ParserDefinition.SpaceRequirements.MAY
        }
    }
}
