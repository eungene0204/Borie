package siva.borie.location.geofence;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Eungjun on 2015-03-17.
 */
public class GoogleApiClientConnectionListener implements
GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
    @Override
    public void onConnected(Bundle bundle)
    {


    }

    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Log.

    }
}
