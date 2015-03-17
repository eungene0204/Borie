package siva.borie.location.geofence;

import android.util.Log;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

/**
 * Created by Eungjun on 2015-03-18.
 */
public class GeofenceResultListener implements ResultCallback<Status>
{
    public static final String TAG = GeofenceResultListener.class.getSimpleName();

    @Override
    public void onResult(Status status)
    {
        Log.i(TAG, "onResult");
        int code = status.getStatusCode();

        if(CommonStatusCodes.SUCCESS == code)
        {
            Log.i(TAG, "Add Geofence Success");
        }

    }
}
