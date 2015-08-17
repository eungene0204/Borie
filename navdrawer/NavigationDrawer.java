package siva.borie.navdrawer;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
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
import siva.borie.facebook.FacebookUser;
import siva.borie.user.UserInfoActivity;
import siva.borie.viewpager.ViewPagerAdapter;

import static siva.borie.navdrawer.NavDrawerUtils.ItemId;


/**
 * Created by Eungjun on 2015-02-16.
 */
public class NavigationDrawer
{
    private static final String TAG = "NavigationDrawer" ;

    private final Activity mActivity;
    private final ActionBarDrawerToggle mToggle;
    private final ViewPager mViewPager;
    private final ListView mDrawerMainListView;
    private final ArrayList<NavDrawerItem> mUserInfoList;
    private DrawerLayout mDrawerLayout;
    private FacebookUser mFacebookUser = null;
    private NavDrawerListAdapter mAdapter;
    private ArrayList<NavDrawerItem> mMainItemList;
    private boolean mIsUserMenu = false;

    public NavigationDrawer(final Activity context, final ViewPager viewpager )
    {
        Log.i(TAG, "NavigationDrawer");

        this.mActivity = context;

        mViewPager = viewpager;

        mUserInfoList = new ArrayList<NavDrawerItem>();

        mDrawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, R.string.app_name, R.string.app_name)
        {
            View.OnClickListener listener = getToolbarNavigationClickListener();

            @Override
            public void onDrawerOpened(View drawerView)
            {
                super.onDrawerOpened(drawerView);
                Log.i(TAG, "onDrawerOpened");


            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                Log.i(TAG, "onDrawerClosed");

                mIsUserMenu = false;

                mAdapter.setItemList(mMainItemList);
                mAdapter.notifyDataSetChanged();

            }
        };

        mDrawerLayout.setDrawerListener(mToggle);
        mDrawerMainListView = (ListView) mActivity.findViewById(R.id.drawer_main_listview);
        mDrawerMainListView.setOnItemClickListener(new MainItemClickListener());

