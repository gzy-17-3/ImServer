package com.gzy.im.core.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

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

    /**
     * 包含
     * @return
     */
    @SafeVarargs
    public static  <T> boolean contains(T target, T ...sample1){
        return Arrays.stream(sample1).anyMatch(new Predicate<T>() {
            @Override
            public boolean test(T t) {
                return target.equals(t);
            }
        });
    }

    /**
     * 比对
     * @return
     */
    public static  <T> boolean isEquality(T i1_1, T i1_2, T i2_1, T i2_2){
        List<T> l1 = new ArrayList<>();
        List<T> l2 = new ArrayList<>();

        l1.add(i1_1);
        l1.add(i1_2);

        l2.add(i2_1);
        l2.add(i2_2);

        return isEquality(l1,l2);
    }

    /**
     * 比对集合 是否一致 （顺序不论）
     * @return
     */
    public static  <T extends Object> boolean isEquality(List<T> i1,List<T> i2){
        List<T> i2_copy = new ArrayList<>(i2);

        if (i1.size() != i2.size()){
            return false;
        }

        for (T t : i1) {
            if (i2_copy.contains(t)) {
                i2_copy.remove(t);
            }
        }
        return i2_copy.size() == 0;
    }


}
