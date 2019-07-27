package object;

import java.util.HashMap;

public class Enviroment {
  public HashMap<String, object.Object> store;
  public object.Enviroment outer;

  public Enviroment() {
    this.store = new HashMap<String, object.Object>();
  }

  public object.Object get(String name) {
    object.Object obj = store.get(name);
    if(obj == null && outer != null) {
      return outer.get(name);
    }

    return obj;
  }

  public static object.Enviroment newEnclosedEnviroment(object.Enviroment outer) {
    object.Enviroment env = new Enviroment();
    env.outer = outer;
    return env;
  }

  public object.Object set(String name, object.Object obj) {
    store.put(name, obj);

    return obj;
  }
}
