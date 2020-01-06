package com.gzy.im.core.math;

public class Logic {
    /**
     * 排重
     * @return
     */
    public static  <T extends Object> T removeDuplicate(T target,T sample1, T sample2){
        if (target.equals(sample1)){
            return sample2;
        }
        return sample1;
    }


}
