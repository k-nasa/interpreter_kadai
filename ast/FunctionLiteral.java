package ast;

import lexer.*;
import java.util.ArrayList;

public class FunctionLiteral implements Expression {
  public Token token;
  public ArrayList<Identifier> parameters;
  public BlockStatement body;

  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
