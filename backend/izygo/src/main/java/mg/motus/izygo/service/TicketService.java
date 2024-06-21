package mg.motus.izygo.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.io.FileHandler;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import lombok.Getter;
import lombok.Setter;
import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.utilities.Hashing;
import mg.motus.izygo.utilities.QrCodeGen;

import io.github.bonigarcia.wdm.WebDriverManager;



@Service
@Getter
@Setter
public class TicketService{

    ReservationDTO reservationDTO;
    private String pathHtml;
    private String pathImg;

    public void addTicketInfo() {
        // Configure Thymeleaf template resolver
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/qr_ressources/Ticket/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");

        // Créer le QR Code
        String reservationSeatId = getReservationDTO().reservationSeatId().toString();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String filename = "qr" + timestamp + ".jpg";
        String qrCodePath = "src/main/resources/qr_ressources/Ticket/" + filename;
        String htmlPath = timestamp + ".html";
        try {
            QrCodeGen.generateQRCodeImage(reservationSeatId, 100, 100, qrCodePath);
            System.out.println("QR Code créé");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Créer le moteur de template Thymeleaf
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        // Créer le contexte et ajouter des variables
        Context context = new Context();
        context.setVariable("reservation", getReservationDTO());
        context.setVariable("qrCode", filename);

        // Générer le contenu HTML
        String htmlContent = templateEngine.process("ticket", context);

        pathHtml = "src/main/resources/qr_ressources/Ticket/" + htmlPath;
        System.out.println("pathHtml initialisé: " + pathHtml);

        // Écrire le contenu HTML dans un fichier
        try (FileWriter writer = new FileWriter(pathHtml)) {
            writer.write(htmlContent);
            System.out.println("Fichier HTML généré avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        pathImg = "src/main/resources/qr_ressources/Ticket/"+Hashing.encodeBase64(reservationDTO.reservationSeatId().toString())+".png";    
        
         // Spécifiez le chemin local du ChromeDriver
         String chromeDriverPath = "src/main/resources/drivers/126/chromedriver-win64/chromedriver.exe";
         System.setProperty("webdriver.chrome.driver", chromeDriverPath);

        // Setup WebDriverManager to manage ChromeDriver binaries
        WebDriverManager.chromedriver().setup();

        // Configure ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--window-size=1280x1024");

        // Initialize WebDriver
        WebDriver driver = new ChromeDriver(options);

        try {
            // Path to your HTML file
            System.out.println("Debut du screenShot");
            String htmlFilePath = new File(pathHtml).getAbsolutePath();
            String htmlFileUrl = "file:///" + htmlFilePath.replace("\\", "/");

            // Open HTML file in browser
            driver.get(htmlFileUrl);

            // Locate the element to capture
            WebElement element = driver.findElement(By.id("getimg"));

            // Take screenshot of the specific element
            File screenshot = element.getScreenshotAs(OutputType.FILE);

            // Path to save the screenshot
            File outputFile = new File(pathImg);

            // Save the screenshot to file
            FileHandler.copy(screenshot, outputFile);

            System.out.println("Image created successfully: " + pathImg);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }

        // Supprimer le fichier HTML temporaire
        File htmlFile = new File(pathHtml);
        if (htmlFile.exists() && htmlFile.delete()) {
            System.out.println("Fichier HTML supprimé avec succès : " + pathHtml);
        } else {
            System.out.println("Échec de la suppression du fichier HTML : " + pathHtml);
        }

        // Supprimer le fichier QR code temporaire
        File qrFile = new File(qrCodePath);
        if (qrFile.exists() && qrFile.delete()) {
            System.out.println("Fichier QR code supprimé avec succès : " + qrCodePath);
        } else {
            System.out.println("Échec de la suppression du fichier QR code : " + qrCodePath);
        }
    }


}
