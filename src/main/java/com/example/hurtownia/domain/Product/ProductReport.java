package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class ProductReport extends AbstractReport {

    private List<ProductTableViewDTO> data;

    public ProductReport(List<ProductTableViewDTO> data) {
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
        String[] tableHeader = {"Id", "Id dostawcy", "Kod", "Cena", "Ilosc", "Jednostka miary", "Kraj", "Kolor", "Max ilosc", "Stan"};
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

        for (ProductTableViewDTO datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getSupplierId()))));
            table.addCell(new Cell().add(new Paragraph(datum.getCode())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getPrice()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getNumber()))));
            table.addCell(new Cell().add(new Paragraph(datum.getUnitOfMeasurement())));
            table.addCell(new Cell().add(new Paragraph(datum.getCountry())));
            table.addCell(new Cell().add(new Paragraph(datum.getColor())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getMaxNumber()))));
            table.addCell(new Cell().add(new Paragraph(ProductState.calculateState(datum.getNumber(), datum.getMaxNumber()))));
        }

        document.add(table);

        document.close();
    }
}
