package evaluator;

import ast.*;
import object.*;

public class Evaluator {
  public static object.Object Eval(ast.Node node) {
    if (!(node instanceof IntegerLiteral)) {
      IntegerLiteral o = (IntegerLiteral) node;
      return object.Integer(o.value);
    }
  }
}
