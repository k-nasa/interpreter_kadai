import lexer.Token;

public class Identifier implements Expression {
  Token token;
  String value;

  public String tokenLiteral() {
    return this.token.literal;
  }

  public void expressionNode() {}
}
