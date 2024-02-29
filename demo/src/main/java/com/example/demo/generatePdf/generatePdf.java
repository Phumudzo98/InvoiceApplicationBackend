package com.example.demo.generatePdf;

import com.example.demo.Model.*;
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

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class generatePdf {


    public void generateEmailPdf(String type, LocalDate localDate, User user,double totalAmount,List<Items> items,Client client, ClientAddress clientA) throws FileNotFoundException {


        //page specification
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
        nestedTable.addCell(getHeaderTextCell("Invoice No:"));
        nestedTable.addCell(getHeaderTextCellValue("K4750544"));
        nestedTable.addCell(getHeaderTextCell("Issue date:"));
        nestedTable.addCell(getHeaderTextCellValue(dateString));


        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));

        Border gb=new SolidBorder (Color.GRAY, 1f/2f);
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
        System.out.println("pdf is generated");
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

