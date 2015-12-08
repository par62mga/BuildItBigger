package com.udacity.gradle.builditbigger;

import android.test.InstrumentationTestCase;
import android.test.UiThreadTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * InstrumentationTest -- defines test case used to validate FetchJokeTask behavior.
 */
public class InstrumentationTest extends InstrumentationTestCase {

    private static final String TEST_REQUEST = "102";

    private String mJokeResult = null;

    /*
     * testFetchJokeTask -- The following code was based on a template found on StackOverflow for
	 *     testing AsyncTask behavior and updated to test FetchJokeTask to ensure that the string
	 *     returned is non-null (Server must be running).
	 *
	 *      http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
     */
    public void testFetchJokeTask () throws Throwable {
        // create  a signal to let us know when our task is done.
        final CountDownLatch signal = new CountDownLatch(1);

        // onPostExecute is over-ridden to capture the result and signal task is done
        mJokeResult = null;
        final FetchJokeTask jokeTask = new FetchJokeTask () {
            @Override
            protected void onPostExecute(String result) {
                mJokeResult = result;
                signal.countDown();
            }
        };


        // Execute the async task on the UI thread! THIS IS KEY!
        runTestOnUiThread(new Runnable() {

            @Override
            public void run() {
                jokeTask.executeFetchJoke (
                        getInstrumentation().getTargetContext().getString(R.string.url_joke_server),
                        TEST_REQUEST);
            }
        });

	    // wait until the UI thread is released or a 30-second timeout occurs
        signal.await(30, TimeUnit.SECONDS);

        // The task is done, check for result
        assertTrue("Received Null String from FetchJokeTask! Is the jokeserver running?",
                mJokeResult != null);
    }
}
