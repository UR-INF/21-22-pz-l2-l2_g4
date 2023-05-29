package com.example.hurtownia.domain.supplier;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Zawiera metodę generującą raport z tabeli dostawców.
 */
@Component
public class SupplierReport extends AbstractReport {

    private List<SupplierDTO> data;

    protected SupplierReport() throws IOException {}

    public void setData(List<SupplierDTO> data) {
        this.data = data;
    }

    /**
     * Generuje raport z tabeli dostawców.
     *
     * @param path ścieżka zapisu
     * @param title tytuł raportu
     * @throws IOException
     */
    @Override
    public void generateReport(String path, String title) throws IOException {
        generateReportHeader(path, title);

        float columnWidth[] = {10f, 20f, 70f, 70f, 70f, 70f, 70f, 70f};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Lp.", "Id", "Nazwa", "NIP", "Email", "Miejscowość", "Ulica", "Kraj"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[6]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[7]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        int n =1;
        for (SupplierDTO datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(n)).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getName()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getNip()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getEmail()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getPlace()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getStreet()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getCountry()).addStyle(styleTableContent)));
            n++;
        }

        document.add(table);
        document.close();
    }
}
