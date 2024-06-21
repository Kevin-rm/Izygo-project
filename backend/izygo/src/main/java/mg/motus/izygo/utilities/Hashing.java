package mg.motus.izygo.utilities;
import java.net.InetAddress;
import java.util.Base64;

public class Hashing {
    
    public static String encodeBase64(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decodeBase64(String input) {
        byte[] decodedBytes = Base64.getDecoder().decode(input);
        return new String(decodedBytes);
    }

    public static String createUrl(String arg){
        String val = "http://";
        try {
            InetAddress ip = InetAddress.getLocalHost();
            val+=ip.getHostAddress()+":8080/scanTicket/"+encodeBase64(arg);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return val;
    }

}
