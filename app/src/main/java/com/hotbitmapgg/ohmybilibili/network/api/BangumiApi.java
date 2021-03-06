package com.hotbitmapgg.ohmybilibili.network.api;

import com.hotbitmapgg.ohmybilibili.entity.bangumi.Bangumi;
import com.hotbitmapgg.ohmybilibili.entity.base.BasicMessage;
import com.hotbitmapgg.ohmybilibili.network.ApiHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * 番剧查询API
 */
public class BangumiApi
{

    public static final int BTYPE_ALL = -1, BTYPE_2D = 2, BTYPE_RETINA = 3;

    public static final int WD_ALL = -1, WD_SUN = 0, WD_MON = 1, WD_TUE = 2, WD_WED = 3, WD_THU = 4, WD_FRI = 5, WD_SAT = 6;

    public static BasicMessage<ArrayList<Bangumi>> getBangumi(int btype, int weekday)
    {

        String url = ApiHelper.getBangumiUrl(btype, weekday);
        BasicMessage<BangumiResult> result = ApiHelper.getSimpleUrlResult(url, BangumiResult.class);
        BasicMessage<ArrayList<Bangumi>> msg = new BasicMessage<>();
        if (result.getCode() == BasicMessage.CODE_SUCCEED)
        {
            msg.setObject(new ArrayList<>(result.getObject().list));
            msg.setCode(BasicMessage.CODE_SUCCEED);
        } else
        {
            msg.setCode(BasicMessage.CODE_ERROR);
        }
        return msg;
    }

    private class BangumiResult
    {

        public int results;

        public List<Bangumi> list;
    }
}
