package com.sc.netty.codec.request;

import com.sc.netty.codec.Command;
import com.sc.netty.codec.Packet;

/**
 * 请求消息
 */
public class MessageRequestPacket extends Packet {
    private String toUserId;

    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
