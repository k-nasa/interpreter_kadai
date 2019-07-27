package object;

import java.util.HashMap;

public class Enviroment {
  public HashMap<String, object.Object> store;

  public Enviroment() {
    this.store = new HashMap<String, object.Object>();
  }

  public object.Object get(String name) {
    return store.get(name);
  }

  public object.Object set(String name, object.Object obj) {
    store.put(name, obj);

    return obj;
  }
}
