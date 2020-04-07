package com.sc.netty.codec;

import com.sc.netty.codec.request.LoginRequestPacket;
import com.sc.netty.codec.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * 编解码器
 */
public class PacketCodeC {
    //魔术
    private static final int MAGIC_NUMBER = 0x12345678;

    //编解码器实例
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    /**
     * 编码：将对象编码成二进制流
     * @param packet
     * @return
     */
    public ByteBuf encode(ByteBufAllocator allocator, Packet packet) {
        //ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        ByteBuf byteBuf = allocator.ioBuffer();
        byte[] bytes = Serializer.DEFAULT.serialize(packet);
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
        return byteBuf;
    }

    /**
     * 解码：二进制流解码成对象
     * @param byteBuf
     * @return
     */
    public Packet decode(ByteBuf byteBuf) {
        byteBuf.skipBytes(4);//跳过魔数
        byteBuf.skipBytes(1);//跳过版本号
        //算法
        byte serializeAlgorith = byteBuf.readByte();
        //指令
        byte command = byteBuf.readByte();
        //数组长度
        int length = byteBuf.readInt();
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);
        //请求类型
        Class<? extends Packet> requestType = this.getRequestType(command);
        //序列化算法
        Serializer serializer = Serializer.DEFAULT;
        if(requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    Class<? extends  Packet> getRequestType(Byte command) {
        if(command.equals(Command.LOGIN_REQUEST)) {
            return LoginRequestPacket.class;
        } else if(command.equals(Command.LOGIN_RESPONSE)) {
            return LoginResponsePacket.class;
        }
        return null;
    }
}
