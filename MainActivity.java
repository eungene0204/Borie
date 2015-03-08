package siva.borie;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import siva.borie.navdrawer.NavigationDrawer;
import siva.borie.viewpager.ViewPagerAdapter;


public class MainActivity extends ActionBarActivity
{

    public static final String TAG = "MainActivity";

    private NavigationDrawer mNavDrawer;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG,"onCreate");

        //Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

         //Init ViewPager
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

        Log.i(TAG, "after nav");

    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
         Log.i(TAG,"oncreateOptionMenu");
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        //Set NavigationDrawer
        mNavDrawer = new NavigationDrawer(this, mViewPager);

      //  mNavDrawer.onPostCreate(savedInstanceState);

        Log.i(TAG, "onPostCreate");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

       // mNavDrawer.onConfigurationChanged(newConfig);

        Log.i(TAG, "onConfigurationChanged");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        /*
        if(mNavDrawer.onOptionsItemSelected(item))
        {
            return true;
        }
        */

        Log.i(TAG,"onOptionItemSelected");

        return super.onOptionsItemSelected(item);
    }

}
