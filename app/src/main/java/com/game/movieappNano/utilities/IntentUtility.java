package com.game.movieappNano.utilities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.game.movieappNano.models.Movie;

/**
 * Created by Sara on 6/30/2017.
 */

public class IntentUtility {

    public static void openVideo(Activity activity, String key) {
        Uri uri = Uri.parse("http://www.youtube.com/watch").buildUpon()
                .appendQueryParameter("v", key).build();
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(activity.getPackageManager()) != null)
            activity.startActivity(intent);
    }


}
