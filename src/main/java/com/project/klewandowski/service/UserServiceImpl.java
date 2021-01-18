package com.project.klewandowski.service;



import com.project.klewandowski.domain.Company;
import com.project.klewandowski.domain.User;
import com.project.klewandowski.repository.CompanyRepository;
import com.project.klewandowski.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Primary
@Service
public class UserServiceImpl implements UserService {

    private List<User> userList = new ArrayList<>();
    private List<Company> companyList = new ArrayList<>();

    private UserRepository userRepository;
    private CompanyRepository companyRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, CompanyRepository companyRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
                return userRepository.save(user);
    }

    @Override
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(long id, User user) {
        User userToUpdate = getUserById(id);
        userToUpdate.setCompany(user.getCompany());
        userToUpdate.setDateOfEmployment(user.getDateOfEmployment());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());
        return userRepository.save(userToUpdate);
    }

    @Override
    public User getUserById(long id) {

        return userRepository.findById(id).orElse(null);

    }

    @Override
    public List<User> getUsersByCompany(String company) {
        return userRepository.findAllByCompany(company);
    }

    public List<Company> getCompanyList() {
        return companyList;
    }

    public List<String> getCompanyNames() {
        List<String> companyNames = new ArrayList<>();
        for (Company company :
                companyList) {
            companyNames.add(company.getCompanyName());
        }
        return companyNames;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), null);
    }



    private boolean isValidEmailAddress(String email) {
        return email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    private boolean firstNameValidation(String firstName){


        return firstName.matches("/^[\\s\\p{L}]+$/u");
    }


}
