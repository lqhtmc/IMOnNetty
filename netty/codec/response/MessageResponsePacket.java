package com.sc.netty.codec.response;

import com.sc.netty.codec.Command;
import com.sc.netty.codec.Packet;

public class MessageResponsePacket extends Packet {
    private String message;

    private String fromUserId;

    private String fromUserName;

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    @Override
    public Byte getCommand() {

        return Command.MESSAGE_RESPONSE;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
