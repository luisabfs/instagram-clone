/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class StarterApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    //enable local storage
    Parse.enableLocalDatastore(this);

    //app configuration code
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId(getString(R.string.parse_app_id))
            .clientKey(getString(R.string.parse_client_key))
            .server(getString(R.string.parse_server_url))
    .build()
    );

    //ParseUser.enableAutomaticUser();
    ParseACL defaultACL = new ParseACL();

    //Optionally enable public read access.
    defaultACL.setPublicReadAccess(true);
    //ParseACL.setDefaultACL(defaultACL, true);
  }
}
