package com.example.demo.Service;


import com.example.demo.Model.*;
import com.example.demo.Repository.*;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.border.DashedBorder;
import com.itextpdf.layout.border.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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


   //email settings
    @Autowired
    private MailSender mailSender;
    @Autowired
    private JavaMailSender jmSender;


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
            message.setText("Hello "+user.getF_name()+". \n\nHere is your link: ");
            message.setFrom("phumu98@gmail.com");
            message.setTo(user.getEmail());

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
    public List<Invoice> homeTop5Invoice(String email) {

//
        if(invoiceRepo.findAll().isEmpty())
        {
            return Collections.emptyList();
        }

        User user = userRepo.findByEmail(email);
        return invoiceRepo.findTop5ByUserOrderByDateDesc(user);
    }

    @Override
    public List<Quote> homeTop5Quote(String email) {
        if(quoteRepo.findAll().isEmpty())
        {
            return Collections.emptyList();
        }

        User user = userRepo.findByEmail(email);
        return quoteRepo.findTop5ByUserOrderByDateDesc(user);
    }

    @Override
    @Transactional
    public boolean createInvoiceOrQuote(String email, ClientAddressInvoiceQuoteItems caiqi) throws FileNotFoundException {
        //Desmond

        //extract info from payload
        User user = userRepo.findByEmail(email);
        double total=0;
        Client client = caiqi.getClient();
        client.setUser(user);

        ClientAddress clientAddress = caiqi.getClientAddress();
        clientAddressRepo.save(clientAddress);

        client.setClientAddress(clientAddress);

        clientRepo.save(client);


        if(caiqi.getType().equals("Invoice")) {
            Invoice invoice = caiqi.getInvoice();

            invoice.setUser(user);
            invoice.setDate(LocalDate.now());

            List<Items> items = caiqi.getItems();
            for (Items item : items) {
                item.setInvoice(invoice);
                total=total+(item.getPrice()*item.getQty());
            }

            invoice.setTotalAmount(total);

            //generate invoice no
            Random random = new Random();
            int randomNumber;

            do {
                randomNumber = random.nextInt(9000) + 1000;
            } while (invoiceRepo.existsByInvoiceNo(randomNumber));

            // Save the unique random number to the database

            invoice.setInvoiceNo(randomNumber);
            invoiceRepo.save(invoice);

            for (Items item : items) {
                item.setInvoice(invoice);
                itemRepo.save(item);
            }

            generateEmailPdf(caiqi.getType(),invoice.getDate(),user,invoice.getTotalAmount(),items,client,clientAddress, randomNumber);

            return true;
        }
        else if (caiqi.getType().equals("Quote"))
        {
            Quote quote = caiqi.getQuote();

            quote.setUser(user);
            quote.setDate(LocalDate.now());

            List<Items> items = caiqi.getItems();
            for (Items item : items) {
                item.setQuote(quote);
                total=total+(item.getPrice()*item.getQty());
            }

            quote.setTotalAmount(total);

            //generate quote no
            Random random = new Random();
            int randomNumber;

            do {
                // Generate a random number between 1000 and 9999
                randomNumber = random.nextInt(9000) + 1000;
            } while (quoteRepo.existsByQuoteNo(randomNumber));

            // Save the unique random number to the database

            quote.setQuoteNo(randomNumber);
            quoteRepo.save(quote);

            for (Items item : items) {
                item.setQuote(quote);
                itemRepo.save(item);
            }
            generateEmailPdf(caiqi.getType(),quote.getDate(),user,quote.getTotalAmount(),items,client,clientAddress,randomNumber);
            return true;
        }
        return false;
    }

    @Override
    public List<Invoice> getAllInvoices(String email) {

        try
        {
            return invoiceRepo.findByUserEmail(email);
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
    }



    public List<Quote> getAllQuote(String email)
    {

        try
        {

            return quoteRepo.findByUserEmail(email);
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
    }



    @Override
    public Invoice searchInvoice(int number, String email) {

        User user = userRepo.findByEmail(email);

        if(user!=null)
        {
            return invoiceRepo.findByInvoiceNoAndUser(number,user);
        }
        else {
            return null;
        }

    }

    @Override
    public Quote searchQuote(int number, String email) {
        User user = userRepo.findByEmail(email);

        if(user!=null)
        {
            return quoteRepo.findByQuoteNoAndUser(number,user);
        }
        else {
            return null;
        }
    }

    @Override
    public boolean updateUserDetails(User user) {

        User updateUser = userRepo.findByEmail(user.getEmail());

        if(updateUser!=null)
        {
            updateUser.setPassword(user.getPassword());
            updateUser.setF_name(user.getF_name());
            updateUser.setL_name(user.getL_name());

            userRepo.save(updateUser);

            return true;
        }
        return false;
    }

    public void sendDoc(String to, String from,String path, Client client, String type)
    {
        try {
            MimeMessage mimeMessage = jmSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // Set basic email properties
            helper.setTo(to);
            helper.setFrom("phumu98@gmail.com");
            helper.setSubject(type+" attachment");
            helper.setText("Dear "+client.getF_name()+",\n\n Attached is your "+type+".\n" +
                    "Thank your for your time.\n\nRegards\n", false); // Set to 'true' if using HTML in the body

            // Attach the file from the specified path
            FileSystemResource file = new FileSystemResource(new File(path));
            helper.addAttachment(Objects.requireNonNull(file.getFilename()),file);

            // Send the email
            jmSender.send(mimeMessage);
        } catch (MessagingException e) {
            // Handle exception (e.g., log error)
            e.printStackTrace();
        }
    }

    public void generateEmailPdf(String type, LocalDate localDate, User user, double totalAmount, List<Items> items, Client client, ClientAddress clientA, int randomNo) throws FileNotFoundException {
        String path = "invoice.pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDocument);
        //page spec end

        //change LocalDate to String
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = localDate.format(formatter);

        //header
        float threecol=190f;
        float twocol=285f;
        float twocol150=twocol+150f;
        float[] twocolumnWidth ={twocol150,twocol};
        float [] threeColumnWidth={threecol,threecol,threecol};
        float fullwidth []={threecol * 3};
        Paragraph onesp = new Paragraph("\n");

        Table table = new Table(twocolumnWidth);
        table.addCell(new Cell().add(type).setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
        Table nestedTable = new Table(new float[]{twocol/2, twocol/2});
        nestedTable.addCell(getHeaderTextCell(type+" No:"));
        nestedTable.addCell(getHeaderTextCellValue(String.valueOf(randomNo)));
        nestedTable.addCell(getHeaderTextCell("Issue date:"));
        nestedTable.addCell(getHeaderTextCellValue(dateString));


        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

        Border gb=new SolidBorder(Color.GRAY, 1f/2f);
        Table divider=new Table (fullwidth);
        divider.setBorder(gb);
        document.add(table);
        document.add(onesp);
        document.add(divider);
        document.add(onesp);

        Table twoColTable = new Table(twocolumnWidth);
        twoColTable.addCell(getBilling("Company Information"));
        twoColTable.addCell(getBilling("Client Information"));
        document.add(twoColTable.setMarginBottom(12f));

        Table twoColTable2 = new Table(twocolumnWidth);
        twoColTable2.addCell(getCell10left("Company Name", true));
        twoColTable2.addCell(getCell10left("Name", true));
        twoColTable2.addCell(getCell10left("Mfactory", false));
        twoColTable2.addCell(getCell10left(client.getF_name()+" "+client.getL_name(), false));



        document.add(twoColTable2);

        Table twoColTable3 = new Table(twocolumnWidth);
        twoColTable3.addCell(getCell10left("Company Number", true));
        twoColTable3.addCell(getCell10left("Address", true));
        twoColTable3.addCell(getCell10left("U809010098", false));
        twoColTable3.addCell(getCell10left(String.valueOf(clientA.getStreetNo()+", "+clientA.getStreetName()+","+clientA.getTown()
                +"\n"+clientA.getCity()+"\n"+String.valueOf(clientA.getPostalCode())), false));
        document.add(twoColTable3);

        float oneColumnwidth[]={twocol150};

        Table oneColTable1=new Table(oneColumnwidth);
        oneColTable1.addCell(getCell10left("Company Address", true));
        oneColTable1.addCell(getCell10left("132 Partridge Avenue, Allen Grove\n" +
                "Kempton Park, Gauteng\n", false));
        oneColTable1.addCell(getCell10left("Email", true));
        oneColTable1.addCell(getCell10left("help@mfactory.mobi", false));
        document.add(oneColTable1.setMarginBottom(10f));

        Table tableDivider2=new Table(fullwidth);
        Border dgb = new DashedBorder(Color.GRAY,0.5f);
        document.add(tableDivider2.setBorder(dgb));
        Paragraph ProductPara=new Paragraph("Products");

        document.add(ProductPara.setBold());
        Table threeColTable1 = new Table(threeColumnWidth);
        threeColTable1.setBackgroundColor(Color.BLACK,0.7f);

        threeColTable1.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threeColTable1.addCell(new Cell().add("Price").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));
        document.add(threeColTable1);



        Table threeColTable2 = new Table(threeColumnWidth);

        float totalsum = (float) totalAmount;
        for(Items product:items)
        {

            threeColTable2.addCell(new Cell().add(product.getDesc()).setBorder(Border.NO_BORDER).setMarginLeft(10f));
            threeColTable2.addCell(new Cell().add(String.valueOf(product.getQty())).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
            threeColTable2.addCell(new Cell().add(String.valueOf(product.getPrice())).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER).setMarginRight(15f));

        }
        document.add(threeColTable2.setMarginBottom(20f));
        float onetwo[]={threecol+155f,threecol*2};
        Table threecolTable4 = new Table(onetwo);
        threecolTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
        threecolTable4.addCell(new Cell().add(tableDivider2).setBorder(Border.NO_BORDER));
        document.add(threecolTable4);

        Table threecolTable3 = new Table(threeColumnWidth);
        threecolTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).setMarginLeft(10f);
        threecolTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        threecolTable3.addCell(new Cell().add(String.valueOf(totalsum)).setTextAlignment(TextAlignment.RIGHT).setBorder(Border.NO_BORDER));

        document.add(threecolTable3);
        document.add(tableDivider2);
        document.add(new Paragraph("\n"));
        document.add(divider.setBorder(new SolidBorder(Color.GRAY,1)).setMarginBottom(15f));
        Table tb = new Table(fullwidth);
        tb.addCell(new Cell().add("TERMS AND CONDITIONS\n").setBold().setBorder(Border.NO_BORDER));
        List<String>TncList = new ArrayList<>();
        TncList.add("1. The Seller shall bot be liable to the buyer directly or indirectly for any loss or damage suffered by the buyer");
        TncList.add("1. The Seller warrants the products for one (1) year fromthe issued date");

        for (String tnc:TncList){
            tb.addCell(new Cell().add(tnc).setBorder(Border.NO_BORDER));
        }
        document.add(tb);

        document.close();

        sendDoc(client.getEmail(), user.getEmail(), path, client,type);
    }

    static Cell getHeaderTextCell (String textValue){
        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    static Cell getHeaderTextCellValue (String textValue){
        return new Cell().add(textValue).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
    }

    static Cell getBilling(String textValue){
        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
    }

    static Cell getCell10left(String textValue,Boolean isBold){
        Cell myCell=new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
        return isBold ?myCell.setBold():myCell;
    }
    }

