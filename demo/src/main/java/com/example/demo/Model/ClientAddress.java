package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ClientAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int streetNo;
    private String streetName;
    private String town;
    private String city;
    private int postalCode;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="client_id")
    @JsonIgnore
    private Client client;

    public ClientAddress() {
    }

    public ClientAddress(int id, int streetNo, String streetName, String town, String city, int postalCode, Client client) {
        this.id = id;
        this.streetNo = streetNo;
        this.streetName = streetName;
        this.town = town;
        this.city = city;
        this.postalCode = postalCode;
        this.client = client;
    }

    public int getId() {
        return id;
    }


    public int getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(int streetNo) {
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
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
