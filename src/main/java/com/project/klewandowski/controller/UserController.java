package com.project.klewandowski.controller;


import com.project.klewandowski.domain.User;
import com.project.klewandowski.service.CompanyService;
import com.project.klewandowski.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private CompanyService companyService;


    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, CompanyService companyService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.companyService = companyService;
    }

    @GetMapping("/user/main")
    public String viewUserHomePage(Model model, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user_details";
    }

    @GetMapping("/admin/users")
    public String viewHomePage(Model model) {

        return findPaginated(1, model);
//        model.addAttribute("userList", userService.getAllUsers());
//        return "index";
    }



    @RequestMapping("/addUser")
    public ModelAndView addUserByAdmin() {
        return new ModelAndView("new_user", "user", new User());
    }

    @RequestMapping("/signup")
    public ModelAndView addUser() {
        return new ModelAndView("register", "user", new User());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView saveUser(User user) {
        userService.addUser(user);
        return new ModelAndView("redirect:/main");
    }


//    @PostMapping("/api/persons")
//    public String addPerson(@ModelAttribute("user") User user){
//        userService.addPerson(user);
//        return "redirect:/";
//    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_person";
    }

    @GetMapping("/user/manage/{id}")
    public String manageUser(@PathVariable(value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user_details";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable(value = "id") long id) {
        this.userService.deleteUser(id);
        return "redirect:/main";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteAcc(@PathVariable(value = "id") long id) {
        this.userService.deleteUser(id);
        return "redirect:/login";
    }



    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/user/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<User> page = userService.findPaginated(pageNo, pageSize);
        List<User> userList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("userList", userList);
        return "index";
    }

    @GetMapping("/main")
    public String main() {
        return "main";
    }
}
