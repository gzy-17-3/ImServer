package com.gzy.im.core;

import com.gzy.im.model.Account;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;

public class AppBeanUtils {
    /**
     * 获取空 属性名
     *
     *

     String[] nullPropertyNames = AppBeanUtils.getNullPropertyNames(studentPara);
     BeanUtils.copyProperties(studentPara, bySid, nullPropertyNames);

     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    /**
     * 拷贝非空字段到指定对象
     * @param fromObject 从此对象
     * @param toObject 拷贝到该对象
     */
    public static void copyNotNullProperties(Account fromObject, Account toObject) {
        String[] nullPropertyNames = AppBeanUtils.getNullPropertyNames(fromObject);
        BeanUtils.copyProperties(fromObject, toObject, nullPropertyNames);
    }
}
