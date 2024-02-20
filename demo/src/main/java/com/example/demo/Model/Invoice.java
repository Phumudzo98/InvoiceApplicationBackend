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

    public Invoice() {
    }

    public Invoice(int invoiceId, double totalAmount, LocalDate date, List<Items> items) {
        this.invoiceId = invoiceId;
        this.totalAmount = totalAmount;
        this.date = date;
        Items = items;
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

    public List<Items> getItems() {
        return Items;
    }

    public void setItems(List<Items> items) {
        Items = items;
    }
}
