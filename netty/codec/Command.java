package com.sc.netty.codec;

/**
 * 指令集
 */
public interface Command {
    Byte LOGIN_REQUEST = 1;
    Byte LOGIN_RESPONSE = 2;
}
