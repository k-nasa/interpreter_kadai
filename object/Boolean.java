package object;

public class Boolean implements Object {
  public boolean value;

  public String inspect() {
    return String.valueOf(value);
  }

  public Boolean(boolean value)  {
    this.value = value;
  }

  public String type() {
    return "BOOLEAN";
  }
}
