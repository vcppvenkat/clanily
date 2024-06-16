package com.vbl.clanily.ui.web.controller.transaction;

public class AccountDisplayUnit {

	public AccountDisplayUnit() {

	}

	public AccountDisplayUnit(int accountId, String accountName, float balance, boolean selected) {
		this.accountId = accountId;
		this.accountName = accountName;
		this.balance = balance;
		this.selected = selected;
	}

	public int accountId;
	public String accountName;
	public float balance;
	public boolean selected;
	public boolean group;
	public boolean account;
	public String controllerMethod;

	public String getControllerMethod() {
		return controllerMethod;
	}

	public void setControllerMethod(String controllerMethod) {
		this.controllerMethod = controllerMethod;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean isGroup) {
		this.group = isGroup;
	}

	public boolean isAccount() {
		return account;
	}

	public void setAccount(boolean isAccount) {
		this.account = isAccount;
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

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean isSelected) {
		this.selected = isSelected;
	}

}
