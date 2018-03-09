/**
 * 
 */
package com.sap.sample.model;

import java.io.Serializable;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Rajnish
 *
 */

@Entity
@Table(name = "EMPLOYEE")
@Cacheable(value=false)
public class Employee implements Serializable{

	private static final long serialVersionUID = 2666457964068403553L;

	@Id
	@Column(name = "EMP_ID")
	private String employeeId = null;
	
	@Column(name = "EMP_NAME")
	private String employeeName = null;

	@Column(name = "EMP_ADDRESS")
	private String employeeAddress = null;

	@Column(name = "DESCRIPTION")
	private String description = null;
	
	@Column(name = "PHONE")
	private String phone = null;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeAddress() {
		return employeeAddress;
	}

	public void setEmployeeAddress(String employeeAddress) {
		this.employeeAddress = employeeAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	

}
