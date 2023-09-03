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

package it.czerwinski.ide.plugins.valve.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import static com.intellij.psi.TokenType.BAD_CHARACTER;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static it.czerwinski.ide.plugins.valve.lang.psi.VdfTypes.*;

%%

%class VdfLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%unicode
%eof{  return;
%eof}

WHITE_SPACE=\s+
COMMENT="//".*
STRING=\"([^\\\"\r\n]|\\[^\r\n])*\"?

%%

<YYINITIAL> {
  {WHITE_SPACE} { return WHITE_SPACE; }
  {COMMENT}     { return COMMENT; }
  {STRING}      { return STRING; }
  "{"           { return LBRACE; }
  "}"           { return RBRACE; }
}

.               { return BAD_CHARACTER; }
