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

import com.intellij.ide.navigationToolbar.StructureAwareNavBarModelExtension
import com.intellij.lang.Language
import it.czerwinski.ide.plugins.valve.ValveIcons
import it.czerwinski.ide.plugins.valve.lang.VdfLanguage
import it.czerwinski.ide.plugins.valve.lang.psi.VdfElement
import it.czerwinski.ide.plugins.valve.lang.psi.VdfFile
import javax.swing.Icon

/**
 * Structure-aware navigation bar for Valve Data Format files.
 */
class VdfStructureAwareNavBar : StructureAwareNavBarModelExtension() {

    /**
     * Returns Valve Data Format language.
     */
    override val language: Language
        get() = VdfLanguage

    /**
     * Returns presentable text for the given [object].
     */
    override fun getPresentableText(`object`: Any?): String? =
        when (`object`) {
            is VdfFile -> `object`.name
            is VdfElement -> `object`.name
            else -> null
        }

    /**
     * Returns icon for the given [object].
     */
    override fun getIcon(`object`: Any?): Icon? =
        when (`object`) {
            is VdfFile -> ValveIcons.VdfFile
            is VdfElement -> if (`object`.isObject()) ValveIcons.Object else ValveIcons.Property
            else -> null
        }
}
