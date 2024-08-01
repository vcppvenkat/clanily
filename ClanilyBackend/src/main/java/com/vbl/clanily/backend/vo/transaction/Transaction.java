package com.vbl.clanily.backend.vo.transaction;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.account.Account;
import com.vbl.clanily.backend.vo.settings.Category;
import com.vbl.clanily.backend.vo.settings.Objective;
import com.vbl.clanily.backend.vo.settings.Payee;
import com.vbl.clanily.backend.vo.settings.User;

public class Transaction implements ValueObject {

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
	private DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
	public int transactionId;
	public String summary;
	public Date transactionDate;
	public Date effectiveDate;
	public String transactionDateString;

	public String displayGroupValue;

	public int categoryId;
	public String categoryName;

	public Category category;

	public float transactionAmount;
	public String transactionAmountString;
	public String transactionType;

	public int accountId;
	public int toAccountId;
	public String accountName;
	public Account account;

	public int payeeId;
	public Payee payee;
	public String payeeName;

	public String transactionUserId;
	public String transactionUserName;
	public User transactionUser;

	public String notes;
	public String importedNotes;
	public String insertionType;
	public boolean cleared;

	public int projectId;
	public String projectName;

	public int objectiveId;
	public String objectiveName;
	public Objective objective;

	public int splitParentId;
	public Transaction splitParent;
	public List<Transaction> splitTransactions;
	public List<Integer> splitTransactionIds;

	public int loanId;
	public String loanName;

	public int mergeParentId;

	public List<Transaction> mergeTransactions;
	public List<Integer> mergeTransactionIds;

	public int transferTransactionId;
	public Transaction transferTransaction;

	public float sumOfSplit;
	public float sumOfMergedTransactions;

	public int recurrenceId;

	public String customField1;
	public String customField2;
	public String customField3;

	public boolean autoSync;

	public boolean isAlreadyInitialized;

	public String beneficiaryName;

	public int beneficiaryId;

	public List<TransactionFile> transactionFilesMetaData;

	public boolean hasMergedChildren() {
		return (mergeTransactionIds != null && !mergeTransactionIds.isEmpty());
	}

	public boolean hasSplitChildren() {
		return (splitTransactionIds != null && !splitTransactionIds.isEmpty());
	}

	public List<TransactionFile> getTransactionFilesMetaData() {
		return transactionFilesMetaData;
	}

	public void setTransactionFilesMetaData(List<TransactionFile> transactionFilesMetaData) {
		this.transactionFilesMetaData = transactionFilesMetaData;
	}

	public void addTransactionFilesMetaData(TransactionFile file) {
		if (this.transactionFilesMetaData == null) {
			this.transactionFilesMetaData = new ArrayList<TransactionFile>();
		}
		this.transactionFilesMetaData.add(file);
	}

	public String getObjectiveName() {
		return objectiveName;
	}

