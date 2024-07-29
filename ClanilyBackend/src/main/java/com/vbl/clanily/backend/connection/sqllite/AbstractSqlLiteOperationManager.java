package com.vbl.clanily.backend.connection.sqllite;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.account.AccountDBTranslator;
import com.vbl.clanily.backend.vo.account.Account;
import com.vbl.clanily.backend.vo.loan.Loan;
import com.vbl.clanily.backend.vo.settings.Beneficiary;
import com.vbl.clanily.backend.vo.settings.Category;
import com.vbl.clanily.backend.vo.settings.Objective;
import com.vbl.clanily.backend.vo.settings.Payee;
import com.vbl.clanily.backend.vo.settings.User;
import com.vbl.clanily.backend.vo.transaction.Transaction;

public abstract class AbstractSqlLiteOperationManager extends AbstractSqlLiteManager {

	protected boolean isValid(String str) {
		return (str != null && !str.isEmpty());
	}

	@SuppressWarnings("rawtypes")
	protected boolean isValid(List list) {
		return (list != null && !list.isEmpty());
	}

	protected float convertCurrencyFormat(float amount) {
		return amount;
	}

	protected boolean containsValue(String value) {
		return value != null && !value.trim().isEmpty();
	}

	protected boolean containsValue(String[] value) {
		return value != null && value.length > 0;
	}

	protected boolean containsValue(int[] value) {
		return value != null && value.length > 0;
	}

	@SuppressWarnings("rawtypes")
	protected boolean containsValue(List list) {
		return list != null && !list.isEmpty();
	}

	protected Category copyCategory(Category c, ResultSet rs) throws Exception {
		if (c == null) {
			c = new Category();
		}
		c.categoryId = rs.getInt("CATEGORY_ID");
		c.categoryName = rs.getString("CATEGORY_NAME");
		c.description = rs.getString("CATEGORY_DESCRIPTION");
		c.type = rs.getString("CATEGORY_TYPE");
		c.tag = rs.getString("TAG");
		c.internal = rs.getBoolean("INTERNAL");
		c.active = rs.getBoolean("ACTIVE");

		return c;
	}

	protected Loan copyLoan(Loan l, ResultSet rs) throws Exception {
		if (l == null) {
			l = new Loan();
		}
		l.loanId = rs.getInt("LOAN_ID");
		l.loanSummary = rs.getString("SUMMARY");
		l.startDate = rs.getLong("START_DATE");
		l.endDate = rs.getLong("END_DATE");
		l.noEndDate = rs.getBoolean("NO_END_DATE");
		l.loanType = rs.getString("LOAN_TYPE");
		l.amount = rs.getFloat("AMOUNT");
		l.loanStatus = rs.getString("LOAN_STATUS");
		l.description = rs.getString("DESCRIPTION");
		l.payeeId = rs.getInt("PAYEE_ID");
		l.interestPerAnum = rs.getFloat("INTEREST_PER_ANUM");
		l.createdTs = rs.getLong("CREATED_TS");
		l.internal = rs.getBoolean("INTERNAL");

		return l;
	}

	protected Beneficiary copyBeneficiary(Beneficiary b, ResultSet rs) throws Exception {
		if (b == null) {
			b = new Beneficiary();
		}
		b.beneficiaryId = rs.getInt("BENEFICIARY_ID");
		b.beneficiaryName = rs.getString("BENEFICIARY_NAME");

		return b;
	}

	protected Payee copyPayee(Payee payee, ResultSet rs) throws Exception {
		if (payee == null) {
			payee = new Payee();
		}

		payee.payeeId = rs.getInt("PAYEE_ID");
		payee.payeeName = rs.getString("PAYEE_NAME");
		payee.payeeOfficialName = rs.getString("OFFICIAL_NAME");
		payee.webSite = rs.getString("WEBSITE");
		payee.internal = rs.getBoolean("INTERNAL");
		payee.payeeType = rs.getString("PAYEE_TYPE");
		payee.description = rs.getString("DESCRIPTION");
		return payee;
	}

	protected Objective copyObjective(Objective obj, ResultSet rs) throws Exception {
		if (obj == null) {
			obj = new Objective();
		}

		obj.objectiveId = rs.getInt("OBJECTIVE_ID");
		obj.objectiveName = rs.getString("OBJECTIVE_NAME");
		obj.objectiveDescription = rs.getString("OBJECTIVE_DESCRIPTION");
		obj.internal = rs.getBoolean("INTERNAL");

		return obj;
	}

