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
import ru.job4j.cars.repository.ParticipatesRepository;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.service.FileService;
import ru.job4j.cars.service.ParticipatesService;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
public class PostController {
    private final PostRepository postRepository;
    private final BodyTypeRepository bodyTypeRepository;
    private final FileService fileService;
    private final BodyTypeRepository btRepository;
    private final PostService postService;
    private final ParticipatesService participatesService;

    public PostController(PostRepository postRepository, BodyTypeRepository bodyTypeRepository, FileService fileService,
                          BodyTypeRepository btRepository, PostService postService, ParticipatesService participatesService) {
        this.postRepository = postRepository;
        this.bodyTypeRepository = bodyTypeRepository;
        this.fileService = fileService;
        this.btRepository = btRepository;
        this.postService = postService;
        this.participatesService = participatesService;
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("bodyType", bodyTypeRepository.findAllType());
        return "post/create";
    }

    @GetMapping("/one/{postId}")
    public String  getOnePage(@PathVariable int postId, Model model, @SessionAttribute User user) {
        model.addAttribute("user", user);
        model.addAttribute("post", postRepository.findById(postId).get());
        return "post/one";
    }

    @GetMapping("/delete/{postId}")
    public String  deletePost(@PathVariable int postId) {
        postService.delete(postId);
        participatesService.delete(postId);
        postRepository.delete(postId);
        return "redirect:/index";
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
        postService.savePriceHistory(post);
        participatesService.save(post.getId(), user.getId());
        return "redirect:/index";
    }

    @PostMapping("change_price")
    public String changePrice(@ModelAttribute Post post) {
        int postId = post.getId();
        postRepository.updateById(postId, post.getPrice());
        postService.savePriceHistory(post);
        return String.format("redirect:/one/%d", postId);
    }


    @GetMapping("favourites/{postId}")
    public String addToFavourites(@PathVariable int postId, @SessionAttribute User user) {
        participatesService.save(postId, user.getId());
        return "redirect:/index";
    }

    @GetMapping("sold/{postId}")
    public String soldPost(@PathVariable int postId) {
        postRepository.sold(postId);
        return "redirect:/index";
    }
}
