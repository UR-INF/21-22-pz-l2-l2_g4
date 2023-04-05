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

    private List<Order> data;

    public OrderReport(List<Order> data) {
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

        for (int i = 0; i < data.size(); i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getKlient().getId()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getDate())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getDiscount()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getState())));
        }

        document.add(table);

        document.close();
    }
}
