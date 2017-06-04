package com.game.movieappNano.models;

/**
 * Created by Sara on 6/3/2017.
 */

public class Movie {

    private String id;
    private String imgURL;
    private String originalTitle;
    private String overview;
    private String voteAverage;
    private String releaseDate;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getImgURL() {
        return imgURL;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
