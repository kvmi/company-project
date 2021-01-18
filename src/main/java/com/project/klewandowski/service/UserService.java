package com.project.klewandowski.service;


import com.project.klewandowski.domain.Company;
import com.project.klewandowski.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User addUser(User user);

    void deleteUser(long id);

    User updateUser(long id, User user);

    User getUserById(long id);

    List<User> getUsersByCompany(String company);

    List<Company> getCompanyList();

    List<String> getCompanyNames();

    UserDetails loadUserByUsername(String username);

}
