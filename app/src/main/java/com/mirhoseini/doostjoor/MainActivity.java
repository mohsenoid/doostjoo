package com.mirhoseini.doostjoor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.mirhoseini.doostjoor.utils.AppSettings;
import com.mirhoseini.doostjoor.utils.Constants;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    private static boolean isAfterQuickStart = false;
    ArrayList<ImageButton> colorButtons;
    private Toolbar toolbar;
    private int[] colors;
    private String[] colorsName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        colorsName = getResources().getStringArray(R.array.colors_name);

        initToolbar();
        initButtons();
        fillColors(AppSettings.getInt(this, Constants.LAST_COLOR_PACKAGE, R.array.color_package_normal));

        checkQuickStart();
    }

    private void checkQuickStart() {
        int i = AppSettings.getInt(this, Constants.DEFAULT_COLOR, 0);
        if (i != 0 && !isAfterQuickStart) {
            openBlinker(colors[i - 1], colorsName[i - 1]);
            isAfterQuickStart = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        fillColors(AppSettings.getInt(this, Constants.LAST_COLOR_PACKAGE, R.array.color_package_normal));
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);


//        toolbar.setNavigationIcon(R.drawable.fab_label_background);

//        TextView toolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
//        toolbarTitle.setTypeface(Utils.getDefaultTypeface(this));
//        toolbarTitle.setText(getString(R.string.app_name));


        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set an OnMenuItemClickListener to handle menu item clicks
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    openSettings();
                    return true;
                }

                return false;
            }
        });

        // Inflate a menu to be displayed in the toolbar
        toolbar.inflateMenu(R.menu.menu_main);
    }

    private void fillColors(int colorPackage) {
        colors = getResources().getIntArray(colorPackage);

        if (colors != null) {
            for (int i = 0; i < colorButtons.size(); i++) {
                colorButtons.get(i).setBackgroundColor(colors[i]);
                colorButtons.get(i).setTag(i);
            }
        }
    }


    private void initButtons() {
        colorButtons = new ArrayList<>();
        colorButtons.add((ImageButton) findViewById(R.id.btColor1));
        colorButtons.add((ImageButton) findViewById(R.id.btColor2));
        colorButtons.add((ImageButton) findViewById(R.id.btColor3));
        colorButtons.add((ImageButton) findViewById(R.id.btColor4));
        colorButtons.add((ImageButton) findViewById(R.id.btColor5));
        colorButtons.add((ImageButton) findViewById(R.id.btColor6));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            openSettings();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent settingIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingIntent);
    }

    @Override
    public void onClick(View v) {
        int i = (int) v.getTag();
        openBlinker(colors[i], colorsName[i]);
    }

    private void openBlinker(int selectedColor, String selectedColorName) {
        Intent blinkerIntent = new Intent(this, BlinkerActivity.class);
        blinkerIntent.putExtra(Constants.SELECTED_COLOR, selectedColor);
        blinkerIntent.putExtra(Constants.SELECTED_COLOR_NAME, selectedColorName);
        startActivity(blinkerIntent);
    }
}