        initAdapter();
    }

    private void initAdapter()
    {
        Log.i(TAG, "initAdapter");

        mMainItemList = new ArrayList<NavDrawerItem>();

        //Add User info item
        NavDrawerItem userInfo = new NavDrawerItem(ItemId.USER_INFO);
        userInfo.setTitle("USER");
        mMainItemList.add(userInfo);

        //Add Title
        NavDrawerItem serviceTitle = new NavDrawerItem(ItemId.SUB_HEADER);
        serviceTitle.setTitle("Services");
        mMainItemList.add(serviceTitle);

        //Add location based recommended service
        NavDrawerItem lbsServicve = new NavDrawerItem( ItemId.LBS_SERVICE);
        lbsServicve.setTitle("LBS");
        mMainItemList.add(lbsServicve) ;

        //Add All Service
        NavDrawerItem allService = new NavDrawerItem(ItemId.ALL_SERVICE);
        allService.setTitle("All Service");
        mMainItemList.add(allService);

        //Add Visited Service
        NavDrawerItem visitedService = new NavDrawerItem(ItemId.VISITED_SERVICE);
        visitedService.setTitle("Visited Service");
        mMainItemList.add(visitedService);

        //Add Tool Title
        NavDrawerItem toolTitle= new NavDrawerItem(ItemId.SUB_HEADER);
        toolTitle.setTitle("Tools");
        mMainItemList.add(toolTitle);

        //Add Setting
        NavDrawerItem setting = new NavDrawerItem(ItemId.SETTING);
        setting.setTitle("Setting");
        mMainItemList.add(setting);

        //Add FeedBack
        NavDrawerItem feedBack = new NavDrawerItem(ItemId.FEED_BACK);
        feedBack.setTitle("Feedback");
        mMainItemList.add(feedBack);

        //Add Share
        NavDrawerItem share = new NavDrawerItem(ItemId.SHARE);
        share.setTitle("Share");
        mMainItemList.add(share);

        //Add Empty
        NavDrawerItem empty = new NavDrawerItem(ItemId.EMPTY);
        mMainItemList.add(empty);

        //List for UserInfo
        NavDrawerItem userInfoItem = new NavDrawerItem(ItemId.USER_INFO);
        mUserInfoList.add(userInfoItem);


        //Setting Adapter
        mAdapter = new NavDrawerListAdapter(mMainItemList, mActivity);
        mDrawerMainListView.setAdapter(mAdapter);

    }

    public void setFacebookUser(final FacebookUser user)
    {
        mFacebookUser = user;

    }

    public void notifyDataSetChanged()
    {
        mAdapter.notifyDataSetChanged();

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
        Log.i(TAG, "Toggle Item" + item.toString());

        return mToggle.onOptionsItemSelected(item);

    }

    private void showUserInfoMenu()
    {
        Log.i(TAG, "showUserInforMenu");

        mIsUserMenu = true != mIsUserMenu;


        if(true == mIsUserMenu)
        {
            mAdapter.setItemList(mUserInfoList);
            mAdapter.notifyDataSetChanged();

        }
        else
        {
            mAdapter.setItemList(mMainItemList);
            mAdapter.notifyDataSetChanged();

        }


    }

    private void startUserInfoActivity()
    {
        Intent intent = new Intent(mActivity, UserInfoActivity.class);
        mActivity.startActivity(intent);
    }

    /*
    ============================================================

            Adapter

    =============================================================
     */
    private class NavDrawerListAdapter extends BaseAdapter
    {

        private final Activity mActivity;
        private ArrayList<NavDrawerItem> mItemList;

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

        public void setItemList(final ArrayList<NavDrawerItem> itemList)
        {
            this.mItemList = itemList;

        }


        /*
        =================================================

        getView

        ==================================================
         */

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            ViewHolder viewHolder;
            ItemId id = mItemList.get(position).getItemId();

            View row = convertView;
            if (null == row)
            {
                viewHolder = new ViewHolder();

                switch (id)
                {
                    case USER_INFO:
                        row = View.inflate(mActivity,R.layout.drawer_item_user_info,null);

                        /*
                        viewHolder.mImageView = (ImageView)
                                row.findViewById(R.id.user_info_profile_pic_imgview);
                                */
                        viewHolder.mImageView = (ImageView)
                                row.findViewById(R.id.user_info_circleimgView);

                        viewHolder.mTitle_tv = (TextView)
                                row.findViewById(R.id.user_info_user_name_tv);

                        viewHolder.mEmail_tv = (TextView)
                                row.findViewById(R.id.user_info_user_email_tv);

                        viewHolder.mArrowImg = (ImageView)
                                row.findViewById(R.id.user_info_arrow_img);

                        break;

                    case SUB_HEADER:
                        row = View.inflate(mActivity,R.layout.drawer_item_subheader, null);
                        viewHolder.mTitle_tv =
                                (TextView) row.findViewById(R.id.drawer_item_title);
                        break;

                    case LBS_SERVICE:
                        row = View.inflate(mActivity, R.layout.drawer_item_lbs_service, null);
                        viewHolder.mTitle_tv =
                                (TextView) row.findViewById(R.id.drawer_item_lbs_text);
                        break;

                    case ALL_SERVICE:
                        row = View.inflate(mActivity, R.layout.drawer_item_all_service, null);
                        viewHolder.mTitle_tv =
                                (TextView) row.findViewById(R.id.drawer_item_all_service_text);
                        break;

                    case VISITED_SERVICE:
                        row = View.inflate(mActivity, R.layout.drawer_item_visited_service, null);
                        viewHolder.mTitle_tv =
                                (TextView) row.findViewById(R.id.drawer_item_visited_service_text);
                        break;

                    case SETTING:
                        row = View.inflate(mActivity, R.layout.drawer_item_setting, null);
                        viewHolder.mTitle_tv =
                                (TextView) row.findViewById(R.id.drawer_item_setting_text);
                        break;

                    case FEED_BACK:
                        row = View.inflate(mActivity, R.layout.drawer_item_feedback, null);
                        viewHolder.mTitle_tv =
                                (TextView) row.findViewById(R.id.drawer_item_feedback_text);
                        break;

                    case SHARE:
                        row = View.inflate(mActivity, R.layout.drawer_item_share, null);
                        viewHolder.mTitle_tv =
                                (TextView) row.findViewById(R.id.drawer_item_share_text);
                        break;

                    default:
                        row = View.inflate(mActivity, R.layout.drawer_item_empty, null);
                        break;

                }

                row.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder) row.getTag();
            }

            //USER INFO
            if(id == ItemId.USER_INFO && null != mFacebookUser)
            {

                Log.i(TAG, "USER INFO GETVIEW");

                viewHolder.mImageView.setImageBitmap(mFacebookUser.getProfilePicure());
                viewHolder.mTitle_tv.setText(mFacebookUser.getmName().toUpperCase());
                viewHolder.mEmail_tv.setText(mFacebookUser.getmEmail());

                if( false == mIsUserMenu)
                {
                    Drawable arrowImg = mActivity.getResources()
                            .getDrawable(R.drawable.ic_arrow_drop_down_black_48dp);

                    viewHolder.mArrowImg.setImageDrawable(arrowImg);

                }
                else
                {
                     Drawable arrowImg = mActivity.getResources()
                            .getDrawable(R.drawable.ic_arrow_drop_up_black_48dp);

                    viewHolder.mArrowImg.setImageDrawable(arrowImg);

                }

            }
            else
            {

                if(null != viewHolder.mTitle_tv)
                {
                    viewHolder.mTitle_tv.setText(mItemList.get(position).getTitle());
                }

            }


            viewHolder.mId = mItemList.get(position).getItemId();


            return row;
        }
    }

    /*
    ===================================================

     ClickListener

     ===================================================
     */
    private class MainItemClickListener implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            ViewHolder viewHolder = (ViewHolder) view.getTag();

            ItemId itemId = viewHolder.mId;

            switch (itemId)
            {
                case USER_INFO:
                    showUserInfoMenu();
                    break;

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

            if(ItemId.USER_INFO != itemId)
                mDrawerLayout.closeDrawers();

        }
    }

    private class ViewHolder
    {
        public TextView mTitle_tv;
        public TextView mEmail_tv;
        public ImageView mImageView;
        public ImageView mArrowImg;
        public ItemId mId;
    }
}
