package com.herokuapp.ezhao.penciled;

import android.app.Application;

import com.herokuapp.ezhao.penciled.models.PencilPerson;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    public static final String PARSE_APPLICATION_ID = "JITd7JQXNLZEJ6kt2MiyHNMcZWncWyC618EHDMsC";
    public static final String PARSE_CLIENT_KEY = "eNxa5hBQE9BbiazbOs3jEmZpoBbuJ8FgMl4jCLPW";

    @Override
    public void onCreate() {
        super.onCreate();

        // Register models
        ParseObject.registerSubclass(PencilPerson.class);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, PARSE_APPLICATION_ID, PARSE_CLIENT_KEY);

        ParseObject testObject = new ParseObject("TestObject");
    }
}
