package com.ceruleansource.SmoothieWebsite.backend.Controllers;

//@RestController
//@RequestMapping("/user")
public class UserController {


//    private final UserRepository userRepository;
//
//    @Autowired
//    public UserController(UserRepository userRepository) {
//        Assert.notNull(userRepository, "User Repository must not be null.");
//        this.userRepository = userRepository;
//    }
//
//    @RequestMapping("/{id}")
//    public String showUser(@PathVariable("id") Long id, Model model) {
//
//        User user = userRepository.findById(id).orElse(null);
//        Assert.notNull(user, "User must not be null.");
//
//        model.addAttribute("user", user);
//        return "user";
//    }
//
//    @GetMapping("/{id}/smoothies")
//    public String showFavouriteSmoothiesForUser(@PathVariable("id") Long id, Model model) {
//
//        User user = userRepository.findById(id).orElse(null);
//        Assert.notNull(user, "User must not be null.");
//
//        List<Smoothie> smoothies = user.getFavouriteSmoothies();
//
//        model.addAttribute("smoothies", smoothies);
//        return "smoothies";
//    }
}
