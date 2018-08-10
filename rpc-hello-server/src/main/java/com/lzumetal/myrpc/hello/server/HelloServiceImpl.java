package com.lzumetal.myrpc.hello.server;

import com.lzumetal.myrpc.hello.api.HelloService;
import com.lzumetal.myrpc.server.annotation.MyRpcService;

/**
 * rpc接口实现
 *
 * @author liaosi
 * @date 2018-08-05
 */
@MyRpcService(HelloService.class)
public class HelloServiceImpl implements HelloService {

    @Override
    public String say(String name) {
        return "hello" + name;
    }

}
