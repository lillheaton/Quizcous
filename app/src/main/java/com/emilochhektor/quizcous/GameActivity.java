package com.emilochhektor.quizcous;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GameActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        fakeFriends();
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fakeFriends(){
        String[] names = new String[]{ "Hektor", "Emil", "Kalle", "Sven", "Nisse" };
//        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.activity_game_nameList);

//        for (String name : names){
//            addFriendInList(linearLayout, name);
//        }
    }

    private void addFriendInList(ViewGroup parent, String name){
        // Get service
        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Create template
        View friendTemplate = inflater.inflate(R.layout.friend_item_template, null);

        // Set textview text in template
        TextView textView = (TextView)friendTemplate.findViewById(R.id.friend_item_template_name);
        textView.setText(name);

        // Add template into parent layout
        parent.addView(friendTemplate);
    }
}
