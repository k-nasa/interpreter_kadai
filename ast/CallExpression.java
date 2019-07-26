package ast;

import lexer.*;
import java.util.ArrayList;

public class CallExpression implements Expression {
  Token token;
  Expression function;
  ArrayList<Expression> arguments;

  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
