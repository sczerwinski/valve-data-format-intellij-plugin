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

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.lexer.LayeredLexer
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import it.czerwinski.ide.plugins.valve.ValveBundle
import it.czerwinski.ide.plugins.valve.lang.VdfLexerAdapter
import it.czerwinski.ide.plugins.valve.lang.VdfStringLiteralLexer
import it.czerwinski.ide.plugins.valve.lang.psi.VdfProperty
import it.czerwinski.ide.plugins.valve.lang.psi.VdfTypes

/**
 * Find usages provider for Valve Data Format files.
 */
class VdfFindUsagesProvider : FindUsagesProvider {

    /**
     * Returns a word scanner for Valve Data Format property usages provider.
     */
    override fun getWordsScanner(): WordsScanner =
        DefaultWordsScanner(
            LayeredLexer(VdfLexerAdapter()).apply {
                registerLayer(VdfStringLiteralLexer(), VdfTypes.STRING)
            },
            TokenSet.create(VdfTypes.KEY),
            TokenSet.create(VdfTypes.COMMENT),
            TokenSet.create(VdfTypes.KEY)
        )

    /**
     * Returns `true` if the [psiElement] is a Valve Data Format property.
     */
    override fun canFindUsagesFor(psiElement: PsiElement): Boolean =
        psiElement is VdfProperty

    /**
     * Returns null.
     */
    override fun getHelpId(psiElement: PsiElement): String? =
        null

    /**
     * Returns type name for Valve Data Format property.
     */
    override fun getType(element: PsiElement): String =
        if (element is VdfProperty) ValveBundle.message(key = "fileType.vdf.usages.property") else ""

    /**
     * Returns descriptive name of the Valve Data Format property.
     */
    override fun getDescriptiveName(element: PsiElement): String =
        if (element is VdfProperty) StringUtil.unquoteString(element.key.text) else ""

    /**
     * Returns node text for the [element].
     */
    override fun getNodeText(element: PsiElement, useFullName: Boolean): String =
        if (element is VdfProperty) {
            if (useFullName) {
                val valueText = if (element.isObject()) OBJECT_VALUE else element.value.text
                "${element.key.text} $valueText"
            } else {
                element.key.text
            }
        } else {
            ""
        }

    companion object {
        private const val OBJECT_VALUE = "{...}"
    }
}
