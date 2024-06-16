package com.vbl.clanily.backend.vo.search;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionSearchCriteria extends SearchCriteria {

	private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy");

	public TransactionSearchCriteria() {

		
	}

	public String summary;
	public Date fromDate;
	public String fromDateString;

	public Date toDate;
	public String toDateString;

	public List<Integer> accountIds;
	public float amount;
	public String notes;
	public List<String> transactionTypes;
	public String orderBy;
	public boolean asc = false;
	public int transactionId;
	public List<Integer> categoryIds;
	public String groupBy = "Date";
	public String searchGroupName = "";
	public List<Integer> loanIds;
	public List<Integer> payeeIds;
	public List<Integer> objectiveIds;
	public List<Integer> projectIds;
	public List<Integer> groupParentIds;
	public List<Integer> splitParentIds;
	public String accountType;
	public String currentTransactionTheme = "Transactions";
	public String currentTransactionGroup = "Date";
	public String currentTransactionView = "Income, Expense";
	public String currentDateRange = "Month";
	public boolean allSelected;
	public String selectedGroup;
	public int selectedAccount;
	public boolean includePending = true;

	public boolean isIncludePending() {
		return includePending;
	}

	public void setIncludePending(boolean includePending) {
		this.includePending = includePending;
	}

	public boolean isNothingSelected() {
		return !allSelected && (selectedGroup == null || selectedGroup.trim().isEmpty()) && selectedAccount < 1;
	}

	public void clearGroupParentIds() {
		if (groupParentIds != null) {
			groupParentIds.clear();
		}
	}

	public void addGroupParentId(int groupParentId) {
		if (groupParentIds == null) {
			groupParentIds = new ArrayList<>();
		}
		groupParentIds.add(groupParentId);
	}

	public void clearSplitParentIds() {
		if (splitParentIds != null) {
			splitParentIds.clear();
		}
	}

	public void addSplitParentId(int splitParentId) {
		if (splitParentIds == null) {
			splitParentIds = new ArrayList<>();
		}
		splitParentIds.add(splitParentId);
	}

	public void clearProjectIds() {
		if (projectIds != null) {
			projectIds.clear();
		}
	}

	public void addProjectId(int objectiveId) {
		if (projectIds == null) {
			projectIds = new ArrayList<>();
		}
		projectIds.add(objectiveId);
	}

	public void clearObjectiveIds() {
		if (objectiveIds != null) {
			objectiveIds.clear();
		}
	}

	public void addObjectiveId(int objectiveId) {
		if (objectiveIds == null) {
			objectiveIds = new ArrayList<>();
		}
		objectiveIds.add(objectiveId);
	}

	public void clearPayeeIds() {
		if (payeeIds != null) {
			payeeIds.clear();
		}
	}

	public void addPayeeId(int payeeId) {
		if (payeeIds == null) {
			payeeIds = new ArrayList<>();
		}
		payeeIds.add(payeeId);
	}

	public void clearLoanIds() {
		if (loanIds != null) {
			loanIds.clear();
		}
	}

	public void addLoanId(int loanId) {
		if (loanIds == null) {
			loanIds = new ArrayList<>();
		}
		loanIds.add(loanId);
	}

	public void clearTransactionTypes() {
		if (transactionTypes != null)
			transactionTypes.clear();
	}

	public void clear() {
		summary = "";

		accountIds = null;
		summary = notes = null;
		transactionTypes = null;
		amount = 0.0F;
		orderBy = "";
		transactionId = 0;
		categoryIds = null;
		loanIds = null;
	}

	public void addCategoryId(int id) {
		if (categoryIds == null) {
			categoryIds = new ArrayList<>();
		}
		categoryIds.add(id);
	}

	public void clearCategoryIds() {
		if (categoryIds != null) {
			categoryIds = null;
		}
	}

	public void addAccountId(int accountId) {
		if (accountIds == null) {
			accountIds = new ArrayList<>();
		}
		accountIds.add(accountId);
	}

	public void clearAndAddAccountId(int accountId) {
		if (accountIds == null) {
			accountIds = new ArrayList<>();
		}
		accountIds.clear();
		accountIds.add(accountId);
	}

	public void clearAccountIds() {
		if (accountIds != null) {
			accountIds.clear();
		}
	}

	public void addTransactionType(String type) {
		if (transactionTypes == null) {
			transactionTypes = new ArrayList<>();
		}
		transactionTypes.add(type);
	}

	public void clearAndAddTransactionTypes(String type) {
		if (transactionTypes == null) {
			transactionTypes = new ArrayList<>();
		}
		transactionTypes.clear();
		transactionTypes.add(type);
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public List<Integer> getAccountIds() {
		return accountIds;
	}

	public void setAccountIds(List<Integer> accountIds) {
		this.accountIds = accountIds;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<String> getTransactionTypes() {
		return transactionTypes;
	}

	public void setTransactionTypes(List<String> transactionTypes) {
		this.transactionTypes = transactionTypes;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public List<Integer> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(List<Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public List<Integer> getLoanIds() {
		return loanIds;
	}

	public void setLoanIds(List<Integer> loanIds) {
		this.loanIds = loanIds;
	}

	public List<Integer> getPayeeIds() {
		return payeeIds;
	}

	public void setPayeeIds(List<Integer> payeeIds) {
		this.payeeIds = payeeIds;
	}

	public List<Integer> getObjectiveIds() {
		return objectiveIds;
	}

	public void setObjectiveIds(List<Integer> objectiveIds) {
		this.objectiveIds = objectiveIds;
	}

	public List<Integer> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Integer> projectIds) {
		this.projectIds = projectIds;
	}

	public List<Integer> getGroupParentIds() {
		return groupParentIds;
	}

	public void setGroupParentIds(List<Integer> groupParentIds) {
		this.groupParentIds = groupParentIds;
	}

	public List<Integer> getSplitParentIds() {
		return splitParentIds;
	}

	public void setSplitParentIds(List<Integer> splitParentIds) {
		this.splitParentIds = splitParentIds;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getCurrentTransactionTheme() {
		return currentTransactionTheme;
	}

	public void setCurrentTransactionTheme(String currentTransactionTheme) {
		this.currentTransactionTheme = currentTransactionTheme;
	}

	public String getCurrentTransactionGroup() {
		return currentTransactionGroup;
	}

	public void setCurrentTransactionGroup(String currentTransactionGroup) {
		this.currentTransactionGroup = currentTransactionGroup;
	}

	public String getCurrentTransactionView() {
		return currentTransactionView;
	}

	public void setCurrentTransactionView(String currentTransactionView) {
		this.currentTransactionView = currentTransactionView;
	}

	public String getCurrentDateRange() {
		return currentDateRange;
	}

	public void setCurrentDateRange(String currentDateRange) {
		this.currentDateRange = currentDateRange;
	}

	public boolean isAllSelected() {
		return allSelected;
	}

	public void setAllSelected(boolean isAllSelected) {
		this.allSelected = isAllSelected;
	}

	public String getSelectedGroup() {
		return selectedGroup;
	}

	public void setSelectedGroup(String selectedGroup) {
		this.selectedGroup = selectedGroup;
	}

	public int getSelectedAccount() {
		return selectedAccount;
	}

	public void setSelectedAccount(int selectedAccount) {
		this.selectedAccount = selectedAccount;
	}

	public String getFromDateString() {
		return fromDateString = dateTimeFormat.format(fromDate);
	}

	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	public String getToDateString() {
		return toDateString = dateTimeFormat.format(toDate);

	}

	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

}
