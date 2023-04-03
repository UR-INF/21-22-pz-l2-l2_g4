package PDFGeneration;

import Product.Produkt;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class DostawaRaport extends RaportAbstract {
    private List<Produkt> data;

    public DostawaRaport(List<Produkt> data) {
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

        float columnWidth[] = {40, 40, 40, 40};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Id dostawcy", "Kod produktu", "Jednostka miary", "Ilosc"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2])));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3])));

        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).isDostawa()) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getDostawca().getId()))));
                table.addCell(new Cell().add(new Paragraph(data.get(i).getKod())));
                table.addCell(new Cell().add(new Paragraph(data.get(i).getJednostkaMiary())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getMaxIlosc()-data.get(i).getIlosc()))));
            }
        }

        document.add(table);

        document.close();
    }
}
