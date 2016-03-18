package orm.fields;

/**
 * Created by Bartosz on 18.03.2016.
 */
public class StringField {

    String name;
    private String value;

    public StringField(String name){
        this.name = name;
    }

    public void set(Object object) {
        this.value = (String) object;
    }

    public String get() {
        return this.value;
    }

}
