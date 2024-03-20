package com.example.demo.controller;


import com.example.demo.Model.ClientAddressInvoiceQuoteItems;
import com.example.demo.Model.Invoice;
import com.example.demo.Model.Quote;
import com.example.demo.Model.User;
import com.example.demo.Service.Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
public class Control implements ErrorController {

    //Interact with backend methods
    @Autowired
    private Interface appService;

    //For testing purpose only. Spring Security will be implemented
    private String email="phumu98@gmail.com";


    //Register User/Admin
    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody User user)
    {
        boolean result = appService.registerUser(user);

        if(result)
        {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/{email}/{invoiceNo}")
    public RedirectView changeStatus(@PathVariable("email") String email, @PathVariable("invoiceNo") int invoiceNo)
    {

        appService.changeStatus(email,invoiceNo);

        return new RedirectView("https://www.google.com");

    }

    //login User/Admin
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

    //Create invoice or quote document
    @PostMapping("/createInvoiceOrQuote")
    public ResponseEntity<Boolean> createInvoiceOrQuote(@RequestBody ClientAddressInvoiceQuoteItems caiqi) throws IOException {
        boolean check = appService.createInvoiceOrQuote(email,caiqi);
        if(check)
        {
            return ResponseEntity.ok(true);
        }
        else {
            return ResponseEntity.badRequest().body(false);
        }

    }

    //search invoice. No front end for this one yet.
    @GetMapping("/searchInvoice")
    public ResponseEntity<Invoice> searchInvoice(@RequestParam int number)
    {
        Invoice invoice = appService.searchInvoice(number,email);

                if(invoice!=null)
                {
                    return ResponseEntity.ok(invoice);
                }
                else
                {
                    return ResponseEntity.notFound().build();
                }
    }

    @GetMapping("/searchQuote")
    public ResponseEntity<Quote> searchQuote(@RequestParam int number)
    {
        Quote quote = appService.searchQuote(number,email);

        if(quote!=null)
        {
            return ResponseEntity.ok(quote);
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getTotalUnpaid")
    public double totalAmountofInvoices()
    {
        return appService.invoiceTotalAmt(email);
    }

    //Display 5 latest quotes on home page
    @GetMapping("/homeQuotes")
    public ResponseEntity<List<Quote>> display5QuoteOnHome()
    {
        List<Quote> quotes = appService.homeTop5Quote(email);

        if(quotes!=null)
        {
            return ResponseEntity.ok(quotes);
        }
        else
        {
            return ResponseEntity.ok(null);
        }
    }


    //Display 5 latest invoices on home page
    @GetMapping("/homeInvoices")
    public ResponseEntity<List<Invoice>> display5InvoicesHome()
    {
        List<Invoice> invoices = appService.homeTop5Invoice(email);

        if(invoices!=null)
        {
            return ResponseEntity.ok(invoices);
        }
        else
        {
            return ResponseEntity.ok(null);
        }
    }


    //Button on home page for invoices
    @GetMapping("/displayAllInvoices")
    public ResponseEntity<List<Invoice>> getAllInvoice()
    {
        List<Invoice> allInvoice = appService.getAllInvoices(email);

        if(allInvoice!=null)
        {
            return ResponseEntity.ok(allInvoice);
        }
        else {
            return ResponseEntity.ok(null);
        }
    }


    @GetMapping("/displayAllQuotes")
    public ResponseEntity<List<Quote>> getAllQuotes()
    {
        List<Quote> allQuote = appService.getAllQuote(email);

        if(allQuote!=null)
        {
            return ResponseEntity.ok(allQuote);
        }
        else {
            return ResponseEntity.ok(null);
        }
    }

    @GetMapping("/forgotPassword")
    public ResponseEntity<Boolean> forgotPassword(@RequestParam String email)
    {
        boolean result = appService.forgotPassword(email);

        if(result)
        {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.badRequest().body(false);
    }

   @PostMapping("/updateProfile")
    public ResponseEntity<Boolean> updateProfile(User user)    {
        boolean result = appService.updateUserDetails(user);

        if(result)
        {
            return ResponseEntity.ok(true);
        }

        return ResponseEntity.badRequest().body(false);
    }

    @GetMapping("/homeTop5Quotes")
    public ResponseEntity<List<Quote>> homeTop5Quotes()
    {
        List<Quote> result = appService.homeTop5Quote(email);

        if(result.isEmpty())
        {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.ok(result);
    }

    @GetMapping("/homeTop5Invoices")
    public ResponseEntity<List<Invoice>> homeTop5Invoices()
    {
        List<Invoice> result = appService.homeTop5Invoice(email);

        if(result.isEmpty())
        {
            return ResponseEntity.ok(result);
        }

        return ResponseEntity.ok(result);

    }

}
