package com.example.demo.Model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Quote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double totalAmount;
    private LocalDate date;


    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL)
    private List<Items> items;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Quote() {
    }

    public Quote(int id, double totalAmount, LocalDate date, List<Items> items, Client client) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.date = date;
        this.items = items;
        this.client = client;
    }

    public int getId() {
        return id;
    }
    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
