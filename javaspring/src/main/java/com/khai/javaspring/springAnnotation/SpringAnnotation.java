package com.khai.javaspring.springAnnotation;

import com.khai.javaspring.springAnnotation.loader.LazyLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringAnnotation {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringAnnotation.class);
        AnnoController controller =  context.getBean(AnnoController.class);
        System.out.println(controller.getService());

        LazyLoader loader = context.getBean(LazyLoader.class);
    }
}
