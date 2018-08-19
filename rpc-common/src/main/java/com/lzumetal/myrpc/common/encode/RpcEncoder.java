package com.lzumetal.myrpc.common.encode;

import com.lzumetal.myrpc.common.utils.SerializationUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang3.SerializationUtils;

/**
 * Rpc编码器
 * @author liaosi
 * @date 2018-08-18
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;


    public RpcEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    /**
     * 将Object对象序列化成byte[]数据。
     * 在Netty中，将byte[]封装到ByteBuf对象中，并将ByteBuf对象放在Channel中传输。
     * @param channelHandlerContext
     * @param obj
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object obj, ByteBuf byteBuf) throws Exception {
        if (genericClass.isInstance(obj)) {
            byte[] bytes = SerializationUtil.serialize(obj);
            byteBuf.writeInt(bytes.length);
            byteBuf.writeBytes(bytes);
        }
    }

}
