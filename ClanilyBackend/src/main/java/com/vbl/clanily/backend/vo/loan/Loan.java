package com.vbl.clanily.backend.vo.loan;

import java.util.ArrayList;
import java.util.List;

import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.transaction.Transaction;

public class Loan implements ValueObject {

	public int loanId;
	public String loanSummary;
	public long startDate;
	public long endDate;
	public boolean noEndDate;
	public String loanType;
	public float amount;
	public String loanStatus;
	public String description;
	public int payeeId;
	public float interestPerAnum;
	public boolean internal;
	public long createdTs;
	public float totalPaid;

	public List<Transaction> loanTransactions = new ArrayList<Transaction>();

	public void addTransaction(Transaction t) {
		if (loanTransactions == null) {
			loanTransactions = new ArrayList<Transaction>();
		}
		loanTransactions.add(t);
	}

	public List<Transaction> getLoanTransactions() {
		return loanTransactions;
	}

	public void setLoanTransactions(List<Transaction> loanTransactions) {
		this.loanTransactions = loanTransactions;
	}

	public long getCreatedTs() {
		return createdTs;
	}

	public void setCreatedTs(long createdTs) {
		this.createdTs = createdTs;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public String getLoanSummary() {
		return loanSummary;
	}

	public void setLoanSummary(String loanSummary) {
		this.loanSummary = loanSummary;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public boolean isNoEndDate() {
		return noEndDate;
	}

	public void setNoEndDate(boolean noEndDate) {
		this.noEndDate = noEndDate;
	}

	public String getLoanType() {
		return loanType;
	}

	public void setLoanType(String loanType) {
		this.loanType = loanType;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}

	public float getInterestPerAnum() {
		return interestPerAnum;
	}

	public void setInterestPerAnum(float interestPerAnum) {
		this.interestPerAnum = interestPerAnum;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public float getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(float totalPaid) {
		this.totalPaid = totalPaid;
	}

	public float getTotalPending() {
		return amount - totalPaid;
	}

}
