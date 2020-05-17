package com.app.util;


import sun.reflect.Reflection;


import org.apache.log4j.Logger;



public class Log4JUtils {
    private static Logger logger = null;

    public static Logger getLogger(){
        if (null == logger){
            logger = Logger.getLogger(Reflection.getCallerClass(2).getName());
        }
        return logger;
    }
}
