package siva.borie.Businesses.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;

import java.util.ArrayList;

import siva.borie.Businesses.Business;
import siva.borie.R;
import siva.borie.network.NetworkController;

/**
 * Created by Eungjun on 2015-02-16.
 */
public class RecommendedServiceFragment extends Fragment
{
    public static final String TAG = RecommendedServiceFragment.class.getSimpleName();
    private final String URL = "http://192.168.0.1:8080/biz/recommendlist";

    private ListView mListView;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_recommended_service, container,false );

        mListView = (ListView) root.findViewById(R.id.recommended_service_listview);

        getList();

        return root;
    }

    private void getList()
    {
        Log.d(TAG, "getList");
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray jsonArray)
            {
                Log.d(TAG, jsonArray.toString());

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                VolleyLog.d(TAG,"Error" + volleyError.getMessage());
            }
        });

        NetworkController.getInstance(getActivity().getApplicationContext()).addToRequestQueue(req);

        Log.d(TAG,"end of getList");
    }



    private class listViewAdapter extends BaseAdapter
    {
        private final ArrayList<Business> arrayList;

        private listViewAdapter(final ArrayList<Business> arrayList)
        {
            this.arrayList = arrayList;
        }


        @Override
        public int getCount()
        {
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            return null;
        }
    }
}
