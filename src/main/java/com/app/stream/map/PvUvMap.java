package com.app.stream.map;


import com.alibaba.fastjson.JSONObject;
import com.app.dao.SetUserInfo;
import com.app.input.LogMessage;
import com.app.stream.analy.PvUv;
import com.app.stream.analy.UserState;
import com.app.util.DateUtil;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class PvUvMap implements FlatMapFunction<LogMessage, PvUv> {

    @Override
    public void flatMap(LogMessage value, Collector<PvUv> out) throws Exception {

        String group = value.getGroup();
        if (group == null ) {
            return;//group should not null
        }
        String[] strs = verify.split(":");
        Integer userid = Integer.valueOf(strs[0]);

        long timestamp = value.getDate().getTime();

        String hourtimestamp = DateUtil.getDateby(timestamp, "yyyyMMddhh");
        String daytimestamp = DateUtil.getDateby(timestamp, "yyyyMMdd");
        String monthtimestamp = DateUtil.getDateby(timestamp, "yyyyMM");

        UserState userState = SetUserInfo.getUserState(group, userid , timestamp);
        boolean isNew=userState.isIsnew();
        boolean isFirsthour = userState.isFisrthour();
        boolean isFisrtday = userState.isFisrtday();
        boolean isFisrtmonth = userState.isFisrtmonth();



        PvUv pvUv = new PvUv();
        pvUv.setGroup(group);
        pvUv.setUserid(userid);
        pvUv.setKey(userid+"_");
        pvUv.setUvcount(isNew == true ? 1 : 0);
        pvUv.setPvcount(1);
        pvUv.setTimestring(hourtimestamp);
        pvUv.setTimestamp(timestamp);
        out.collect(pvUv);


    }

}
