package lk.dialog.ideabiz.model.Payment;

/**
 * Created by Malinda on 8/5/2015.
 */
public class ChargingInformation {
    Double amount;
    String currency;
    String description;


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setAmount(String amount) {

        try {
            this.amount = Double.parseDouble(amount);
        } catch (Exception e) {
        }
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
