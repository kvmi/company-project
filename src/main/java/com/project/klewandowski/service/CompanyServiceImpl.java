package com.project.klewandowski.service;

import com.project.klewandowski.domain.Company;
import com.project.klewandowski.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {


    private UserService userService;
    private List<Company> companyList;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(UserService userService) {
        this.userService = userService;

        this.companyList = userService.getCompanyList();
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company addCompany(Company company) {
        return companyRepository.save(company);
    }



    @Override
    public void deleteCompany(String companyName) {
        companyRepository.deleteByCompanyName(companyName);
    }

    @Override
    public Company updateCompany(String companyName, Company company) {
        Company companyToUpdate = getCompanyByName(companyName);
        companyToUpdate.setCompanyName(company.getCompanyName());
        companyToUpdate.setCompanyPresident(company.getCompanyPresident());
        return companyRepository.save(companyToUpdate);
    }

    @Override
    public Company getCompanyByName(String companyName) {

        return companyRepository.findAllByCompanyName(companyName);
    }

}
