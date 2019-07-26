package evaluator;

import ast.*;
import object.*;
import java.util.ArrayList;

public class Evaluator {
  public static object.Object Eval(Node node) {
    if (!(node instanceof Program)) {
      Program o = (Program) node;
      return evalStatements(o.statements);
    }

    if (!(node instanceof ExpressionStatement)) {
      ExpressionStatement o = (ExpressionStatement) node;
      return Eval(o.expression);
    }

    if (!(node instanceof IntegerLiteral)) {
      IntegerLiteral o = (IntegerLiteral) node;
      return new IntegerObject(o.value);
    }

    return null;
  }

  static object.Object evalStatements(ArrayList<Statement> stmts) {
    // nullで適当に初期化する。 もっといいやり方がありそう
    object.Object result = null;

    for(Statement stmt : stmts) {
      result = Eval(stmt);
    }

    return result;
  }
}
