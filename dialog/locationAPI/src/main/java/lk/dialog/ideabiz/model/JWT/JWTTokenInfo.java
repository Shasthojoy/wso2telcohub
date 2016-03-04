package lk.dialog.ideabiz.model.JWT;

import java.util.ArrayList;

/**
 * Created by Malinda on 7/27/2015.
 */
public class JWTTokenInfo {
    String exp;
    String subscriber;
    String applicationid;
    String applicationname;
    String applicationtier;
    String apicontext;
    String version;
    String tier;
    String keytype;
    String usertype;
    String enduser;
    String enduserTenantId;
    String emailaddress;
    String givenname;
    String lastname;
    ArrayList<String> role;

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(String applicationid) {
        this.applicationid = applicationid;
    }

    public String getApplicationname() {
        return applicationname;
    }

    public void setApplicationname(String applicationname) {
        this.applicationname = applicationname;
    }

    public String getApplicationtier() {
        return applicationtier;
    }

    public void setApplicationtier(String applicationtier) {
        this.applicationtier = applicationtier;
    }

    public String getApicontext() {
        return apicontext;
    }

    public void setApicontext(String apicontext) {
        this.apicontext = apicontext;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getKeytype() {
        return keytype;
    }

    public void setKeytype(String keytype) {
        this.keytype = keytype;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getEnduser() {
        return enduser;
    }

    public void setEnduser(String enduser) {
        this.enduser = enduser;
    }

    public String getEnduserTenantId() {
        return enduserTenantId;
    }

    public void setEnduserTenantId(String enduserTenantId) {
        this.enduserTenantId = enduserTenantId;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public ArrayList<String> getRole() {
        return role;
    }

    public void setRole(ArrayList<String> role) {
        this.role = role;
    }
}