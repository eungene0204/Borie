package siva.borie.Businesses.fragments.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import siva.borie.Businesses.Business;

/**
 * Created by Eungjun on 2015-08-18.
 */
public class RecommendedLitViewAdapter extends
        RecyclerView.Adapter<RecommendedLitViewAdapter.ViewHolder>
{
    private ArrayList<Business> mList;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    static public class ViewHolder extends RecyclerView.ViewHolder
    {

        public ViewHolder(View itemView)
        {
            super(itemView);
        }
    }

}
