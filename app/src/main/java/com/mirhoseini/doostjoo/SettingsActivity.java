package com.mirhoseini.doostjoo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.gms.analytics.Tracker;
import com.mirhoseini.doostjoo.utils.AppSettings;
import com.mirhoseini.doostjoo.utils.Constants;

public class SettingsActivity extends ActionBarActivity {

    private Toolbar toolbar;
    private Spinner spPackageColors, spQuickStart;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

//        // Get a Tracker (should auto-report)
//        AnalyticsApplication application = (AnalyticsApplication) getApplication();
//        mTracker = application.getTracker();

        initToolbar();
        initForm();
        loadSettings();
    }

    private void initForm() {
        spPackageColors = (Spinner) findViewById(R.id.spPackageColors);
        spPackageColors.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case (0):
                        AppSettings.setValue(getApplicationContext(), Constants.LAST_COLOR_PACKAGE, R.array.color_package_normal);
                        break;
                    case (1):
                        AppSettings.setValue(getApplicationContext(), Constants.LAST_COLOR_PACKAGE, R.array.color_package_dark);
                        break;
                    case (2):
                        AppSettings.setValue(getApplicationContext(), Constants.LAST_COLOR_PACKAGE, R.array.color_package_neon);
                        break;
                    case (3):
                        AppSettings.setValue(getApplicationContext(), Constants.LAST_COLOR_PACKAGE, R.array.color_package_pastel);
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AppSettings.clearValue(getApplicationContext(), Constants.LAST_COLOR_PACKAGE);
            }
        });

        spQuickStart = (Spinner) findViewById(R.id.spQuickStart);
        spQuickStart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AppSettings.setValue(getApplicationContext(), Constants.DEFAULT_COLOR, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AppSettings.clearValue(getApplicationContext(), Constants.DEFAULT_COLOR);
            }
        });
    }

    private void loadSettings() {
        spQuickStart.setSelection(AppSettings.getInt(getApplicationContext(), Constants.DEFAULT_COLOR, 0));

        switch (AppSettings.getInt(getApplicationContext(), Constants.LAST_COLOR_PACKAGE, R.array.color_package_normal)) {
            case R.array.color_package_normal:
                spPackageColors.setSelection(0);
                break;
            case R.array.color_package_dark:
                spPackageColors.setSelection(1);
                break;
            case R.array.color_package_neon:
                spPackageColors.setSelection(2);
                break;
            case R.array.color_package_pastel:
                spPackageColors.setSelection(3);
                break;
            default:
                spPackageColors.setSelection(0);
                break;
        }
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        //Get an Analytics tracker to report app starts and uncaught exceptions etc.
//        GoogleAnalytics.getInstance(this).reportActivityStart(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//
//        //Stop the analytics tracking
//        GoogleAnalytics.getInstance(this).reportActivityStop(this);
//    }
}
