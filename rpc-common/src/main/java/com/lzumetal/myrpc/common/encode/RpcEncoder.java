package com.lzumetal.myrpc.common.encode;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang3.SerializationUtils;

/**
 * @author liaosi
 * @date 2018-08-18
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;


    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        byte[] bytes = SerializationUtil.ser;

    }
}
