package com.lzumetal.myrpc.server;

import com.lzumetal.myrpc.server.annotation.MyRpcService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.imageio.spi.ServiceRegistry;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liaosi
 * @date 2018-08-05
 */
@Component
public class RpcServer implements ApplicationContextAware, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(RpcServer.class);

    @Value("${rpc.port}")
    private int port;

    @Autowired
    private ServiceRegistry serviceRegistry;

    /**
     * 存放服务名称与服务实例之间的映射关系
     */
    private Map<String, Object> handlerMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        //扫描带有MyPpcService注解的服务类
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(MyRpcService.class);

        //将服务类放入一个map中
        if (!CollectionUtils.isEmpty(serviceBeanMap)) {
            for (Object serviceBean : serviceBeanMap.values()) {
                MyRpcService myRpcService = serviceBean.getClass().getAnnotation(MyRpcService.class);
                String serviceName = myRpcService.value().getName();
                handlerMap.put(serviceName, serviceBean);
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new RpcDecoder(RpcRequest.class));
                        pipeline.addLast(new RpcEecoder(RpcRequest.class));
                        pipeline.addLast(new RpcServerHandler(handlerMap));

                    }
                });

        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        log.error("server started, listening on {}", port);

        //注册rpc地址
        String serverAddress = InetAddress.getLocalHost().getHostAddress() + ":" + port;
        for (String interfaceName : handlerMap.keySet()) {
            serviceRegistry.register
            
        }

    }
}
