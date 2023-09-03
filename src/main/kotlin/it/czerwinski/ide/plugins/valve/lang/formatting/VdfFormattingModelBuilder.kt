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

package it.czerwinski.ide.plugins.valve.lang.formatting

import com.intellij.formatting.FormattingContext
import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.formatting.FormattingModelProvider
import com.intellij.formatting.Indent
import com.intellij.formatting.SpacingBuilder
import com.intellij.psi.codeStyle.CodeStyleSettings
import it.czerwinski.ide.plugins.valve.lang.VdfLanguage
import it.czerwinski.ide.plugins.valve.lang.psi.VdfTypes

/**
 * Formatting model builder for Valve Data Format files.
 */
class VdfFormattingModelBuilder : FormattingModelBuilder {

    /**
     * Creates formatting model for Valve Data Format files.
     */
    override fun createModel(formattingContext: FormattingContext): FormattingModel {
        val settings = formattingContext.codeStyleSettings
        val spacingBuilder = createSpacingBuilder(settings)
        val block = VdfBlock(
            node = formattingContext.node,
            indent = Indent.getSmartIndent(Indent.Type.NORMAL),
            spacingBuilder = spacingBuilder
        )
        return FormattingModelProvider
            .createFormattingModelForPsiFile(formattingContext.containingFile, block, settings)
    }

    private fun createSpacingBuilder(settings: CodeStyleSettings): SpacingBuilder =
        SpacingBuilder(settings, VdfLanguage)
            .between(VdfTypes.KEY, VdfTypes.LITERAL).spacing(1, 1, 0, false, 0)
            .between(VdfTypes.KEY, VdfTypes.OBJECT).lineBreakInCode()
            .between(VdfTypes.LBRACE, VdfTypes.PROPERTY).lineBreakInCode()
            .between(VdfTypes.PROPERTY, VdfTypes.RBRACE).lineBreakInCode()
            .between(VdfTypes.PROPERTY, VdfTypes.PROPERTY).lineBreakInCode()
            .between(VdfTypes.LBRACE, VdfTypes.RBRACE).lineBreakInCode()
            .after(VdfTypes.RBRACE).lineBreakInCode()
}
