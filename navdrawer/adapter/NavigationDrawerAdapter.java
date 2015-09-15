package siva.borie.navdrawer.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import siva.borie.R;
import siva.borie.facebook.FacebookHelper;
import siva.borie.facebook.FacebookUser;
import siva.borie.navdrawer.NavDrawerItem;
import siva.borie.navdrawer.NavDrawerUtils;
import siva.borie.navdrawer.NavDrawerUtils.ItemId;


/**
 * Created by Eungjun on 2015-09-09.
 */
public class NavigationDrawerAdapter extends BaseAdapter
{
    private final String TAG = NavigationDrawerAdapter.class.getSimpleName();

    private final ArrayList<NavDrawerItem> mItemList;
    private final Activity mActivity;

    public NavigationDrawerAdapter(final ArrayList<NavDrawerItem> itemList, final Activity activity)
    {

        mItemList = itemList;
        this.mActivity = activity;

    }

    @Override
    public int getCount()
    {
        return mItemList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mItemList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        DrawerViewHolder viewHolder = new DrawerViewHolder();
        NavDrawerUtils.ItemId id = mItemList.get(position).getItemId();

        View row = convertView;
        if (null == row)
        {
            switch (id)
            {
                case USER_INFO:
                    row = View.inflate(mActivity, R.layout.drawer_item_user_info, null);

                    viewHolder.mCircleImageivew =
                            (ImageView) row.findViewById(R.id.user_info_circleimgView);

                    viewHolder.mUserNameTextView =
                            (TextView) row.findViewById(R.id.user_info_user_name_tv);

                    viewHolder.mEmailTextView =
                            (TextView) row.findViewById(R.id.user_info_user_email_tv);

                    viewHolder.mArrowImageView =
                            (ImageView) row.findViewById(R.id.user_info_arrow_img);

                    break;

                case SUB_HEADER:
                    row = View.inflate(mActivity, R.layout.drawer_item_subheader, null);
                    viewHolder.mTitleTextView =
                            (TextView) row.findViewById(R.id.drawer_item_title);
                    break;

                case LBS_SERVICE:
                    row = View.inflate(mActivity, R.layout.drawer_item_lbs_service, null);
                    viewHolder.mTitleTextView =
                            (TextView) row.findViewById(R.id.drawer_item_lbs_text);
                    break;

                case ALL_SERVICE:
                    row = View.inflate(mActivity, R.layout.drawer_item_all_service, null);
                    viewHolder.mTitleTextView =
                            (TextView) row.findViewById(R.id.drawer_item_all_service_text);
                    break;

                case VISITED_SERVICE:
                    row = View.inflate(mActivity, R.layout.drawer_item_visited_service, null);
                    viewHolder.mTitleTextView =
                            (TextView) row.findViewById(R.id.drawer_item_visited_service_text);
                    break;

                case SETTING:
                    row = View.inflate(mActivity, R.layout.drawer_item_setting, null);
                    viewHolder.mTitleTextView =
                            (TextView) row.findViewById(R.id.drawer_item_setting_text);
                    break;

                case FEED_BACK:
                    row = View.inflate(mActivity, R.layout.drawer_item_feedback, null);
                    viewHolder.mTitleTextView =
                            (TextView) row.findViewById(R.id.drawer_item_feedback_text);
                    break;

                case SHARE:
                    row = View.inflate(mActivity, R.layout.drawer_item_share, null);
                    viewHolder.mTitleTextView =
                            (TextView) row.findViewById(R.id.drawer_item_share_text);
                    break;

                default:
                    break;

            }

            row.setTag(viewHolder);
        } else
        {
            viewHolder = (DrawerViewHolder) row.getTag();
        }


        viewHolder.mId = mItemList.get(position).getItemId();


        if (ItemId.USER_INFO == id)
        {
            FacebookUser user = FacebookHelper.getInstance().getFacebookUserInfo();

            // Need to deal this
            if(null == user)
            {
                Log.e(TAG, "User info isn empty");
            }

            Bitmap profile = user.getProfilePicure();
            Drawable arrow = mActivity.getDrawable(R.drawable.ic_arrow_drop_down_black_48dp);

            viewHolder.mCircleImageivew.setImageBitmap(profile);
            viewHolder.mUserNameTextView.setText(user.getmName());
            viewHolder.mEmailTextView.setText(user.getmEmail());
            viewHolder.mArrowImageView.setImageDrawable(arrow);

        }
        else
        {
            if( null != viewHolder.mTitleTextView)
            {
                viewHolder.mTitleTextView.setText(mItemList.get(position).getTitle());

            }

        }


        return row;
    }
}


