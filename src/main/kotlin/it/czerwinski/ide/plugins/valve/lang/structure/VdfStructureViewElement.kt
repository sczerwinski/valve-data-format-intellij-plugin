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

package it.czerwinski.ide.plugins.valve.lang.structure

import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.ide.util.treeView.smartTree.TreeElement
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.util.childrenOfType
import it.czerwinski.ide.plugins.valve.lang.psi.VdfElement

/**
 * Valve Data Format structure view element for given PSI [element].
 */
data class VdfStructureViewElement(
    private val element: NavigatablePsiElement
) : StructureViewTreeElement {

    /**
     * Returns presentation of this [element].
     */
    override fun getPresentation(): ItemPresentation =
        VdfItemPresentationFactory.createPresentation(element)

    /**
     * Returns children of this [element].
     */
    override fun getChildren(): Array<TreeElement> =
        if (element is VdfElement) {
            element.getProperties()
        } else {
            element.childrenOfType<NavigatablePsiElement>()
        }.map { psiElement ->
            VdfStructureViewElement(psiElement)
        }.toTypedArray()

    /**
     * Navigates to this [element].
     */
    override fun navigate(requestFocus: Boolean) {
        element.navigate(requestFocus)
    }

    /**
     * Returns `true if this [element] can navigate.
     */
    override fun canNavigate(): Boolean =
        element.canNavigate()

    /**
     * Returns `true if this [element] can navigate to source.
     */
    override fun canNavigateToSource(): Boolean =
        element.canNavigateToSource()

    /**
     * Returns this [element].
     */
    override fun getValue(): Any =
        element
}
