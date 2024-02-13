package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;

@Controller
public class IndexController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public IndexController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model, @SessionAttribute User user) {
        User thisUser = userRepository.findById(user.getId()).get();
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("posts", postRepository.findAll());
        return "index";
    }
}
