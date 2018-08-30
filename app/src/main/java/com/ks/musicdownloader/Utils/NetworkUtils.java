package com.ks.musicdownloader.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

@SuppressWarnings("DanglingJavadoc")
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static void regReceiverForConnectionValidationOnly(Context context, ConnectivityManager.NetworkCallback networkCallback) {
        Log.d(TAG, "regReceiverForConnectionValidationOnly");
        ConnectivityManager connMgr = getConnectivityManager(context);
        if (connMgr == null) {
            Log.d(TAG, "regReceiverForNetworkValidationOnly: Received null connMgr!! Can not register for network callback.");
            return;
        }
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connMgr.registerNetworkCallback(networkRequest, networkCallback);
    }

    public static void unRegReceiverForConnectionValidationOnly(Context context, ConnectivityManager.NetworkCallback networkCallback) {
        Log.d(TAG, "unRegReceiverForConnectionValidationOnly");
        ConnectivityManager connMgr = getConnectivityManager(context);
        if (connMgr == null) {
            Log.d(TAG, "regReceiverForNetworkValidationOnly: Received null connMgr!! Can not register for network callback.");
            return;
        }
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();
        connMgr.registerNetworkCallback(networkRequest, networkCallback);
    }

    public static void regNetworkReceiver(Context context, NetworkRequest networkRequest,
                                          ConnectivityManager.NetworkCallback networkCallback) {
        ConnectivityManager connMgr = getConnectivityManager(context);
        if (connMgr == null) {
            Log.d(TAG, "regNetworkReceiver: Received null connMgr!! Can not register for network callback.");
            return;
        }
        connMgr.registerNetworkCallback(networkRequest, networkCallback);
    }

    /******************Private************************************/
    /******************Methods************************************/

    private static ConnectivityManager getConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
