package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.kata.spring.boot_security.demo.configs.SuccessUserHandler;
import ru.kata.spring.boot_security.demo.configs.WebSecurityConfig;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootSecurityDemoApplication {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public SpringBootSecurityDemoApplication(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityDemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository userRepository, RoleRepository roleRepository) {
        return (args) -> {
            Role admin = roleRepository.save(new Role("ROLE_ADMIN"));
            Role user = roleRepository.save(new Role("ROLE_USER"));

            userRepository.save(new User("Sergey", "Fedorov", "sf@gmail.com",
                    bCryptPasswordEncoder.encode("sf"),
                    Stream.of(admin, user).collect(Collectors.toSet())));
            userRepository.save(new User("Lena", "Levitskikh", "el@mail.ru",
                    bCryptPasswordEncoder.encode("el"),
                    Stream.of(user).collect(Collectors.toSet())));
            userRepository.save(new User("Mentor", "Mentorov", "mm@yandex.ru",
                    bCryptPasswordEncoder.encode("mm"),
                    Stream.of(user).collect(Collectors.toSet())));
        };
    }
}
