package com.cqjtu.ets.onlinejudge.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class CommonUtil {
//    @Autowired
//    private static UserMapper mapper;

    public static long getUserId(HttpServletRequest req){
        String userName = (String) req.getSession().getAttribute("USERNAME");
//        return mapper.getUserIdByUserName(userName);
        return 1;
    }
    static ConfigurableApplicationContext applicationContext;

    public static <T> T getBean(Class<T> s){
        return applicationContext==null?null:applicationContext.getBean(s);
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        CommonUtil.applicationContext = applicationContext;
    }

    public static String getHomeDirectory(){
        return System.getProperty("user.home")+"/";
    }
}
