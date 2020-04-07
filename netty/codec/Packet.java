package com.sc.netty.codec;

public abstract class Packet {
    /**
     * 协议版本
     */
    private Byte version = 1;

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }

    /**
     * 获取命令
     * @return
     */
    public abstract Byte getCommand();
 }
