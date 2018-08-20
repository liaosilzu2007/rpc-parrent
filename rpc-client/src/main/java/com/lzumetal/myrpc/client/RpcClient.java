package com.lzumetal.myrpc.client;

import com.lzumetal.myrpc.common.bean.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * RPC客户端：用于创建RPC服务代理
 * @author liaosi
 * @date 2018-08-20
 */
@Component
public class RpcClient {

    private static final Logger log = LoggerFactory.getLogger(RpcClient.class);

    //存放请求编号和响应对象的映射关系
    private ConcurrentMap<String, RpcResponse> responseMap = new ConcurrentHashMap<>();




}
