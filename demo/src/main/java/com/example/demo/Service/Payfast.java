package com.example.demo.Service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.client.methods.HttpPost;
import org.springframework.stereotype.Service;

@Service
public class Payfast {
    private static final String PAY_FAST_API_URL = "https://sandbox.payfast.co.za/eng/process";
    private static final String MERCHANT_ID = "10029164";
    private static final String MERCHANT_KEY = "2oki9md2y1wjc";
    private static final String PAYMENT_SUCCESS_URL = "http://localhost:8080/";


    public String initiatePayment(double amount, String description, String email,int randomNo) throws IOException {
        String paymentProcessUrl="";

        HttpPost post = new HttpPost(PAY_FAST_API_URL);
        List<NameValuePair> params = new ArrayList<>();


        params.add(new BasicNameValuePair("merchant_id", MERCHANT_ID));
        params.add(new BasicNameValuePair("merchant_key", MERCHANT_KEY));
        params.add(new BasicNameValuePair("amount", String.valueOf(amount)));
        params.add(new BasicNameValuePair("item_name", description));
        params.add(new BasicNameValuePair("return_url", PAYMENT_SUCCESS_URL+email+"/"+randomNo+"/"+amount));
        post.setEntity(new UrlEncodedFormEntity(params));

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(post);
        HttpEntity responseEntity = response.getEntity();

        String responseBody = EntityUtils.toString(responseEntity);
        System.out.println(responseBody);

        Pattern pattern = Pattern.compile("<a href=\"(.*?)\">");
        Matcher matcher = pattern.matcher(responseBody);
        if (matcher.find()) {
            paymentProcessUrl = matcher.group(1);
        }

        return paymentProcessUrl;
    }

    private String extractPaymentProcessUrl(String responseBody) {
        // Implement logic to extract payment process URL from HTML response body
        // Example: use regular expressions or HTML parsing libraries to find the URL
        return "example_payment_process_url";
    }

}
