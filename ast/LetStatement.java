import lexer.Token;

public class LetStatement implements Statement {
  Token token;
  Identifier name;
  Expression value;

  public String tokenLiteral() {
    return this.token.literal;
  }

  public void statementNode() {}
}

