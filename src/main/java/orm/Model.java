package orm;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bartosz on 18.03.2016.
 */
public abstract class Model {

    private HashMap<Field, String> fields = new HashMap<>();

    protected String table;

    protected Model() {
        Field[] fields = this.getClass().getFields();
        for (Field field : fields) {
            this.fields.put(field, getSimpleName(field));
        }
    }

    public List<Object> all() {
        return new Select(this).all(fields, this.getClass());
    }

    public Object find(Integer id) {
        return new Select(this).find(id, fields, this.getClass());
    }

    public List<Object> where(HashMap<String, String> conditions) {
        return new Select(this).where(conditions, fields, this.getClass());
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

    String getTable() {
        return this.table;
    }
}
// TODO: test it all
// TODO: add relations
