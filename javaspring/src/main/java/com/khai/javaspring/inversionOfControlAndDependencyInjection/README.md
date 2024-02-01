# Inversion of control (Ioc) <chuyển điều khiển>
Inversion of Control is a principle in software engineering which transfers the control of objects or portions of a program (Một phần của chương trình) to a container or framework

IoC enables a framework to take control of the flow of a program and make calls to our custom code. To enable this, frameworks use abstractions with additional behavior built in. If we want to add our own behavior, we need to extend the classes of the framework or plugin our own classes.

The advantages of this architecture are:
- decoupling (tách rời) the execution of a task from its implementation
- making it easier to switch between different implementations
- greater modularity  (Tính modul cao hơn) of a program
- greater ease in testing a program by isolating(cô lập) a component or mocking its dependencies, and allowing components to communicate through contracts

We can achieve Ioc through various mechanisms(cơ chế): Strategy design pattern, Service Locator pattern, Factory pattern, and Dependency Injection (DI)

# Dependencies injection
Dependency injection is a pattern we can use to implement IoC, where the control being inverted is setting an object’s dependencies.

Connecting objects with other objects, or “injecting” objects into other objects, is done by an assembler rather than by the objects themselves.
### In traditional programing
```
public class Store {
    private Item item;
 
    public Store() {
        item = new ItemImpl1();    
    }
}
```
### Dependency injection
```
public class Store {
    private Item item;
 
    public Store(Item item) {
        this.item = item;    
    }
}
```

# The spring Ioc container
An Ioc container is a common characteristics(đặc trưng) of frameworks that implement Ioc

In the Spring framework, the interface ApplicationContext represents(V: đại diện) the IoC container. The Spring container is responsible for instantiating(/in`staenshieiting/: khởi tạo), configuring and assembling(Tập hợp) objects known as beans, as well as managing their life cycles.

- Manually instantiate a container
```
ApplicationContext context
  = new ClassPathXmlApplicationContext("applicationContext.xml");
```
- using AnnotationConfigApplicationContext
```
AnnotationConfigApplicationContext annotationContext = new AnnotationConfigApplicationContext();
```
When creating an instance of AnnotationConfigApplicationContext and provide it with one or more configuration classes, it scans these classes for the @Bean annotations and other relevant annotations. It then initializes and manages the beans defined in these classes, setting up their dependencies and managing their lifecycle.
#  Constructor-Based Dependency Injection
