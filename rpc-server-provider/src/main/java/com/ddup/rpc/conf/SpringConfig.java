package com.ddup.rpc.conf;

import com.ddup.rpc.spring.RpcServerPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * spring 配置类
 */
@Configuration
@ComponentScan("com.ddup.rpc")//包扫描路径
public class SpringConfig {

    @Bean
    public RpcServerPublisher rpcServerPublisher() {
        return new RpcServerPublisher(8080);//直接设置端口
    }
}

