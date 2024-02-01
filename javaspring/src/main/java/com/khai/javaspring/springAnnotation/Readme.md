# Spring annotation
## @autowired annotation (tự động kết nối)
- allows Spring to resolve and inject collaborating beans into our bean automatically. In other words, by declaring
all the beans dependencies in a spring configuration file, spring container can autowire relationships
between collaborating beans
- this annotation using constructor injection, setter injection or field injection
```
@component
public class Controller{
    private Service service;
    // controller injection
    @Autowired
    public Controller(Service service) {
        this.service = service;
    }
    
    // setter injection
    @Autowired
    public void setService(Service service) {
        this.service = service;
    }
}
```

## @springBootApplication annotation
- this single annotation equivalent to (tương đương với) using @Configuration, @EnableAutoConfiguration, and @ComponentScan.
- This annotation often use in main class  of the application
```
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

## @Qualified annotation
- using in conjunction with (sử dụng kết hợp với) Autowired to avoid confusion (nhầm lẫn) when we have two or more beans configured (được cấu hình) for same type
## Primary annotation
- using this anno to give higher preference to a bean there are multiple bean of the same type
- Use this anno in implement class
```
@Component
@Primary
public class AnnotationService implements ServiceExample {
    public String getService() {
        return "this is service class";
    }
}
```
## @Lazy annotation
- By default, Spring creates all singleton(đơn lẻ) beans eagerly at the startup/bootstrapping(dùng trong spring cloud) of the application context
- We can load the spring beans lazily (on-demand) using @Lazy annotation
- @Lazy can use with @Configuration, @Component and @Bean annotation
- Normally, if there are any possible errors while creating spring beans eagerly at the startup application
  context then those errors will be automatically detected and the application should terminate but in case
  of lazy initialization we can get the errors at a run time and this should not
  terminate the application.
- Contructor of lazy class is only called until it is injected
```
public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(SpringAnnotation.class);
    AnnoController controller =  context.getBean(AnnoController.class);
    System.out.println(controller.getService());

    LazyLoader loader = context.getBean(LazyLoader.class);
}
```
**result**
```
Eager loader...
2023-12-13T14:53:54.370+07:00  INFO 7676 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port 8080 (http) with context path ''
2023-12-13T14:53:54.370+07:00  INFO 7676 --- [           main] c.k.j.springAnnotation.SpringAnnotation  : Started SpringAnnotation in 1.978 seconds (process running for 2.302)
None service
Lazy loader ...
```
## @PropertySource and @PropertySources
- **@PropertySource("classpath:<fileName.properties>"):** Used to provide property file to spring environment
- **@PropertySources("classpath:<fileName.properties>"):** Used to provide multiple property files to spring environment
- Two annotation use with @Configuration classes 
```
@PropertySources({
    @PropertySource("classpath:foo.properties"),
    @PropertySource("classpath:bar.properties")
})
public class PropertiesWithJavaConfig {
    //...
}
```

## @RequestBody: Chỉ định giá trị trả về của phương thức này là nội dung body của response
## RequestParam(name = "< param name >") < var type > VarName -> pass value of param has specify name to the variable
```
@GetMapping("/abc")
public Student getStudent(@RequestParam("firstName") String firstName) {
    ...
}
```

## @Profile("\<Value\>"): indicate that this class only represent in specify enviroment
**available values**: 
- dev 
- !dev(only present if dev profile is not active)
- production
```
@Component
@Profile("dev")
public class DevDatasourceConfig {
    ...
}
```
### Set Profiles
**1. via WebApplicationInitializer Interface**
```
@Configuration
public class MyWebApplicationInitializer 
  implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
 
        servletContext.setInitParameter(
          "spring.profiles.active", "dev");
    }
}
```
**2. via ConfigurableEnvironment**
```
@Autowired
private ConfigurableEnvironment env;
...
env.setActiveProfiles("someProfile");
```
**3. @ActiveProfile in Tests**
```
@ActiveProfiles("dev")
```
### Get active profile
**1. via Environment**
```
public class ProfileManager {
    @Autowired
    private Environment environment;

