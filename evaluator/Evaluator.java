package evaluator;

import ast.*;
import object.*;
import java.util.ArrayList;

public class Evaluator {
  static object.Object NULL = new Null();
  static object.Object TRUE = new object.Boolean(true);
  static object.Object FALSE = new object.Boolean(false);

  public static object.Object Eval(Node node, object.Enviroment env) {
    // node interfaceを具体型にキャストする必要がある
    // 可読性は悪いがifでどのクラス化を判定してASTの評価をする

    if (node instanceof Program)
      return evalProgram(((Program) node), env);

    if (node instanceof BlockStatement)
      return evalBlockStatemenet(((BlockStatement) node), env);

    if (node instanceof IfExpression)
      return evalIfExpression((IfExpression) node, env);

    if (node instanceof ExpressionStatement) {
      ExpressionStatement o = (ExpressionStatement) node;
      return Eval(o.expression, env);
    }

    if (node instanceof ReturnStatement) {
      ReturnStatement o = (ReturnStatement) node;
      object.Object value = Eval(o.returnValue, env);
      if(isError(value)) {
        return value;
      }

      return new object.ReturnValue(value);
    }

    if (node instanceof Identifier) {
      Identifier o = (Identifier) node;
      return evalIdentifier(o, env);
    }

    if (node instanceof CallExpression) {
      CallExpression o = (CallExpression) node;
      object.Object function = Eval(o.function, env);

      if(isError(function))
        return function;

      ArrayList<object.Object> args = evalExpressions(o.arguments, env);

      if(args.size() == 1 && isError(args.get(0)))
        return args.get(0);
    }

    if (node instanceof FunctionLiteral) {
      FunctionLiteral o = (FunctionLiteral) node;

      ArrayList<Identifier> params = o.parameters;
      BlockStatement body = o.body;

      return new object.Function(params, env, body);
    }

    if (node instanceof IntegerLiteral) {
      IntegerLiteral o = (IntegerLiteral) node;
      return new IntegerObject(o.value);
    }

    if (node instanceof LetStatement) {
      LetStatement ls = (LetStatement) node;

      object.Object value = Eval(ls.value, env);

      if(isError(value))
        return value;

      env.set(ls.name.value, value);
    }

    if (node instanceof PrefixExpression) {
      PrefixExpression o = (PrefixExpression) node;
      object.Object right = Eval(o.right, env);
      if(isError(right)) {
        return right;
      }

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
      object.Object right = Eval(o.right, env);
      if(isError(right)) {
        return right;
      }
      object.Object left = Eval(o.left, env);
      if(isError(left)) {
        return left;
      }

      return evalInfixExpression(o.operator, right, left);
    }

    if (node instanceof CallExpression) {
      CallExpression o = (CallExpression) node;
      object.Object function = Eval(o.function, env);

      if(isError(function)){
        return function;
      }

      ArrayList<object.Object> args = evalExpressions(o.arguments, env);
      if(args.size() == 1 && isError(args.get(0))) {
        return args.get(0);
      }

      return applyFunction(function, args);
    }

    return NULL;
  }

  static object.Object applyFunction(object.Object fn, ArrayList<object.Object> args) {
    object.Function function = (object.Function) fn;
    object.Enviroment extendedEnv = extendFunctionEnv(function, args);
    object.Object evaluated = Eval(function.body, extendedEnv);

    return unwrapReturnValue(evaluated);
  }

  static object.Object evalProgram(Program program, object.Enviroment env) {
    object.Object result = null;

    for(Statement stmt : program.statements) {
      result = Eval(stmt, env);

      if(result instanceof object.ReturnValue) {
        object.ReturnValue returnValue = (object.ReturnValue) result;
        return returnValue.value;
      }

      if(result instanceof object.Error) {
        return result;
      }
    }

    return result;
  }

  static ArrayList<object.Object> evalExpressions(ArrayList<Expression> exps, object.Enviroment env) {
    ArrayList<object.Object> result = new ArrayList<object.Object>();

    for(Expression e : exps) {
      object.Object evaluated = Eval(e, env);

      if(isError(evaluated)) {
        result.add(evaluated);
        return result;
      }

      result.add(evaluated);
    }

    return result;
  }

  static object.Object evalIdentifier(Identifier node, object.Enviroment env) {
    object.Object val = env.get(node.value);

    if(val == null) {
      return new object.Error("identifier not found: " + node.value);
    }

    return val;
  }

  static object.Object evalBlockStatemenet(BlockStatement block, object.Enviroment env) {
    object.Object result = null;

    for(Statement stmt : block.statements) {
      result = Eval(stmt, env);

      String type = result.type();
      if(type == "RETURN_VALUE" || type == "ERROR") {
        return result;
      }
    }

    return result;
  }

  static object.Object evalIfExpression(IfExpression ifExpression, object.Enviroment env) {
    object.Object condition = Eval(ifExpression.condition, env);
      if(isError(condition)) {
        return condition;
      }

    if (isTruthy(condition)) {
      return Eval(ifExpression.consequence, env);
    } else if(ifExpression != null) {
      return Eval(ifExpression.alternative, env);
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

    if(left.type() != right.type()) {
      return new object.Error(String.format("type mismatch: %s %s %s", left.type(), operator, right.type()));
    }

    return new object.Error(String.format("unkown operator: %s %s %s", left.type(), operator, right.type()));
  }

  static object.Object evalPrefixExpression(String operator, object.Object right) {
    switch(operator) {
      case "!":
        return evalBangOperatorExpression(right);
      case "-":
        return evalMinusPrefixOperatorExpression(right);
      default:
        return new object.Error(String.format("unkown operator: %s%s", operator, right.type()));
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
      return new object.Error(String.format("unkown operator: %s%s", "-", right.type()));
    }

    object.IntegerObject o = (object.IntegerObject) right;

    return new IntegerObject(-o.value);

  }

  static object.Object evalStatements(ArrayList<Statement> stmts, object.Enviroment env) {
    // nullで適当に初期化する。 もっといいやり方がありそう
    object.Object result = null;

    for(Statement stmt : stmts) {
      result = Eval(stmt, env);

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

  static boolean isError(object.Object obj) {
    return obj instanceof object.Error;
  }

  static object.Enviroment extendFunctionEnv(object.Function fn, ArrayList<object.Object> args) {
    object.Enviroment env = object.Enviroment.newEnclosedEnviroment(fn.env);

    for(int i =0; i < args.size(); i++) {
      env.set(fn.parameters.get(i).value, args.get(i));
    }

    return env;
  }

  static object.Object unwrapReturnValue(object.Object obj) {
    if(obj instanceof object.ReturnValue) {
      object.ReturnValue o = (object.ReturnValue) obj;
      return o;
    }

    return obj;
  }
}
