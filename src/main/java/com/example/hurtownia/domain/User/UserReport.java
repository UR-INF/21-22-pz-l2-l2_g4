package com.example.hurtownia.domain.user;

import com.example.hurtownia.domain.AbstractReport;
import com.example.hurtownia.domain.supplier.Supplier;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class UserReport extends AbstractReport {

    private List<UserTableViewDTO> data;

    public UserReport(List<UserTableViewDTO> data) {
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

        float columnWidth[] = {40, 40, 40, 40, 40, 40, 40, 40, 40};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Id", "Imie", "Nazwisko", "Numer telefonu", "Email", "Haslo", "Administrator", "Generowanie raportow", "Udzielanie rabatow"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[6])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[7])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[8])));

        for (UserTableViewDTO datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId()))));
            table.addCell(new Cell().add(new Paragraph(datum.getName())));
            table.addCell(new Cell().add(new Paragraph(datum.getSurname())));
            table.addCell(new Cell().add(new Paragraph(datum.getPhoneNumber())));
            table.addCell(new Cell().add(new Paragraph(datum.getEmail())));
            table.addCell(new Cell().add(new Paragraph(datum.getPassword())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getIsAdmin()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getGeneratingReports()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getGrantingDiscounts()))));
        }

        document.add(table);

        document.close();
    }
}
