package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class OrderReport extends AbstractReport {

    private List<OrderDTO> data;

    public OrderReport(List<OrderDTO> data) {
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

        float columnWidth[] = {40, 40, 40, 40, 40};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Id", "Id klienta", "Data zamowienia", "Rabat", "Stan"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4])));

        for (OrderDTO datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getCustomerId()))));
            table.addCell(new Cell().add(new Paragraph(datum.getDate())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getDiscount()))));
            table.addCell(new Cell().add(new Paragraph(datum.getState())));
        }

        document.add(table);
        document.close();
    }
}
