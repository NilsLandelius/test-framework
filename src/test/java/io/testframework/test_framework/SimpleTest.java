package io.testframework.test_framework;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SimpleTest {

    @Autowired
    HomeController controller;

    @Test
    public void context(){
        assertThat(controller).isNotNull();
    }

}
