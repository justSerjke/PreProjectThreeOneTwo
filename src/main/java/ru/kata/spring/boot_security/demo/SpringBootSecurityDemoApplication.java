package ru.kata.spring.boot_security.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, RoleRepository roleRepository) {
        return (args) -> {
            Role admin = roleRepository.save(new Role("ROLE_ADMIN"));
            Role user = roleRepository.save(new Role("ROLE_USER"));

            userRepository.save(new User("Sergey", "Fedorov", "sf@gmail.com",
                    "$2a$12$.cKYQ6fVU75lLe.EeyKf7u7mz/EmsSIZ4fE8/5VP7z913nsv3oE5y",
                    Stream.of(admin, user).collect(Collectors.toSet())));
            userRepository.save(new User("Lena", "Levitskikh", "el@mail.ru",
                    "$2a$12$FTF9trzFi/lpy5dtbf93Ke/3HVXUm0wQuYz1FWgxf5ct73lJVXBtK",
                    Stream.of(user).collect(Collectors.toSet())));
            userRepository.save(new User("Mentor", "Mentorov", "mm@yandex.ru",
                    "$2a$12$OATE0jGt2dcymTwOoyMBue1nwosOsd6ORLIm.gmbDy1IQx5f1FoLC\n",
                    Stream.of(user).collect(Collectors.toSet())));
        };
    }
}
