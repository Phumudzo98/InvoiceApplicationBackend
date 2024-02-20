package com.example.demo.Model;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class BusinessInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String companyName;
    private String streetNo;
    private String streetName;
    private String town;
    private String City;
    private int postalCode;

    @OneToOne(fetch =FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    public BusinessInfo() {
    }

    public BusinessInfo(String companyName, String streetNo, String streetName, String town, String city, int postalCode) {

        this.companyName = companyName;
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.town = town;
        City = city;
        this.postalCode = postalCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }
}