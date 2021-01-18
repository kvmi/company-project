package com.project.klewandowski.service;

import com.project.klewandowski.domain.Company;
import com.project.klewandowski.domain.User;
import com.project.klewandowski.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public void deleteCompany(long id) {
        companyRepository.deleteById(id);
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

    @Override
    public Page<Company> findPaginatedCom(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.companyRepository.findAll(pageable);
    }

    @Override
    public Company getCompanyById(long id) {
        return companyRepository.findById(id).orElse(null);
    }

}
