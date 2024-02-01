package com.khai.javaspring.springAnnotation;

import org.springframework.stereotype.Component;

@Component
public class NoneService implements ServiceExample {

    @Override
    public String getService() {
        return "None service";
    }
}
