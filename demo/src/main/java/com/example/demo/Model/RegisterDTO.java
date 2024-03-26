package com.example.demo.Model;

import java.io.Serializable;

public class RegisterDTO implements Serializable {

    private User user;
    private BusinessInfo businessInfo;

    public RegisterDTO(User user, BusinessInfo businessInfo) {
        this.user = user;
        this.businessInfo = businessInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BusinessInfo getBusinessInfo() {
        return businessInfo;
    }

    public void setBusinessInfo(BusinessInfo businessInfo) {
        this.businessInfo = businessInfo;
    }
}
