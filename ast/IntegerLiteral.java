package ast;

import lexer.*;

public class IntegerLiteral implements Expression {
  public Token token;
  public int value;

  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
