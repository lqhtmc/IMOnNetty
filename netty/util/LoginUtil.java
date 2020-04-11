package com.sc.netty.util;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * 登录工具类
 */
public class LoginUtil {
    private static AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");
    /**
     * 使用Attribute标记客户端channel状态为登录
     * @param channel
     */
    public static void markAsLogin(Channel channel) {
        channel.attr(LOGIN).set(true);
    }

    /**
     * 判断是否登录
     * @param channel
     * @return
     */
    public static boolean isLogin(Channel channel) {
        if(channel.attr(LOGIN) == null) {
            return false;
        }
        Attribute<Boolean> loginAttr = channel.attr(LOGIN);
        return loginAttr.get() != null;
    }
}
