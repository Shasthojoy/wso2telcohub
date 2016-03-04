package com.dialog.mife.ussd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Charith_02380
 *
 */
@Entity
@Table(name="SERVICE_PROVIDER")
public class ServiceProvider {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="SP_ID", unique=true, insertable=true, updatable=false)
    private Long id;
	
	@Column(name="SP_NAME", nullable=false)
	private String name;
	
	@Column(name="COMMENT", nullable=false)
	private String comment;
	
	/**
	 * 
	 */
	public ServiceProvider() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
}
