package org.example.aspect;

import org.example.controller.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AspectTest {

    @Autowired
    private UserController userController;

    @Autowired
    private LoggingAspect loggingAspect;

    @Test
    public void test() {
        userController.deleteUser(3);
        assertEquals(2, loggingAspect.getCounter());
    }
}
