package com.example.hurtownia.domain;

import com.example.hurtownia.authentication.LoginService;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfVersion;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.WriterProperties;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Klasa abstrakcyjna dla klas generujących raporty.
 */
public abstract class AbstractReport {
    protected PdfFont fontBold;
    protected PdfFont fontNormal;
    protected Style styleTableHeader;
    protected Style styleTableContent;
    protected Style styleGeneratingInfo;
    protected Style styleTitle;
    protected Document document;
    @Autowired
    protected LoginService loginService;

    protected AbstractReport() throws IOException {
        fontBold = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD, PdfEncodings.CP1250);
        fontNormal = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN, PdfEncodings.CP1250);

        styleTableHeader = new Style().setFont(fontBold).setFontSize(8);
        styleGeneratingInfo = new Style().setFont(fontNormal).setFontSize(10);
        styleTableContent = new Style().setFont(fontNormal).setFontSize(8);
        styleTitle = new Style().setFont(fontBold).setFontSize(12);
    }

    /**
     * Metoda abstrakcyjna generowania raportu.
     *
     * @param path ścieżka zapisu
     * @param title tytuł raportu
     * @throws IOException
     */
    public abstract void generateReport(String path, String title) throws IOException;

    /**
     * Generuje nagłówek dokumentu.
     *
     * @param path ścieżka zapisu
     * @param title tytuł dokumentu
     * @throws FileNotFoundException
     * @throws MalformedURLException
     */
    protected void generateReportHeader(String path, String title) throws FileNotFoundException, MalformedURLException {
        PdfWriter pdfWriter = new PdfWriter(path, new WriterProperties().setPdfVersion(PdfVersion.PDF_2_0));
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        pdfDocument.addNewPage();
        document = new Document(pdfDocument);

        document.add(new Paragraph("Pracownik: " + loginService.getCurrentUserName()).addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT));

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        document.add(new Paragraph("Data wygenerowania: " + formatter.format(date)).addStyle(styleGeneratingInfo).setTextAlignment(TextAlignment.LEFT));

        String imagePath = getClass().getResource("/Images/logo_black.png").getPath();
        ImageData imageData = ImageDataFactory.create(imagePath);
        Image image = new Image(imageData);
        image.setHeight(80);
        image.setWidth(80);
        image.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
        image.setFixedPosition(PageSize.A4.getWidth() - 120, PageSize.A4.getHeight() - 100);
        document.add(image);

        for (int i = 0; i < 8; i++) document.add(new Paragraph(""));

        document.add(new Paragraph(title).addStyle(styleTitle).setTextAlignment(TextAlignment.LEFT));

        for (int i = 0; i < 3; i++) document.add(new Paragraph(""));
    }
}
