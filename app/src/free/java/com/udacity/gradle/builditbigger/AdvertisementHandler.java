package com.udacity.gradle.builditbigger;

import android.content.Context;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

/**
 * AdvertisementHandler -- class that manages fetching and showing of interstitial ads.
 */
public class AdvertisementHandler {

    Context        mContext;
    InterstitialAd mAdvertisement;

    /**
     * Instantiate class and fetch first ad using "test" credentials
     * @param context
     */
    public AdvertisementHandler (Context context) {
        mContext = context;
        mAdvertisement = new InterstitialAd(mContext);
        mAdvertisement.setAdUnitId(context.getString(R.string.banner_ad_unit_id));

        mAdvertisement.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewAdvertisement();
                ((HandleAdvertisementClose)mContext).handleCloseCallback();
            }
        });
        requestNewAdvertisement();
    }

    /**
     * showAdvertisement -- if advertisement is ready, show the add
     * @return true if ad ready and shown; otherwise, false
     */
    public boolean showAdvertisement () {
        if (mAdvertisement.isLoaded()) {
            mAdvertisement.show();
            return true;
        } else {
            return false;
        }
    }

    /**
     * HandleAdvertisementClose -- interface supported by the activity to handle what is done
     *     when the interstitial advertisement is closed
     */
    public interface HandleAdvertisementClose {
        public void handleCloseCallback ();
    }

    /**
     * requestNewAdvertisement -- fetches next interstitial ad using test credentials
     */
    private void requestNewAdvertisement () {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdvertisement.loadAd(adRequest);
    }
}
