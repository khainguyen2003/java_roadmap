# Bean
A bean is an objec that the spring instantiates(Khởi tạo), assembles and managements

We should define beans for service layer objects, data access objects (DAOs), presentation objects(Models), infrastructure objects such as Hibernate SessionFactories, JMS Queues, and so forth.

## Bean scope
Spring framework define 6 scopes:
- **singleton(default):** the container creates a single instance of that bean; all requests for that bean name will return the same object, which is cached. Any modifications to the object will be reflected in all references to the bean
```
@Bean
@Scope("singleton") \\ hoăc @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public Person personSingleton() {
    return new Person();
}

// in run file
SingletonBean singBean = context.getBean(SingletonBean.class);
SingletonBean singBean2 = context.getBean(SingletonBean.class);
SingletonBean singBean3 = context.getBean(SingletonBean.class);
System.out.println(singBean.hashCode());
System.out.println(singBean2.hashCode());
System.out.println(singBean3.hashCode());
// result
singleton bean
193492784
193492784
193492784
```
- **prototype:** Instantiate a difference instance every time container request it
```
// in run file
PrototypeBean proBean = context.getBean(PrototypeBean.class);
PrototypeBean proBean2 = context.getBean(PrototypeBean.class);
PrototypeBean proBean3 = context.getBean(PrototypeBean.class);
System.out.println(proBean.hashCode());
System.out.println(proBean2.hashCode());
System.out.println(proBean3.hashCode());
// result
Prototype bean
Prototype bean
Prototype bean
1845760645
645777794
1134237170
```
- **request:** create a bean for a single HTTP request
- **session:** create a bean for an HTTP session
- **application:** create a bean for the lifecycle of ServletContext (Tương tự session storage)
- **websocket:** create a bean for particular Websocket session


The last four scopes mentioned, request, session, application and websocket, are only available in a web-aware application.

# Bean annotation
- **@ComponentScan:** chỉ định các packages chừa các lớp đuơc quét bởi spring
```
@Configuration
@ComponentScan(basePackages = "com.baeldung.annotations")
class VehicleFactoryConfig {}
```
- **@component("< nameComponent >"):** specify this class is a spring bean/component
- **@repository:** specify this is a class which access to the database
- **@service:** indicate this class belongs to service layer
- **@controller:** indicate this class belongs to control layer
- **@configuration** indicate this is a configuration class which contains bean definition methods annotated with @Bean
- **Stereotype Annotations and AOP**: 
