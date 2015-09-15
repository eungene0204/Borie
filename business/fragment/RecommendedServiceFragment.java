package siva.borie.business.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import siva.borie.R;
import siva.borie.business.Business;
import siva.borie.business.BusinessUtils;
import siva.borie.business.adapter.RecommendedLitViewAdapter;
import siva.borie.location.geofence.GeonfenceController;

/**
 * Created by Eungjun on 2015-02-16.
 */
public class RecommendedServiceFragment extends Fragment
{
    public static final String TAG = RecommendedServiceFragment.class.getSimpleName();
    private final String URL = "http://192.168.0.1:8080/biz/recommendlist";

    private ListView mListView;
    private ListViewAdapter mAdapter;
    private ArrayList<Business> mBusinessArrayList;
    private GeonfenceController mGeofenceController;

    private RecyclerView mRecylerView;
    private LinearLayoutManager mLayoutManager;
    private RecommendedLitViewAdapter mRVAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initDataSet();
    }

    private void initDataSet()
    {
        mBusinessArrayList = new ArrayList<Business>();

        Business item1 = new Business();
        item1.setmName("test1");
        mBusinessArrayList.add(item1);

        Business item2 = new Business();
        item2.setmName("test2");
        mBusinessArrayList.add(item2);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        Log.i(TAG, "onCreateView");

        View root = inflater.inflate (
                R.layout.fragment_recommended_service, container,false );

        mRecylerView = (RecyclerView)
                root.findViewById(R.id.recommended_service_recylerview);
        mLayoutManager = new LinearLayoutManager(getActivity()) ;
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecylerView.setLayoutManager(mLayoutManager);

        mRVAdapter = new RecommendedLitViewAdapter(mBusinessArrayList);
        mRecylerView.setAdapter(mRVAdapter);

        return root;
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        Log.i(TAG, "onAttach" );

        //Geofence Test remove after test
        Context context = activity.getApplicationContext();
        mGeofenceController = new GeonfenceController(getActivity().getApplicationContext());

    }

    @Override
    public void onStart()
    {
        super.onStart();
        mGeofenceController.stopGeofences();
        mGeofenceController.startGoogleApiClient();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mGeofenceController.stopGoogleApiClient();
        mGeofenceController.stopGeofences();
    }

    private void setListRequest()
    {
        Log.d(TAG, "setListRequest");
        JsonArrayRequest req = new JsonArrayRequest(URL, new Response.Listener<JSONArray>()
        {
            @Override
            public void onResponse(JSONArray jsonArray)
            {
                Log.d(TAG, "onResponse");


                Log.d(TAG, "json result: " + jsonArray.toString());

                getResponse(jsonArray);
                mAdapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                VolleyLog.d(TAG,"Error" + volleyError.getMessage());
            }
        });

//        NetworkController.getInstance(getActivity().getApplicationContext())
       //.addToRequestQueue(req);

    }

    private void getResponse(JSONArray _jsonArray)
    {
        JSONArray jsonArray = _jsonArray;

        int lenght = jsonArray.length();

       for(int i = 0; i < lenght; ++i)
       {
           Business biz  = new Business();

           try
           {
               JSONObject json = (JSONObject) jsonArray.get(i);

               biz.setmName(json.getString(BusinessUtils.BIZ_NAME));
               biz.setmAddress(json.getString(BusinessUtils.BIZ_ADDRESS));
               biz.setmEmail(json.getString(BusinessUtils.OWNER_EMAIL));

               mBusinessArrayList.add(biz);

           } catch (JSONException e)
           {
               Log.d(TAG, e.toString());
           }
       }
    }

    private static class ViewHolder
    {
        public TextView name;
        public TextView address;

    }

    private class ListViewAdapter extends ArrayAdapter
    {

        public ListViewAdapter(Context context, int resource)
        {
            super(context, resource);

        }

        @Override
        public int getCount()
        {
            return mBusinessArrayList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return mBusinessArrayList.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return position;
        }

        @Override
        public android.view.View getView(final int position, final android.view.View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;

            View rowView= convertView;

            if(null == rowView)
            {
                rowView = View.inflate(getActivity(),R.layout.listview_item_biz, null);

                viewHolder = new ViewHolder();
                viewHolder.name = (TextView) convertView.findViewById(R.id.tv_list_item_biz_name);
                viewHolder.address = (TextView)
                        convertView.findViewById(R.id.tv_list_item_biz_address);

                rowView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder)rowView.getTag();
            }

            viewHolder.name.setText(mBusinessArrayList.get(position).getmName());
            viewHolder.address.setText(mBusinessArrayList.get(position).getmAddress());

            return rowView;
        }
    }

}