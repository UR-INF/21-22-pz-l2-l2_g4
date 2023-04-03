package PDFGeneration;

import Entities.Klient;
import Entities.Uzytkownik;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class KlienciRaport extends RaportAbstract {

    private List<Klient> data;

    public KlienciRaport(List<Klient> data) {
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
            table.addCell(new Cell().add(new Paragraph(data.get(i).getImie())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getNazwisko())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getMiejscowosc())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getUlica())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getNumerBudynku()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getNumerMieszkania()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getEmail())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getNumerTelefonu())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getPesel())));
        }

        document.add(table);

        document.close();
    }
}