package com.khai.javaspring.bean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BeanApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(BeanApplication.class);

        SingletonBean singBean = context.getBean(SingletonBean.class);
        SingletonBean singBean2 = context.getBean(SingletonBean.class);
        SingletonBean singBean3 = context.getBean(SingletonBean.class);
        System.out.println(singBean.hashCode());
        System.out.println(singBean2.hashCode());
        System.out.println(singBean3.hashCode());

        PrototypeBean proBean = context.getBean(PrototypeBean.class);
        PrototypeBean proBean2 = context.getBean(PrototypeBean.class);
        PrototypeBean proBean3 = context.getBean(PrototypeBean.class);
        System.out.println(proBean.hashCode());
        System.out.println(proBean2.hashCode());
        System.out.println(proBean3.hashCode());
    }
}
