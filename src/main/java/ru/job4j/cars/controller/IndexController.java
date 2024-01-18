package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.UserRepository;

@Controller
public class IndexController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public IndexController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/index"})
    public String testMethod(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "index";
    }
}