package com.example.hurtownia.domain.customer;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class CustomerReport extends AbstractReport {

    private List<Customer> data;

    public CustomerReport(List<Customer> data) {
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

        float columnWidth[] = {40, 40, 40, 40, 40, 40, 40, 40, 40, 40};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Id", "Imie", "Nazwisko", "Miejscowosc", "Ulica", "Nr budynku", "Nr mieszkania", "Email", "Nr telefonu", "PESEL"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[6])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[7])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[8])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[9])));

        for (int i = 0; i < data.size(); i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getId()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getName())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getSurname())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getPlace())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getStreet())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getBuildingNumber()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getApartmentNumber()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getEmail())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getPhoneNumber())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getPesel())));
        }

        document.add(table);

        document.close();
    }
}
