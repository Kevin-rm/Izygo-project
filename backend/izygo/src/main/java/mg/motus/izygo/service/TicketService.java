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
import mg.motus.izygo.dto.ReservationDTO;
import mg.motus.izygo.utilities.Hashing;
import mg.motus.izygo.utilities.QrCodeGen;

import io.github.bonigarcia.wdm.WebDriverManager;



@Service
public class TicketService{

    public void addTicketInfo(ReservationDTO reservationDTO) {

        // Créer le QR Code
        String reservationSeatId = reservationDTO.reservationSeatId().toString();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String filename = "qr" + timestamp + ".jpg";
        String qrCodePath = "src/main/resources/qr_ressources/Ticket/" + filename;
        String htmlPath = timestamp + ".html";
        try {
            QrCodeGen.generateQRCodeImage(reservationSeatId, 100, 100, qrCodePath);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Contenu HTML avec formatage dynamique
        String htmlContent = String.format(
                "<!DOCTYPE html>\r\n" +
                "<html lang=\"en\">\r\n" +
                "<head>\r\n" +
                "    <meta charset=\"UTF-8\"></meta>\r\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"></meta>\r\n" +
                "    <title>Ticket</title>\r\n" +
                "    <link rel=\"stylesheet\" href=\"assets/css/bootstrap.min.css\"></link>\r\n" +
                "    <link rel=\"stylesheet\" href=\"assets/css/template.css\"></link>\r\n" +
                "</head>\r\n" +
                "<body>\r\n" +
                "    <div id=\"getimg\" class=\"container\">\r\n" +
                "        <div class=\"row mt-2 mb-3 d-flex flex-row align-items-center\">\r\n" +
                "            <div class=\"col-6\">\r\n" +
                "                <p class=\"mx-2\">\r\n" +
                "                    Ticket #%s\r\n" +
                "                </p>\r\n" +
                "            </div>\r\n" +
                "            <div class=\"col-6 d-flex justify-content-end logo\"><img src=\"assets/icons/logo.png\" alt=\"\"></img></div>\r\n" +
                "        </div>\r\n" +
                "        <div class=\"container bg-dark text-white rounded-4 px-3 py-2 mb-2\">\r\n" +
                "            <div class=\"px-3\">\r\n" +
                "                <h6 class=\"fw-bolder title\">%s %s</h6>\r\n" +
                "                <p>%s</p>\r\n" +
                "            </div>\r\n" +
                "            <hr></hr>\r\n" +
                "            <div class=\"container\">\r\n" +
                "                <div class=\"row justify-content-between\">\r\n" +
                "                    <div class=\"col-5\">\r\n" +
                "                        <center class=\"fw-bold\">Matricule</center>\r\n" +
                "                        <center>%s</center>\r\n" +
                "                    </div>\r\n" +
                "                    <div class=\"col-5\">\r\n" +
                "                        <center class=\"fw-bold\">Numero de siège</center>\r\n" +
                "                        <center>%s</center>\r\n" +
                "                    </div>\r\n" +
                "                </div>\r\n" +
                "                <div class=\"row justify-content-between\">\r\n" +
                "                    <div class=\"col-5\">\r\n" +
                "                        <center class=\"fw-bold\">Date de réservation</center>\r\n" +
                "                        <center>01/04/2024</center>\r\n" +
                "                    </div>\r\n" +
                "                    <div class=\"col-5\">\r\n" +
                "                        <center class=\"fw-bold\">Heure de départ</center>\r\n" +
                "                        <center>12:45:00</center>\r\n" +
                "                    </div>\r\n" +
                "                </div>\r\n" +
                "            </div>\r\n" +
                "        </div>\r\n" +
                "        <div class=\"container\">\r\n" +
                "            <div class=\"row\">\r\n" +
                "                <div class=\"col-5\">\r\n" +
                "                    <center class=\"fw-bold\">Départ</center>\r\n" +
                "                    <center>%s</center>\r\n" +
                "                </div>\r\n" +
                "                <div class=\"col-2 d-flex justify-content-center align-items-center\">\r\n" +
                "                    <img src=\"assets/icons/bus-front-fill.svg\"alt=\"Bus Icon\"></img>\r\n" +
                "                </div>\r\n" +
                "                <div class=\"col-5\">\r\n" +
                "                    <center class=\"fw-bold\">Arrivée</center>\r\n" +
                "                    <center>%s</center>\r\n" +
                "                </div>\r\n" +
                "            </div>\r\n" +
                "            <div class=\"d-flex justify-content-center align-items-center my-1\" >\r\n" +
                "                <img src=\"%s\" alt=\"\" width=\"100\" style=\"width: 100px; height: 100px;\"></img>\r\n" +
                "            </div>\r\n" +
                "            <div class=\"text-center\">\r\n" +
                "                <p class=\"faded\">Merci d'avoir choisi IzyGo by Motus</p>\r\n" +
                "            </div>\r\n" +
                "        </div>\r\n" +
                "    </div>\r\n" +
                "</body>\r\n" +
                "</html>",
                reservationDTO.id().toString(), reservationDTO.firstname(), reservationDTO.lastname(), reservationDTO.lineLabel(), 
                reservationDTO.licensePlate(), reservationDTO.seatLabel(), reservationDTO.startStop(), reservationDTO.endStop(), filename
        );
        
        String pathHtml = "src/main/resources/qr_ressources/Ticket/" + htmlPath;
        // Écrire le contenu HTML dans un fichier
        try (FileWriter writer = new FileWriter(pathHtml)) {
            writer.write(htmlContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String pathImg = "src/main/resources/qr_ressources/Ticket/"+Hashing.encodeBase64(reservationDTO.reservationSeatId().toString())+".png";    
        
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

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            driver.quit();
        }
        // Supprimer le fichier HTML temporaire
        File htmlFile = new File(pathHtml);
        if (htmlFile.exists() && htmlFile.delete()) {}

        // Supprimer le fichier QR code temporaire
        File qrFile = new File(qrCodePath);
        if (qrFile.exists() && qrFile.delete()) {}
    }

}
