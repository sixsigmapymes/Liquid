package com.sap.sample.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Coments Vo to read the JSON Object to Object of: Comment
 *
 */

public class CommentsVO implements Serializable {

	
	@JsonProperty
	private String EntityId;

	@JsonProperty
	private String CommentId;

	@JsonProperty
	private String Title;

	@JsonProperty
	private String Description;

	@JsonProperty
	private String EntityType;

	@JsonProperty
	private String Language;

	@JsonProperty
	private String ParentId;

	@JsonProperty
	private String IsDeleted;
	
	@JsonProperty
	private String Context	= null;

	@JsonProperty
	private String CreatedBy;

	@JsonProperty
	private Date CreatedAt;
	
	@JsonProperty
	private String ChangedBy;

	@JsonProperty
	private Date ChangedAt;

	
	private static final long serialVersionUID = 1L;

	public CommentsVO() {
		super();
	}

	public String getEntityId() {
		return EntityId;
	}

	public void setEntityId(String entityId) {
		EntityId = entityId;
	}

	public String getCommentId() {
		return CommentId;
	}

	public void setCommentId(String commentId) {
		CommentId = commentId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getEntityType() {
		return EntityType;
	}

	public void setEntityType(String entityType) {
		EntityType = entityType;
	}

	public String getLanguage() {
		return Language;
	}

	public void setLanguage(String language) {
		Language = language;
	}

	public String getParentId() {
		return ParentId;
	}

	public void setParentId(String parentId) {
		ParentId = parentId;
	}

	public String getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		IsDeleted = isDeleted;
	}

	public String getContext() {
		return Context;
	}

	public void setContext(String context) {
		Context = context;
	}

	public String getCreatedBy() {
		return CreatedBy;
	}

	public void setCreatedBy(String createdBy) {
		CreatedBy = createdBy;
	}

	public Date getCreatedAt() {
		return CreatedAt;
	}

	public void setCreatedAt(Date createdAt) {
		CreatedAt = createdAt;
	}

	public String getChangedBy() {
		return ChangedBy;
	}

	public void setChangedBy(String changedBy) {
		ChangedBy = changedBy;
	}

	public Date getChangedAt() {
		return ChangedAt;
	}

	public void setChangedAt(Date changedAt) {
		ChangedAt = changedAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
