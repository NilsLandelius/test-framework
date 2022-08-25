package io.testframework.test_framework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


//@ComponentScan(basePackages = {"io.testframework.*.*.*"})
@SpringBootApplication
public class TestFrameworkApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctxt = SpringApplication.run(TestFrameworkApplication.class, args);

        String[] beans = ctxt.getBeanDefinitionNames();
        for(String bean: beans){
            System.out.println(bean);
        }
    }

}
