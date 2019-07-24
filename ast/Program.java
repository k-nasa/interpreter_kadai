package ast;

import java.util.ArrayList;

public class Program implements Node {
  public ArrayList<Statement> statements;

  public String tokenLiteral() {
    if(this.statements.size() > 0) {
      return this.statements.get(0).tokenLiteral();
    }

    return "";
  }
}
