package com.vbl.clanily.backend.vo.settings;

import com.vbl.clanily.backend.vo.ValueObject;

public class Payee implements ValueObject {

	public int payeeId;
	public String payeeName;
	public String payeeOfficialName;
	public String webSite;
	public String payeeType;
	public float totalAmount;
	public float totalTransactions;
	public boolean internal;
	public String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean intenral) {
		this.internal = intenral;
	}

	public int getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayeeOfficialName() {
		return payeeOfficialName;
	}

	public void setPayeeOfficialName(String payeeOfficialName) {
		this.payeeOfficialName = payeeOfficialName;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(float totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public String getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(String payeeType) {
		this.payeeType = payeeType;
	}

}
