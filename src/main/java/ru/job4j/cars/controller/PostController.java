package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.BodyType;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.BodyTypeService;
import ru.job4j.cars.service.FileService;
import ru.job4j.cars.service.ParticipatesService;
import ru.job4j.cars.service.PostService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
public class PostController {
    private final BodyTypeService bodyTypeService;
    private final FileService fileService;
    private final PostService postService;
    private final ParticipatesService participatesService;

    public PostController(BodyTypeService bodyTypeService, FileService fileService,
                          PostService postService, ParticipatesService participatesService) {
        this.bodyTypeService = bodyTypeService;
        this.fileService = fileService;
        this.postService = postService;
        this.participatesService = participatesService;
    }

    @GetMapping("/create")
    public String getCreatePage(Model model) {
        model.addAttribute("bodyType", bodyTypeService.findAllType());
        return "post/create";
    }

    @GetMapping("/one/{postId}")
    public String  getOnePage(@PathVariable int postId, Model model, @SessionAttribute User user) {
        model.addAttribute("user", user);
        model.addAttribute("post", postService.findById(postId).get());
        return "post/one";
    }

    @GetMapping("/delete/{postId}/{fileId}")
    public String  deletePost(@PathVariable int postId, @PathVariable int fileId, Model model) throws IOException {
        if(!postService.deletePost(postId, fileId)) {
            model.addAttribute("errorText", "Не удалось удалить объявление");
            return "errorPage";
        }
        return "redirect:/index";
    }

    @PostMapping("save")
    public String saveNewPost(@ModelAttribute Post post, @RequestParam MultipartFile myFile,
                              @SessionAttribute User user) throws IOException {
        postService.savePost(post, myFile, user);
        return "redirect:/index";
    }

    @PostMapping("change_price")
    public String changePrice(@ModelAttribute Post post) {
        int postId = post.getId();
        postService.updateById(postId, post.getPrice());
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
        postService.sold(postId);
        return "redirect:/index";
    }
}
