package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.UserRepository;
import ru.job4j.cars.service.PostService;

import java.util.List;

@Controller
public class IndexController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostService postService;

    public IndexController(PostRepository postRepository, UserRepository userRepository, PostService postService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postService = postService;
    }

    @GetMapping({"/", "/index"})
    public String getIndexPage(Model model, @SessionAttribute User user) {
        User thisUser = userRepository.findById(user.getId()).get();
        List<Post> postList = postRepository.findAll();
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("posts", postList);
        return "index";
    }
}
