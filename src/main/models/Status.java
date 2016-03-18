package main.models;

import orm.Model;
import orm.fields.StringField;
import orm.fields.TableName;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Bartosz on 18.03.2016.
 */
public class Status {

    TableName tableName = new TableName("statuses");

    public StringField nameField = new StringField("name");

    private Model model;

    private String name;

    public Status() throws NoSuchFieldException, IllegalAccessException, URISyntaxException, SQLException {
        model = new Model(this);
    }

    public List getAll() {
        return model.get();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
