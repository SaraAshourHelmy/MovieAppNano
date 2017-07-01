package com.game.movieappNano.models;

/**
 * Created by Sara on 6/30/2017.
 */

public class Review {

    private String id;
    private String author;
    private String content;

    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
