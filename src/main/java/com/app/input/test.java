package com.app.input;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;

import java.util.*;

public class test {
    public static void main(String[] args) {


        HashMap<String, Integer> map = new HashMap<>();


        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });


        for(Map.Entry<String, Integer> t:list){
            System.out.println(t.getKey()+":"+t.getValue());
        }


    @Test
    public void splitTest()  {
        String str = "{241=(li,241), 185=(zhang,185), 182=(wu,182), 142=(wang,142), 98=(zhao,98), 91=(mori,91), 77=(hayashi,77), 46=(lin,46), 37=(yamada,37), 36=(tanaka,36), 23=(suzuki,23), 22=(toyota,22), 19=(honnda,19), 16=(smith,16), 12=(chen,12), 9=(yuyuki,9), 8=(shibuya,8), 4=(yokohama,4), 3=(tokyo,3), 2=(kyoto,2), 2=(kinnjyo,2), 2=(ann,2), 2=(liu,2), 1=(hajime,1), 1=(saisyuu,1), 1=(first,1), 1=(new,1)}";
        String[] splitList = str.substring(1, str.length() - 1).split(", ");

        List oList = new ArrayList();
        for (String s : splitList) {
            System.out.println(s);
            String[] split = s.split("=");
            for (String s1 : split) {
                System.out.println(s1);
            }
            String s1 = split[1];
            String[] split1 = s1.substring(1, s1.length() - 1).split(",");
            for (String s2 : split1) {
                System.out.println(s2);
            }
            UserRank testBean = new UserRank();
            testBean.setUserid(split1[0]);
            testBean.setValue(Integer.valueOf(split1[1]));
            oList.add(testBean);
        }

        Gson gson = new Gson();
        String json = gson.toJson(oList);
        System.out.println(json);
        Collections.sort(oList);
        String json1 = gson.toJson(oList);
        System.out.println(json1);
    }

    public static HashMap<String,Integer> addRank(HashMap<String,Integer> map,String key,Integer value){
        Integer v=map.get(key);
        if (v<value){
            map.put(key,value);
        }
        return map;
    }

    private static <T> LogMessage<T> parseResult(String json) {
        return JSON.parseObject(json, new TypeReference<LogMessage<T>>() {
        });

    }
}

