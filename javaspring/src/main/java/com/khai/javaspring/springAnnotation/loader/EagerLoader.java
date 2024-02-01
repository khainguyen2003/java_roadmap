package com.khai.javaspring.springAnnotation.loader;

import org.springframework.stereotype.Component;

@Component
public class EagerLoader {
    public EagerLoader() {
        System.out.println("Eager loader...");
    }
}
