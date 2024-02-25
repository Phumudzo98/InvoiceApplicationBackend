package com.example.demo.Service;

import com.example.demo.Model.ClientAddressInvoiceQuoteItems;
import com.example.demo.Model.Invoice;
import com.example.demo.Model.Quote;
import com.example.demo.Model.User;

import java.util.List;

public interface Interface {

    public boolean registerUser(User user);
    public boolean loginApp(String email, String password);
    public boolean forgotPassword(String email);
    public boolean deleteInvoice(int id, String email);
    public Invoice searchInvoice(int id, String email);
    public List<Invoice> homeTop5Invoice(String email);
    public List<Quote> homeTop5Quote(String email);
    public boolean createInvoiceOrQuote(String email, ClientAddressInvoiceQuoteItems caiqi);
    public List<Invoice> getAllInvoices(String email);
    public boolean updateUserDetails(User user);

}
