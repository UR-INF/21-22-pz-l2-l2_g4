package com.example.hurtownia.domain.order;

import com.example.hurtownia.domain.AbstractReport;
import com.example.hurtownia.domain.orderitem.OrderItem;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Zawiera metodę generującą fakturę z zamówienia.
 */
@Component
public class InvoiceReport extends AbstractReport {

    private InvoiceData invoiceData;

    protected InvoiceReport() throws IOException {}

    public void setData(InvoiceData invoiceData) {
        this.invoiceData = invoiceData;
    }

    /**
     * Generuje fakturę z zamówienia.
     *
     * @param path ścieżka zapisu
     * @param title tytuł faktury
     * @throws FileNotFoundException
     * @throws MalformedURLException
     */
    @Override
    public void generateReport(String path, String title) throws FileNotFoundException, MalformedURLException {
        generateReportHeader(path, title);

        float columnWidth[] = {120f, 120f, 120f, 120f};
        Table table = new Table(columnWidth);
        table.addCell(new Cell().add(new Paragraph("SPRZEDAWCA:").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Jan Kowalski Hurtownia").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("NABYWCA:").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(invoiceData.getName() + " " + invoiceData.getSurname()).addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("ADRES:").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("Testowa 123\n30-300 Kraków").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("ADRES:").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(invoiceData.getStreet() + " " + invoiceData.getBuildingNumber() + "/" + invoiceData.getApartmentNumber() + "\n"
                + invoiceData.getZipCode() + " " + invoiceData.getPlace()).addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("NIP:").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("12345678912").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("NUMER TELEFONU:").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph(invoiceData.getPhoneNumber()).addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("NUMER TELEFONU:").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER));
        table.addCell(new Cell().add(new Paragraph("1234546789").addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT)).setBorder(Border.NO_BORDER));

        document.add(table);

        for (int i = 0; i < 3; i++) document.add(new Paragraph(""));

        float columnWidth1[] = {10f, 100f, 100f, 100f, 100f, 100f, 100f};
        Table table1 = new Table(columnWidth1);
        String[] tabHeader1 = {"Lp.", "Id", "Nazwa produktu", "Jm", "Cena za jm", "Ilość", "Cena elementu"};
        table1.addCell(new Cell().add(new Paragraph(tabHeader1[0]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table1.addCell(new Cell().add(new Paragraph(tabHeader1[1]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table1.addCell(new Cell().add(new Paragraph(tabHeader1[2]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table1.addCell(new Cell().add(new Paragraph(tabHeader1[3]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table1.addCell(new Cell().add(new Paragraph(tabHeader1[4]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table1.addCell(new Cell().add(new Paragraph(tabHeader1[5]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table1.addCell(new Cell().add(new Paragraph(tabHeader1[6]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));

        int n = 1;
        for (OrderItem item : invoiceData.getItems()) {
            table1.addCell(new Cell().add(new Paragraph(String.valueOf(n)).addStyle(styleTableContent)));
            table1.addCell(new Cell().add(new Paragraph(String.valueOf(item.getId())).addStyle(styleTableContent)));
            table1.addCell(new Cell().add(new Paragraph(item.getProduct().getName()).addStyle(styleTableContent)));
            table1.addCell(new Cell().add(new Paragraph(item.getProduct().getUnitOfMeasurement()).addStyle(styleTableContent)));
            table1.addCell(new Cell().add(new Paragraph(String.valueOf(item.getPricePerUnit())).addStyle(styleTableContent)));
            table1.addCell(new Cell().add(new Paragraph(String.valueOf(item.getAmount())).addStyle(styleTableContent)));
            table1.addCell(new Cell().add(new Paragraph(String.valueOf(item.getPricePerUnit()*item.getAmount())).addStyle(styleTableContent)));
            n++;
        }

        document.add(table1);

        float columnWidth2[] = {85f, 85f};
        Table table2 = new Table(columnWidth2).setHorizontalAlignment(HorizontalAlignment.RIGHT);
        String[] tableHeader2 = {"Razem", "Rabat", "Cena po rabacie", "Do zapłaty"};
        table2.addCell(new Cell().add(new Paragraph(tableHeader2[0]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table2.addCell(new Cell().add(new Paragraph(invoiceData.getValue()).addStyle(styleTableContent)));
        table2.addCell(new Cell().add(new Paragraph(tableHeader2[1]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table2.addCell(new Cell().add(new Paragraph(invoiceData.getDiscount()).addStyle(styleTableContent)));
        table2.addCell(new Cell().add(new Paragraph(tableHeader2[2]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table2.addCell(new Cell().add(new Paragraph(invoiceData.getValueAfterDiscount()).addStyle(styleTableContent)));
        table2.addCell(new Cell().add(new Paragraph(tableHeader2[3]).addStyle(styleTableHeader)).setBackgroundColor(ColorConstants.LIGHT_GRAY));
        table2.addCell(new Cell().add(new Paragraph(invoiceData.getValueAfterDiscount() + " PLN").addStyle(styleTableContent)));

        document.add(table2);

        for (int i = 0; i < 5; i++) document.add(new Paragraph(""));

        float columnWidth3[] = {200f, 110f, 200f};
        Table table3 = new Table(columnWidth3).setHorizontalAlignment(HorizontalAlignment.CENTER);
        table3.addCell(new Cell().add(new Paragraph("imię, nazwisko i podpis osoby upoważnionej do odebrania dokumentu").setMarginTop(65f).setTextAlignment(TextAlignment.CENTER).addStyle(styleTableContent)));
        table3.addCell(new Cell().setBorder(Border.NO_BORDER));
        table3.addCell(new Cell().add(new Paragraph(loginService.getCurrentUserName()).setMarginTop(50f).setTextAlignment(TextAlignment.CENTER).addStyle(styleTableHeader))
                .add(new Paragraph( "imię, nazwisko i podpis osoby upoważnionej do wystawienia dokumentu").setTextAlignment(TextAlignment.CENTER).addStyle(styleTableContent)));

        document.add(table3);

        document.close();
    }
}
