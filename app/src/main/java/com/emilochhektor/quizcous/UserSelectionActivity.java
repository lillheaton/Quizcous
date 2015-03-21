package com.emilochhektor.quizcous;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.NumberPicker;

import com.emilochhektor.quizcous.cast.ChromecastConnectionHandler;
import com.emilochhektor.quizcous.cast.IChromecastUser;
import com.emilochhektor.quizcous.util.QuizcousColors;

import org.json.JSONException;
import org.json.JSONObject;


public class UserSelectionActivity extends ActionBarActivity implements IChromecastUser, View.OnClickListener, NumberPicker.OnValueChangeListener, CompoundButton.OnCheckedChangeListener {

    private final String[] ALPHABET = new String[] { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

    private ChromecastConnectionHandler connectionHandler;

    private View backgroundView;
    private CheckBox checkboxReady;
    private Button btnPrevColor;
    private Button btnNextColor;
    private NumberPicker namePicker1;
    private NumberPicker namePicker2;
    private NumberPicker namePicker3;

    private int colorIndex;
    private boolean ready;



    private void init() {
        this.connectionHandler = ChromecastConnectionHandler.getInstance(this);

        this.colorIndex = 0;
        this.ready = false;

        this.initUI();

        this.updateColor();
    }

    private void initUI() {
        this.backgroundView = (View)findViewById(R.id.screen_user_select_background);
        this.checkboxReady = (CheckBox)findViewById(R.id.screen_user_select_checkbox_ready);
        this.btnPrevColor = (Button)findViewById(R.id.screen_user_select_btn_prevcolor);
        this.btnNextColor = (Button)findViewById(R.id.screen_user_select_btn_nextcolor);

        this.namePicker1 = (NumberPicker)findViewById(R.id.screen_user_select_picker_1);
        this.namePicker2 = (NumberPicker)findViewById(R.id.screen_user_select_picker_2);
        this.namePicker3 = (NumberPicker)findViewById(R.id.screen_user_select_picker_3);


        this.fixNumberPicker(this.namePicker1);
        this.fixNumberPicker(this.namePicker2);
        this.fixNumberPicker(this.namePicker3);

        this.checkboxReady.setOnCheckedChangeListener(this);
        this.btnPrevColor.setOnClickListener(this);
        this.btnNextColor.setOnClickListener(this);
        this.namePicker1.setOnValueChangedListener(this);
        this.namePicker2.setOnValueChangedListener(this);
        this.namePicker3.setOnValueChangedListener(this);
    }


    private void updateColor() {
        int color = QuizcousColors.getColorByIndex(this.colorIndex);

        this.backgroundView.setBackgroundColor(color);
        this.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(color));
        this.getWindow().setStatusBarColor(color);

        this.sendUserToReceiver();
    }


    private void sendUserToReceiver() {
        try {
            int color = QuizcousColors.getColorByIndex(this.colorIndex);

            JSONObject json = new JSONObject();
            json.put("color", QuizcousColors.intColorToHex(color));
            json.put("name", this.getName());
            json.put("lobbyReady", this.ready);

            this.connectionHandler.sendMessage("user.change", json);
        } catch (JSONException ex) { }
    }


    private String getName() {
        return
           this.ALPHABET[this.namePicker1.getValue()] +
           this.ALPHABET[this.namePicker2.getValue()] +
           this.ALPHABET[this.namePicker3.getValue()];
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.ready = isChecked;
        this.sendUserToReceiver();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        this.sendUserToReceiver();
    }

    @Override
    public void onClick(View v) {
        if (v == btnPrevColor) {
            this.colorIndex = QuizcousColors.getNextColorIndex(this.colorIndex, -1);
            this.updateColor();
        }
        if (v == btnNextColor) {
            this.colorIndex = QuizcousColors.getNextColorIndex(this.colorIndex, +1);
            this.updateColor();
        }
    }


    // @Implements
    public Context getApplicationContext() {
        return this.getApplicationContext();
    }
    public void onTeardown() { }
    public void onChromecastConnected() { }
    public void onChromecastDisconnected() { }
    public void onReceiverApplicationConnected() { }
    public void onReceiverApplicationDisconnected() { }
    public void onMessageChannelConnected() { }
    public void onMessageReceived(JSONObject json) { }


    private void fixNumberPicker(NumberPicker picker) {
        picker.setMinValue(0);
        picker.setMaxValue(ALPHABET.length - 1);
        picker.setDisplayedValues(ALPHABET);
        picker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        for (java.lang.reflect.Field pf : NumberPicker.class.getDeclaredFields()) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    pf.set(picker, new ColorDrawable(Color.WHITE));
                } catch (Exception ex) {
                    Log.d("", "", ex);
                }
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.screen_user_select);
        setContentView(R.layout.screen_user_select_picker);

        this.init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.connectionHandler.startRoutePolling();
    }

    @Override
    protected void onStop() {
        this.connectionHandler.stopRoutePolling();
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mediaRouteMenuItem = menu.findItem(R.id.media_route_menu_item);
        this.connectionHandler.setChromecastMenuItem(mediaRouteMenuItem);

        return true;
    }
}
