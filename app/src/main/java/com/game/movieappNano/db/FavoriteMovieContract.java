package com.game.movieappNano.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Sara on 6/30/2017.
 */

public final class FavoriteMovieContract {

    public static final String AUTHORITY = "com.game.movieappNano";
    public static final String BASE_URI = "content://" + AUTHORITY;


    public static final class FavoriteMovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "favoriteMovie";

        public static final Uri FAVORITE_URI = Uri.parse(BASE_URI).buildUpon()
                .appendPath(TABLE_NAME).build();

        public static final String COLUMN_IMAGE_URL = "imgUrl";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_VOTE = "vote";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
    }
}
