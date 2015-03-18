package siva.borie.location.geofence;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import siva.borie.GoogleApi.GoogleApiHelper;

/**
 * Created by Eungjun on 2015-03-13.
 */
public class GeonfenceController implements GoogleApiClient.ConnectionCallbacks, ResultCallback<Status>, GoogleApiClient.OnConnectionFailedListener
{
    public static final String TAG = GeonfenceController.class.getSimpleName();

    private final Context mContext;
    private final ArrayList<Geofence> mGeofenceList;
    private final GoogleApiClient mGoogleApiClient;

    public GeonfenceController(Context context)
    {
        this.mContext = context;
        this.mGoogleApiClient = GoogleApiHelper.getGoogleApiClient(mContext,this,
                this, LocationServices.API);

        mGeofenceList = new ArrayList<Geofence>();

    }

    public void startGoogleApiClient()
    {
        mGoogleApiClient.connect();
    }

    public void stopGoogleApiClient()
    {
        mGoogleApiClient.disconnect();
    }

    public void addGeofences()
    {
        if(!mGoogleApiClient.isConnected())
        {
            Log.i(TAG, "mGoogleApiClient is not connected");
            return;
        }

        try
        {
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    getGeofencingRequest(),
                    getGeofencePendingIntent(mContext)
            ).setResultCallback(this);
        }
        catch(SecurityException e)
        {
            Log.e(TAG, e.toString());
        }
    }

    public void addGeofenceObjectToList(final Entry entry)
    {
        mGeofenceList.add(new Geofence.Builder()
                .setRequestId(entry.getKey())
                .setCircularRegion(entry.getLocation().latitude,
                        entry.getLocation().longitude,
                        GeofenceConstans.GEOFENCE_RADIUS_IN_METERS)

                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER |
                        Geofence.GEOFENCE_TRANSITION_EXIT)
                .build());
    }

    private GeofencingRequest getGeofencingRequest()
    {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();

        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(mGeofenceList);

        return builder.build();
    }

    private PendingIntent mGeofencePendingIntent = null;

    public PendingIntent getGeofencePendingIntent(Context context)
    {
        if(null != mGeofencePendingIntent)
            return mGeofencePendingIntent;

        Intent intent = new Intent(context, GeofenceTransitionIntentService.class);

        return PendingIntent.getService(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void stopGeofences()
    {

        if(!mGoogleApiClient.isConnected())
        {
            Log.i(TAG, "mGoogleApiClient is not connected");
            return;
        }

        LocationServices.GeofencingApi.removeGeofences(
               mGoogleApiClient,
                getGeofencePendingIntent(mContext)
        ).setResultCallback(this);
    }


    @Override
    public void onConnected(Bundle bundle)
    {
        Log.i(TAG, "GoogleApiClient Conntected");

        Entry amsa = new Entry(37.550365, 127.127471, "Amsa");
        addGeofenceObjectToList(amsa);
        addGeofences();
    }

    @Override
    public void onConnectionSuspended(int i)
    {

        Log.i(TAG, "GoogleApiClient Suspended");
        startGoogleApiClient();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Log.i(TAG, "GoogleApiClient failed");
        Log.i(TAG, connectionResult.toString());
    }

    //Geofence Add/remove result
    @Override
    public void onResult(Status status)
    {
        Log.i(TAG, "Geofence result: " + status.getStatus().toString());
    }

}
