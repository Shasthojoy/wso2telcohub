package org.dialog.custom.hostobjects;

import org.wso2.carbon.apimgt.api.model.APIIdentifier;
import org.wso2.carbon.apimgt.api.model.SubscribedAPI;
import org.wso2.carbon.apimgt.api.model.Subscriber;

import java.math.BigDecimal;

public class BillingSubscription extends SubscribedAPI {
    /**
     * The charge before tax
     */
	private float price;

	private int count;
	private String context;
    private BigDecimal taxValue = BigDecimal.ZERO;

    /**
     * The charge after tax
     */
    private float finalCharge;

    private String month;
    private String year;
    private String currencyType;

    
    
	public String getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public BillingSubscription(SubscribedAPI subscribedAPI) {
		super(subscribedAPI.getSubscriber(), subscribedAPI.getApiId());
		super.setApplication(subscribedAPI.getApplication());
		super.setBlocked(subscribedAPI.isBlocked());
		super.setTier(subscribedAPI.getTier());
		super.setSubStatus(subscribedAPI.getSubStatus());
	}
	public BillingSubscription(Subscriber subscriber, APIIdentifier apiId) {
		super(subscriber, apiId);
	}

    /**
     * Gets the charge before tax
     * @return The total charge excluding tax
     */
    public float getPrice() {
		return price;
	}

    /**
     * Sets the charge before tax
     * @param price The total charge excluding tax
     */
    public void setPrice(float price) {
		this.price = price;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    /**
     * Gets the charge after tax
     * @return The final charge value including tax
     */
    public float getFinalCharge() {
        return finalCharge;
    }

    /**
     * Sets the charge after tax
     * @param finalCharge The final charge value including tax
     */
    public void setFinalCharge(float finalCharge) {
        this.finalCharge = finalCharge;
    }

    @Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}
}
