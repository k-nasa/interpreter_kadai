package object;

public class IntegerObject implements Object {
  int value;

  public IntegerObject(int value) {
    this.value = value;
  }

  public String inspect() {
    return String.valueOf(value);
  }

  public String type() {
    return "INTEGER";
  }
}
