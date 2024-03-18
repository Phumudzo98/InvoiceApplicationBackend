package com.example.demo.Service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalService {

    @Value("${paypal.clientId}")
    private String clientId;
    @Value("${paypal.clientSecret}")
    private String clientSecret;
    @Value("${paypal.mode}")
    private String mode;

    public String createPayment(String amount, String description)
    {
        APIContext apiContext = new APIContext(clientId, clientSecret, mode);
        Amount paymentAmount = new Amount();
        paymentAmount.setCurrency("USD");
        paymentAmount.setTotal(amount);

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(paymentAmount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://google.com");
        redirectUrls.setReturnUrl("http://google.com");

        payment.setRedirectUrls(redirectUrls);


        try{
            Payment createdPayment = payment.create(apiContext);
            return extractApprovalUrl(createdPayment);
        } catch (PayPalRESTException e) {
            throw new RuntimeException(e);
        }

    }

    public String extractApprovalUrl(Payment payment)
    {
        return payment.getLinks().stream()
                .filter(link -> "approval_url".equals(link.getRel()))
                .findFirst()
                .map(Links::getHref)
                .orElse(null);
    }

}
