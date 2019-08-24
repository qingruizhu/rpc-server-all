package com.ddup.rpc;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * 用以处理请求，反射实现
 */
public class ProcessorHandler implements Runnable {
    private Socket socket;
    private Map<String, Object> handlerMap;

    public ProcessorHandler(Socket socket, Map<String, Object> handlerMap) {
        this.socket = socket;
        this.handlerMap = handlerMap;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            RpcRequest request = (RpcRequest) ois.readObject();
            Object result = invoke(request);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            // 结果返回给客户端
            oos.writeObject(result);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public Object invoke(RpcRequest request) {
        String serviceName = request.getClassName();
        // 处理客户端 version
        String version = request.getVersion();
        serviceName = StringUtils.isEmpty(version) ? serviceName : serviceName + "-" + version;
        Object service = handlerMap.get(serviceName);
        if (null == service) {
            throw new RuntimeException("service not found  " + serviceName);
        }
        Object result = null;
        try {
            Class<?> clzz = Class.forName(request.getClassName());
            String methodName = request.getMethodName();
            // 获取形参类型
            Object[] parameters = request.getParameters();
            if (null == parameters) { // 无参方法
                Method method = clzz.getMethod(methodName);
                result = method.invoke(service);
            } else { // 带参方法
                Class<?>[] parameterTypes = new Class[parameters.length];
                for (int i = 0; i < parameters.length; i++) {
                    parameterTypes[i] = parameters[i].getClass();
                }
                // 找到方法
                Method method = clzz.getMethod(methodName, parameterTypes);
                //反射调用
                result = method.invoke(service, parameters);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}

