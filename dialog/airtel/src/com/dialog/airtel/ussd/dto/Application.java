package com.dialog.airtel.ussd.dto;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Charith_02380
 *
 */
@Entity
@Table(name="APPLICATIONS")
public class Application {
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="APP_ID", unique=true, insertable=false, updatable=false)
    private Long id; 
	
	@Column(name="SHORTCODE")
    String shortCode;
	
	@Column(name="KEYWORD")
	String keyword;
    
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="SP_ID")
    private ServiceProvider serviceProvider;
	
	@Column(name="APP_STATUS")
	private ApplicationStatus applicationStatus;
	
	@Column(name="IS_UNIQUE")
	private boolean unique;
	
	/**
	 * 
	 */
	public Application() {
	}
	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the shortCode
	 */
	public String getShortCode() {
		return shortCode;
	}

	/**
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isUnique() {
		return unique;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param shortCode the shortCode to set
	 */
	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	/**
	 * @param keyword the keyword to set
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * @return the serviceProvider
	 */
	public ServiceProvider getServiceProvider() {
		return serviceProvider;
	}
	/**
	 * @return the applicationStatus
	 */
	public ApplicationStatus getApplicationStatus() {
		return applicationStatus;
	}
	
	/**
	 * @param serviceProvider the serviceProvider to set
	 */
	public void setServiceProvider(ServiceProvider serviceProvider) {
		this.serviceProvider = serviceProvider;
	}
	/**
	 * @param applicationStatus the applicationStatus to set
	 */
	public void setApplicationStatus(ApplicationStatus applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
	/**
	 * 
	 * @param unique
	 */
	public void setUnique(boolean unique) {
		this.unique = unique;
	}
}
