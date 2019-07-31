package ast;

import lexer.*;
import java.util.ArrayList;

public class CallExpression implements Expression {
  public Token token;
  public Expression function;
  public ArrayList<Expression> arguments;

  public void expressionNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }

  public CallExpression(Token token, Expression function) {
    this.token = token;
    this.function = function;
  }
}
