package siva.borie.location.geofence;

import android.content.Context;
import android.content.res.Resources;

import siva.borie.R;

/**
 * Created by Eungjun on 2015-03-16.
 */
public class GeofenceErrorMessages
{
    private GeofenceErrorMessages()
    {
    }

    public static String getErrorString(Context context, int errorCode)
    {
        Resources mResources = context.getResources();

        switch (errorCode)
        {
            case GeofencesStatusCodes.GEOFENCE_NOT_AVAILABLE:
                return mResources.getString(R.string.geofence_not_available);

            case GeofencesStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES:
                return mResources.getString(R.string.geofence_too_many_geofences);

            case GeofencesStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS:
                return mResources.getString(R.string.geofence_too_many_pending_intents);

            default:
                return mResources.getString(R.string.geofence_unknown_error);

        }

    }
}

