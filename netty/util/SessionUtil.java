package com.sc.netty.util;

import com.sc.netty.Session;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * session工具类
 */
public class SessionUtil {
    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<String, Channel>();

    private static final AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

    /**
     * 将session信息和客户端连接关联
     * @param session
     * @param channel
     */
    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(SESSION).set(session);
    }

    /**
     * 将session信息和客户端连接解绑
     * @param channel
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(SESSION).set(null);
        }
    }

    public static boolean hasLogin(Channel channel) {

        return channel.hasAttr(SESSION);
    }

    public static Session getSession(Channel channel) {

        return channel.attr(SESSION).get();
    }

    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
