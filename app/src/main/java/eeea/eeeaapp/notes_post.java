package eeea.eeeaapp;

/**
 * Created by Soham on 01-02-2017.
 */

public class notes_post {

    private String title, desc, image;

    public notes_post(){}

    public notes_post(String title, String desc, String image)
    {
        this.title=title;
        this.desc=desc;
        this.image=image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
