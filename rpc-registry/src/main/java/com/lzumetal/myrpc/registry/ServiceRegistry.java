package com.lzumetal.myrpc.registry;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.security.auth.login.Configuration;

/**
 * @author liaosi
 * @date 2018-08-18
 */
public class ServiceRegistry {

    private static final Logger log = LoggerFactory.getLogger(ServiceRegistry.class);

    @Value("${rpc.registry_address}")
    private String zkAddress;

    private ZkClient zkClient;

    @PostConstruct
    public void init() {
        zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
        log.debug("connect to zookeeper!");
    }


    public void registry(String serviceName, String serviceAddress) {

        //创建registry节点（持久）
        String registryPath = Constant.ZK_REGISTRY_PATH;
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
            log.debug("create registry node : {}", registryPath);
        }

        //创建service节点（持久）
        String servicePath = registryPath + "/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            log.debug("create service node : {}", servicePath);
        }

        //创建address节点（临时）
        String addressPath = servicePath + "/" + "address";
        zkClient.createEphemeralSequential(addressPath, serviceAddress);
        log.debug("create address node : {}", addressPath);
    }


}
