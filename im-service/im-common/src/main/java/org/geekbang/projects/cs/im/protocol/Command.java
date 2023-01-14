package org.geekbang.projects.cs.im.protocol;

public interface Command {
    /**
     * 心跳包
     */
    final Byte HEART_BEAT = 0;
    /**
     * 登录请求
     */
    final Byte LOGIN_REQUEST = 1;
    /**
     * 登录响应
     */
    final Byte LOGIN_RESPONSE = 2;

    /**
     * 消息请求
     */
    final Byte MESSAGE_REQUEST = 3;
    /**
     * 消息响应
     */
    final Byte MESSAGE_RESPONSE = 4;

    /**
     * 默认错误
     */
    final Byte DEFAULT_ERROR = 127;
}
