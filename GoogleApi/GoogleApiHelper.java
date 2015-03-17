package siva.borie.GoogleApi;

import android.content.Context;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Eungjun on 2015-03-13.
 */
public class GoogleApiHelper
{

    public static GoogleApiClient getGoogleApiClient(Context context,
                                                     GoogleApiClient.ConnectionCallbacks listener,
                                                     GoogleApiClient.OnConnectionFailedListener failListener,
                                                     Api<? extends Api.ApiOptions.NotRequiredOptions> api)
    {

        GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(listener)
                .addOnConnectionFailedListener(failListener)
                .addApi(api)
                .build();

        return mGoogleApiClient;

    }

}
