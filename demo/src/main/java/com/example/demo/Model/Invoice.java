package com.example.demo.Model;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Invoice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int invoiceId;
    private double totalAmount;
    private LocalDate date;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
    private List<Items> Items;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Invoice() {
    }

    public Invoice(int invoiceId, double totalAmount, LocalDate date, List<com.example.demo.Model.Items> items, User user) {
        this.invoiceId = invoiceId;
        this.totalAmount = totalAmount;
        this.date = date;
        Items = items;
        this.user = user;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
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

    public List<com.example.demo.Model.Items> getItems() {
        return Items;
    }

    public void setItems(List<com.example.demo.Model.Items> items) {
        Items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
