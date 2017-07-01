package com.game.movieappNano.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sara on 6/30/2017.
 */

public class MovieDBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "movie.db";
    public static final int DB_VERSION = 1;

    public MovieDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            String query = " CREATE TABLE " + FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME
                    + " ( " +
                    FavoriteMovieContract.FavoriteMovieEntry._ID + " STRING PRIMARY KEY," +
                    FavoriteMovieContract.FavoriteMovieEntry.COLUMN_TITLE + " STRING NOT NULL ," +
                    FavoriteMovieContract.FavoriteMovieEntry.COLUMN_OVERVIEW + " STRING NOT NULL ," +
                    FavoriteMovieContract.FavoriteMovieEntry.COLUMN_IMAGE_URL + " STRING NOT NULL ," +
                    FavoriteMovieContract.FavoriteMovieEntry.COLUMN_RELEASE_DATE + " STRING NOT NULL ," +
                    FavoriteMovieContract.FavoriteMovieEntry.COLUMN_VOTE + " STRING NOT NULL );";

            db.execSQL(query);
        } catch (Exception e) {
            Log.e("db_error", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
