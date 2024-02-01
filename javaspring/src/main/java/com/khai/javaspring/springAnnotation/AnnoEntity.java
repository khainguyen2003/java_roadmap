package com.khai.javaspring.springAnnotation;

import org.springframework.stereotype.Component;

@Component
public class AnnoEntity {
    private int id;
    private String mess;

    public AnnoEntity() {
    }

    public AnnoEntity(int id, String mess) {
        this.id = id;
        this.mess = mess;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }
}
