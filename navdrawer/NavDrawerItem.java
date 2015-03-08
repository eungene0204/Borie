package siva.borie.navdrawer;

/**
 * Created by Eungjun on 2015-02-16.
 */

import siva.borie.navdrawer.NavDrawerUtils.ItemType;
import siva.borie.navdrawer.NavDrawerUtils.ItemId;

public class NavDrawerItem
{

    private String mTitle;
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

    public void setTitle(final String title)
    {
        mTitle = title;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setItemType(final ItemType type)
    {
        mType = type;
    }

    public ItemType getItemType()
    {
        if(null == mType)
            mType = ItemType.ITEM;

        return mType;
    }

    public void setItemId(ItemId id)
    {
        this.mId = id;
    }

    public ItemId getItemId()
    {
        return this.mId;
    }

}
