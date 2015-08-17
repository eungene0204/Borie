package siva.borie.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.AccessToken;
import com.facebook.login.widget.LoginButton;

import siva.borie.R;
import siva.borie.facebook.FacebookHelper;
import siva.borie.facebook.FacebookUser;
import siva.borie.main.MainActivity;

public class LoginActivity extends ActionBarActivity implements
        FacebookHelper.FacebookAuthCallbackListener
{

    private static final String TAG = LoginActivity.class.getSimpleName();

    private FacebookHelper mFacebookHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mFacebookHelper = FacebookHelper.getInstance();
        mFacebookHelper.sdkInit(getApplicationContext());

        setContentView(R.layout.activity_auth_log_in);

        LoginButton button = (LoginButton) findViewById(R.id.fb_login_button);

        mFacebookHelper.init(button);
        mFacebookHelper.setFacebookCallBackListener(this);

        //For test
        if(mFacebookHelper.isTracking())
        {
            Log.d(TAG, "isTacking");
        }

        if(mFacebookHelper.isLogin())
        {
            startMainActivity();
            Log.d(TAG, "Login");
        }

    }

    @Override
    protected void onStop()
    {
        super.onStop();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG,"onActivityResult");
        mFacebookHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
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

        return super.onOptionsItemSelected(item);
    }

    private void startMainActivity()
    {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);
        finish();
    }

    @Override
    public void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken currentToken)
    {
        if( null != currentToken)
            startMainActivity();

    }

    @Override
    public void onRequestCompleted(FacebookUser user)
    {

    }
}
