package domain.Order;

import PDFGeneration.RaportAbstract;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class ZamowieniaRaport  extends RaportAbstract {

    private List<Zamowienie> data;

    public ZamowieniaRaport(List<Zamowienie> data) {
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

        float columnWidth[] = {40, 40, 40, 40, 40};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Id", "Id klienta", "Data zamowienia", "Rabat", "Stan"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4])));

        for (int i = 0; i < data.size(); i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getKlient().getId()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getData())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getRabat()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getStanZamowienia())));
        }

        document.add(table);

        document.close();
    }
}
