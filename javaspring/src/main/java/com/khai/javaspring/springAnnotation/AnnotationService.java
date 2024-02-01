package com.khai.javaspring.springAnnotation;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AnnotationService implements ServiceExample {
    public String getService() {
        return "this is service class";
    }
}
