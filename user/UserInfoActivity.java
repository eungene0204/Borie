package siva.borie.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.facebook.login.widget.LoginButton;

import siva.borie.R;
import siva.borie.auth.LoginActivity;
import siva.borie.facebook.FacebookHelper;

/**
 * Created by Eungjun on 2015-05-26.
 */
public class UserInfoActivity extends Activity implements View.OnClickListener
{
    static final String TAG = UserInfoActivity.class.getSimpleName();

    private LoginButton mFacebookButton;
    private FacebookHelper mFbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        mFbHelper = FacebookHelper.getInstance();

        mFacebookButton = (LoginButton) findViewById(R.id.user_info_fb_login_button);
        mFacebookButton.setOnClickListener(this);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");
        mFbHelper.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    public void onClick(View v)
    {
       if(v.getId() == R.id.user_info_fb_login_button)
       {
           Log.i(TAG, "onClick");

       }
    }

    private void startLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(intent);

    }
}

