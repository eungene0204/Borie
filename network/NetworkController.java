package siva.borie.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Eungjun on 2015-03-10.
 */
public class NetworkController
{
    public static final String TAG = NetworkController.class.getSimpleName();

    private static Context mContext;

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private static NetworkController mInstance;

    private NetworkController(final Context context)
    {
        this.mContext = context;

        mRequestQueue = getRequestQueue();
    }

    public static synchronized NetworkController getInstance(final Context context)
    {
        if (null == mInstance)
            mInstance = new NetworkController(context);

        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if(null == mRequestQueue)
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }

}
