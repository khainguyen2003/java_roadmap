# Error Handling for REST with Spring
Người dùng có thể định nghĩa các exception handling cho rest api và dùng nó thông
qua `HandlerExceptionResolver` hoặc `@ExceptionHandler` annotation
## Giải pháp 1: dùng `@ExceptionHandler`
```java
@ExceptionHandler({HandlerExceptionName1.class, HandlerExceptionName2.class, ...})
public void handleException() {
//
}
```
Dùng `@ExceptionHandler` sẽ dùng được cho các controller được chỉ định chứ không dùng 
được cho toàn bộ ứng dụng

## giải pháp 2: `HandlerExceptionResolver`
- Các resolver này sẽ giải quyết bất kỳ ngọai lệ nào do ứng dụng đưa ra và sẽ được bật mặc định
trong `DispatcherServlet`
## Giải pháp 3: `@ControllerAdvice` annotation -> nên dùng
Gộp các @ExceptionHandler thành một thành phần xử lý toàn cục duy nhất
Cơ chế thực tế vô cùng đơn giản nhưng cũng rất linh hoạt:
- Nó cho phép toàn quyền kiểm soát nội dung của phản hồi cũng như mã trạng thái.
- Nó cung cấp ánh xạ của một số ngoại lệ cho cùng một phương pháp, được xử lý cùng nhau.
- Nó sử dụng tốt phản hồi RESTful ResposeEntity mới hơn.
  
Tuy nhiên, khi ngoại lệ thực sự được ném vào thời gian chạy, __cơ chế giải quyết ngoại lệ sẽ không thành công với__:
```java
java.lang.IllegalStateException: No suitable resolver for argument [0] [type=...]
HandlerMethod details: ...
```

## Giải pháp 4: ResponseStatusException (Spring 5 trở lên)
```java
@GetMapping(value = "/{id}")
public Foo findById(@PathVariable("id") Long id, HttpServletResponse response) {
    try {
        Foo resourceById = RestPreconditions.checkFound(service.findOne(id));

        eventPublisher.publishEvent(new SingleResourceRetrievedEvent(this, response));
        return resourceById;
     }
    catch (MyResourceNotFoundException exc) {
         throw new ResponseStatusException(
           HttpStatus.NOT_FOUND, "Foo Not Found", exc);
    }
}
```

