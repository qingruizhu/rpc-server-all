package com.ddup.rpc;

import com.ddup.rpc.spring.RpcServiceAnno;

@RpcServiceAnno(value = IHelloSevice.class,version = "v2.0")
public class HelloServerImpl2 implements IHelloSevice {

    @Override
    public String sayHello(String name) {
        System.out.println("【v2.0】reqeust in content : " + name);
        return "【v2.0】hello " + name;
    }
    @Override
    public void saveUser(User user) {
        System.out.println("【v2.0】request in saveUser : "+ user.toString());
    }
}


