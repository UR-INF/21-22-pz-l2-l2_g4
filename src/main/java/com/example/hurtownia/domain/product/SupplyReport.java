package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Zawiera metodę generującą raport dostawy.
 */
@Component
public class SupplyReport extends AbstractReport {

    private List<SupplyData> data;

    protected SupplyReport() throws IOException {}

    public void setData(List<SupplyData> data) {
        this.data = data;
    }

    /**
     * Generuje raport dostawy.
     *
     * @param path ścieżka zapisu
     * @param title tytuł raportu
     * @throws FileNotFoundException
     * @throws MalformedURLException
     */
    @Override
    public void generateReport(String path, String title) throws IOException {
        generateReportHeader(path, title);

        float columnWidth[] = {10f, 150f, 150f, 150f, 150f};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Lp.", "Nazwa dostawcy", "Kod produktu", "Jednostka miary", "Ilosc"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        int n =1;
        for (SupplyData datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(n)).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getSupplierName()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getProductCode()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getProductUnitOfMeasurement()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getAmount()).addStyle(styleTableContent)));
            n++;
        }

        document.add(table);
        document.close();
    }
}
