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

import com.intellij.psi.tree.IElementType

/**
 * Valve Data Format elements types inside localized string.
 */
object VdfStringLiteralTypes {

    /**
     * Special character starting reference element.
     */
    @JvmStatic val REFERENCE_CHARACTER: IElementType = VdfElementType(name = "#")

    /**
     * Reference element.
     */
    @JvmStatic val REFERENCE: IElementType = VdfElementType(name = "REFERENCE")

    /**
     * Valid escape sequence inside string literal.
     */
    @JvmStatic val VALID_ESCAPE_TOKEN: IElementType = VdfElementType(name = "VALID_ESCAPE")

    /**
     * Invalid escape sequence inside string literal.
     */
    @JvmStatic val INVALID_ESCAPE_TOKEN: IElementType = VdfElementType(name = "INVALID_ESCAPE")
}
