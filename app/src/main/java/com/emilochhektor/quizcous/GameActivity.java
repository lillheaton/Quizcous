package com.emilochhektor.quizcous;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GameActivity extends ActionBarActivity implements View.OnClickListener{

    private Button sendButton;
    private EditText answerInput;
    private TextView answerText;

    private void init(){
        this.initUi();
    }

    private void initUi(){
        this.sendButton = (Button)findViewById(R.id.sendButton);
        this.answerInput = (EditText)findViewById(R.id.answerInput);
        this.answerText = (TextView)findViewById(R.id.answerTextView);

        this.sendButton.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        if(v == this.sendButton){
            this.answerText.setText(String.format("Your answer: %s", this.answerInput.getText()));
        }
    }



//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    private void fakeFriends(){
//        String[] names = new String[]{ "Hektor", "Emil", "Kalle", "Sven", "Nisse" };
////        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.activity_game_nameList);
//
////        for (String name : names){
////            addFriendInList(linearLayout, name);
////        }
//    }
//
//    private void addFriendInList(ViewGroup parent, String name){
//        // Get service
//        LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//
//        // Create template
//        View friendTemplate = inflater.inflate(R.layout.friend_item_template, null);
//
//        // Set textview text in template
//        TextView textView = (TextView)friendTemplate.findViewById(R.id.friend_item_template_name);
//        textView.setText(name);
//
//        // Add template into parent layout
//        parent.addView(friendTemplate);
//    }
//





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        this.init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
