package siva.borie.location.geofence;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;

import siva.borie.R;
import siva.borie.main.MainActivity;

/**
 * Created by Eungjun on 2015-03-13.
 */
public class GeofenceTransitionIntentService extends IntentService
{
    public static final String TAG = GeofenceTransitionIntentService.class.getSimpleName();
    private String mTriggeringGeofencesIdsString;

    public GeofenceTransitionIntentService()
    {
        super(GeofenceTransitionIntentService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent)
    {
        Log.i(TAG, "Geofence onHandle");

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if(geofencingEvent.hasError())
        {
            String err = GeofenceErrorMessages.getErrorString(this,
                    geofencingEvent.getErrorCode());

            Log.e(TAG,  err);
        }

        //Get transition type
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        if(geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER ||
                geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT )
        {
            List triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            String geoTransitionDetails = getGeofenceTransitionDetails(
                    this,
                    geofenceTransition,
                    triggeringGeofences
            );
           sendNotification(geoTransitionDetails);

            Log.i(TAG, geoTransitionDetails);
        }
        else
        {
            Log.e(TAG, "Geofence Invalid transition type");
        }

    }

    private String getGeofenceTransitionDetails(Context context, int geofenceTransition,
                                                List<Geofence> triggeringGeofences)
    {
        String geofenceTransitionString = getTransitionString(geofenceTransition);

        ArrayList triggeringGeofencesIdList = new ArrayList();

        for(Geofence geofece : triggeringGeofences)
        {
            triggeringGeofencesIdList.add(geofece.getRequestId());
        }

        mTriggeringGeofencesIdsString = TextUtils.join(", ",
                triggeringGeofencesIdList);

        return geofenceTransitionString + ": " + mTriggeringGeofencesIdsString;

    }

    private void sendNotification(String notificationDetails)
    {
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(MainActivity.class);

        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent notificationPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        // Get a notification builder that's compatible with platform versions >= 4
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                        R.drawable.ic_launcher))
                .setColor(Color.RED)
                .setContentTitle(notificationDetails)
                .setContentText(mTriggeringGeofencesIdsString) //getString(R.string.geofence_transition_notification_text))
                .setContentIntent(notificationPendingIntent);

        builder.setAutoCancel(true);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }

    private String getTransitionString(int transitionType)
    {
        switch (transitionType)
        {
            case Geofence.GEOFENCE_TRANSITION_ENTER:
                return getString(R.string.geofence_transition_entered);

            case Geofence.GEOFENCE_TRANSITION_EXIT:
                return getString(R.string.geofence_transition_exited);

            default:
                return getString(R.string.geofence_unknown_transition);
        }
    }

}

