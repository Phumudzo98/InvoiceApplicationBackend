package com.example.demo.Service;

import com.example.demo.Model.ClientAddressInvoiceQuoteItems;
import com.example.demo.Model.Invoice;
import com.example.demo.Model.Quote;
import com.example.demo.Model.User;

import java.util.List;

public interface Interface {

    public boolean registerUser(User user);
    public boolean loginApp(String email, String password);
    public boolean deleteInvoice(int id);
    public Invoice searchInvoice(int id);
    public List<Invoice> homeTop5Invoice();
    public List<Quote> homeTop5Quote();
    public boolean createInvoiceOrQuote(ClientAddressInvoiceQuoteItems caiqi);
    public List<Invoice> getAllInvoices();
    public boolean updateUserDetails(User user);

}
