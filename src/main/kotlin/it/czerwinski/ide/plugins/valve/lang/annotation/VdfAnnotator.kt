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

package it.czerwinski.ide.plugins.valve.lang.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.lang.annotation.HighlightSeverity
import com.intellij.psi.PsiElement
import it.czerwinski.ide.plugins.valve.ValveBundle
import it.czerwinski.ide.plugins.valve.lang.VdfAttributeKeys
import it.czerwinski.ide.plugins.valve.lang.psi.VdfKey
import it.czerwinski.ide.plugins.valve.lang.psi.VdfStringHolder

/**
 * Annotator for Valve Data Format files.
 */
class VdfAnnotator : Annotator {

    /**
     * Annotates given [element] of Valve Data Format file.
     */
    override fun annotate(element: PsiElement, holder: AnnotationHolder) {
        if (element is VdfKey) {
            annotateKey(holder)
        }

        if (element is VdfStringHolder && isMissingClosingQuote(element.string.text)) {
            annotateMissingClosingQuote(holder)
        }
    }

    private fun annotateKey(holder: AnnotationHolder) {
        holder.newSilentAnnotation(HighlightSeverity.INFORMATION)
            .textAttributes(VdfAttributeKeys.attrKey)
            .create()
    }

    private fun isMissingClosingQuote(string: String) =
        !STRING_REGEX.toRegex().matches(string)

    private fun annotateMissingClosingQuote(holder: AnnotationHolder) {
        holder.newAnnotation(
            HighlightSeverity.ERROR,
            ValveBundle.message(key = "fileType.vdf.annotator.error.closingQuote")
        ).create()
    }

    companion object {
        private const val STRING_REGEX = "\"([^\\\\\"\\r\\n]|\\\\[^\\r\\n])*\""
    }
}
