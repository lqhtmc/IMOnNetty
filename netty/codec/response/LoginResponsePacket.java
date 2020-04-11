package com.sc.netty.codec.response;

import com.sc.netty.codec.Command;
import com.sc.netty.codec.Packet;

public class LoginResponsePacket extends Packet {
    private boolean success;

    private String reason;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
