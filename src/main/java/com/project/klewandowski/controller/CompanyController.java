package com.project.klewandowski.controller;


import com.project.klewandowski.domain.Company;
import com.project.klewandowski.domain.User;
import com.project.klewandowski.service.CompanyService;
import com.project.klewandowski.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin
public class CompanyController {
    private CompanyService companyService;
    private UserService userService;

    @Autowired
    public CompanyController(CompanyService companyService, UserService userService) {
        this.companyService = companyService;
        this.userService = userService;
    }

    @GetMapping("/company/main")
    public String viewCompanyPage(Model model) {
        return findPaginatedCom(1, model);
    }

    @RequestMapping("/addCompany")
    public ModelAndView addCompany(Model model) {

        model.addAttribute("userList", userService.getAllUsers());
        return new ModelAndView("new_company", "company", new Company());
    }

    @RequestMapping(value = "/saveCompany", method = RequestMethod.POST)
    public ModelAndView saveCompany(Company company, Principal principal) {
        User user = userService.getUserByUsername(principal.getName());
        if(principal.getName().equals("admin")) {
            companyService.addCompany(company);
            return new ModelAndView("redirect:/company/main");
        }
        company.setCompanyPresident(user);
        companyService.addCompany(company);
        return new ModelAndView("redirect:/company/main");
    }

    @GetMapping("/company/company/edit/{id}")
    public String editCompany(@PathVariable(value = "id") long id, Model model) {
        Company company = companyService.getCompanyById(id);
        model.addAttribute("company", company);
        return "update_company";
    }

    @RequestMapping(value = "/register/company", method = RequestMethod.POST)
    public ModelAndView saveCompany(Company company) {
        companyService.addCompany(company);
        return new ModelAndView("redirect:/main");
    }

    @GetMapping("/company/company/delete/{id}")
    public String deleteCompany(@PathVariable(value = "id") long id) {
        companyService.deleteCompany(id);
        return "redirect:/main";
    }


    @GetMapping("/companies/page/{pageNo}")
    public String findPaginatedCom(@PathVariable (value="pageNo") int pageNo, Model model) {
        int pageSize = 5;
        Page<Company> page = companyService.findPaginatedCom(pageNo, pageSize);
        List<Company> companyList = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("companyList", page.getContent());
        return "indexCompany";
    }
}
