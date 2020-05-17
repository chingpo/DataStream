package com.app.dao;

import java.util.Arrays;
import java.util.List;


public class GetGroup {

    /**
     * do some filter function
     * @param group
     * @return
     */
    public static boolean getGroup(String group) {

        String[] stringArray = {"target1", "target2"};

        List<String> tempList = Arrays.asList(stringArray);

        if (tempList.contains(group)) {
            return true;
        } else {
            return false;
        }
    }
}
