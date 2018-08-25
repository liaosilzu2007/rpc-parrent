package com.lzu.objenesis.test;

import com.lzumetal.myrpc.common.decode.RpcDecoder;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisSerializer;

/**
 * @author liaosi
 * @date 2018-08-25
 */
public class MainTest {


    public static void main(String[] args) {
        Objenesis objenesis = new ObjenesisSerializer(true);
        RpcDecoder bean = objenesis.newInstance(RpcDecoder.class);
        System.out.println(bean);
    }

}
