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

import com.intellij.lexer.LexerBase
import com.intellij.psi.tree.IElementType
import it.czerwinski.ide.plugins.valve.lang.psi.VdfStringLiteralTypes
import it.czerwinski.ide.plugins.valve.lang.psi.VdfTypes

/**
 * String literal lexer for Valve Data Format files.
 */
class VdfStringLiteralLexer : LexerBase() {

    private var myBuffer: CharSequence = ""
    private var myBufferEnd: Int = 0

    private val myStringEnd: Int
        get() = if (myBufferEnd > 0 && myBuffer[myBufferEnd - 1] == QUOTATION_CHARACTER) {
            myBufferEnd - 1
        } else {
            myBufferEnd
        }

    private var myStart: Int = 0
    private var myEnd: Int = 0

    private var myState: Int = INITIAL_STATE
    private var myNextState: Int = INITIAL_STATE

    override fun start(buffer: CharSequence, startOffset: Int, endOffset: Int, initialState: Int) {
        myBuffer = buffer
        myBufferEnd = endOffset

        myState = initialState
        myStart = startOffset
        myEnd = findFirstTokenIndex()
    }

    private fun findFirstTokenIndex(): Int {
        val referenceIndex = myStart + 1
        return if (myBuffer.length > referenceIndex && myBuffer[referenceIndex] == REFERENCE_CHARACTER) {
            myNextState = AT_REFERENCE_CHARACTER
            referenceIndex
        } else {
            findNextEscapeIndex()
        }
    }

    private fun findNextEscapeIndex(): Int {
        val escapeIndex = myBuffer.indexOf(ESCAPE_CHARACTER, myStart)
        return if (escapeIndex < myStart || escapeIndex >= myBufferEnd) {
            myNextState = AFTER_END_QUOTE
            myBufferEnd
        } else {
            myNextState = AT_ESCAPE_SEQUENCE
            escapeIndex
        }
    }

    override fun getState(): Int =
        myState

    override fun getTokenType(): IElementType? {
        if (myStart >= myEnd) {
            return null
        }

        return when (myState) {
            AT_REFERENCE_CHARACTER ->
                VdfStringLiteralTypes.REFERENCE_CHARACTER

            AFTER_REFERENCE_CHARACTER ->
                VdfStringLiteralTypes.REFERENCE

            AT_ESCAPE_SEQUENCE ->
                if (myBufferEnd - myStart > 1 && myBuffer[myStart + 1] in ESCAPABLE_CHARACTERS) {
                    VdfStringLiteralTypes.VALID_ESCAPE_TOKEN
                } else {
                    VdfStringLiteralTypes.INVALID_ESCAPE_TOKEN
                }

            else ->
                VdfTypes.STRING
        }
    }

    override fun getTokenStart(): Int =
        myStart

    override fun getTokenEnd(): Int =
        myEnd

    override fun advance() {
        myState = myNextState
        myStart = myEnd

        when (myState) {
            AT_REFERENCE_CHARACTER -> {
                myEnd = myStart + 1
                myNextState = if (myEnd < myStringEnd) AFTER_REFERENCE_CHARACTER else AFTER_START_QUOTE
            }
            AFTER_REFERENCE_CHARACTER -> {
                myNextState = AFTER_START_QUOTE
                myEnd = myStringEnd
            }
            AT_ESCAPE_SEQUENCE -> {
                myEnd = myStart + 2
                myNextState = if (myBuffer.indexOf(ESCAPE_CHARACTER, startIndex = myEnd) == myEnd) {
                    AT_ESCAPE_SEQUENCE
                } else {
                    AFTER_START_QUOTE
                }
            }
            else -> {
                myEnd = findNextEscapeIndex()
            }
        }

        if (myEnd >= myBufferEnd) {
            myNextState = AFTER_END_QUOTE
        }
    }

    override fun getBufferSequence(): CharSequence =
        myBuffer

    override fun getBufferEnd(): Int =
        myBufferEnd

    companion object {
        private const val QUOTATION_CHARACTER = '"'
        private const val REFERENCE_CHARACTER = '#'
        private const val ESCAPE_CHARACTER = '\\'
        private const val ESCAPABLE_CHARACTERS = "nt\\\""

        private const val INITIAL_STATE = 0
        private const val AFTER_START_QUOTE = 1
        private const val AT_REFERENCE_CHARACTER = 2
        private const val AFTER_REFERENCE_CHARACTER = 3
        private const val AT_ESCAPE_SEQUENCE = 4
        private const val AFTER_END_QUOTE = 5
    }
}
