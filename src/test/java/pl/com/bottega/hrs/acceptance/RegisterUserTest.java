package pl.com.bottega.hrs.acceptance;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.com.bottega.hrs.application.RegisterUserHandler;
import pl.com.bottega.hrs.application.users.RegisterUserCommand;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RegisterUserTest {

    @Autowired
    private RegisterUserHandler userHandler;

    @Autowired


    @Test
    public void shouldRegisterUser() {
        //given
        RegisterUserCommand registerUserCommand = new RegisterUserCommand();
        registerUserCommand.setLogin("adam");
        registerUserCommand.setPassword("kwiatek");
        registerUserCommand.setRepeatedPassword("kwiatek");
        userHandler.handle(registerUserCommand);

        //then


    }
}
