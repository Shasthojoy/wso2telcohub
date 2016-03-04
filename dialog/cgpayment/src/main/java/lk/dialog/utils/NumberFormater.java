package lk.dialog.utils;

/**
 * Created by Malinda on 2/28/2015.
 */
public class NumberFormater {

    /**
     * *
     *
     * @param msisdn      Input number
     * @param countricode eg 94
     * @return for format msisdn without prefix and countrycode
     */
    public static String getLocalNumber(String msisdn, String countricode) {
       
       try{
           
       }catch (Exception e){
           
       }
        msisdn = msisdn.replace("tel:+" + countricode, "");
        msisdn = msisdn.replace("tel:" + countricode, "");

        if (msisdn.startsWith(countricode)) {
            msisdn = msisdn.substring(countricode.length());
        } else if (msisdn.startsWith("+" + countricode)) {
            msisdn = msisdn.substring(countricode.length() +1);
        }else if(msisdn.startsWith("0")){
            msisdn = msisdn.substring(1);
        }
        
        return msisdn;

    }
}
