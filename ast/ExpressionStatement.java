package ast;

import lexer.*;

public class ExpressionStatement implements Statement {
  public Token token;
  public Expression expression;

  public void statementNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
