package com.project.klewandowski.controller;


import com.project.klewandowski.domain.Company;
import com.project.klewandowski.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class CompanyController {
    private CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {this.companyService = companyService; }

    @GetMapping("/api/company")
    public ResponseEntity<List<Company>> getCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(), HttpStatus.OK);
    }

    @GetMapping("/api/company/{companyName}")
    public ResponseEntity<Company> getCompanyByName(@PathVariable String companyName) {
        return new ResponseEntity<>(companyService.getCompanyByName(companyName), HttpStatus.OK);
    }

    @PostMapping("/api/company")
    public ResponseEntity<Company> addCompany(@RequestBody Company company){
        return new ResponseEntity<>(companyService.addCompany(company), HttpStatus.CREATED);
    }


    @PutMapping("/api/company/{companyName}")
    public ResponseEntity<Company> updateCompany(@RequestBody Company company, @PathVariable String companyName){
        return new ResponseEntity<>(companyService.updateCompany(companyName, company), HttpStatus.OK);
    }

    @DeleteMapping("/api/company/{companyName}")
    public ResponseEntity deleteCompany(@PathVariable String companyName){
        companyService.deleteCompany(companyName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
