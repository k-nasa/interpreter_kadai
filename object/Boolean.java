package object;

public class Boolean implements Object {
  boolean value;

  public String inspect() {
    return String.valueOf(value);
  }

  public String type() {
    return "BOOLEAN";
  }
}
