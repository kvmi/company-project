package com.project.klewandowski.service;


import com.project.klewandowski.domain.Company;
import com.project.klewandowski.domain.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CompanyService {

    List<Company> getAllCompanies();

    Company addCompany (Company company);

    void deleteCompany(long id);

    Company updateCompany(String companyName, Company company);

    Company getCompanyByName(String companyName);

    Page<Company> findPaginatedCom(int pageNo, int pageSize);

    Company getCompanyById(long id);

}
