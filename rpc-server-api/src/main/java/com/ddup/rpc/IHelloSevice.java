package com.ddup.rpc;

/**
 * @Description:
 * @Auther: qingruizhu
 * @Date: 2019-08-22 14:02
 */
public interface IHelloSevice {
    String sayHello(String name);
    void saveUser(User user);
}
