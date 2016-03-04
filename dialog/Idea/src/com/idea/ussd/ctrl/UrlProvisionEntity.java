package com.idea.ussd.ctrl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.idea.ussd.dto.Application;

@Entity
@Table(name = "URL_PROVISION")
public class UrlProvisionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", unique = true, insertable = false, updatable = false)
	private Long id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "APP_ID")
	private Application application;

	@Column(name = "NOTIFY_URL")
	private String notifyUrl;
        
        @Column(name = "KEYWORD")
	private String keyword;
        
        @Column(name = "SHORTCODE")
	private String shortcode;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
        public String getKeyWord() {
		return keyword;
	}

	public void setKeyWord(String keyword) {
		this.keyword = keyword;
	}
        
         public String getShortCode() {
		return shortcode;
	}

	public void setShortCode(String shortcode) {
		this.shortcode = shortcode;
	}

}
