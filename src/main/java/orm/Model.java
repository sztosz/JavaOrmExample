package orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Bartosz on 18.03.2016.
 */
public class Model {

    private Connection connection;
    private Class<?> objClass;

    private String table;

    private HashMap<Field, Object> fields = new HashMap<>();


    public Model(Object object) throws NoSuchFieldException, IllegalAccessException, URISyntaxException, SQLException {
        objClass = object.getClass();
        Field tableField = objClass.getDeclaredField("table");
        tableField.setAccessible(true);
        table = (String) tableField.get(object);
        Field[] fields = objClass.getFields();
        for (Field field : fields) {
            this.fields.put(field, field.getType().getSimpleName());
        }

        connection = Connector.getInstance();

    }

    public List<Object> sll() {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + table);
            ArrayList<Object> result = new ArrayList<>();
            while (rs.next()) {
                Object object = objClass.newInstance();
                this.fields.forEach((k, v) -> {
                    try {
                        Method rsMethod = rs.getClass().getMethod("get" + v, k.getType());
                        k.set(object, rsMethod.invoke(rs, k.getName()));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
                result.add(object);
            }
            return result;
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
// TODO: test it all
// TODO: add relations