package object;

public class Error implements Object {
  public String message;

  public String inspect() {
    return "ERROR: " + this.message;
  }

  public Error(String msg)  {
    this.message = msg;
  }

  public String type() {
    return "ERROR";
  }
}
