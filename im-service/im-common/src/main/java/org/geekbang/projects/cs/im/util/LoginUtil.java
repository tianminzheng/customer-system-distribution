package org.geekbang.projects.cs.im.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;

public class LoginUtil {
    /**
     * 标记登录状态
     */
    public static void markAsLogin(Channel channel){
        channel.attr(Attributes.LOGIN).set(true);
    }
    public static boolean hasLogin(Channel channel){
        Attribute<Boolean> login =  channel.attr(Attributes.LOGIN);
        //只要标志位不为空，即表示登录过
        if (login.get() != null)
            return true;
        return false;
    }
}
