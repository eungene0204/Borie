package siva.borie.business.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import siva.borie.R;
import siva.borie.business.Business;

/**
 * Created by Eungjun on 2015-09-16.
 */

public class RecommendListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    static public String TAG = "RecommendListViewHolder";

    private TextView mTestName;
    private int pos = -1;

    public RecommendListViewHolder(View itemView)
    {
        super(itemView);

        Log.i(TAG, "ViewHolder");
        itemView.setOnClickListener(this);

        mTestName = (TextView) itemView.findViewById(R.id.cardview_test_tv);

    }

    public void bind(Business biz, int position)
    {
        mTestName.setText(biz.getName());
        pos = position;

    }

    @Override
    public void onClick(View v)
    {

        Log.i(TAG, "ViewHolder Click!!" + "pos: " + pos);

    }
}

