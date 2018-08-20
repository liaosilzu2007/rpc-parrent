package com.lzumetal.myrpc.server.handler;

import com.lzumetal.myrpc.common.bean.RpcRequest;
import com.lzumetal.myrpc.common.bean.RpcResponse;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * Rpc服务端处理器（用于处理服务端请求）
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger log = LoggerFactory.getLogger(RpcServerHandler.class);

    private final Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }
    

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest req) throws Exception {
        //创建RPC响应对象
        RpcResponse response = new RpcResponse();
        response.setRequestId(req.getRequestId());
        try {
            //处理RPC请求成功
            Object result = handle(req);
            response.setResult(result);
        } catch (Exception e) {
            //处理RPC请求失败
            response.setException(e);
            log.error("handle result failure", e);
        }
        //写入RPC响应对象（写入完毕后立即关闭与客户端的连接）
        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }

    private Object handle(RpcRequest req) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取服务实例
        String interfaceName = req.getInterfaceName();
        Object serviceBean = handlerMap.get(interfaceName);
        if (serviceBean == null) {
            throw new RuntimeException(String.format("can not find service bean by key: %s", interfaceName));
        }

        //获取反射调用的参数
        Class<?> clazz = serviceBean.getClass();
        String methodName = req.getMethodName();
        Class<?>[] parameterTypes = req.getParameterTypes();
        Method method = clazz.getMethod(methodName, parameterTypes);
        Object[] parameters = req.getParameters();

        //执行反射调用
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("server caught exception", cause);
        ctx.close();    //关闭服务端与客户端的连接
    }


}
