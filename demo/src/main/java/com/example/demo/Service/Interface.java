package com.example.demo.Service;

import com.example.demo.Model.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

public interface Interface {

    public boolean registerUser(User user);
    public boolean loginApp(String email, String password);
    public boolean forgotPassword(String email);
    public boolean deleteInvoice(int id, String email);
    public Invoice searchInvoice(int number, String email);
    public Quote searchQuote(int number, String email);
    public List<Invoice> homeTop5Invoice(String email);
    public List<Quote> homeTop5Quote(String email);
    public boolean createInvoiceOrQuote(String email, ClientAddressInvoiceQuoteItems caiqi) throws FileNotFoundException;
    public List<Invoice> getAllInvoices(String email);
    public boolean updateUserDetails(User user);
    public List<Quote> getAllQuote(String email);
    public void sendDoc(String to, String from,String path, Client client, String type);

    public void generateEmailPdf(String type, LocalDate localDate,
                                 User user, double totalAmount, List<Items> items,
                                 Client client, ClientAddress clientA, int randomNo) throws FileNotFoundException;


    }




