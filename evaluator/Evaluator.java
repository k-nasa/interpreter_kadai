package evaluator;

import ast.*;
import object.*;
import java.util.ArrayList;

public class Evaluator {

  static object.Object NULL = new Null();
  static object.Object TRUE = new object.Boolean(true);
  static object.Object FALSE = new object.Boolean(false);

  public static object.Object Eval(Node node) {
    // node interfaceを具体型にキャストする必要がある
    // 可読性は悪いがifでどのクラス化を判定してASTの評価をする

    if (node instanceof Program) {
      Program o = (Program) node;
      return evalStatements(o.statements);
    }

    if (node instanceof ExpressionStatement) {
      ExpressionStatement o = (ExpressionStatement) node;
      return Eval(o.expression);
    }

    if (node instanceof IntegerLiteral) {
      IntegerLiteral o = (IntegerLiteral) node;
      return new IntegerObject(o.value);
    }

    if (node instanceof PrefixExpression) {
      PrefixExpression o = (PrefixExpression) node;
      object.Object right = Eval(o.right);

      return evalPrefixExpression(o.operator, right);
    }

    if (node instanceof BooleanLiteral) {
      BooleanLiteral o = (BooleanLiteral) node;
      if(o.value)
        return TRUE;
      return FALSE;
    }

    if (node instanceof InfixExpression) {
      InfixExpression o = (InfixExpression) node;
      object.Object right = Eval(o.right);
      object.Object left = Eval(o.left);

      return evalInfixExpression(o.operator, right, left);
    }

    return NULL;
  }

  static object.Object evalInfixExpression(String operator, object.Object right, object.Object left) {
    if (right instanceof object.IntegerObject && left instanceof object.IntegerObject) {
      int rightValue = ((object.IntegerObject) right).value;
      int leftValue = ((object.IntegerObject) left).value;

      switch(operator) {
        case "+":
          return new object.IntegerObject(leftValue + rightValue);
        case "-":
          return new object.IntegerObject(leftValue - rightValue);
        case "*":
          return new object.IntegerObject(leftValue * rightValue);
        case "/":
          return new object.IntegerObject(leftValue / rightValue);
      }
    }

    return NULL;

  }

  static object.Object evalPrefixExpression(String operator, object.Object right) {
    switch(operator) {
      case "!":
        return evalBangOperatorExpression(right);
      case "-":
        return evalMinusPrefixOperatorExpression(right);
      default:
        return NULL;
    }
  }

  static object.Object evalBangOperatorExpression(object.Object right) {
    if (right instanceof object.Boolean) {
      object.Boolean o = (object.Boolean) right;

      if(o.value) {
        return FALSE;
      }

      return TRUE;
    }

    if (right instanceof object.Null) {
      return TRUE;
    }

    return FALSE;
  }

  static object.Object evalMinusPrefixOperatorExpression(object.Object right) {
    if(right.type() != "INTEGER")  {
      return NULL;
    }

    object.IntegerObject o = (object.IntegerObject) right;

    return new IntegerObject(-o.value);

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
