package siva.borie.navdrawer;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import siva.borie.R;
import siva.borie.facebook.FacebookUser;
import siva.borie.navdrawer.adapter.DrawerViewHolder;
import siva.borie.navdrawer.adapter.NavigationDrawerAdapter;
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
    private NavigationDrawerAdapter mAdapter;


    public NavigationDrawer(Activity context, ViewPager viewpager)
    {
        Log.i(TAG, "NaviagtionDrawer");

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

        mAdapter = new NavigationDrawerAdapter((ArrayList<NavDrawerItem>)
                DrawerListManager.getInstance().getItemList(),
                mActivity);
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

}
