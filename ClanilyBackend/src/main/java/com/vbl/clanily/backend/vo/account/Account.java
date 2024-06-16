package com.vbl.clanily.backend.vo.account;

import com.vbl.clanily.backend.vo.ValueObject;


public class Account implements ValueObject {

	public int accountId;
	public String accountName;
	public String bankName;
	public String accountHolderId;
	public boolean accountVisible;
	public float currentBalance;
	public String accountGroup;
	public boolean internal;
	public boolean autoSync;
	public int displayOrder;
	public String accountNumber;
	public String bankLoginId;
	public String bankPassword;
	public int autoSyncTemplateId;
	public String notes;
	public String webUrl;

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountHolderId() {
		return accountHolderId;
	}

	public void setAccountHolderId(String accountHolderId) {
		this.accountHolderId = accountHolderId;
	}

	public boolean isAccountVisible() {
		return accountVisible;
	}

	public void setAccountVisible(boolean accountVisible) {
		this.accountVisible = accountVisible;
	}

	public float getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(float currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getAccountGroup() {
		return accountGroup;
	}

	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public boolean isAutoSync() {
		return autoSync;
	}

	public void setAutoSync(boolean autoSync) {
		this.autoSync = autoSync;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankLoginId() {
		return bankLoginId;
	}

	public void setBankLoginId(String bankLoginId) {
		this.bankLoginId = bankLoginId;
	}

	public String getBankPassword() {
		return bankPassword;
	}

	public void setBankPassword(String bankPassword) {
		this.bankPassword = bankPassword;
	}

	public int getAutoSyncTemplateId() {
		return autoSyncTemplateId;
	}

	public void setAutoSyncTemplateId(int autoSyncTemplateId) {
		this.autoSyncTemplateId = autoSyncTemplateId;
	}

}
