package org.geekbang.projects.cs.im.util;

import org.apache.commons.lang3.ObjectUtils;

public class CheckUtil {
    /**
     * 判断所有字段非空
     * 如有一个或多个字段为空，则返回false
     * 全部非空返回true
     * @param objs
     * @return
     */
    public static final boolean isAllNotEmpty(Object... objs){
        for (Object object : objs){
            if (ObjectUtils.isEmpty(object)){
                return false;
            }
        }
        return true;
    }
}
