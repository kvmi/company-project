package com.project.klewandowski.repository;


import com.project.klewandowski.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findAllByCompanyName(String companyName);
    Company deleteByCompanyName(String companyName);
}
