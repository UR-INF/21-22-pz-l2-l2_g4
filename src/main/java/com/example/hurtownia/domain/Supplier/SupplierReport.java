package com.example.hurtownia.domain.supplier;

import com.example.hurtownia.domain.AbstractReport;
import com.example.hurtownia.domain.order.Order;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class SupplierReport extends AbstractReport {

    private List<Supplier> data;

    public SupplierReport(List<Supplier> data) {
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

        float columnWidth[] = {40, 40, 40, 40, 40, 40, 40};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Id", "Nazwa", "NIP", "Email", "Miejscowosc", "Ulica", "Kraj"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[6])));

        for (Supplier datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId()))));
            table.addCell(new Cell().add(new Paragraph(datum.getName())));
            table.addCell(new Cell().add(new Paragraph(datum.getNip())));
            table.addCell(new Cell().add(new Paragraph(datum.getEmail())));
            table.addCell(new Cell().add(new Paragraph(datum.getPlace())));
            table.addCell(new Cell().add(new Paragraph(datum.getStreet())));
            table.addCell(new Cell().add(new Paragraph(datum.getCountry())));
        }

        document.add(table);

        document.close();
    }
}
