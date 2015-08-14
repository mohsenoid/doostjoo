package com.mirhoseini.doostjoo;

/**
 * Created by Mohsen on 8/14/15.
 */

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 */
public class AnalyticsApplication extends Application {
    //Logging TAG
    private static final String TAG = "AnalyticsApplication";

    public static int GENERAL_TRACKER = 0;
    private Tracker mTracker;

    public AnalyticsApplication() {
        super();
    }

    synchronized Tracker getTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(R.xml.analytics_app_tracker);
        }
        return mTracker;
    }
}