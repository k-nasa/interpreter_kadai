public class Program implements Node {
  Statement[] statements;

  public String tokenLiteral() {
    if(this.statements.length > 0) {
      return this.statements[0].tokenLiteral();
    }

    return "";
  }
}
