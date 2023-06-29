package com.example.hurtownia.domain.orderitem;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Zawiera metodę generującą raport z tabeli elementów zamówienia.
 */
@Component
public class OrderItemReport extends AbstractReport {

    private List<OrderItemData> data;

    protected OrderItemReport() throws IOException {
    }

    public void setData(List<OrderItemData> data) {
        this.data = data;
    }

    /**
     * Generuje raport z tabeli elementów zamówień.
     *
     * @param path  ścieżka zapisu
     * @param title tytuł raportu
     * @throws IOException
     */
    @Override
    public void generateReport(String path, String title) throws IOException {
        generateReportHeader(path, title);

        float columnWidth[] = {10f, 100f, 100f, 100f, 100f, 100f, 100f, 100f};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Lp.", "Id", "Id zamowienia", "Id produktu", "Nazwa Produktu", "Cena elementu", "Cena za jednostkę", "Ilość"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[6]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[7]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        int n = 1;
        for (OrderItemData datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(n)).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getOrderItem().getId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getOrderItem().getOrderId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getOrderItem().getProductId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getProduct().getName())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getOrderItem().getItemPrice())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getOrderItem().getPricePerUnit())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getOrderItem().getAmount())).addStyle(styleTableContent)));
            n++;
        }

        document.add(table);
        document.close();
    }
}
