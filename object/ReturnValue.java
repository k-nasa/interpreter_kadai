package object;

public class ReturnValue implements object.Object {
  public object.Object value;

  public String inspect() {
    return value.inspect();
  }

  public String type() {
    return "RETURN_VALUE";
  }

  public ReturnValue(Object o)  {
    this.value = o;
  }

}
