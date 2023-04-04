package PDFGeneration;

import java.io.FileNotFoundException;
import java.util.List;

import domain.User.Uzytkownik;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class UzytkownicyRaport extends RaportAbstract {

    private List<Uzytkownik> data;

    public UzytkownicyRaport(List<Uzytkownik> data) {
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

        for (int i = 0; i < data.size(); i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getId()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getImie())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getNazwisko())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getNumerTelefonu())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getEmail())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getHaslo())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getIsAdmin()!=0 ? "Tak" : "Nie")));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getGenerowanieRaportow()!=0 ? "Tak" : "Nie")));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getIsAdmin()!=0 ? "Tak" : "Nie")));
        }

        document.add(table);

        document.close();
    }
}
