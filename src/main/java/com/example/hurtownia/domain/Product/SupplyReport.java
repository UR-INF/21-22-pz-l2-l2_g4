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

public class SupplyReport extends AbstractReport {
    private List<Product> data;

    public SupplyReport(List<Product> data) {
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

        float columnWidth[] = {40, 40, 40, 40};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Id dostawcy", "Kod produktu", "Jednostka miary", "Ilosc"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));

        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).isSupply()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getSupplier().getId()))));
                table.addCell(new Cell().add(new Paragraph(data.get(i).getCode())));
                table.addCell(new Cell().add(new Paragraph(data.get(i).getUnitOfMeasurement())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getMaxNumber() - data.get(i).getNumber()))));
            }
        }

        document.add(table);

        document.close();
    }
}
