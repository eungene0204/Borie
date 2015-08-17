package siva.borie.facebook;

import android.graphics.Bitmap;

/**
 * Created by Eungjun on 2015-07-21.
 */
public class FacebookUser
{
    private String mId;
    private String mName;
    private String mEmail;
    private Bitmap mProfilePicture;

    public FacebookUser()
    {
    }

    public FacebookUser(String mId, String mName, String mEmail, Bitmap mProfilePicture)
    {
        this.mId = mId;
        this.mName = mName;
        this.mEmail = mEmail;
        this.mProfilePicture = mProfilePicture;
    }

    public String getmName()
    {
        return mName;
    }

    public void setmName(final String mName)
    {
        this.mName = mName;
    }

    public String getmId()
    {
        return mId;
    }

    public void setmId(final String mId)
    {
        this.mId = mId;
    }

    public String getmEmail()
    {
        return mEmail;
    }

    public void setmEmail(final String mEmail)
    {
        this.mEmail = mEmail;
    }

    public void setProfilePicture(final Bitmap picture)
    {
        this.mProfilePicture = picture;

    }

    public Bitmap getProfilePicure()
    {
        //ProfilePicture can be null
        //Need to deal with it
        return this.mProfilePicture;
    }
}
