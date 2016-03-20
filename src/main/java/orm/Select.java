package orm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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

    List<Object> all(HashMap<Field, String> fields, Class<? extends Model> modelClass) {
        try {
            Statement st = Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + model.getTable());
            return resultSetToArrayList(fields, modelClass, rs);
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    List<Object> where(HashMap<String, String> conditions, HashMap<Field, String> fields, Class<? extends Model> modelClass) {
        try {
            Statement st = Connector.getInstance().createStatement();
            List<String> where = new ArrayList<>();
            conditions.forEach((k, v) -> where.add(k + "='" + v + "'"));
            ResultSet rs = st.executeQuery("SELECT * FROM " + model.getTable() + " WHERE " + String.join(" AND ", where));
            return resultSetToArrayList(fields, modelClass, rs);
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    Object find(Integer id, HashMap<Field, String> fields, Class<? extends Model> modelClass) {
        try {
            Statement st = Connector.getInstance().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM " + model.getTable() + " WHERE id=" + id + " LIMIT 1");
            if (rs.next()) {
                Object modelObject = modelClass.newInstance();
                resultToModelObject(fields, rs, modelObject);
                return modelObject;
            } else {
                return null;
            }
        } catch (SQLException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<Object> resultSetToArrayList(HashMap<Field, String> fields, Class<? extends Model> modelClass, ResultSet rs) throws SQLException, InstantiationException, IllegalAccessException {
        ArrayList<Object> result = new ArrayList<>();
        while (rs.next()) {
            Object modelObject = modelClass.newInstance();
            resultToModelObject(fields, rs, modelObject);
            result.add(modelObject);
        }
        return result;
    }

    private void resultToModelObject(HashMap<Field, String> fields, ResultSet rs, Object modelObject) {
        fields.forEach((k, v) -> {
            try {
                Method rsMethod = rs.getClass().getMethod("get" + v, String.class);
                k.set(modelObject, rsMethod.invoke(rs, k.getName()));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}