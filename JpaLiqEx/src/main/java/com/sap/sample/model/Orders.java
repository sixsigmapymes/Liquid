/**
 * 
 */
package com.sap.sample.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Rajnish
 *
 */

@Entity
@Table(name = "ORDERS")
public class Orders implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4526719324031814631L;
	
	
	@Id
	@Column(name = "TRANSACTION_ID")
	private String transactionId	=	null;

	@Column(name = "ORDER_ID")
	private String orderId	=	null;
	
	@Column(name ="ORDER_NAME")
	private String orderName = null;

	@Column(name ="COMMENTS")
	private String comments = null;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
}
