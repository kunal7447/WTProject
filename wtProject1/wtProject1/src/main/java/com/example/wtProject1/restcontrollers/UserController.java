package com.example.wtProject1.restcontrollers;

import com.example.wtProject1.entity.User;
import com.example.wtProject1.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {

    static User user;

    @Autowired
    EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes) {
        // Check if the user exists in the database with the provided username and password
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            redirectAttributes.addAttribute("userId", user.getId());
            // User exists, redirect to input route
            return "redirect:/image/input";
        } else {
            // User does not exist, redirect to registration route
            return "redirect:/login";
        }
    }


    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @Transactional
    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           RedirectAttributes redirectAttributes) {
        // Check if a user with the same username already exists in the database
        User existingUser = userRepository.findByUsernameAndPassword(username,password);
        if (existingUser != null) {
            // User with the same username already exists, redirect to registration page again
            return "redirect:/register";
        } else {
            // Create a new user and save it to the database
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(password);
            user = newUser;
            userRepository.save(newUser);
            String tableName = "cart_" + newUser.getId();
            createCartTable(tableName);
            // Redirect the user to the input route
            redirectAttributes.addAttribute("userId", newUser.getId());
            return "redirect:/image/input";
        }
    }

    private void createCartTable(String tableName) {
        String sql = "CREATE TABLE " + tableName + " (fav INT)";
        entityManager.createNativeQuery(sql).executeUpdate();
    }

}
