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

package it.czerwinski.ide.plugins.valve.lang.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.util.childrenOfType
import it.czerwinski.ide.plugins.valve.lang.VdfFileType
import it.czerwinski.ide.plugins.valve.lang.VdfLanguage

/**
 * Valve Data Format file.
 */
class VdfFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, VdfLanguage) {

    /**
     * Returns type of this file.
     */
    override fun getFileType(): FileType =
        VdfFileType

    /**
     * Returns all properties in this file.
     */
    fun findAllProperties(): Sequence<VdfProperty> =
        childrenOfType<VdfProperty>()
            .asSequence()
            .flatMap { property -> sequenceOf(property) + property.getPropertiesRecursively() }

    /**
     * Returns all properties with given [name] in this file.
     */
    fun findProperties(name: String): Sequence<VdfProperty> =
        childrenOfType<VdfProperty>()
            .asSequence()
            .flatMap { property -> property.findProperties(name) }

    /**
     * Returns string representation of this file.
     */
    override fun toString(): String =
        "Valve Data Format file"
}
