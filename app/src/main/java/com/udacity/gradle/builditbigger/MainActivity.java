package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.pkrobertson.androidjokes.HandleJokeActivity;

/**
 * MainActivity -- shared between free and paid versions. Specific productFlavor functionality
 *     is implemented within the MainActivityFragment
 */
public class MainActivity extends ActionBarActivity implements AdvertisementHandler.HandleAdvertisementClose {
    private static final String JOKE_REQUEST = "101";

    AdvertisementHandler mAdHandler;
    String               mJokeResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdHandler = new AdvertisementHandler(this);
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

    /**
     * handleCloseCallback -- used to display joke after interstitial ad closed. This is never
     *     called on the "paid" version
     */
    @Override
    public void handleCloseCallback() {
        displayJoke();
    }

    /**
     * tellJoke -- used to handle the "tellJoke" button
     */
    public void tellJoke(View view){
        // show loading spinner while we fetch the joke
        final ProgressBar loadSpinner = (ProgressBar) findViewById(R.id.progress_bar_spinner);
        loadSpinner.setVisibility(View.VISIBLE);


        // define custom onPostExecute behavior to launch activity with the result
        FetchJokeTask jokeTask = new FetchJokeTask () {
            @Override
            protected void onPostExecute(final String result) {
                // if result is null, we've already waited a long time...
                if (result == null) {
                    loadSpinner.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this,
                            getString(R.string.error_server),
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                // if non-null, the response may have been fast so wait one second just to
                // make sure spinner is shown for at least one second
                    mJokeResult = result;
                    new CountDownTimer (1000, 500) {
                        public void onTick(long millisUntilFinished) {
                        }
                        public void onFinish() {
                            loadSpinner.setVisibility(View.GONE);
                            if (! mAdHandler.showAdvertisement()) {
                                displayJoke();
                            }
                        }
                    }.start();

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

    /**
     * displayJoke -- launch HandleJokeActivity with the joke fetched from the async task
     */
    private void displayJoke () {
        Intent intent = new Intent(MainActivity.this, HandleJokeActivity.class);
        intent.putExtra(HandleJokeActivity.JOKE_EXTRA, mJokeResult);
        startActivity(intent);
    }

}
