package com.example.hurtownia.domain.customer;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class CustomerReport extends AbstractReport {

    private List<CustomerDTO> data;

    protected CustomerReport() throws IOException {}

    public void setData(List<CustomerDTO> data) {
        this.data = data;
    }

    @Override
    public void generateReport(String path, String title) throws IOException {
        generateReportHeader(path, title);

        float columnWidth[] = {10f, 20f, 50f, 50f, 50f, 50f, 50f, 60f, 50f, 50f, 50f, 50f};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Lp.", "Id", "Imię", "Nazwisko", "Kod pocztowy", "Miejscowość", "Ulica", "Nr budynku", "Nr mieszkania", "Email", "Nr telefonu", "PESEL"};
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
        table.addCell(new Cell().add(new Paragraph(tableHeader[11]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        int n = 1;
        for (CustomerDTO datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(n)).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getName()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getSurname()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getZipCode()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getPlace()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getStreet()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getBuildingNumber())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getApartmentNumber())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getEmail()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getPhoneNumber()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getPesel()).addStyle(styleTableContent)));
            n++;
        }

        document.add(table);
        document.close();
    }
}
