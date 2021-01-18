package com.project.klewandowski.domain;


import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String companyName;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User companyPresident;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public User getCompanyPresident() {
        return companyPresident;
    }

    public void setCompanyPresident(User companyPresident) {
        this.companyPresident = companyPresident;
    }


}
