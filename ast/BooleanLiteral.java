package ast;

import lexer.*;

public class BooleanLiteral implements Expression {
  public Token token;
  public boolean value;

  public BooleanLiteral(Token token, boolean value) {
    this.token = token;
    this.value = value;
  }

  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
