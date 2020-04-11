package com.sc.netty.codec.request;

import com.sc.netty.codec.Command;
import com.sc.netty.codec.Packet;

/**
 * 请求消息
 */
public class MessageRequestPacket extends Packet {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
