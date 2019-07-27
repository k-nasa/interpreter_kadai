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

    if (node instanceof Program)
      return evalStatements(((Program) node).statements);

    if (node instanceof BlockStatement)
      return evalStatements(((BlockStatement) node).statements);

    if (node instanceof IfExpression)
      return evalIfExpression((IfExpression) node);

    if (node instanceof ExpressionStatement) {
      ExpressionStatement o = (ExpressionStatement) node;
      return Eval(o.expression);
    }

    if (node instanceof ReturnStatement) {
      ReturnStatement o = (ReturnStatement) node;
      object.Object value = Eval(o.returnValue);

      return new object.ReturnValue(value);
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

  static object.Object evalIfExpression(IfExpression ifExpression) {
    object.Object condition = Eval(ifExpression.condition);

    if (isTruthy(condition)) {
      return Eval(ifExpression.consequence);
    } else if(ifExpression != null) {
      return Eval(ifExpression.alternative);
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
        case "<":
          return new object.Boolean(leftValue < rightValue);
        case ">":
          return new object.Boolean(leftValue > rightValue);
        case "==":
          return new object.Boolean(leftValue == rightValue);
        case "!=":
          return new object.Boolean(leftValue != rightValue);
      }
    }

    switch(operator){
      case "==":
        return new object.Boolean(left == right);
      case "!=":
        return new object.Boolean(left!= right);
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

      if(result instanceof object.ReturnValue) {
        object.ReturnValue returnValue = (object.ReturnValue) result;
        return returnValue.value;
      }
    }

    return result;
  }

  static boolean isTruthy(object.Object obj) {
    if(obj == NULL) {
      return false;
    }

    if(obj.inspect() == TRUE.inspect()) {
      return true;
    }

    if(obj.inspect() == FALSE.inspect()) {
      return false;
    }

    return true;
  }

  static void debugObject(object.Object obj) {
    System.out.println("debug inspect: " + obj.inspect());
    System.out.println("debug type: " + obj.type());
  }
}
