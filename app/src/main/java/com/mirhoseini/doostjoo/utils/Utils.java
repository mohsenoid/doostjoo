package com.mirhoseini.doostjoo.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Mohsen on 8/13/15.
 */
public class Utils {

    public static Typeface getDefaultTypeface(Context context) {
        return Typeface.createFromAsset(context.getResources().getAssets(),
                Constants.FONT_NAME);
    }
}
