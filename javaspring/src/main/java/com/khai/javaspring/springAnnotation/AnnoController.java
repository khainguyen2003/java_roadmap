package com.khai.javaspring.springAnnotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AnnoController {
    private ServiceExample service;

    // Controller injection
    // trong trường hợp này phải chỉ rõ tên class đã cài đặt vì có 2 class implement interface ServiceExample.
    // Ioc sẽ biên dịch tên class chỉ định.
    // Tên class chỉ định sẽ chuyển chữ cái đầu tiên thành chữ thường. Các chữ cái còn lại giữ nguyên
    // Vd: AnnotationService.java -> annotationService
    // Nếu đã dùng chú thích @Primary ở lớp cài đặt thì không can chú thích này
    @Autowired
    public AnnoController(@Qualifier("noneService") ServiceExample service) {
        this.service = service;
    }

    // Setter injection
//    @Autowired
//    public void setService(ServiceExample service) {
//        this.service = service;
//    }

    public String getName() {
        return "Controller";
    }

    public String getService() {
        return this.service.getService();
    }

    // @ResponseBody: chỉ định đây là phương thức trả về body request
    // Nếu ở trên chỉ định @RestController thay vì @Controller thì không cần annotation này
    @RequestMapping("/anno")
    @ResponseBody
    public AnnoEntity getAnno() {
        AnnoEntity annoEntity = new AnnoEntity(1, "abcv");
        return annoEntity;
    }
}
