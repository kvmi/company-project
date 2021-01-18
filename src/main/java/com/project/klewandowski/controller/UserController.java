package com.project.klewandowski.controller;


import com.project.klewandowski.domain.Company;
import com.project.klewandowski.domain.User;
import com.project.klewandowski.service.CompanyService;
import com.project.klewandowski.service.UserService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public String viewHomePage(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        return "index";
    }


    @GetMapping("/api/persons")
    public ResponseEntity<List<User>> getUsers() {

        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/api/persons/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/api/persons/company/{company}")
    public ResponseEntity<List<User>> getUsersByCompany(@PathVariable String company) {
        return new ResponseEntity<>(userService.getUsersByCompany(company), HttpStatus.OK);
    }
    @RequestMapping("/addUser")
    public ModelAndView addUserByAdmin() {
        return new ModelAndView("new_user", "user", new User());
    }

    @RequestMapping("/signup")
    public ModelAndView addUser() {
        return new ModelAndView("register", "user", new User());
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public ModelAndView saveUser(User user) {
//        Company company = new Company();
//        company.setCompanyName("brak");
//        company.setCompanyPresident(new User());
//        User user1 = new User();
//        User user2 = new User();
//        Set<User> userSet = Stream.of(user1, user2).collect(Collectors.toCollection(HashSet::new));
//        company.setUsers(userSet);
//        Set<Company> companySet = Stream.of(company).collect(Collectors.toCollection(HashSet::new));
//        user.setCompanies(companySet);
//        company = companyService.addCompany(company);
//        user.setCompany((Set<Company>) company);
        userService.addUser(user);
        System.out.println("es");
        return new ModelAndView("redirect:/login");
    }


//    @PostMapping("/api/persons")
//    public String addPerson(@ModelAttribute("user") User user){
//        userService.addPerson(user);
//        return "redirect:/";
//    }

    @GetMapping("/edit/{id}")
    public String editPerson(@PathVariable ( value = "id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "update_person";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable (value = "id") long id){
        this.userService.deleteUser(id);
        return "redirect:/";
    }

    @DeleteMapping("/api/persons/{id}")
    public ResponseEntity deletePerson(@PathVariable long id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/company/names")
    public ResponseEntity<List<String>> getCompanyNames() {
        return new ResponseEntity<>(userService.getCompanyNames(), HttpStatus.OK);
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
