package siva.borie.business.listmanager;

import java.util.ArrayList;
import java.util.List;

import siva.borie.business.Business;

/**
 * Created by Eungjun on 2015-09-16.
 */
public class RecommendListManager
{
    private List<Business> mBusinessList;
    private static RecommendListManager sInstance;

    private RecommendListManager()
    {
        mBusinessList = new ArrayList<Business>();
        initDataSet();

    }

    private void initDataSet()
    {
        Business item1 = new Business();
        item1.setName("test1");
        mBusinessList.add(item1);

        Business item2 = new Business();
        item2.setName("test2");
        mBusinessList.add(item2);

    }

    public static RecommendListManager getInstance()
    {
        if(null ==  sInstance )
            sInstance = new RecommendListManager();

        return sInstance;
    }


    public List<Business> getBusinessList()
    {
        //Need to change this should not return list;
        return mBusinessList;
    }

}
