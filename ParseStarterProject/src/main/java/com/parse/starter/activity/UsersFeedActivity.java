package com.parse.starter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.starter.R;
import com.parse.starter.adapter.PostAdapter;

import java.util.ArrayList;
import java.util.List;

public class UsersFeedActivity extends AppCompatActivity {
    //layout
    private Toolbar toolbar;
    private ListView listView;
    private String username;

    //lists
    private ArrayList<ParseObject> posts;
    private ArrayAdapter<ParseObject> adapter;
    private ParseQuery<ParseObject> query;

    //constants
    private static final String TAG = "UsersFeedActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_feed);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        toolbar = findViewById(R.id.toolbar_users);
        toolbar.setTitle(username);
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left);
        setSupportActionBar(toolbar);

        posts = new ArrayList<>();
        listView = findViewById(R.id.lv_feed_users);
        adapter = new PostAdapter(this, posts);
        listView.setAdapter(adapter);

        getPosts();

    }

    private void getPosts() {
        query = new ParseQuery<ParseObject>("Photo");
        query.whereEqualTo("username", username);
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){
                    if(objects.size() > 0){
                        posts.clear();

                        for(ParseObject parseObject: objects){
                            posts.add(parseObject);
                        }

                        adapter.notifyDataSetChanged();
                    }

                }else{
                    Log.i(TAG, "query: findInBackground: " + e.getMessage());
                    Toast.makeText(UsersFeedActivity.this, "Error finding feed. Please, try again later.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
