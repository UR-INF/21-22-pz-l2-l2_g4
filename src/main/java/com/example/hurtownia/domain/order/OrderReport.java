package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Zawiera metodę generującą raport z tabeli zamówień.
 */
@Component
public class OrderReport extends AbstractReport {

    private List<OrderDTO> data;

    protected OrderReport() throws IOException {}

    public void setData(List<OrderDTO> data) {
        this.data = data;
    }

    /**
     * Generuje raport z tabeli zamówień.
     *
     * @param path ścieżka zapisu
     * @param title tytuł raportu
     * @throws IOException
     */
    @Override
    public void generateReport(String path, String title) throws IOException {
        generateReportHeader(path, title);

        float columnWidth[] = {10f, 100f, 100f, 100f, 100f, 100f};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Lp.", "Id", "Id klienta", "Data zamówienia", "Rabat", "Stan"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        int n = 1;
        for (OrderDTO datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(n)).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getCustomerId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getDate()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(DiscountConverter.fromNumericToPercentage(datum.getDiscount())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getState()).addStyle(styleTableContent)));
            n++;
        }

        document.add(table);
        document.close();
    }
}