	protected User copyUser(User user, ResultSet rs) throws Exception {
		if (user == null) {
			user = new User();
		}

		user.userId = rs.getString("USER_ID");
		user.userName = rs.getString("USER_NAME");
		user.userRole = rs.getString("USER_ROLE");
		user.userActive = rs.getBoolean("USER_ACTIVE");
		user.internal = rs.getBoolean("INTERNAL");

		return user;
	}

	protected Account copyAccount(Account account, ResultSet rs) throws Exception {
		if (account == null) {
			account = new Account();
		}

		account.accountId = rs.getInt("ACCOUNT_ID");
		account.accountName = rs.getString("ACCOUNT_NAME");
		account.bankName = rs.getString("BANK_NAME");
		account.accountHolderId = rs.getString("ACCOUNT_HOLDER");
		account.accountVisible = rs.getBoolean("ACCOUNTS_VISIBLE");
		account.currentBalance = rs.getFloat("CURRENT_BALANCE");
		account.accountGroup = rs.getString("ACCOUNT_GROUP");

		account.internal = rs.getBoolean("INTERNAL");
		account.autoSync = rs.getBoolean("AUTO_SYNC");
		account.displayOrder = rs.getInt("DISPLAY_ORDER");
		account.accountNumber = rs.getString("ACCOUNT_NUMBER");
		account.bankLoginId = rs.getString("BANK_LOGIN_ID");
		account.bankPassword = rs.getString("BANK_PASSWORD");
		account.autoSyncTemplateId = rs.getInt("AUTO_SYNC_TEMPLATE_ID");
		account.notes = rs.getString("NOTES");
		account.webUrl = rs.getString("BANK_LOGIN_URL");

		return account;

	}

	protected Transaction copyTransaction(Transaction t, ResultSet rs) throws Exception {
		if (t == null) {
			t = new Transaction();
		}
		t.transactionId = rs.getInt("TRANSACTION_ID");
		t.summary = rs.getString("SUMMARY");
		t.transactionDate = new Date(rs.getLong("TRANSACTION_DATE"));
		t.effectiveDate = new Date(rs.getLong("EFFECTIVE_DATE"));
		t.categoryId = rs.getInt("CATEGORY_ID");
		t.categoryName = rs.getString("CATEGORY_NAME");
		t.transactionAmount = rs.getFloat("TRANSACTION_AMOUNT");
		t.transactionType = rs.getString("TRANSACTION_TYPE");
		t.accountId = rs.getInt("ACCOUNT_ID");
		t.account = AccountDBTranslator.getInstance().getById(t.accountId);
		t.accountName = rs.getString("ACCOUNT_NAME");

		t.payeeId = rs.getInt("PAYEE_ID");
		t.payeeName = rs.getString("PAYEE_NAME");
		t.transactionUserId = rs.getString("TRANSACTION_USER_ID");
		t.notes = rs.getString("NOTES");
		t.importedNotes = rs.getString("IMPORTED_NOTES");
		t.insertionType = rs.getString("INSERT_TYPE");
		t.cleared = rs.getBoolean("CLEARED");
		t.projectId = rs.getInt("PROJECT_ID");
		t.objectiveId = rs.getInt("OBJECTIVE_ID");
		t.beneficiaryId = rs.getInt("BENEFICIARY_ID");
		t.beneficiaryName = rs.getString("BENEFICIARY_NAME");
		t.splitParentId = rs.getInt("SPLIT_PARENT_ID");
		t.loanId = rs.getInt("LOAN_ID");
		t.mergeParentId = rs.getInt("MERGE_PARENT_ID");
		t.transferTransactionId = rs.getInt("TRANSFER_TRANSACTION_ID");
		t.recurrenceId = rs.getInt("RECURRENCE_ID");
		t.customField1 = rs.getString("CUSTOM_FIELD_1");
		t.customField2 = rs.getString("CUSTOM_FIELD_2");
		t.customField3 = rs.getString("CUSTOM_FIELD_3");
		t.sumOfMergedTransactions = rs.getFloat("TOTAL_VALUE");

		return t;
	}

}
