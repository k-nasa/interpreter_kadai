package ast;

import lexer.*;

public class InfixExpression implements Expression {
  public Token token;
  public Expression left;
  public Expression right;
  public String operator;


  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
