package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.pkrobertson.jokeserver.myApi.MyApi;

import java.io.IOException;

/**
 * FetchJokeTask -- defines task used to fetch Joke from server running on local host. The
 *     following changes are needed when the server is deployed in the cloud:
 *     1. Run Build->Deploy Module to App Engine (with your own developer's project)
 *     2. Update jokeserver configuration:
 *          update src/main/webapp/WEB-INF/appengine-web.xml file's <application> property
 *          and replace myApplicationId with the ID of the project that you just created.
 *     3. Change url_joke_server in "strings.xml" to:
 *          https://<<<your project ID here>>>.appspot.com/_ah/api/
 */
public class FetchJokeTask extends AsyncTask<String, Void, String> {
    private static final String LOG_TAG = FetchJokeTask.class.getSimpleName();

    private static MyApi myApiService = null;

    /**
     * executeFetchJoke -- helper method use to pass String[2] args to "execute"
     * @param requestURL
     * @param requestName
     */
    public void executeFetchJoke (String requestURL, String requestName) {
        String params[] = new String[2];
        params[0] = requestURL;
        params[1] = requestName;
        execute (params);
    }

    @Override
    protected String doInBackground(String... params) {
        // get URL and Name to identify client type ("101" or "102");
        String requestURL  = params[0];
        String requestName = params[1];

        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.fetchJoke(requestName).execute().getResponseData();
        } catch (IOException e) {
            Log.e(LOG_TAG, "fetchJoke failure ==> " + e.getMessage());
            return null;
        }
    }

    /**
     * onPostExecute -- this code should be overridden to customize how the resulting joke
     *      String is handled.
     */
    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            Log.d (LOG_TAG, "result ==>" + result);
        }
    }
}
