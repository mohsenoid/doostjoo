package com.mirhoseini.doostjoo;

/**
 * Created by Mohsen on 8/14/15.
 */

import android.app.Application;

import com.google.android.gms.analytics.ExceptionReporter;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * This is a subclass of {@link Application} used to provide shared objects for this app, such as
 * the {@link Tracker}.
 */
public class AnalyticsApplication extends Application {
    public static GoogleAnalytics analytics;
    public static Tracker tracker;


    public AnalyticsApplication() {
        super();
    }

    synchronized Tracker getTracker() {
        if (tracker == null) {
            analytics = GoogleAnalytics.getInstance(this);

            tracker = analytics.newTracker(R.xml.analytics_global_config); // Replace with actual tracker/property Id
            tracker.enableAdvertisingIdCollection(true);

            Thread.UncaughtExceptionHandler myHandler = new ExceptionReporter(
                    tracker,                                        // Currently used Tracker.
                    Thread.getDefaultUncaughtExceptionHandler(),      // Current default uncaught exception handler.
                    getApplicationContext());                                         // Context of the application.

            // Make myHandler the new default uncaught exception handler.
            Thread.setDefaultUncaughtExceptionHandler(myHandler);
        }
        return tracker;
    }
}