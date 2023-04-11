package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class OrderItemReport extends AbstractReport {

    private List<OrderItem> data;

    public OrderItemReport(List<OrderItem> data) {
        this.data = data;
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
        String[] tableHeader = {"Id", "Id zamowienia", "Id produktu", "Cena elementu", "Cena za jednostke", "Ilosc"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5])));

        for (OrderItem datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getOrder().getId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getProduct().getId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getItemPrice()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getPricePerUnit()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getAmount()))));
        }

        document.add(table);

        document.close();
    }
}