    public void getActiveProfiles() {
        for (String profileName : environment.getActiveProfiles()) {
            System.out.println("Currently active profile - " + profileName);
        }  
    }
}
```
**2. via inject spring.profiles.active property**
```
public class ProfileManager {
    @Value("${spring.profiles.active:}")
    private String activeProfiles;

    public String getActiveProfiles() {
        for (String profileName : activeProfiles.split(",")) {
            System.out.println("Currently active profile - " + profileName);
        }
    }
}
```

# @Schedule: Lập lịch chạy component
- To enable support for scheduling tasks and the @Scheduled annotation in Spring, we can use
@EnableScheduling annotation in main class
```
package com.example.schedulingtasks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SchedulingTasksApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchedulingTasksApplication.class);
	}
}
```
## Schedule fixed delay
@Scheduled(fixedDelay = <miliSecond>): makes sure that there is a delay of n 
millisecond between the finish time of an execution of a task and the start 
time of the next execution of the task.

This property is specifically useful when we need to make sure that only one 
instance of the task runs all the time. For dependent jobs, it is quite helpful.
```
@Scheduled(fixedDelay = 1000)
public void scheduleFixedDelayTask() {
    System.out.println(
      "Fixed delay task - " + System.currentTimeMillis() / 1000);
}
```
## Schedule fixed rate
**Cú pháp:** @Schedule(fixedRate = <n milliSeconds>) -> run scheduled task every n milliseconds
It doesn’t check for any previous executions of the task.

This is useful when all executions of the task are independent. 
If we don’t expect to exceed the size of the memory and the thread pool, 
fixedRate should be quite handy.
## Schedule a Task with initial delay
**Cú pháp:** @Schedule(fixedDelay = <n milliSeconds>, initialDelay = <m milliSeconds>)

=> at the first time, task will run after initialDelay. after that, The task will run after fixedDelay
## Schedule a Task Using Cron Expressions
**Cú pháp:** @Scheduled(cron = "<second> <minute> <hour> <day of month> <month> <day of week>")
- English names can also be used for the day-of-month and day-of-week fields. Use the first three letters of the particular day or month (case does not matter).
- A field may be an asterisk (*), which always stands for “first-last”. For the day-of-the-month or day-of-the-week fields, a question mark (?) may be used instead of an asterisk.
- Commas (,) are used to separate items of a list.
- Two numbers separated with a hyphen (-) express a range of numbers. The specified range is inclusive.
- Following a range (or *) with / specifies the interval of the number’s value through the range.

```
// executed at 10:15 AM on the 15th day of every month
@Scheduled(cron = "0 15 10 15 * ?")
// executed at top of every hour
@Scheduled(cron = "0 0 * * * *")
// executed at every 10 minutes
@Scheduled(cron = "0 */10 * * * *")
// executed at 6,7,8 o'clock of every day
@Scheduled(cron = "0 0 6-8 * * *")
public void scheduleTaskUsingCronExpression() {
  ...
}
```
[More example: https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions](https://spring.io/blog/2020/11/10/new-in-spring-5-3-improved-cron-expressions)
### Setting Delay or Rate Dynamically at Runtime
```
@Configuration
@EnableScheduling
public class DynamicSchedulingConfig implements SchedulingConfigurer {

    @Autowired
    private TickService tickService;

    @Bean
    public Executor taskExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
          new Runnable() {
              @Override
              public void run() {
                  tickService.tick();
              }
          },
          new Trigger() {
              @Override
              public Date nextExecutionTime(TriggerContext context) {
                  Optional<Date> lastCompletionTime =
                    Optional.ofNullable(context.lastCompletionTime());
                  Instant nextExecutionTime =
                    lastCompletionTime.orElseGet(Date::new).toInstant()
                      .plusMillis(tickService.getDelay());
                  return Date.from(nextExecutionTime);
              }
          }
        );
    }

}
```
### Running Tasks in Parallel
By default, **Spring uses a local single-threaded scheduler to run the tasks**. As a result, even if we have multiple @Scheduled methods, they each need to wait for the thread to complete executing a previous task.
```
@Bean
public TaskScheduler  taskScheduler() {
    ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
    threadPoolTaskScheduler.setPoolSize(5);
    threadPoolTaskScheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
    return threadPoolTaskScheduler;
}
```