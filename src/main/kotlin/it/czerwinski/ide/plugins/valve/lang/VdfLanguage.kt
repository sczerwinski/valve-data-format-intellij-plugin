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

package it.czerwinski.ide.plugins.valve.lang

import com.intellij.lang.Language
import it.czerwinski.ide.plugins.valve.ValveBundle

/**
 * Language for the Valve Data Format.
 */
object VdfLanguage : Language("VDF") {

    /**
     * Returns display name of the Valve Data Format language.
     */
    override fun getDisplayName(): String =
        ValveBundle.message(key = "fileType.vdf.name")

    @Suppress("UnusedPrivateMember")
    private fun readResolve(): Any = VdfLanguage
}
