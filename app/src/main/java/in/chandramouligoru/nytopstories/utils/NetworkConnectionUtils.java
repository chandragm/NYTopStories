/*
 * Copyright (c) 2011,2012,2013,2014,2015 Mutual Mobile. All rights reserved.
 */

package in.chandramouligoru.nytopstories.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnectionUtils {

    private Context mContext;
    private ConnectivityManager mConnectionManager;

    public NetworkConnectionUtils(Context context) {
        mContext = context;
    }

    public boolean isConnected() {
        mConnectionManager = mConnectionManager != null ? mConnectionManager : (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = mConnectionManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}