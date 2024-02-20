package com.example.demo.controller;


import com.example.demo.Model.ClientAddressInvoiceQuoteItems;
import com.example.demo.Model.Invoice;
import com.example.demo.Model.Quote;
import com.example.demo.Model.User;
import com.example.demo.Service.Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class Control {

    @Autowired
    private Interface appService;

    @GetMapping("/name")
    public String getName()
    {
        return "Phumudzo";
    }
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody User user)
    {


        return ResponseEntity.ok(true);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestParam String email, @RequestParam String password)
    {
        boolean log = appService.loginApp(email,password);
        if(log)
        {
            return ResponseEntity.ok(true);
        }
        else
        {
            return ResponseEntity.badRequest().body(false);
        }
    }

    @PostMapping("/createInvoiceOrQuote")
    public ResponseEntity<Boolean> createInvoiceOrQuote(@RequestBody ClientAddressInvoiceQuoteItems caiqi)
    {
        boolean check = appService.createInvoiceOrQuote(caiqi);
        if(check)
        {
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.badRequest().body(false);
        }

    }
    @DeleteMapping("/searchInvoice")
    public ResponseEntity<Invoice> searchInvoice(@RequestParam int id)
    {
        Invoice invoice = appService.searchInvoice(id);

                if(invoice!=null)
                {
                    return ResponseEntity.ok(invoice);
                }
                else
                {
                    return ResponseEntity.notFound().build();
                }
    }

    @GetMapping("/homeQuotes")
    public ResponseEntity<List<Quote>> display5QuoteOnHome()
    {
        List<Quote> quotes = appService.homeTop5Quote();

        if(quotes!=null)
        {
            return ResponseEntity.ok(quotes);
        }
        else
        {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/displayAllInvoices")
    public ResponseEntity<List<Invoice>> getAllInvoice()
    {
        List<Invoice> allInvoice = appService.getAllInvoices();

        if(allInvoice!=null)
        {
            return ResponseEntity.ok(allInvoice);
        }
        else {
            return ResponseEntity.ok(null);
        }
    }



}
