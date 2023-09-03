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

import com.intellij.ide.projectView.PresentationData
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import it.czerwinski.ide.plugins.valve.ValveBundle
import it.czerwinski.ide.plugins.valve.ValveIcons
import it.czerwinski.ide.plugins.valve.lang.psi.VdfElement
import it.czerwinski.ide.plugins.valve.lang.psi.VdfFile
import javax.swing.Icon

/**
 * Item presentation factory for Valve Data Format PSI elements.
 */
object VdfItemPresentationFactory {

    /**
     * Creates item presentation for given PSI [element].
     */
    fun createPresentation(element: PsiElement): ItemPresentation =
        when (element) {
            is VdfFile -> createFilePresentation(element)
            is VdfElement -> createPropertyPresentation(element)
            else -> createErrorPresentation(element)
        }

    private fun createFilePresentation(element: VdfFile): ItemPresentation =
        createPresentation(
            presentableText = element.name,
            locationString = "",
            icon = ValveIcons.VdfFile
        )

    private fun createPropertyPresentation(element: VdfElement): ItemPresentation =
        if (element.isObject()) createObjectPresentation(element) else createLiteralPresentation(element)

    private fun createObjectPresentation(element: VdfElement): ItemPresentation =
        createPresentation(
            presentableText = element.name.orEmpty(),
            locationString = "",
            icon = ValveIcons.Object
        )

    private fun createLiteralPresentation(element: VdfElement): ItemPresentation =
        createPresentation(
            presentableText = element.name.orEmpty(),
            locationString = element.getTextValue().orEmpty(),
            icon = ValveIcons.Property
        )

    private fun createErrorPresentation(element: PsiElement): ItemPresentation =
        createPresentation(
            presentableText = ValveBundle.message(key = "fileType.vdf.structure.error.invalidElement"),
            locationString = element.javaClass.simpleName,
            icon = ValveIcons.Error
        )

    private fun createPresentation(
        presentableText: String,
        locationString: String,
        icon: Icon
    ): ItemPresentation =
        PresentationData(presentableText, locationString, icon, null)
}
