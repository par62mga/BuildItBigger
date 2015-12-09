package com.udacity.gradle.builditbigger;

import android.content.Context;

/**
 * AdvertisementHandler -- stub class that does basically nothing in the "paid" flavor.
 */
public class AdvertisementHandler {

    Context mContext;

    public AdvertisementHandler (Context context) {
        mContext = context;
    }

    public boolean showAdvertisement () {
        return false;
    }

    public interface HandleAdvertisementClose {
        public void handleCloseCallback ();
    }

}
