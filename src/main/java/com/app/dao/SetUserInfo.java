package com.app.dao;

import com.app.stream.analy.UserState;
import com.app.util.DateUtil;
import com.app.input.*;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;


public class SetUserInfo {


    /**
     * check user stats 
     *
     * @param userid
     * @param timestamp
     * @return
     */
    public static UserState getUserState(String group, Integer userid, long timestamp) {
        UserState userState = new UserState();
        try {
            String rowKey = group +":"+ userid;
            String result = HbaseUtil.getdata("pvfuninfo", rowKey, "info", "fisrtvisittime");
            if (result == null) {//first click
                Map<String, String> datamap = new HashMap<String, String>();
                datamap.put("fisrtvisittime", timestamp + "");
                datamap.put("lastvisittime", timestamp + "");
                HbaseUtil.put("pvfuninfo", rowKey, "info", datamap);
                userState.setIsnew(true);
                userState.setFisrtday(true);
                userState.setFisrthour(true);
                userState.setFisrtmonth(true);
            } else {
                String lastvisittimestring = HbaseUtil.getdata("pvfuninfo", rowKey, "info", "lastvisittime");
                if (StringUtils.isNotBlank(lastvisittimestring)) {
                    long lastvisittime = Long.valueOf(lastvisittimestring);
                    //count by hour
                    long timstamp = DateUtil.getDatebyConditon(timestamp, "yyyyMMddhh");
                    if (lastvisittime < timestamp) {
                        userState.setFisrthour(true);
                    }
                    //count by day
                    timstamp = DateUtil.getDatebyConditon(timestamp, "yyyyMMdd");
                    if (lastvisittime < timestamp) {
                        userState.setFisrtday(true);
                    }
                    //count by month
                    timstamp = DateUtil.getDatebyConditon(timestamp, "yyyyMM");
                    if (lastvisittime < timestamp) {
                        userState.setFisrtmonth(true);
                    }
                }
                HbaseUtil.putdata("userpvinfo", rowKey, "info", "lastvisittime", timestamp + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userState;
    }
}
