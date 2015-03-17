package siva.borie.location.geofence;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

import siva.borie.GoogleApi.GoogleApiHelper;

/**
 * Created by Eungjun on 2015-03-13.
 */
public class GeonfenceController
{
    private final Context mContext;
    private final ArrayList<Geofence> mGeofenceList;
    private final GoogleApiClient mGoogleApiClient;
    private GoogleApiClientConnectionListener mGoogleApiClientListener =
            new GoogleApiClientConnectionListener();
    private GeofenceResultListener mGeofenceResultListener =
            new GeofenceResultListener();


    public GeonfenceController(Context context)
    {
        this.mContext = context;
        this.mGoogleApiClient = GoogleApiHelper.getGoogleApiClient(mContext,mGoogleApiClientListener,
                mGoogleApiClientListener, LocationServices.API);

        mGeofenceList = new ArrayList<Geofence>();

    }

    public void addGeofences()
    {
        LocationServices.GeofencingApi.addGeofences(
                mGoogleApiClient,
                getGeofencingRequest(),
                getGeofencePendingIntent(mContext)
        ).setResultCallback(mGeofenceResultListener);
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

    public void stopGeofence()
    {
        LocationServices.GeofencingApi.removeGeofences(
               mGoogleApiClient,
                getGeofencePendingIntent(mContext)
        ).setResultCallback(mGeofenceResultListener);
    }

    public void connectionResult()
    {

    }

}
