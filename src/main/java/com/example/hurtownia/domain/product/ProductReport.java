package com.example.hurtownia.domain.product;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ProductReport extends AbstractReport {

    private List<ProductDTO> data;

    protected ProductReport() throws IOException {}

    public void setData(List<ProductDTO> data) {
        this.data = data;
    }

    @Override
    public void generateReport(String path, String title) throws IOException {
        generateReportHeader(path, title);

        float columnWidth[] = {10f, 20f, 20f, 50f, 50f, 50f, 50f, 50f, 50f, 50f, 50f};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Lp.", "Id", "Id dostawcy", "Kod", "Cena", "Ilość", "Jednostka miary", "Kraj", "Kolor", "Maksymalna ilość", "Stan"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[6]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[7]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[8]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[9]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[10]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        int n = 1;
        for (ProductDTO datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(n)).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getSupplierId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getCode()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getPrice())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getNumber())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getUnitOfMeasurement()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getCountry()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getColor()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getMaxNumber())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(ProductState.calculateState(datum.getNumber(), datum.getMaxNumber())).addStyle(styleTableContent)));
            n++;
        }

        document.add(table);
        document.close();
    }
}
