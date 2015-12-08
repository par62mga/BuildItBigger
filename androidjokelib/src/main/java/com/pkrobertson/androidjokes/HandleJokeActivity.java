package com.pkrobertson.androidjokes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/*
 * HandleJokeActivity -- simple activity that is launched with an intent containing a joke
 *     string.
 */
public class HandleJokeActivity extends AppCompatActivity {
    public final static String JOKE_EXTRA = "com.pkrobertson.androidjokes.jokeExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_joke);

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(JOKE_EXTRA);
        if (message != null) {
            TextView textView = (TextView) findViewById(R.id.text_view_joke);
            textView.setText(message);
        } else {
            // TODO : display error dialog if intent extra not found
        }
    }
}
