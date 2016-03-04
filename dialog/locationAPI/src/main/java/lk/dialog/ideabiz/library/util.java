package lk.dialog.ideabiz.library;

import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
/**
 * Created by Malinda on 7/8/2015.
 */
public class util {

    public static String getRandomNumber(){
        return UUID.randomUUID().toString();
    }

    public static String cleanMSG(String msg) {
        if (msg == null)
            return "";
        msg = msg.replaceAll("  ", " ");
        msg = msg.replaceAll("[^A-Za-z0-9 ]", " ");
        msg = msg.trim();
        return msg;
    }

    public static String base64Encode(String text){
        byte[] encodedBytes = Base64.encodeBase64(text.getBytes());
        return new String(encodedBytes);

    }

    public static String base64Decode(String encodedText){
        byte[] encodedBytes = Base64.decodeBase64(encodedText.getBytes());
        return new String(encodedBytes);

    }

}
