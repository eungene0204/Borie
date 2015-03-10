package siva.borie.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import siva.borie.Businesses.fragments.AllServiceListFragment;
import siva.borie.Businesses.fragments.RecommendedServiceFragment;
import siva.borie.Businesses.fragments.VisitedServiceFragment;

/**
 * Created by Eungjun on 2015-02-16.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter
{
    public static final int LBS_SERVICE_FRGMT = 0;
    public static final int ALL_SERVICE_FRGMT = 1;
    public static final int VISITED_SERVICE_FRGMT = 2;

    private static final int NUM_PAGES = 3;

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;

        switch (position)
        {
            case LBS_SERVICE_FRGMT:
                fragment = new RecommendedServiceFragment();
                break;

            case ALL_SERVICE_FRGMT:
                fragment = new AllServiceListFragment();
                break;

            case VISITED_SERVICE_FRGMT:
                fragment = new VisitedServiceFragment();
                break;

            default:
                break;
        }

        return fragment;
    }

    @Override
    public int getCount()
    {
        return NUM_PAGES;
    }
}
