package ast;

import lexer.*;

public class PrefixExpression implements Expression {
  public Token token;
  public String operator;
  public Expression right;

  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