	public void setObjectiveName(String objectiveName) {
		this.objectiveName = objectiveName;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getTransactionAmountString() {
		return transactionAmountString = decimalFormat.format(transactionAmount);
	}

	public void setTransactionAmountString(String transactionAmountString) {
		this.transactionAmountString = transactionAmountString;
	}

	public String getTransactionDateString() {
		transactionDateString = "dd-mmm-yyyy";
		if (transactionDate != null) {
			transactionDateString = dateFormat.format(transactionDate);
		}
		return transactionDateString;
	}

	public void setTransactionDateString(String transactionDateString) {
		this.transactionDateString = transactionDateString;
	}

	public boolean isAutoSync() {
		return autoSync;
	}

	public void setAutoSync(boolean autoSync) {
		this.autoSync = autoSync;
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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getPayeeId() {
		return payeeId;
	}

	public void setPayeeId(int payeeId) {
		this.payeeId = payeeId;
	}

	public Payee getPayee() {
		return payee;
	}

	public void setPayee(Payee payee) {
		this.payee = payee;
	}

	public String getTransactionUserId() {
		return transactionUserId;
	}

	public void setTransactionUserId(String transactionUserId) {
		this.transactionUserId = transactionUserId;
	}

	public User getTransactionUser() {
		return transactionUser;
	}

	public void setTransactionUser(User transactionUser) {
		this.transactionUser = transactionUser;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getImportedNotes() {
		return importedNotes;
	}

	public void setImportedNotes(String importedNotes) {
		this.importedNotes = importedNotes;
	}

	public String getInsertionType() {
		return insertionType;
	}

	public void setInsertionType(String insertionType) {
		this.insertionType = insertionType;
	}

	public boolean isCleared() {
		return cleared;
	}

	public void setCleared(boolean cleared) {
		this.cleared = cleared;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getObjectiveId() {
		return objectiveId;
	}

	public void setObjectiveId(int objectiveId) {
		this.objectiveId = objectiveId;
	}

	public Objective getObjective() {
		return objective;
	}

	public void setObjective(Objective objective) {
		this.objective = objective;
	}

	public int getSplitParentId() {
		return splitParentId;
	}

	public void setSplitParentId(int splitParentId) {
		this.splitParentId = splitParentId;
	}

	public Transaction getSplitParent() {
		return splitParent;
	}

	public void setSplitParent(Transaction splitParent) {
		this.splitParent = splitParent;
	}

	public List<Transaction> getSplitTransactions() {
		return splitTransactions;
	}

	public void setSplitTransactions(List<Transaction> splitTransactions) {
		this.splitTransactions = splitTransactions;
	}

	public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public int getMergeParentId() {
		return mergeParentId;
	}

	public void setMergeParentId(int mergeParentId) {
		this.mergeParentId = mergeParentId;
	}

	public List<Transaction> getMergeTransactions() {
		return mergeTransactions;
	}

	public void setMergeTransactions(List<Transaction> mergeTransactions) {
		this.mergeTransactions = mergeTransactions;
	}

	public int getTransferTransactionId() {
		return transferTransactionId;
	}

	public void setTransferTransactionId(int transferTransactionId) {
		this.transferTransactionId = transferTransactionId;
	}

	public Transaction getTransferTransaction() {
		return transferTransaction;
	}

	public void setTransferTransaction(Transaction transferTransaction) {
		this.transferTransaction = transferTransaction;
	}

	public float getSumOfSplit() {
		return sumOfSplit;
	}

	public void setSumOfSplit(float sumOfSplit) {
		this.sumOfSplit = sumOfSplit;
	}

	public float getSumOfMergedTransactions() {
		return sumOfMergedTransactions;
	}

	public void setSumOfMergedTransactions(float sumOfMergedTransactions) {
		this.sumOfMergedTransactions = sumOfMergedTransactions;
	}

	public int getRecurrenceId() {
		return recurrenceId;
	}

	public void setRecurrenceId(int recurrenceId) {
		this.recurrenceId = recurrenceId;
	}

	public String getCustomField1() {
		return customField1;
	}

	public void setCustomField1(String customField1) {
		this.customField1 = customField1;
	}

	public String getCustomField2() {
		return customField2;
	}

	public void setCustomField2(String customField2) {
		this.customField2 = customField2;
	}

	public String getCustomField3() {
		return customField3;
	}

	public void setCustomField3(String customField3) {
		this.customField3 = customField3;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiary) {
		this.beneficiaryName = beneficiary;
	}

	public int getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(int beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getDisplayGroupValue() {
		return displayGroupValue;
	}

	public void setDisplayGroupValue(String displayGroupName) {
		this.displayGroupValue = displayGroupName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getLoanName() {
		return loanName;
	}

	public void setLoanName(String loanName) {
		this.loanName = loanName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getTransactionUserName() {
		return transactionUserName;
	}

	public void setTransactionUserName(String transactionUserName) {
		this.transactionUserName = transactionUserName;
	}

	public List<Integer> getMergeTransactionIds() {
		return mergeTransactionIds;
	}

	public void setMergeTransactionIds(List<Integer> mergeTransactionIds) {
		this.mergeTransactionIds = mergeTransactionIds;
	}

	public void addMergeTransactionId(int mergeTransactionId) {
		if (mergeTransactionId > 0) {
			if (this.mergeTransactionIds == null) {
				this.mergeTransactionIds = new ArrayList<Integer>();
			}
			this.mergeTransactionIds.add(mergeTransactionId);
		}
	}
	
	public void addSplitTransactionId(int splitTransactionId) {
		if (splitTransactionId > 0) {
			if (this.splitTransactionIds == null) {
				this.splitTransactionIds = new ArrayList<Integer>();
			}
			this.splitTransactionIds.add(splitTransactionId);
		}
	}

	public int getToAccountId() {
		return toAccountId;
	}

	public void setToAccountId(int toAccountId) {
		this.toAccountId = toAccountId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(transactionId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		return transactionId == other.transactionId;
	}

	@Override
	public String toString() {
		return "Transaction [transactionId=" + transactionId + ", summary=" + summary + ", transactionAmount="
				+ transactionAmount + "]";
	}
}
