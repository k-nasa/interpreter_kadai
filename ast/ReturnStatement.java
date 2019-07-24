package ast;

import lexer.*;

public class ReturnStatement implements Statement {
  public Token token;
  public Expression returnValue;

  public void statementNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
