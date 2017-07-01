package com.game.movieappNano.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;


/**
 * Created by Sara on 6/30/2017.
 */

public class FavoriteProvider extends ContentProvider {

    MovieDBHelper helper;
    public static final int FAVORITE_DIRECTORY_CODE = 100;
    public static final int FAVORITE_SINGLE_CODE = 101;

    public UriMatcher getMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        // FOR directory or add
        matcher.addURI(FavoriteMovieContract.AUTHORITY,
                FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                FAVORITE_DIRECTORY_CODE);

        // for  remove
        matcher.addURI(FavoriteMovieContract.AUTHORITY,
                FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME + "/*", FAVORITE_SINGLE_CODE);
        return matcher;
    }

    @Override
    public boolean onCreate() {

        helper = new MovieDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = helper.getReadableDatabase();
        int match = getMatcher().match(uri);
        Cursor cursor;
        switch (match) {
            case FAVORITE_DIRECTORY_CODE:
                cursor = db.query
                        (FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                                null, selection, null, null, null, null);
                if (cursor.getCount() < 0)
                    throw new SQLException("error in retrieve this uri " + uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown this uri : " + uri.toString());


        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int match = getMatcher().match(uri);
        Uri result;
        switch (match) {
            case FAVORITE_DIRECTORY_CODE:
                long id = db.insert(FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                        null, values);
                if (id > 0) {
                    result = ContentUris.withAppendedId(FavoriteMovieContract.FavoriteMovieEntry.FAVORITE_URI,
                            id);
                } else {
                    Log.e("insert_error", "error");
                    throw new SQLException("error in  this uri " + uri);

                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown this uri : " + uri.toString());
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = helper.getWritableDatabase();
        int match = getMatcher().match(uri);
        int rowNo = 0;
        switch (match) {
            case FAVORITE_SINGLE_CODE:
                String id = uri.getPathSegments().get(1);
                rowNo = db.delete(FavoriteMovieContract.FavoriteMovieEntry.TABLE_NAME,
                        FavoriteMovieContract.FavoriteMovieEntry._ID + " = " + id, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown this uri : " + uri.toString());

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowNo;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
