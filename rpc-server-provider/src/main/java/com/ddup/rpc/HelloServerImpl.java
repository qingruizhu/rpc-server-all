package com.ddup.rpc;

import com.ddup.rpc.spring.RpcServiceAnno;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2019-08-22 14:05
 */
@RpcServiceAnno(value = IHelloSevice.class,version = "v1.0")
public class HelloServerImpl implements IHelloSevice {

    @Override
    public String sayHello(String name) {
        System.out.println("【v1.0】reqeust in content : " + name);
        return "【v1.0】hello " + name;
    }
    @Override
    public void saveUser(User user) {
        System.out.println("【v1.0】request in saveUser : "+ user.toString());
    }
}

