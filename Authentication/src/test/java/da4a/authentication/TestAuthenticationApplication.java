package da4a.authentication;

import org.springframework.boot.SpringApplication;

public class TestAuthenticationApplication {

    public static void main(String[] args) {
        SpringApplication.from(AuthenticationApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
