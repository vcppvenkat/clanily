package com.vbl.clanily.backend.vo.settings;

import java.util.Date;

import com.vbl.clanily.backend.vo.ValueObject;

public class Beneficiary implements ValueObject, Comparable<Beneficiary> {

	@Override
	public int compareTo(Beneficiary b) {
		return (b != null && b.beneficiaryId == this.beneficiaryId) ? 1 : 0;
	}

	public int beneficiaryId;
	public String beneficiaryName;
	public Date createdDate;
	public boolean internal;

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
