package android.libs.demo.sticky;

import java.io.Serializable;

public class MainBean implements Serializable {

    public String time;
    public String description;

    public MainBean(String time, String description) {
        this.time = time;
        this.description = description;
    }
}