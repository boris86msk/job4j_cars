package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.BodyType;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.BodyTypeRepository;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PostController {
    private final PostRepository postRepository;
    private final BodyTypeRepository bodyTypeRepository;
    private final FileService fileService;
    private final BodyTypeRepository btRepository;

    public PostController(PostRepository postRepository, BodyTypeRepository bodyTypeRepository, FileService fileService, BodyTypeRepository btRepository) {
        this.postRepository = postRepository;
        this.bodyTypeRepository = bodyTypeRepository;
        this.fileService = fileService;
        this.btRepository = btRepository;
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        int[] ages = {2024, 2023, 2022, 2021, 2020};
        model.addAttribute("ages",  ages);
        model.addAttribute("bodyType", bodyTypeRepository.findAllType());
        return "post/create";
    }

    @GetMapping("/one/{postId}")
    public String  getOnePage(@PathVariable int postId) {
        Map<Integer, Integer> map = new HashMap<>();
        return "post/one";
    }

    @PostMapping("save")
    public String saveNewPost(@ModelAttribute Post post, @RequestParam MultipartFile myFile,
                              HttpServletRequest request) throws IOException {
        User user = (User) request.getSession().getAttribute("user");
        post.setUser(user);
        post.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES));
        BodyType bodyType = btRepository.findById(post.getCar().getBodyType().getId()).get();
        post.getCar().setBodyType(bodyType);
        post.setFile(fileService.savePhoto(new FileDto(myFile.getOriginalFilename(), myFile.getBytes())));
        postRepository.save(post);
        return "redirect:/index";
    }
}