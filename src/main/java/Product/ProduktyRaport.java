package Product;

import PDFGeneration.RaportAbstract;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.FileNotFoundException;
import java.util.List;

public class ProduktyRaport extends RaportAbstract {

    private List<Produkt> data;

    public ProduktyRaport(List<Produkt> data) {
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

        for (int i = 0; i < data.size(); i++) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getId()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getDostawca().getId()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getKod())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getCena()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getIlosc()))));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getJednostkaMiary())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getKraj())));
            table.addCell(new Cell().add(new Paragraph(data.get(i).getKolor())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(data.get(i).getMaxIlosc()))));
            double stosunek = data.get(i).getIlosc()/data.get(i).getMaxIlosc();
            String str;
            if (stosunek < 30) str = "niski";
            else if (stosunek < 70) str = "umiarkowany";
            else str = "wysoki";
            table.addCell(new Cell().add(new Paragraph(str)));
        }

        document.add(table);

        document.close();
    }
}
