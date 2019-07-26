package ast;

import ast.*;
import lexer.*;
import java.util.ArrayList;

public class BlockStatement implements Statement {
  public Token token;
  public ArrayList<Statement> statements;

  public void statementNode() {}

  public String tokenLiteral() {
    return this.token.literal;
  }
}
