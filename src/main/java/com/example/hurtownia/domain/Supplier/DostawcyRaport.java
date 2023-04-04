package com.example.hurtownia.domain.Supplier;

import com.example.hurtownia.PDFGeneration.RaportAbstract;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class DostawcyRaport extends RaportAbstract {

    private List<Dostawca> data;

    public DostawcyRaport(List<Dostawca> data) {
        this.data = data;
    }

    @Override
    public void generatePDF(String path, String title) throws FileNotFoundException {
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

        for (int i = 0; i < data.size(); i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getId()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getNazwa())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getNip())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getEmail())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getMiejscowosc())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getUlica())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getKraj())));
        }

        document.add(table);

        document.close();
    }
}
