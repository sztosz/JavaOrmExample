package orm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bartosz on 18.03.2016.
 */
public class Model {

    private Class<?> modelClass;

    private String table;

    private HashMap<Field, String> fields = new HashMap<>();


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

    String getTable() {
        return table;
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