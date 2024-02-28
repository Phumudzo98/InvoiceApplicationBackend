package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.List;


public class ClientAddressInvoiceQuoteItems implements Serializable {

    private Client client;
    private ClientAddress clientAddress;

    private Invoice invoice;
    private String type;
    private List<Items> items;

    private Quote quote;

    public ClientAddressInvoiceQuoteItems() {
    }

    public ClientAddressInvoiceQuoteItems(Client client, ClientAddress clientAddress, Invoice invoice, String type, List<Items> items) {
        this.client = client;
        this.clientAddress = clientAddress;
        this.invoice = invoice;
        this.type = type;
        this.items = items;
    }

    public Client getClient() {
        return client;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientAddress getClientAddress() {
        return clientAddress;
    }

    public void setClientAddress(ClientAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Invoice getInvoice() {
      return invoice;
    }
//
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<Items> getItems() {
        return items;
    }
//
    public void setItems(List<Items> items) {
        this.items = items;
    }
//
    public Quote getQuote() {
        return quote;
    }
//
    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
