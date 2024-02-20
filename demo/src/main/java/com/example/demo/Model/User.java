package com.example.demo.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;


@Entity
public class User implements Serializable {


    @Id
    private String email;
    private String password;
    private String f_name;
    private String l_name;
    //private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private BusinessInfo businessInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Client> client;

    public User() {
    }

    public User(String email, String password, String f_name, String l_name, BusinessInfo businessInfo, List<Client> client) {
        this.email = email;
        this.password = password;
        this.f_name = f_name;
        this.l_name = l_name;
        this.businessInfo = businessInfo;
        this.client = client;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public String getL_name() {
        return l_name;
    }

    public void setL_name(String l_name) {
        this.l_name = l_name;
    }

    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }

    public List<Client> getClient() {
        return client;
    }

    public void setClient(List<Client> client) {
        this.client = client;
    }
}
