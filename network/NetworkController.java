package siva.borie.network;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by Eungjun on 2015-03-10.
 */
public class NetworkController
{
    public static final String TAG = NetworkController.class.getSimpleName();

    private static Context mContext;
    private static NetworkController mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public NetworkController(final Context context)
    {
        mContext = context;
        mRequestQueue = Volley.newRequestQueue(context);
    }


    public StringRequest makeStringRequest(final String url)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String s)
                    {
                        Log.i(TAG, s);

                    }

                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                Log.e(TAG, volleyError.toString());

            }
        }
        );

        return stringRequest;

    }

    public void add(final StringRequest req)
    {
        if(null != req)
            mRequestQueue.add(req);
    }



    public <T> void addToRequestQueue(Request<T> req)
    {
        mRequestQueue.add(req);

    }

}
