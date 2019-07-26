package ast;

import lexer.*;
import java.util.ArrayList;

public class FunctionLiteral implements Expression {
  Token token;
  ArrayList<Identifier> parameters;
  BlockStatement Body;

  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
