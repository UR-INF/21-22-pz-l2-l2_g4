package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class InvoiceReport extends AbstractReport {

    private InvoiceData invoiceData;

    public void setData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
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

        table.addCell(new Cell().add(new Paragraph(invoiceData.getName())));
        table.addCell(new Cell().add(new Paragraph(invoiceData.getSurname())));
        table.addCell(new Cell().add(new Paragraph(invoiceData.getDate())));
        table.addCell(new Cell().add(new Paragraph(invoiceData.getValue())));
        table.addCell(new Cell().add(new Paragraph(invoiceData.getDiscount())));
        table.addCell(new Cell().add(new Paragraph(invoiceData.getValueAfterDiscount())));

        document.add(table);
        document.close();
    }
}
