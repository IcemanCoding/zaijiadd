package com.zaijiadd.app.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * applicationContext的帮助类
 */

@Component
public class SpringContextHolder implements ApplicationContextAware{

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext () {
        return applicationContext;
    }

    public static <T> T getBean (String beanId) {
        return  (T)applicationContext.getBean(beanId);
    }

    public static <T> T getBean (Class<T> classType){
        return applicationContext.getBean(classType);
    }
}
