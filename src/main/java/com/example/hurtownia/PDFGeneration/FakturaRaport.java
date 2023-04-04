package com.example.hurtownia.PDFGeneration;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.example.hurtownia.domain.Order.Zamowienie;

import java.io.FileNotFoundException;

public class FakturaRaport extends RaportAbstract {

    private Zamowienie zamowienie;

    public FakturaRaport(Zamowienie zamowienie) {
        this.zamowienie = zamowienie;
    }

    @Override
    public void generatePDF(String path, String title) throws FileNotFoundException {
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

        table.addCell(new Cell().add(new Paragraph(zamowienie.getKlient().getImie())));
        table.addCell(new Cell().add(new Paragraph(zamowienie.getKlient().getNazwisko())));
        table.addCell(new Cell().add(new Paragraph(zamowienie.getData())));
        table.addCell(new Cell().add(new Paragraph(zamowienie.getKlient().getImie())));
        String rabat;
        switch (String.valueOf(zamowienie.getRabat())) {
            case "0.1":
                rabat = "-10%";
                break;
            case "0.2":
                rabat = "-20%";
                break;
            default:
                rabat = "nie udzielono";
        }
        table.addCell(new Cell().add(new Paragraph(rabat)));
        table.addCell(new Cell().add(new Paragraph(zamowienie.getStanZamowienia())));

        document.add(table);

        document.close();
    }
}
