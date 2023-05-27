package com.example.hurtownia.domain.user;

import com.example.hurtownia.domain.AbstractReport;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Zawiera metodę generującą raport z tabeli użytkowników.
 */
@Component
public class UserReport extends AbstractReport {

    private List<UserDTO> data;

    protected UserReport() throws IOException {}

    public void setData(List<UserDTO> data) {
        this.data = data;
    }

    /**
     * Generuje raport z tabeli użytkowników.
     *
     * @param path ścieżka zapisu
     * @param title tytuł raportu
     * @throws IOException
     */
    @Override
    public void generateReport(String path, String title) throws IOException {
        generateReportHeader(path, title);

        float columnWidth[] = {10f, 20f, 60f, 60f, 60f, 80f, 50f, 50f, 50f};
        Table table = new Table(columnWidth);
        String[] tableHeader = {"Lp.", "Id", "Imię", "Nazwisko", "Numer telefonu", "Email", "Administrator", "Generowanie raportów", "Udzielanie rabatów"};
        table.addCell(new Cell().add(new Paragraph(tableHeader[0]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[1]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[2]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[3]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[4]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[5]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[6]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[7]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table.addCell(new Cell().add(new Paragraph(tableHeader[8]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        int n = 1;
        for (UserDTO datum : data) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(n)).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getId())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getName()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getSurname()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getPhoneNumber()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(datum.getEmail()).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getIsAdmin())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getGeneratingReports())).addStyle(styleTableContent)));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(datum.getGrantingDiscounts())).addStyle(styleTableContent)));
            n++;
        }

        document.add(table);
        document.close();
    }
}
