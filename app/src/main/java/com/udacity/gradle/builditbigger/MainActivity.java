package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pkrobertson.androidjokes.HandleJokeActivity;

/**
 * MainActivity -- shared between free and paid versions. Specific productFlavor functionality
 *     is implemented within the MainActivityFragment
 */
public class MainActivity extends ActionBarActivity {
    private static final String JOKE_REQUEST = "101";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void tellJoke(View view){
        // define custom onPostExecute behavior to launch activity with the result
        FetchJokeTask jokeTask = new FetchJokeTask () {
            @Override
            protected void onPostExecute(String result) {
                if (result == null) {
                    Toast.makeText(MainActivity.this,
                            getString(R.string.error_server),
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    Intent intent = new Intent(MainActivity.this, HandleJokeActivity.class);
                    intent.putExtra(HandleJokeActivity.JOKE_EXTRA, result);
                    startActivity(intent);
                }
            }
        };
        jokeTask.executeFetchJoke (getString(R.string.url_joke_server), JOKE_REQUEST);

        //the below code is from earlier iterations of this project...
        //Intent intent = new Intent(this, HandleJokeActivity.class);
        //intent.putExtra (HandleJokeActivity.JOKE_EXTRA, FetchJavaJoke.fetchRandomJoke());
        //startActivity (intent);
        //Toast jokeToast = Toast.makeText(this, FetchJavaJoke.fetchRandomJoke(), Toast.LENGTH_LONG);
        //jokeToast.setGravity(Gravity.CENTER, 0, 0);
        //jokeToast.show();
    }


}
