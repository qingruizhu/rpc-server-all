package com.ddup.rpc.spring;

import com.ddup.rpc.ProcessorHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RPC服务端 集成 spring
 */
public class RpcServerPublisher implements ApplicationContextAware, InitializingBean {

    private int port;
    private Map<String, Object> handlerMap  =  new HashMap<String, Object>();
    /**
     * 缓存线程池
     */
    private ExecutorService executorService = Executors.newCachedThreadPool();

    public RpcServerPublisher(int port) {
        this.port = port;
    }

    /**
     * 启动服务
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                // 利用多线程处理任务
                executorService.execute(new ProcessorHandler(socket, handlerMap));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 利用容器处理自定义的RPC注解
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(RpcServiceAnno.class);
        if (beansWithAnnotation != null) {
            for (Object bean : beansWithAnnotation.values()) {
                // 处理注解
                RpcServiceAnno rpcServiceAnno = bean.getClass().getAnnotation(RpcServiceAnno.class);
                String serviceName = rpcServiceAnno.value().getName();
                // 处理version
                String version = rpcServiceAnno.version();
                serviceName = StringUtils.isEmpty(version) ? serviceName : serviceName+"-"+version;
                System.out.println("IOC publish service ---> "+serviceName);
                handlerMap.put(serviceName, bean);
            }
        }
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}

