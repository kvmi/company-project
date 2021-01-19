package com.project.klewandowski.service;


import com.opencsv.CSVReader;
import com.project.klewandowski.domain.Company;
import com.project.klewandowski.domain.User;
import com.project.klewandowski.repository.CompanyRepository;
import com.project.klewandowski.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


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

//        loadPersonCSV();
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

    public org.springframework.security.core.userdetails.User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority(("ROLE_USER"))));
    }


    @Override
    public Page<User> findPaginated(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    private boolean isValidEmailAddress(String email) {
        return email.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$");
    }

    private boolean firstNameValidation(String firstName) {


        return firstName.matches("/^[\\s\\p{L}]+$/u");
    }

//    private void loadPersonCSV() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
//
//        try {
//
//            // Create an object of filereader
//            // class with CSV file as a parameter.
//            FileReader filereader = new FileReader("D:\\Git\\projekt\\src\\main\\java\\com\\lewandowski\\springproject\\service\\Data.csv");
//
//            // create csvReader object passing
//            // file reader as a parameter
//            CSVReader csvReader = new CSVReader(filereader);
//            String[] nextRecord;
//            csvReader.readNext();
//
//            // we are going to read data line by line
//            while ((nextRecord = csvReader.readNext()) != null) {
//                Company company = new Company();
//                company.setId(Long.parseLong(nextRecord[0]));
//                company.setCompanyName(nextRecord[4]);
//                company.setCompanyPresident(null);
//                companyRepository.save(company);
//
//                User user = new User();
//                user.setId(Long.parseLong(nextRecord[0]));
//                user.setFirstName(nextRecord[1]);
//                user.setLastName(nextRecord[2]);
//                user.setEmail(nextRecord[3]);
//                user.setDateOfEmployment(LocalDate.parse(nextRecord[5], formatter));
//                Set<Company> companySet = new HashSet<>();
//                companySet.add(company);
//                user.setCompany(companySet);
//                user.setUsername(user.getFirstName());
//                user.setPassword(user.getLastName());
//
//                userRepository.save(user);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}
