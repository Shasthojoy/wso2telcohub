package lk.dialog.cg.payment.provision;


import java.util.List;

public class Provisioninfo {

    private String applicationid;

    public String getApplicationid() {
        return applicationid;
    }

    public void setApplicationid(String applicationid) {
        this.applicationid = applicationid;
    }
    private List<String> resoncode;

    public List<String> getResoncode() {
        return resoncode;
    }

    public void setResoncode(List<String> resoncode) {
        this.resoncode = resoncode;
    }
    private String maxamount;

    public String getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(String maxamount) {
        this.maxamount = maxamount;
    }
}