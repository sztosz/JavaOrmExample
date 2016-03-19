package models;

import orm.Model;

import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Bartosz on 18.03.2016.
 */
public class Status {

    private String table = "statuses";
    private Model model;
    public String name;
    public Integer id;

    public Status() throws NoSuchFieldException, IllegalAccessException, URISyntaxException, SQLException {
        model = new Model(this);
    }

    public List all() {
        return model.all();
    }
}