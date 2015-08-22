package siva.borie.navdrawer;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import siva.borie.R;
import siva.borie.facebook.FacebookHelper;
import siva.borie.facebook.FacebookUser;
import siva.borie.viewpager.ViewPagerAdapter;

import static siva.borie.navdrawer.NavDrawerUtils.ItemId;


/**
 * Created by Eungjun on 2015-02-16.
 */
public class NavigationDrawer
{
    private static final String TAG = "NaviagtionDrawer" ;

    private final Activity mActivity;

    private DrawerLayout mDrawerLayout;
    private final ActionBarDrawerToggle mToggle;
    private final ViewPager mViewPager;
    private final ListView mDrawerListView;
    private NavDrawerListAdapter mAdapter;

    public NavigationDrawer(Activity context, ViewPager viewpager)
    {
        Log.i(TAG, "NaviagtionDrawer contrt");

        this.mActivity = context;

        mViewPager = viewpager;

        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.setDrawerListener(mToggle);
        mDrawerListView = (ListView) mActivity.findViewById(R.id.drawer_main_listview);
        mDrawerListView.setOnItemClickListener(new ItemClickListener());

        initAdapter();
    }

    private void initAdapter()
    {
        Log.i(TAG,"init");
        ArrayList<NavDrawerItem> itemList = new ArrayList<NavDrawerItem>();

        //Add User info item
        NavDrawerItem userInfo = new NavDrawerItem(ItemId.USER_INFO);
        userInfo.setTitle("USER");
        itemList.add(userInfo);

        //Add Title
        NavDrawerItem serviceTitle = new NavDrawerItem(ItemId.SUB_HEADER);
        serviceTitle.setTitle("Services");
        itemList.add(serviceTitle);

        //Add location based recommended service
        NavDrawerItem lbsServicve = new NavDrawerItem( ItemId.LBS_SERVICE);
        lbsServicve.setTitle("LBS");
        itemList.add(lbsServicve) ;

        //Add All Service
        NavDrawerItem allService = new NavDrawerItem(ItemId.ALL_SERVICE);
        allService.setTitle("All Service");
        itemList.add(allService);

        //Add Visited Service
        NavDrawerItem visitedService = new NavDrawerItem(ItemId.VISITED_SERVICE);
        visitedService.setTitle("Visited Service");
        itemList.add(visitedService);

        //Add Tool Title
        NavDrawerItem toolTitle= new NavDrawerItem(ItemId.SUB_HEADER);
        toolTitle.setTitle("Tools");
        itemList.add(toolTitle);

        //Add Setting
        NavDrawerItem setting = new NavDrawerItem(ItemId.SETTING);
        setting.setTitle("Setting");
        itemList.add(setting);

        //Add FeedBack
        NavDrawerItem feedBack = new NavDrawerItem(ItemId.FEED_BACK);
        feedBack.setTitle("Feedback");
        itemList.add(feedBack);

        //Add Share
        NavDrawerItem share = new NavDrawerItem(ItemId.SHARE);
        share.setTitle("Share");
        itemList.add(share);

        mAdapter = new NavDrawerListAdapter(itemList, mActivity);
        mDrawerListView.setAdapter(mAdapter);

    }

    public void onPostCreate(Bundle savedInstanceState)
    {
        mToggle.syncState();
    }

    public void onConfigurationChanged(Configuration newConfig)
    {
        mToggle.onConfigurationChanged(newConfig);
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mToggle.onOptionsItemSelected(item))
            return true;

        return false;
    }

    public void notifyDataSetChanged()
    {
        mAdapter.notifyDataSetChanged();

    }

    private FacebookUser mFacebookUser;
    public void setFacebookUser(FacebookUser user)
    {
        mFacebookUser = user;

    }

    //Adapter
    private class NavDrawerListAdapter extends BaseAdapter
    {
        private final ArrayList<NavDrawerItem> mItemList;
        private final Activity mActivity;

        public NavDrawerListAdapter(ArrayList<NavDrawerItem> mItemList, Activity activity)
        {
            this.mItemList = mItemList;
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


        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            DrawerViewHolder viewHolder;
            ItemId id = mItemList.get(position).getItemId();

            View row = convertView;
            if (null == row)
            {
                viewHolder = new DrawerViewHolder();

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

    //Click Listener
    private class ItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            DrawerViewHolder viewHolder = (DrawerViewHolder) view.getTag();

            ItemId itemId= viewHolder.mId;

            switch (itemId)
            {
                case LBS_SERVICE:
                    mViewPager.setCurrentItem(ViewPagerAdapter.LBS_SERVICE_FRGMT);
                    break;

                case ALL_SERVICE:
                    mViewPager.setCurrentItem(ViewPagerAdapter.ALL_SERVICE_FRGMT);
                    break;

                case VISITED_SERVICE:
                    mViewPager.setCurrentItem(ViewPagerAdapter.VISITED_SERVICE_FRGMT);
                    break;

                default:
                    break;

            }

            mDrawerLayout.closeDrawers();
        }
    }

    private class DrawerViewHolder
    {
        public TextView mTitleTextView;
        public TextView mUserNameTextView;
        public TextView mEmailTextView;
        public ImageView mIconImageView;
        public ImageView mCircleImageivew;
        public ImageView mArrowImageView;
        public ItemId mId;
    }
}
