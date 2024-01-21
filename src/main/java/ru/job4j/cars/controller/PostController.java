package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.BodyTypeRepository;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
public class PostController {
    private final PostRepository postRepository;
    private final BodyTypeRepository bodyTypeRepository;
    private final FileService fileService;

    public PostController(PostRepository postRepository, BodyTypeRepository bodyTypeRepository, FileService fileService) {
        this.postRepository = postRepository;
        this.bodyTypeRepository = bodyTypeRepository;
        this.fileService = fileService;
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("bodyType", bodyTypeRepository.findAllType());
        return "post/create";
    }

    @PostMapping("save")
    public String saveNewPost(@ModelAttribute Post post, @RequestParam MultipartFile[] file, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        post.setUser(user);
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        post.setFiles(fileService.savePhoto(file));
        postRepository.save(post);
        return "redirect:/index";
    }
}
