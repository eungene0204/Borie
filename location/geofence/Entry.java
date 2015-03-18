package siva.borie.location.geofence;

import com.google.android.gms.maps.model.LatLng;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Eungjun on 2015-03-13.
 */
public class Entry
{
    private static final String PRE_KEY = "SIVA_BORIE";
    private static SecureRandom RANDOM;
    private final LatLng mLocation;
    private final String mName;

    public Entry(final double lat, final double lng, final String name )
    {
        RANDOM = new SecureRandom();
        mLocation = new LatLng(lat,lng);
        mName = name;
    }

    public LatLng getLocation()
    {
        return mLocation;
    }

    public String getKey()
    {
        String key = new BigInteger(130,RANDOM).toString(32);

        return PRE_KEY + "_" + mName;
    }












}
