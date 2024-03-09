package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.CarBrandService;
import ru.job4j.cars.service.PostService;
import ru.job4j.cars.service.UserService;

import java.util.List;
import java.util.function.Supplier;

@Controller
@RequestMapping("/index")
public class IndexController {
    private final PostService postService;
    private final UserService userService;
    private final CarBrandService carBrandService;

    public IndexController(PostService postService, UserService userService,
                           CarBrandService carBrandService) {
        this.postService = postService;
        this.userService = userService;
        this.carBrandService = carBrandService;
    }

    @GetMapping
    public String getIndexPage(Model model, @SessionAttribute User user) {
        addAttributeToModel(() -> postService.findAll(), user, model);
        return "index";
    }

    @GetMapping("/price")
    public String  sortByPrice(Model model, @SessionAttribute User user, @RequestParam String price) {
        addAttributeToModel(() -> postService.findByMaxPrice(Integer.parseInt(price)), user, model);
        return "index";
    }

    @GetMapping("/byToday")
    public String sortByToday(Model model, @SessionAttribute User user) {
        addAttributeToModel(() -> postService.findByToday(), user, model);
        return "index";
    }

    @GetMapping("/brand")
    public String sortByBrand(Model model, @SessionAttribute User user, @RequestParam String carBrand) {
        addAttributeToModel(() -> postService.findByBrand(carBrand), user, model);
        return "index";
    }

    private void addAttributeToModel(Supplier<List<Post>> posts, User user, Model model) {
        User thisUser = userService.findById(user.getId()).get();
        List<CarBrand> modelsList = carBrandService.findAllBrand();
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("posts", posts.get());
        model.addAttribute("models", modelsList);
    }
}
