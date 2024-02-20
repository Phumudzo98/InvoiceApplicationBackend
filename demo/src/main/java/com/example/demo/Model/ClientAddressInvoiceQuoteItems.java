package com.example.demo.Model;

import java.util.List;

public class ClientAddressInvoiceQuoteItems {

    private Client client;
    private ClientAddress clientAddress;
    private Invoice invoice;
    private List<Items> items;
    private Quote quote;

    public ClientAddressInvoiceQuoteItems() {
    }

    public ClientAddressInvoiceQuoteItems(Client client, ClientAddress clientAddress, Invoice invoice, List<Items> items, Quote quote) {
        this.client = client;
        this.clientAddress = clientAddress;
        this.invoice = invoice;
        this.items = items;
        this.quote = quote;
    }

    public Client getClient() {
        return client;
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

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }

    public Quote getQuote() {
        return quote;
    }

    public void setQuote(Quote quote) {
        this.quote = quote;
    }
}
