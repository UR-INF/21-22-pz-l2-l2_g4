package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;

public class InvoiceReport extends AbstractReport {

    private Order order;

    public InvoiceReport(Order order) {
        this.order = order;
    }

    @Override
    public void generateReport(String path, String title) throws FileNotFoundException {
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();
        Document document = new Document(pdfDocument);

        Paragraph paragraph = new Paragraph(title);
        document.add(paragraph);

        float columnWidth[] = {40, 40, 40, 40, 40, 40};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Imie", "Nazwisko", "Data zamowienia", "Cena", "Rabat", "Cena po rabacie"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5])));

        table.addCell(new Cell().add(new Paragraph(order.getKlient().getName())));
        table.addCell(new Cell().add(new Paragraph(order.getKlient().getSurname())));
        table.addCell(new Cell().add(new Paragraph(order.getDate())));
        table.addCell(new Cell().add(new Paragraph(order.getKlient().getName())));
        String discount;
        switch (String.valueOf(order.getDiscount())) {
            case "0.1":
                discount = "-10%";
                break;
            case "0.2":
                discount = "-20%";
                break;
            default:
                discount = "nie udzielono";
        }
        table.addCell(new Cell().add(new Paragraph(discount)));
        table.addCell(new Cell().add(new Paragraph(order.getState())));

        document.add(table);

        document.close();
    }
}
