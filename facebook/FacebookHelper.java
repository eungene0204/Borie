package siva.borie.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/**
 * Created by Eungjun on 2015-03-31.
 */
public class FacebookHelper
{
    public static final String TAG = FacebookHelper.class.getSimpleName();

    public static final String TAG_ID = "id";
    public static final String TAG_NAME = "name";
    public static final String TAG_EMAIL = "email";
    public static final String TAG_DATA = "data";
    public static final String TAG_PICTURE = "picture";
    public static final String TAG_URL = "url";


    static volatile private FacebookHelper mInstance;

    private ProfileTracker mProfileTracker;
    private AccessTokenTracker mAccessTokenTracker;

    private boolean mIsLogin = false;

    private FacebookHelper()
    {
    }

    public static FacebookHelper getInstance()
    {
        if (null == mInstance)
        {
            synchronized (FacebookHelper.class)
            {
                if (null == mInstance)
                {
                    mInstance = new FacebookHelper();


                }
            }
        }

        return mInstance;
    }


    public void init(LoginButton button)
    {
        setLoginButton(button);
        setCallBackManager();
        registerFacebookCallback();

        mAccessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken)
            {
                Log.i(TAG, "onCurrentAccessTokenChanged");

                if (null == currentAccessToken)
                {
                    Log.d(TAG, "Logout");
                    mFBAuthListener.onCurrentAccessTokenChanged(oldAccessToken,currentAccessToken);

                }
                else
                {
                    Log.d(TAG, "Login");

                    mFBAuthListener.onCurrentAccessTokenChanged(oldAccessToken, currentAccessToken);

                }

            }
        };


        mProfileTracker = new ProfileTracker()
        {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile)
            {
                Log.d(TAG, "ProfileChanged ");

            }


        };


        startAccessTokenTracker();
        startProfileTracker();
    }

    private LoginButton mLoginButton;

    public void setLoginButton(final LoginButton button)
    {
        this.mLoginButton = button;

    }

    public void graphRequest()
    {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response)
                    {
                        Log.i(TAG,"Request onCompleted");

                        Log.i(TAG, object.toString());

                        parseJSONObject(object);

                        mFBAuthListener.onRequestCompleted(getFacebookUserInfo());

                    }
                }
                );

        Bundle parameter = new Bundle();
        parameter.putString("fields", "id,name,picture,email,bio");
        request.setParameters(parameter);
        request.executeAsync();

    }


    private String mId;
    private String mName;
    private String mProfileImageURL;
    private String mEmail;

    private void parseJSONObject(final JSONObject object)
    {
        try
        {
            mId = object.getString(TAG_ID);
            mName = object.getString(TAG_NAME);
            mEmail = object.getString(TAG_EMAIL);
            mProfileImageURL = object.getJSONObject(TAG_PICTURE).getJSONObject(TAG_DATA).
                    getString(TAG_URL);

            Log.i(TAG, "id: " + mId);
            Log.i(TAG, "name: " + mName);
            Log.i(TAG, "email: " + mEmail);
            Log.i(TAG, "url: " + mProfileImageURL);

        }
        catch (JSONException e)
        {
            Log.e(TAG, "JSONException: " + e.toString());
            e.printStackTrace();
        }

    }

    private Bitmap getFacebookProfilePicture(final String userId, final String url)
    {
        Bitmap bitmap = null;

        try
        {
            bitmap = new AsyncTask<Void, Void, Bitmap>()

            {
                @Override
                protected Bitmap doInBackground(Void... params)
                {
                    Bitmap mBitmap = null;

                    try
                    {
                        Log.i(TAG, "async url: " + url);

                        URL imageURL = new URL(url);
                        mBitmap = BitmapFactory.
                                decodeStream(imageURL.openConnection().getInputStream());

                        if(null == mBitmap)
                            Log.e(TAG, "profile bitmap is null");

                    }
                    catch (MalformedURLException e)
                    {
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        Log.e(TAG, e.toString());
                    }

                    return mBitmap;
                }

            }.execute().get();

        } catch (InterruptedException e)
        {
            e.printStackTrace();
        } catch (ExecutionException e)
        {
            e.printStackTrace();
        }

        return bitmap;

    }

    public FacebookUser getFacebookUserInfo()
    {
        FacebookUser user = null;
        Bitmap bitmap  = getFacebookProfilePicture(mId,mProfileImageURL);

        user = new FacebookUser(mId,mName,mEmail,bitmap);

        return user;

    }

    /*
    =======================================================

            addReadPermissions

    ========================================================
     */
    public void addReadPermissions(final Activity activity)
    {
        try
        {
            //First parameter can be fragment. need to change
            LoginManager.getInstance().
                    logInWithReadPermissions(activity,
                            Arrays.asList("user_posts, email, public_profile"));

            Log.i(TAG, "addReadPermissions");
        }
        catch (Exception e)
        {
            Log.e(TAG, e.toString());
        }
    }

    private CallbackManager mCallBackManager;

    public void setCallBackManager()
    {
        mCallBackManager = CallbackManager.Factory.create();

    }

   /*
    ===============================================

      Login

    ================================================
     */
    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>()
    {
        @Override
        public void onSuccess(LoginResult loginResult)
        {
            Log.d(TAG, "onSuccess");

            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();

            if (null != profile)
            {

            }
        }

        @Override
        public void onCancel()
        {

        }

        @Override
        public void onError(FacebookException error)
        {

        }
    };


    public void startAccessTokenTracker()
    {
        mAccessTokenTracker.startTracking();
    }

    public void stopAcccesTokenTracker()
    {
        mAccessTokenTracker.stopTracking();
    }

    public void startProfileTracker()
    {
        mProfileTracker.startTracking();
    }

    public void stopProfileTracker()
    {
        mProfileTracker.stopTracking();
    }

    public boolean isTracking()
    {
        return mAccessTokenTracker.isTracking();
    }


    public void registerFacebookCallback()
    {

        mLoginButton.registerCallback(mCallBackManager, mFacebookCallback);

    }


    public void sdkInit(final Context context)
    {
        FacebookSdk.sdkInitialize(context);
    }


    public void onActivityResult(final int requestCode, final int resultCode, final Intent data)
    {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);

    }

    public boolean isLogin()
    {
        mIsLogin = AccessToken.getCurrentAccessToken() != null;


        return mIsLogin;
    }


    public void printFacebookHashKey(final Activity activity)
    {
        try
        {
            PackageInfo info = activity.getPackageManager().getPackageInfo("siva.borie"
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



    private FacebookAuthCallbackListener mFBAuthListener;

    public void setFacebookCallBackListener(final FacebookAuthCallbackListener listener)
    {
        this.mFBAuthListener = listener;

    }


    public interface FacebookAuthCallbackListener
    {
        void onCurrentAccessTokenChanged(final AccessToken oldToken, final AccessToken currentToken);
        void onRequestCompleted(final FacebookUser user);
    }


}
