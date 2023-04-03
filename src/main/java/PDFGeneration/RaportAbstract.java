package PDFGeneration;

import java.io.FileNotFoundException;

public abstract class RaportAbstract {
    public abstract void generatePDF(String path, String title) throws FileNotFoundException;
}
