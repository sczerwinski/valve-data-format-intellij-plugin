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

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.PsiReferenceProvider
import com.intellij.psi.ResolveResult
import com.intellij.util.ProcessingContext
import it.czerwinski.ide.plugins.valve.ValveIcons
import it.czerwinski.ide.plugins.valve.lang.psi.VdfFile
import it.czerwinski.ide.plugins.valve.lang.psi.VdfProperty

/**
 * Reference to a Valve Data Format property.
 */
class VdfPropertyReference(
    element: PsiElement,
    textRange: TextRange
) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    private val file = element.containingFile as? VdfFile

    private val propertyName = element.text.substring(textRange.startOffset, textRange.endOffset)

    /**
     * Returns an array of resolve results for this property reference.
     */
    override fun multiResolve(incompleteCode: Boolean): Array<ResolveResult> =
        findMatchingProperties()
            .map(::PsiElementResolveResult)
            .toList()
            .toTypedArray()

    private fun findMatchingProperties(): Sequence<VdfProperty> =
        file?.findProperties(propertyName).orEmpty()

    /**
     * Returns a single referred PSI element if only one exists.
     */
    override fun resolve(): PsiElement? =
        findMatchingProperties().singleOrNull()

    /**
     * Returns lookup element builders for all available properties in the same file.
     */
    override fun getVariants(): Array<Any> =
        file?.findAllProperties().orEmpty()
            .map { property ->
                LookupElementBuilder.create(property)
                    .withIcon(if (property.isObject()) ValveIcons.Object else ValveIcons.Property)
                    .withTypeText(if (property.isObject()) OBJECT_VALUE else property.value.text, property.isObject())
            }
            .toList()
            .toTypedArray()

    /**
     * Provider of a reference to a Valve Data Format property.
     */
    object Provider : PsiReferenceProvider() {

        private const val REFERENCE_CHARACTER = '#'

        /**
         * Returns all PSI references for given [element].
         */
        override fun getReferencesByElement(element: PsiElement, context: ProcessingContext): Array<PsiReference> {
            val propertyText = StringUtil.unquoteString(element.text)
            val propertyName = if (propertyText.startsWith(REFERENCE_CHARACTER)) {
                propertyText.removePrefix(REFERENCE_CHARACTER.toString())
            } else {
                null
            }
            return if (!propertyName.isNullOrBlank()) {
                val textRange = TextRange.from(element.text.indexOf(propertyName), propertyName.length)
                arrayOf(VdfPropertyReference(element, textRange))
            } else {
                return PsiReference.EMPTY_ARRAY
            }
        }
    }

    companion object {
        private const val OBJECT_VALUE = "{...}"
    }
}
