/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.starter.R;
import com.parse.starter.adapter.TabAdapter;
import com.parse.starter.fragment.HomeFragment;
import com.parse.starter.help.SlidingTabLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {
    //layout
    private Toolbar toolbar;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    //constants
    private static final String TAG = "MainActivity";
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      toolbar = findViewById(R.id.toolbar_main);
      toolbar.setLogo(R.drawable.instagramlogo);
      toolbar.setTitle("");
      setSupportActionBar(toolbar);

      slidingTabLayout = findViewById(R.id.stl_main);
      viewPager = findViewById(R.id.vp_main);

      TabAdapter adapter = new TabAdapter(getSupportFragmentManager(), this);
      viewPager.setAdapter(adapter);
      slidingTabLayout.setCustomTabView(R.layout.tab_view, R.id.tv_item_tab);
      slidingTabLayout.setDistributeEvenly(true);
      slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.darkGrey));
      slidingTabLayout.setViewPager(viewPager);

  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_logout:
                logoutUser();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_share:
                sharePhoto();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sharePhoto(){
      Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri selectedPhotoLocation = data.getData();

            try {
                Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedPhotoLocation);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 75, stream);

                byte[] byteArray = stream.toByteArray();

                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaahhmmss");
                String photoName = dateFormat.format(new Date());
                ParseFile parseFile = new ParseFile(photoName+"photo.png", byteArray);

                ParseObject parseObject = new ParseObject("Photo");
                parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                parseObject.put("photo", parseFile);

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null){
                            Toast.makeText(getApplicationContext(), "Your photo was posted!", Toast.LENGTH_LONG).show();

                            TabAdapter newAdapter = (TabAdapter) viewPager.getAdapter();
                            HomeFragment newHomeFragment = (HomeFragment) newAdapter.getFragment(0);
                            newHomeFragment.updatePosts();
                        }else{
                            Log.i(TAG, "saveInBackground: " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Error posting photo, please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void logoutUser(){
      ParseUser.logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
