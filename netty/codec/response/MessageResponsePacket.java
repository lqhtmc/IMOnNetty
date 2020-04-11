package com.sc.netty.codec.response;

import com.sc.netty.codec.Command;
import com.sc.netty.codec.Packet;

public class MessageResponsePacket extends Packet {
    private String message;

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
