package orm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bartosz on 18.03.2016.
 */
public class Model {

    private Class<?> modelClass;
    private HashMap<Field, String> fields = new HashMap<>();

    String table;

    public Model(Object modelObject) throws NoSuchFieldException, IllegalAccessException {
        modelClass = modelObject.getClass();
        Field tableField = modelClass.getDeclaredField("table");
        tableField.setAccessible(true);
        table = (String) tableField.get(modelObject);
        Field[] fields = modelClass.getFields();
        for (Field field : fields) {
            this.fields.put(field, getSimpleName(field));
        }
    }

    public List<Object> all() {
        return new Select(this).all(fields, modelClass);
    }

    public Object find(Integer id) {
        return new Select(this).find(id, fields, modelClass);
    }

    private String getSimpleName(Field field) {
        String name = field.getType().getSimpleName();
        switch (name) {
            case "Integer":
                name = "Int";
                break;
            default:
                break;
        }
        return name;
    }
}
// TODO: test it all
// TODO: add relations
// TODO: Make model extendable, and make all model inherit it