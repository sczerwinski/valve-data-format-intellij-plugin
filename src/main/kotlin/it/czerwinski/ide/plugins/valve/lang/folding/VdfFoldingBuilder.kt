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

package it.czerwinski.ide.plugins.valve.lang.folding

import com.intellij.lang.ASTNode
import com.intellij.lang.folding.FoldingBuilderEx
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import it.czerwinski.ide.plugins.valve.lang.psi.VdfObject
import it.czerwinski.ide.plugins.valve.lang.psi.VdfTypes
import org.jetbrains.annotations.NonNls

/**
 * Folding builder for Valve Data Format files.
 */
class VdfFoldingBuilder : FoldingBuilderEx(), DumbAware {

    /**
     * Returns array of Valve Data Format objects.
     */
    override fun buildFoldRegions(root: PsiElement, document: Document, quick: Boolean): Array<FoldingDescriptor> =
        PsiTreeUtil.findChildrenOfType(root, VdfObject::class.java)
            .map { vdfObject -> FoldingDescriptor(vdfObject.node, vdfObject.textRange) }
            .toTypedArray()

    /**
     * Returns placeholder text for collapsed braces block.
     */
    override fun getPlaceholderText(node: ASTNode): String =
        if (isVdfObjectEmpty(node)) EMPTY_BRACES_PLACEHOLDER_TEXT
        else BRACES_PLACEHOLDER_TEXT

    /**
     * Returns `true` if the given [node] contains no properties.
     */
    override fun isCollapsedByDefault(node: ASTNode): Boolean =
        isVdfObjectEmpty(node)

    private fun isVdfObjectEmpty(node: ASTNode) = node.getChildren(TokenSet.create(VdfTypes.PROPERTY)).isEmpty()

    companion object {
        @NonNls private const val BRACES_PLACEHOLDER_TEXT = "{...}"
        @NonNls private const val EMPTY_BRACES_PLACEHOLDER_TEXT = "{}"
    }
}
