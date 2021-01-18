package com.project.klewandowski.repository;


import com.project.klewandowski.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByCompany(String companyName);
    User findByUsername(String username);
}
