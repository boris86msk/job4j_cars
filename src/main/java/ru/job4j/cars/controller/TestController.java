package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
public class TestController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public TestController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String testMethod() {
     //   List<Post> all = postRepository.findAll();
        List<Post> byId = postRepository.findByToday();
 //       List<Post> byPhoto = postRepository.findAllWhereFilesNotNull();
 //       List<Post> byPhoto = postRepository.findByModel("Vesta");
        System.out.println(byId);

//        List<Post> byToday = postRepository.findByToday();
//        byToday.stream().forEach(System.out::println);
        return "index";
    }
}
