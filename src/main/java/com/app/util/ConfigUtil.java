package com.app.util;


import org.apache.log4j.Logger;
import sun.reflect.Reflection;

import java.io.*;

import java.util.*;

/**
 * read config
 */
public  class ConfigUtil
{
    private static Properties propertie;
    private static FileInputStream inputFile;

    /**
     * override
     * @param key 
     * @return 
     */
    public static String  getValue(String key)
    {
        if (null==propertie){
            propertie = new Properties();
        }
        String filePath=ConfigUtil.class.getClassLoader().getResource("application.properties").getPath();
        try {
            inputFile = new FileInputStream(filePath);
            propertie.load(inputFile);
            inputFile.close();
        } catch (FileNotFoundException ex) {
            System.out.println("file not exist");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("failed!");
            ex.printStackTrace();
        }
        if(propertie.containsKey(key)){
            String value = propertie.getProperty(key);/
            return value;
        }
        else
            return "";
    }//end getValue(...)





    public static void main(String[] args)
    {
//        Configuration rc = new Configuration();//relative path
//        String hosts=rc.getValue("elasticsearch.hosts");

        String hosts= ConfigUtil.getValue("elasticsearch.hosts");
//        String redis = rc.getValue("redis.host");//read properties
//        String auth = rc.getValue("redis.auth");

        System.out.println(hosts);


    }//end main()

}//end class ReadConfigInfo


