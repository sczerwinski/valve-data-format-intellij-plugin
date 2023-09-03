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
import com.intellij.psi.PsiElement
import com.intellij.util.IncorrectOperationException
import it.czerwinski.ide.plugins.valve.lang.psi.VdfLiteral
import it.czerwinski.ide.plugins.valve.lang.psi.VdfObject
import it.czerwinski.ide.plugins.valve.lang.psi.VdfProperty
import org.jetbrains.annotations.NonNls

/**
 * Base implementation for Valve Data Format properties.
 */
abstract class VdfPropertyMixin(node: ASTNode) : VdfElementImpl(node), VdfProperty {

    /**
     * Returns name of this property.
     */
    override fun getName(): String =
        StringUtil.unquoteString(key.text)

    /**
     * Renames this Valve Data Format property.
     *
     * @return Renamed Valve Data Format property PSI element.
     */
    @Throws(IncorrectOperationException::class)
    override fun setName(@NonNls name: String): PsiElement {
        throw IncorrectOperationException("Not implemented")
    }

    /**
     * Returns text value of this Valve Data Format PSI element.
     */
    override fun getTextValue(): String? =
        (value as? VdfLiteral)?.text?.let(StringUtil::unquoteString)?.let(StringUtil::unescapeStringCharacters)

    /**
     * Returns `true` if this Valve Data Format PSI element is an object property.
     */
    override fun isObject(): Boolean =
        value is VdfObject

    /**
     * Returns Valve Data Format properties inside this element.
     */
    override fun getProperties(): List<VdfProperty> =
        (value as? VdfObject)?.propertyList.orEmpty()
}
