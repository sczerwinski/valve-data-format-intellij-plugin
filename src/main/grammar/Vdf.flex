package it.czerwinski.ide.plugins.valve.lang;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

%%

%class VdfLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

%state INVALID

%%

. { yybegin(INVALID); return TokenType.BAD_CHARACTER; }
