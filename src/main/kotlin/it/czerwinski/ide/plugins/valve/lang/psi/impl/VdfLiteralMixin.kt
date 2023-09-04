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

package it.czerwinski.ide.plugins.valve.lang.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import it.czerwinski.ide.plugins.valve.lang.psi.VdfLiteral

/**
 * Base implementation for Valve Data Format literals.
 */
abstract class VdfLiteralMixin(node: ASTNode) : VdfValueImpl(node), VdfLiteral {

    /**
     * Returns references of this literal.
     */
    override fun getReferences(): Array<PsiReference> =
        if (StringUtil.unquoteString(string.text).startsWith(REFERENCE_CHARACTER)) {
            ReferenceProvidersRegistry.getReferencesFromProviders(this)
        } else {
            emptyArray()
        }

    companion object {
        private const val REFERENCE_CHARACTER = '#'
    }
}
