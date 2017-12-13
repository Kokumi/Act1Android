package com.debruyckere.florian.act1android.Model;

import java.net.URL;
import java.util.Date;

/**
 * Created by Debruyckère Florian on 12/12/2017.
 */

public class News {
    private String title;
    private URL link;
    private String description;
    private Date pubDate;

    public News(){    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", link=" + link +
                ", description='" + description + '\'' +
                ", pubDate=" + pubDate +
                '}';
    }
}
