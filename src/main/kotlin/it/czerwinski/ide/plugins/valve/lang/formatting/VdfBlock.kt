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

package it.czerwinski.ide.plugins.valve.lang.formatting

import com.intellij.formatting.Alignment
import com.intellij.formatting.Block
import com.intellij.formatting.ChildAttributes
import com.intellij.formatting.Indent
import com.intellij.formatting.Spacing
import com.intellij.formatting.SpacingBuilder
import com.intellij.formatting.Wrap
import com.intellij.lang.ASTNode
import com.intellij.psi.TokenType
import com.intellij.psi.formatter.common.AbstractBlock
import it.czerwinski.ide.plugins.valve.lang.psi.VdfTypes

/**
 * Valve Data Format block.
 */
class VdfBlock(
    node: ASTNode,
    private val indent: Indent,
    private val spacingBuilder: SpacingBuilder,
    wrap: Wrap? = null,
    alignment: Alignment? = null
) : AbstractBlock(node, wrap, alignment) {

    /**
     * Returns indent of this block.
     */
    override fun getIndent(): Indent =
        indent

    /**
     * Returns spacing between [child1] and [child2].
     */
    override fun getSpacing(child1: Block?, child2: Block): Spacing? =
        spacingBuilder.getSpacing(this, child1, child2)

    /**
     * Returns `true` if this block is a leaf.
     */
    override fun isLeaf(): Boolean =
        node.firstChildNode == null

    /**
     * Builds and returns children blocks.
     */
    override fun buildChildren(): MutableList<Block> =
        generateSequence(seed = node.firstChildNode) { node -> node.treeNext }
            .filterNot { node -> node.elementType == TokenType.WHITE_SPACE }
            .map { node ->
                val indent = if (this.node.elementType == VdfTypes.OBJECT && node.elementType == VdfTypes.PROPERTY) {
                    Indent.getNormalIndent()
                } else {
                    Indent.getNoneIndent()
                }
                VdfBlock(node, indent, spacingBuilder, wrap, alignment)
            }
            .toMutableList()

    /**
     * Returns attributes of a new child of this block.
     */
    override fun getChildAttributes(newChildIndex: Int): ChildAttributes =
        if (node.elementType == VdfTypes.OBJECT && newChildIndex > 0) {
            ChildAttributes(Indent.getNormalIndent(), alignment)
        } else {
            ChildAttributes(Indent.getNoneIndent(), alignment)
        }
}
