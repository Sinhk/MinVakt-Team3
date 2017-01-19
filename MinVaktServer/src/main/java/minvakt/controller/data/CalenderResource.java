package minvakt.controller.data;

import java.io.Serializable;

/**
 * MinVakt-Team3
 * 19.01.2017
 */
public class CalenderResource implements Serializable {
    private int id;
    private String title;

    public CalenderResource(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
