## 1. Request mehods

```java
@RestController
@RequestMapping("/foos")
class FooController {

    @Autowired
    private IFooService service;

    @GetMapping
    public List<Foo> findAll() {
        return service.findAll();
    }

    @GetMapping(value = "/{id}")
    public Foo findById(@PathVariable("id") Long id) {
        return RestPreconditions.checkFound(service.findById(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long create(@RequestBody Foo resource) {
        Preconditions.checkNotNull(resource);
        return service.create(resource);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@PathVariable( "id" ) Long id, @RequestBody Foo resource) {
        Preconditions.checkNotNull(resource);
        RestPreconditions.checkNotNull(service.getById(resource.getId()));
        service.update(resource);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

    /**
     * {"username": "johnny", "password": "password"}' "https://localhost:8080/.../request"
     * @param loginForm kiểu json
     * @return
     */
    @PostMapping("/request")
    public ResponseEntity postController(
            @RequestBody LoginForm loginForm) {

        exampleService.fakeAuthenticate(loginForm);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
```

## 2. ResponseEntity
Trả về response cho người dùng
```java
@GetMapping("/age")
    ResponseEntity<String> age(
            @RequestParam("yearOfBirth") int yearOfBirth) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Custom-Header", "foo");
        return new ResponseEntity<>(
                "Your age is " + yearOfBirth, headers,
                HttpStatus.OK);
}
```

## 3. pagination
```java
long pageNo = 1;
pageSize = 10;
String sortBy = "title";
String sortDir = "ASC";

Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
		: Sort.by(sortBy).descending();

// create Pageable instance
Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

Page<Post> posts = postRepository.findAll(pageable);

// get content for page object
List<Post> listOfPosts = posts.getContent();
```