package com.ddup.rpc;
import com.ddup.rpc.conf.SpringConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        applicationContext.start();
    }
}

