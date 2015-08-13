package com.mirhoseini.doostjoor;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mirhoseini.doostjoor.utils.AppSettings;
import com.mirhoseini.doostjoor.utils.Constants;

import net.i2p.android.ext.floatingactionbutton.FloatingActionButton;
import net.i2p.android.ext.floatingactionbutton.FloatingActionsMenu;


public class BlinkerActivity extends ActionBarActivity implements View.OnClickListener {
    int currentColor;
    String currentColorName;
    private Toolbar toolbar;
    private View blinker;
    private boolean blinkState = false;
    private FloatingActionsMenu sendFloatingActionsMenu;
    private FloatingActionButton actionSendSMS;
    private FloatingActionButton actionShare;
    private String colorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blinker);

        currentColor = getIntent().getIntExtra(
                Constants.SELECTED_COLOR,
                AppSettings.getInt(
                        this,
                        Constants.DEFAULT_COLOR,
                        R.color.package_normal_color1));

        currentColorName = getIntent().getStringExtra(
                Constants.SELECTED_COLOR_NAME);
        if (currentColorName == null)
            currentColorName = AppSettings.getString(
                    this,
                    Constants.DEFAULT_COLOR_NAME
                    , getResources().getStringArray(R.array.colors_name)[0]);

        colorMessage = String.format(getString(R.string.share_string), currentColorName);

        initToolbar();
        initFAB();
        initBlinker();
    }

    private void initFAB() {
        sendFloatingActionsMenu = (FloatingActionsMenu) findViewById(R.id.sendFloatingActionsMenu);
        actionSendSMS = (FloatingActionButton) findViewById(R.id.actionSendSMS);
        actionSendSMS.setOnClickListener(this);
        actionShare = (FloatingActionButton) findViewById(R.id.actionShare);
        actionShare.setOnClickListener(this);
    }

    private void initBlinker() {
        blinker = findViewById(R.id.blinker);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (blinkState) {
                                blinker.setBackgroundColor(Color.BLACK);
                                toolbar.setBackgroundColor(Color.BLACK);
                            } else {
                                blinker.setBackgroundColor(currentColor);
                                toolbar.setBackgroundColor(currentColor);
                            }
                        }
                    });

                    blinkState = !blinkState;

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();


    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actionSendSMS:
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"));
                sendIntent.putExtra("sms_body", colorMessage);
                startActivity(sendIntent);
                break;
            case R.id.actionShare:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, colorMessage);
                startActivity(Intent.createChooser(shareIntent, getResources().getString(R.string.share_using)));
                break;
        }
    }

}
