package orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Select {
    private final Model model;

    Select(Model model) {
        this.model = model;
    }

    List<Object> all(HashMap<Field, String> fields, Class<?> modelClass) {
        try {
            Statement st = Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + model.getTable());
            ArrayList<Object> result = new ArrayList<>();
            while (rs.next()) {
                Object modelObject = modelClass.newInstance();
                fields.forEach((k, v) -> {
                    try {
                        Method rsMethod = rs.getClass().getMethod("get" + v, String.class);
                        k.set(modelObject, rsMethod.invoke(rs, k.getName()));
                    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
                result.add(modelObject);
            }
            return result;
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}