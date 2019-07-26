package ast;

import ast.*;
import lexer.*;
import java.util.ArrayList;

public class BlockStatement {
  public Token token;
  public ArrayList<Statement> statements;

  public void statementNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
