package com.vbl.clanily.backend.vo.transaction;

import java.util.Date;

import com.vbl.clanily.backend.vo.ValueObject;

public class TransactionFile implements ValueObject {

	public int transactionFileId;
	public int transactionId;
	public String fileName;
	public String fileType;
	public String summary;
	public String description;
	public String file;
	public Date dateAdded;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getTransactionFileId() {
		return transactionFileId;
	}

	public void setTransactionFileId(int transactionFileId) {
		this.transactionFileId = transactionFileId;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

}
