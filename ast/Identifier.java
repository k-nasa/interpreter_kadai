package ast;

import lexer.Token;

public class Identifier implements Expression {
  public Token token;
  public String value;

  public Identifier(Token token, String literal) {
    this.token = token;
    this.value = literal;
  }

  public String tokenLiteral() {
    return this.token.literal;
  }

  public void expressionNode() {}
}
