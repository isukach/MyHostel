package by.bsuir.suite.page.person.option;

import java.io.Serializable;

/**
 * @author i.sukach
 */
public class SelectOption implements Serializable {
    private String key;
    private String value;
    
    public SelectOption(String key, String value){
        this.key = key;
        this.value = value;
    }

    public SelectOption(){}

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
