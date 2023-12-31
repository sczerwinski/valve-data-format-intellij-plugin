/*
 * Copyright 2020-2023 Slawomir Czerwinski
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

import com.intellij.codeInsight.generation.actions.CommentByLineCommentAction
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.psi.codeStyle.CodeStyleManager
import com.intellij.testFramework.fixtures.BasePlatformTestCase
import com.intellij.util.containers.ContainerUtil

class VdfCodeInsightTest : BasePlatformTestCase() {

    override fun getBasePath(): String = "src/test/testData"

    override fun setUp() {
        super.setUp()
        myFixture.testDataPath = basePath
    }

    fun testAnnotator() {
        myFixture.configureByFiles("AnnotatorTestData.vdf")
        myFixture.checkHighlighting(true, true, true)
    }

    fun testFormatter() {
        myFixture.configureByFile("FormatterTestDataBefore.vdf")
        WriteCommandAction.writeCommandAction(project).run<RuntimeException> {
            CodeStyleManager.getInstance(project)
                .reformatText(myFixture.file, ContainerUtil.newArrayList(myFixture.file.textRange))
        }
        myFixture.checkResultByFile("FormatterTestDataExpected.vdf")
    }

    fun testFolding() {
        myFixture.testFolding("$basePath/FoldingTestData.vdf")
    }

    fun testCommenter() {
        myFixture.configureByText(VdfFileType, "<caret>\"object\" {}")
        val commentAction = CommentByLineCommentAction()
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("//\"object\" {}")
        commentAction.actionPerformedImpl(project, myFixture.editor)
        myFixture.checkResult("\"object\" {}")
    }
}
