package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private int quoteNo;

    @OneToMany(mappedBy = "quote", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Items> items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Quote() {
    }

    public Quote(int id, double totalAmount, LocalDate date, int quoteNo, List<Items> items, User user) {
        this.id = id;
        this.totalAmount = totalAmount;
        this.date = date;
        this.quoteNo = quoteNo;
        this.items = items;
        this.user = user;
    }

    public int getId() {
        return id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuoteNo() {
        return quoteNo;
    }

    public void setQuoteNo(int quoteNo) {
        this.quoteNo = quoteNo;
    }
}
