package uth.raad.raad_news;

/**
 * Created by AssassinM on 7/2/2017.
 */

public class NewsAdapter {
    private String title;
    private String description;
    private String image;
    private String date;

    public NewsAdapter(){

    }

    public NewsAdapter(String description, String image, String title) {
        this.description = description;
        this.image = image;
        this.title = title;
        this.date = date;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String dsc) {
        this.description = dsc;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
