package mg.motus.izygo.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.itextpdf.text.DocumentException;

import java.nio.file.Paths;
import java.util.List;

@Service

public class TicketService {

    @Value("${ticket.template.path}")
    private String pathHtml;

    @Value("${ticket.assets.path}")
    private String assetsPath;
    
    static private String projectPath = System.getProperty("user.dir");

    public void toPdf(String inputHtmlPath, String outputPdfPath) throws IOException, DocumentException {
        // Load the HTML content from the file
        String htmlContent = new String(Files.readAllBytes(Paths.get(inputHtmlPath)));

        // Create an instance of ITextRenderer
        ITextRenderer renderer = new ITextRenderer();

         // Get the base path for the assets
        String basePath = Paths.get(projectPath, assetsPath).toUri().toString();

         // Ensure base path ends with a slash
         if (!basePath.endsWith("/")) {
            basePath += "/";
        }

        // Set the document content to the renderer
        renderer.setDocumentFromString(htmlContent, basePath);

         // Charger les CSS
        renderer.getSharedContext().setBaseURL(basePath);
        // Lay out and create the PDF
        renderer.layout();

        // Create the PDF and save it to the specified path
        try (FileOutputStream os = new FileOutputStream(outputPdfPath)) {
            renderer.createPDF(os);
        }
    }
    // generer le html du ticket à partir des données
    public String generateHtml(List<String> data) {
        // Assuming pathHtml should be the absolute path of the template file
        String templatePath = Paths.get(projectPath, pathHtml).toString();
        return templatePath;
    }

}
