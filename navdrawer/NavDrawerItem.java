package siva.borie.navdrawer;

/**
 * Created by Eungjun on 2015-02-16.
 */

import android.graphics.Bitmap;

import siva.borie.navdrawer.NavDrawerUtils.ItemId;
import siva.borie.navdrawer.NavDrawerUtils.ItemType;

public class NavDrawerItem
{

    private String mTitle = "";
    private Bitmap mBitmap;
    private NavDrawerUtils.ItemType mType;
    private NavDrawerUtils.ItemId mId;

    public NavDrawerItem(final ItemType mType, final ItemId id)
    {
        this.mType = mType;
        this.mId = id;
    }

    public NavDrawerItem(final ItemId id)
    {
        this.mId = id;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(final String title)
    {
        mTitle = title;
    }

    public ItemType getItemType()
    {
        if(null == mType)
            mType = ItemType.ITEM;

        return mType;
    }

    public void setItemType(final ItemType type)
    {
        mType = type;
    }

    public ItemId getItemId()
    {
        return this.mId;
    }

    public void setItemId(ItemId id)
    {
        this.mId = id;
    }

}
