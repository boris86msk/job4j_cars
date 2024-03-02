package ru.job4j.cars.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CarModelRepository;
import ru.job4j.cars.repository.PostRepository;
import ru.job4j.cars.repository.UserRepository;
import ru.job4j.cars.service.PostService;

import java.util.List;
import java.util.function.Consumer;
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
        User thisUser = userRepository.findById(user.getId()).get();
        List<Post> postList = postRepository.findAll();
        List<CarModel> modelsList = carModelRepository.findAllCarModel();
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("posts", postList);
        model.addAttribute("models", modelsList);
        return "index";
    }

    @GetMapping("/price")
    public void sortToPrice(@SessionAttribute User user, @RequestParam String price) {
        sortListCars(() -> postRepository.findByMaxPrice(Integer.parseInt(price)), user);
    }

    @GetMapping("/byToday")
    public String sortByToday(Model model, @SessionAttribute User user) {
        User thisUser = userRepository.findById(user.getId()).get();
        List<Post> postList = postRepository.findByToday();
        List<CarModel> modelsList = carModelRepository.findAllCarModel();
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("posts", postList);
        model.addAttribute("models", modelsList);
        return "index";
    }

    @GetMapping("/model")
    public String sortByModel(Model model, @SessionAttribute User user, @RequestParam String carModel) {
        User thisUser = userRepository.findById(user.getId()).get();
        List<Post> postList = postRepository.findByModel(carModel);
        List<CarModel> modelsList = carModelRepository.findAllCarModel();
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("posts", postList);
        model.addAttribute("models", modelsList);
        return "index";
    }

    private String sortListCars(Supplier<List<Post>> posts, User user) {
        Model model = null;
        User thisUser = userRepository.findById(user.getId()).get();
        List<CarModel> modelsList = carModelRepository.findAllCarModel();
        model.addAttribute("thisUser", thisUser);
        model.addAttribute("posts", posts);
        model.addAttribute("models", modelsList);
        return "index";
    }
}
