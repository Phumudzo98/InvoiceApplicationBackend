package com.example.demo.Service;


import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class Impl implements Interface {


    //Initialize/Declare variables/repository
    //repo
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private BusinessInfoRepository businessRepo;
    @Autowired
    private InvoiceRepository invoiceRepo;
    @Autowired
    private ItemsRepository itemRepo;
    @Autowired
    private QuoteRepository quoteRepo;
    @Autowired
    private ClientRepository clientRepo;
    @Autowired
    private ClientAddressRepository clientAddressRepo;
    @Autowired
    private MailSender mailSender;

    @Override
    @Transactional
    public boolean registerUser(User user)
    {
        Optional<User> check = userRepo.findById(user.getEmail());

        if(check.isEmpty())
        {
            user.setEmail(user.getEmail());
            userRepo.save(user);

            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("Registration activation link");
            message.setText("Here is your link: ");
            message.setFrom("phumu98@gmail.com");
            message.setTo("phumu98@gmail.com");

            mailSender.send(message);

            return true;
        }
        return false;
    }


    @Override
    public boolean loginApp(String email, String password) {
        User user = userRepo.findByEmailAndPassword(email, password);
        if(user==null)
        {
            return false;
        }
        return user.getEmail().equals(email) && user.getPassword().equals(password);
    }

    @Override
    public boolean forgotPassword(String email) {
        User checkEmail = userRepo.findByEmail(email);

        if(checkEmail==null)
        {
            return false;
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("Forgot password");
        message.setText("Dear "+checkEmail.getF_name()+" "+checkEmail.getL_name()+"\n\nHere is your link: ");
        message.setFrom("phumu98@gmail.com");
        message.setTo(email);

        mailSender.send(message);

        return true;

    }


    @Override
    public boolean deleteInvoice(int id, String email) {

        if(invoiceRepo.existsById(id))
        {
            invoiceRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Invoice searchInvoice(int id, String email) {
        try
        {
            if(invoiceRepo.existsById(id))
            {
                return invoiceRepo.findById(id).orElse(null);
            }
        }
        catch (Exception e)
        {
            return null;
        }
        return null;
    }

    @Override
    public List<Invoice> homeTop5Invoice(String email) {

//

        if(invoiceRepo.findAll().isEmpty())
        {
            return Collections.emptyList();
        }

        return invoiceRepo.findTop5InvoicesByUserEmailOrderByDateDesc(email);
    }

    @Override
    public List<Quote> homeTop5Quote(String email) {
        if(quoteRepo.findAll().isEmpty())
        {
            return Collections.emptyList();
        }
        return quoteRepo.findTop5QuotesByUserEmailOrderByDateDesc(email);
    }

    @Override
    @Transactional
    public boolean createInvoiceOrQuote(String email, ClientAddressInvoiceQuoteItems caiqi) {
        //Desmond

        Client client = caiqi.getClient();
        ClientAddress clientAddress = caiqi.getClientAddress();
        Invoice invoice = caiqi.getInvoice();
        List<Items> items = caiqi.getItems();
        Quote quote =caiqi.getQuote();

        if(invoice==null)
        {
            clientRepo.save(client);
            clientAddressRepo.save(clientAddress);
            quoteRepo.save(quote);

            for(Items item: quote.getItems())
            {
                item.setQuote(quote);
                itemRepo.save(item);
            }
            return true;

        } else if (quote==null) {
            clientRepo.save(client);
            clientAddressRepo.save(clientAddress);
            invoiceRepo.save(invoice);

            for(Items item: invoice.getItems())
            {
                item.setInvoice(invoice);
                itemRepo.save(item);
            }
            return true;

        }
        return false;
    }

    @Override
    public List<Invoice> getAllInvoices(String email) {

        try
        {
            return invoiceRepo.getAllInvoices(email);
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
    }

    @Override
    public boolean updateUserDetails(User user) {

        User updateUser = userRepo.findByEmail(user.getEmail());
        return false;
    }


}
