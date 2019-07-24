package ast;

import lexer.Token;

public class LetStatement implements Statement {
  public Token token;
  public Identifier name;
  public Expression value;

  public String tokenLiteral() {
    return this.token.literal;
  }

  public void statementNode() {}
}

