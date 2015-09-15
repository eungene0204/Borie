package siva.borie.business.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import siva.borie.business.Business;
import siva.borie.R;

/**
 * Created by Eungjun on 2015-08-18.
 */
public class RecommendedLitViewAdapter extends
        RecyclerView.Adapter<RecommendedLitViewAdapter.RecommendListViewHolder>
{
    private ArrayList<Business> mList;

    public RecommendedLitViewAdapter(List<Business> mList)
    {
        this.mList = (ArrayList<Business>) mList;
    }

    @Override
    public RecommendListViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,
                                                        parent,false);

        return new RecommendListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecommendListViewHolder holder, int position)
    {
        holder.mTestName.setText(mList.get(position).getmName());

    }

    @Override
    public int getItemCount()
    {
        return mList.size();

    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);

    }

    static public class RecommendListViewHolder extends RecyclerView.ViewHolder
    {
        CardView mCardView;
        TextView mTestName;


        public RecommendListViewHolder(View itemView)
        {
            super(itemView);

            mCardView = (CardView) itemView.findViewById(R.id.cardview);
            mTestName = (TextView) itemView.findViewById(R.id.cardview_test_tv);

        }
    }

}
