# @RequestMapping
```java
// Multi Path variables: fooid, barid
// Regex path: {numericId:[\\d]+}
// params = { "id", "second" } -> ?id=...&second=...
@RequestMapping(
        value = "/ex/foos/{fooid}/bar/{barid}/{numericId:[\\d]+}", 
        // multiple path
        // value = { "/ex/advanced/bars", "/ex/advanced/foos" },
        headers = { "key1=val1", "key2=val2" },
        params = { "id", "second" },
        method = GET,
        // Multiple methods
        // method = { RequestMethod.PUT, RequestMethod.POST },
        produces = { "application/json", "application/xml" }
)
@ResponseBody
public String getFoosBySimplePathWithPathVariables(
        @PathVariable long fooid, 
        @PathVariable long barid, 
        @PathVariable long numericId, 
        @RequestParam("id") long id
) {
    return "Get a specific Bar with id=" + barid + 
      " from a Foo with id=" + fooid;
}

// Phương thức dự phòng(fallback) cho các request
@RequestMapping(
        value = "*",
        method = { RequestMethod.GET, RequestMethod.POST ... })
@ResponseBody
public String allFallback() {
    return "Fallback for All Requests";
}
```

### Lặp phương thức xử lý cho đường dẫn
Có thể có 2 phương thức cùng xử lý cho cùng đương dẫn với cùng method requestMapping nhưng 2 phương thức
phải khác nhau MediaType(kiểu dữ liệu xử lý)
```java
// ok
@GetMapping(value = "foos/duplicate", produces = MediaType.APPLICATION_XML_VALUE)
public String duplicateXml() {
    return "<message>Duplicate</message>";
}
    
@GetMapping(value = "foos/duplicate", produces = MediaType.APPLICATION_JSON_VALUE)
public String duplicateJson() {
    return "{\"message\":\"Duplicate\"}";
}

// Lỗi
@GetMapping(value = "foos/duplicate" )
public String duplicate() {
    return "Duplicate";
}

@GetMapping(value = "foos/duplicate" )
public String duplicateEx() {
    return "Duplicate";
}
```

Tương ứng với các method sẽ có
- @GetMapping
- @PostMapping
- @PutMapping
- @DeleteMapping
- @PatchMapping

# 2. Request param
```java
@PostMapping("/api/foos")
@ResponseBody
/**
 * specify name. Nếu không chỉ định tên thì sẽ lấy tên của biền làm tên RequestParam
 * required = false -> nếu có thì lấy gia trị, nếu không thì null
 *    http://localhost:8080/spring-mvc-basics/api/foos?id=abc
 *    ----
 *    ID: abc
 *   
 *   http://localhost:8080/spring-mvc-basics/api/foos
 *   ----
 *   ID: null
 *   
 *   defaultValue = "test"
 */

public String addFoo(@RequestParam(name = "id", required = false, defaultValue = "test") Optional<String> fooId, @RequestParam String name) { 
    return "ID: " + fooId.orElseGet(() -> "not provided") + " Name: " + name;
}
```
### Mapping multi values
```java
@GetMapping("/api/foos")
@ResponseBody
public String getFoos(@RequestParam List<String> id) {
    return "IDs are " + id;
}

/*
   http://localhost:8080/spring-mvc-basics/api/foos?id=1,2,3
    ----
    IDs are [1,2,3]
    hoặc
    http://localhost:8080/spring-mvc-basics/api/foos?id=1&id=2
    ----
    IDs are [1,2]    
 */
```