package siva.borie.navdrawer;

import java.util.ArrayList;
import java.util.List;

import siva.borie.navdrawer.NavDrawerUtils.ItemId;

/**
 * Created by Eungjun on 2015-09-19.
 */
public class DrawerListManager
{
    private List<NavDrawerItem> mItemList;

    static DrawerListManager sInstance;

    private DrawerListManager()
    {
        initData();
    }

    static DrawerListManager getInstance()
    {
        if(null == sInstance)
            sInstance = new DrawerListManager();

        return sInstance;
    }

    public List<NavDrawerItem> getItemList()
    {
        //Need to do refactoring
        return mItemList;
    }

    private void initData()
    {
         mItemList = new ArrayList<NavDrawerItem>();

        //Add User info item
        NavDrawerItem userInfo = new NavDrawerItem(ItemId.USER_INFO);
        userInfo.setTitle("USER");
        mItemList.add(userInfo);

        //Add Title
        NavDrawerItem serviceTitle = new NavDrawerItem(ItemId.SUB_HEADER);
        serviceTitle.setTitle("Services");
        mItemList.add(serviceTitle);

        //Add location based recommended service
        NavDrawerItem lbsServicve = new NavDrawerItem( ItemId.LBS_SERVICE);
        lbsServicve.setTitle("LBS");
        mItemList.add(lbsServicve) ;

        //Add All Service
        NavDrawerItem allService = new NavDrawerItem(ItemId.ALL_SERVICE);
        allService.setTitle("All Service");
        mItemList.add(allService);

        //Add Visited Service
        NavDrawerItem visitedService = new NavDrawerItem(ItemId.VISITED_SERVICE);
        visitedService.setTitle("Visited Service");
        mItemList.add(visitedService);

        //Add Tool Title
        NavDrawerItem toolTitle= new NavDrawerItem(ItemId.SUB_HEADER);
        toolTitle.setTitle("Tools");
        mItemList.add(toolTitle);

        //Add Setting
        NavDrawerItem setting = new NavDrawerItem(ItemId.SETTING);
        setting.setTitle("Setting");
        mItemList.add(setting);

        //Add FeedBack
        NavDrawerItem feedBack = new NavDrawerItem(ItemId.FEED_BACK);
        feedBack.setTitle("Feedback");
        mItemList.add(feedBack);

        //Add Share
        NavDrawerItem share = new NavDrawerItem(ItemId.SHARE);
        share.setTitle("Share");
        mItemList.add(share);
    }
}
