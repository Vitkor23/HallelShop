package com.example.HallelShop.controller;


import com.example.HallelShop.models.User;
import com.example.HallelShop.repositories.UserRepository;
import com.example.HallelShop.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login(Principal principal, Model model, @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("user", userService.getUserByPrincipal(principal));
        model.addAttribute("error", error);  // Передача информации об ошибке
        return "login";
    }


    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        User user = userService.getUserByPrincipal(principal);
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/registration")
    public String registration(Principal principal, Model model) {
        model.addAttribute("user",userService.getUserByPrincipal(principal));
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(User user, Model model) {
        // Проверка: Email не должен быть пустым
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            model.addAttribute("error", "Email не может быть пустым.");
            return "registration";
        }

        // Проверка: Email должен быть корректного формата (простая проверка)
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            model.addAttribute("error", "Некорректный формат email.");
            return "registration";
        }

        // Если пользователь не был создан, показываем ошибку
        if (!userService.createUser(user)) {
            model.addAttribute("error", "Пользователь с таким email уже существует.");
            return "registration";
        }

        return "redirect:/login";
    }



    @GetMapping("/user/{user}")
    public String userInfo(@PathVariable("user") User user, Model model, Principal principal) {
        model.addAttribute("user", user);
        model.addAttribute("userByPrincipal", userService.getUserByPrincipal(principal));
        model.addAttribute("products", user.getProducts());
        return "user-info";
    }






}
