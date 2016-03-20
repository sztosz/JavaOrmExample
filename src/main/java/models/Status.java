package models;

import orm.Model;


/**
 * Created by Bartosz on 18.03.2016.
 */
public class Status extends Model {

    public String name;
    public Integer id;

    public Status() {
        this.table = "statuses";
    }
}