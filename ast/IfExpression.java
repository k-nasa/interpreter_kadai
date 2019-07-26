package ast;

import lexer.*;

public class IfExpression implements Expression {
  public Token token;
  public Expression condition;
  public BlockStatement consequence;
  public BlockStatement alternative;

  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
