package object;

public class Null implements Object {
  int value;

  public String inspect() {
    return "null";
  }

  public String type() {
    return "NULL";
  }
}
