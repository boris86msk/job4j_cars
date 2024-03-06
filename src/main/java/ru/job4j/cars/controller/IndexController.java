package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CarModelRepository;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.UserRepository;

import java.util.List;
import java.util.function.Supplier;

@Controller
@RequestMapping("/index")
public class IndexController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CarModelRepository carModelRepository;

    public IndexController(PostRepository postRepository, UserRepository userRepository,
                           CarModelRepository carModelRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.carModelRepository = carModelRepository;
    }

    @GetMapping
    public String getIndexPage(Model model, @SessionAttribute User user) {
        addAttributeToModel(() -> postRepository.findAll(), user, model);
        return "index";
    }

    @GetMapping("/price")
    public String  sortByPrice(Model model, @SessionAttribute User user, @RequestParam String price) {
        addAttributeToModel(() -> postRepository.findByMaxPrice(Integer.parseInt(price)), user, model);
        return "index";
    }

    @GetMapping("/byToday")
    public String sortByToday(Model model, @SessionAttribute User user) {
        addAttributeToModel(() -> postRepository.findByToday(), user, model);
        return "index";
    }

    @GetMapping("/brand")
    public String sortByModel(Model model, @SessionAttribute User user, @RequestParam String carBrand) {
        addAttributeToModel(() -> postRepository.findByBrand(carBrand), user, model);
        return "index";
    }

    private void addAttributeToModel(Supplier<List<Post>> posts, User user, Model model) {
        User thisUser = userRepository.findById(user.getId()).get();
        List<CarBrand> modelsList = carModelRepository.findAllCarBrand();
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("posts", posts.get());
        model.addAttribute("models", modelsList);
    }
}
