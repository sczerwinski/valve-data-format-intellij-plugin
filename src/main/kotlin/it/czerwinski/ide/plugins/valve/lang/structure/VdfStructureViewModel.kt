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

import com.intellij.ide.structureView.StructureViewModel
import com.intellij.ide.structureView.StructureViewModelBase
import com.intellij.ide.structureView.StructureViewTreeElement
import com.intellij.openapi.editor.Editor
import com.intellij.psi.PsiFile

/**
 * Structure view model for Valve Data Format files.
 */
class VdfStructureViewModel(
    psiFile: PsiFile,
    editor: Editor?
) : StructureViewModelBase(psiFile, editor, VdfStructureViewElement(psiFile)),
    StructureViewModel.ElementInfoProvider {

    /**
     * Returns `false`.
     */
    override fun isAlwaysShowsPlus(element: StructureViewTreeElement?): Boolean =
        false

    /**
     * Returns `false`.
     */
    override fun isAlwaysLeaf(element: StructureViewTreeElement?): Boolean =
        false
}
