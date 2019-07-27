package object;

import java.util.ArrayList;
import ast.*;

public class Function implements object.Object {
  public ArrayList<Identifier> parameters;
  public BlockStatement body;
  public Enviroment env;

  public Function(ArrayList<Identifier> params, object.Enviroment env, BlockStatement body) {
    this.parameters = params;
    this.env = env;
    this.body = body;
  }

  public String type() {
    return "FUNCTION";
  }

  public String inspect() {
    String str = "";

    for(Identifier ident : parameters) {
      str += ident.value + ",";
    }

    return String.format("fn(%s) {\n\t%s\n}", str, "function bodyの表示したいけど面倒なので未実装");
  }
}
