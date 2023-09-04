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

package it.czerwinski.ide.plugins.valve.lang.psi.reference

import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import it.czerwinski.ide.plugins.valve.ValveBundle
import it.czerwinski.ide.plugins.valve.ValveIcons
import it.czerwinski.ide.plugins.valve.lang.psi.VdfFile
import it.czerwinski.ide.plugins.valve.lang.psi.VdfLiteral
import javax.swing.Icon

/**
 * Line marker provider for Valve Data Format property references.
 */
class VdfLineMarkerProvider : RelatedItemLineMarkerProvider() {

    /**
     * Returns name of this marker.
     */
    override fun getName(): String =
        ValveBundle.message(key = "fileType.vdf.marker.property.name")

    /**
     * Returns icon of this marker.
     */
    override fun getIcon(): Icon =
        ValveIcons.Property

    /**
     * Puts navigation markers for the given [element] into a [result] collection.
     */
    override fun collectNavigationMarkers(
        element: PsiElement,
        result: MutableCollection<in RelatedItemLineMarkerInfo<*>>
    ) {
        if (element.parent is VdfLiteral) {
            val elementText = StringUtil.unquoteString(element.text)
            val markedElement = element.takeIf { elementText.startsWith(REFERENCE_CHARACTER) }
            if (markedElement != null) {
                val references = (element.containingFile as? VdfFile)
                    ?.findProperties(elementText.removePrefix(REFERENCE_CHARACTER.toString()))
                    .orEmpty()
                    .toList()
                if (references.isNotEmpty()) {
                    val marker = NavigationGutterIconBuilder.create(icon)
                        .setTargets(references)
                        .setTooltipText(ValveBundle.message(key = "fileType.vdf.marker.property.tooltip"))
                        .createLineMarkerInfo(element)
                    result.add(marker)
                }
            }
        }
    }

    companion object {
        private const val REFERENCE_CHARACTER = '#'
    }
}
