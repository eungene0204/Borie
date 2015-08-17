package siva.borie.main;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import siva.borie.R;
import siva.borie.auth.LoginActivity;
import siva.borie.facebook.FacebookHelper;
import siva.borie.facebook.FacebookUser;
import siva.borie.navdrawer.NavigationDrawer;
import siva.borie.viewpager.ViewPagerAdapter;


public class MainActivity extends ActionBarActivity implements
        FacebookHelper.FacebookAuthCallbackListener
{

    public static final String TAG = "MainActivity";

    private NavigationDrawer mNavDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ViewPager mViewPager;
    private FacebookHelper mFacebookHelper;
    private FacebookUser mFacebookUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(TAG, "onCreate");

        //Set toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Set toggle
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setDrawerListener(mDrawerToggle);


         //Init ViewPager
        mViewPager = (ViewPager) findViewById(R.id.main_viewpager);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(viewPagerAdapter);

        //Print Facebook Hash key
        printFacebookHashKey();

        //Facebook stuff
        FacebookHelper.getInstance().setFacebookCallBackListener(this);
        FacebookHelper.getInstance().addReadPermissions(this);
        FacebookHelper.getInstance().graphRequest();


    }


    private void printFacebookHashKey()
    {
        try
        {
            PackageInfo info = getPackageManager().getPackageInfo("siva.borie"
                    , PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i(TAG, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
         Log.i(TAG, "oncreateOptionMenu");
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);

        Log.i(TAG, "onPostCreate");

        //Set NavigationDrawer
        mNavDrawer = new NavigationDrawer(this, mViewPager);

        mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        mDrawerToggle.onConfigurationChanged(newConfig);

        Log.i(TAG, "onConfigurationChanged");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        Log.i(TAG,"onOptionItemSelected");

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
            return true;

        if(mDrawerToggle.onOptionsItemSelected(item))
            return true;


        return super.onOptionsItemSelected(item);
    }

    private void startLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }

    @Override
    public void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken currentToken)
    {
        Log.i(TAG, "onCurrentAcessTokenChanged");

        if(null == currentToken)
            startLoginActivity();


    }

    @Override
    public void onRequestCompleted(final FacebookUser user)
    {
        Log.i(TAG, "onRequestCompleted");

        try
        {
            mFacebookUser = user;

            Log.i(TAG, mFacebookUser.getmEmail());
            Log.i(TAG, mFacebookUser.getmId());
            Log.i(TAG, mFacebookUser.getmName());

            mNavDrawer.setFacebookUser(mFacebookUser);
            mNavDrawer.notifyDataSetChanged();


        }
        catch(Exception e)
        {
            Log.e(TAG, e.toString());
        }

    }
}
