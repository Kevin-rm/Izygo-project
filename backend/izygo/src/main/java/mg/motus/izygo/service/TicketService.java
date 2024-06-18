package mg.motus.izygo.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.itextpdf.text.DocumentException;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TicketService {
    public static void toPdf(String inputHtmlPath, String outputPdfPath) throws IOException, DocumentException {
        // Load the HTML content from the file
        String htmlContent = new String(Files.readAllBytes(Paths.get(inputHtmlPath)));

        // Create an instance of ITextRenderer
        ITextRenderer renderer = new ITextRenderer();

        // Set the document content to the renderer
        renderer.setDocumentFromString(htmlContent);

        // Lay out and create the PDF
        renderer.layout();

        // Create the PDF and save it to the specified path
        try (FileOutputStream os = new FileOutputStream(outputPdfPath)) {
            renderer.createPDF(os);
        }
    }

    // generer le html du ticket à partir des données
    public static String generateHtml(List<String> data) {
        String inputFilePath = "/home/yoannah/Documents/ITU/MOTUS/demo/test.html";
        return inputFilePath;
    }

    //test
    public static void main(String[] args) {
        try {
            String inputFilePath =generateHtml(null);

            String outputFilePath = "output.pdf";

            toPdf(inputFilePath, outputFilePath);
            System.out.println("PDF created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
