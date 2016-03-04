package lk.dialog.ideabiz.library;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lk.dialog.ideabiz.library.util;
import lk.dialog.ideabiz.model.JWT.JWTTokenInfo;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Malinda on 7/27/2015.
 */
public class JWT {
    final static Logger logger = Logger.getLogger(JWT.class);
    public static Gson gson;

    public static JWTTokenInfo tokenInfo(String header) throws Exception {
        if (gson == null)
            gson = new Gson();

        JWTTokenInfo token = new JWTTokenInfo();

        try {

            String jwt = header.split("\\.")[1];

            jwt = util.base64Decode(jwt);
            JsonObject jsonObject = gson.fromJson(jwt, JsonObject.class);

            token.setExp(removeQuote(jsonObject.get("exp")));
            token.setSubscriber(removeQuote(jsonObject.get("http://wso2.org/claims/subscriber")));
            token.setApplicationid(removeQuote(jsonObject.get("http://wso2.org/claims/applicationid")));
            token.setApplicationname(removeQuote(jsonObject.get("http://wso2.org/claims/applicationname")));
            token.setApplicationtier(removeQuote(jsonObject.get("http://wso2.org/claims/applicationtier")));
            token.setApicontext(removeQuote(jsonObject.get("http://wso2.org/claims/apicontext")));
            token.setVersion(removeQuote(jsonObject.get("http://wso2.org/claims/version")));
            token.setTier(removeQuote(jsonObject.get("http://wso2.org/claims/tier")));
            token.setKeytype(removeQuote(jsonObject.get("http://wso2.org/claims/keytype")));
            token.setUsertype(removeQuote(jsonObject.get("http://wso2.org/claims/usertype")));
            token.setEnduser(removeQuote(jsonObject.get("http://wso2.org/claims/enduser")));
            token.setEnduserTenantId(removeQuote(jsonObject.get("http://wso2.org/claims/enduserTenantId")));
            token.setEmailaddress(removeQuote(jsonObject.get("http://wso2.org/claims/emailaddress")));
            token.setGivenname(removeQuote(jsonObject.get("http://wso2.org/claims/givenname")));
            token.setLastname(removeQuote(jsonObject.get("http://wso2.org/claims/lastname")));


            try{
                String role = removeQuote(jsonObject.get("http://wso2.org/claims/role"));
                ArrayList<String> roles = new ArrayList<String>(Arrays.asList(role.split(",")));
                token.setRole(roles);
            }catch (Exception e){
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("error ",e);
            throw new Exception("Error when reading JWT token");
        }
        return token;
    }

    public static String removeQuote(String string) {
        if (string == null)
            return null;
        return string.replaceAll("^\"|\"$", "");
    }

    public static String removeQuote(JsonElement elm) {
        if (elm == null)
            return null;
        try {
            return removeQuote(elm.toString());
        } catch (Exception e) {
            return null;
        }

    }

    public static JWTTokenInfo tokenInfo(HttpServletRequest header) throws Exception {
        return tokenInfo(header.getHeader("X-JWT-Assertion"));
    }

}
