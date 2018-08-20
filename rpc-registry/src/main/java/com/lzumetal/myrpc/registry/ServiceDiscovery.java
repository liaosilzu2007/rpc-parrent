package com.lzumetal.myrpc.registry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 服务发现
 * @author liaosi
 * @date 2018-08-20
 */
@Component
public class ServiceDiscovery {

    private static final Logger log = LoggerFactory.getLogger(ServiceDiscovery.class);

    @Value("${rpc.registry_address")
    private String zkAddress;



}
