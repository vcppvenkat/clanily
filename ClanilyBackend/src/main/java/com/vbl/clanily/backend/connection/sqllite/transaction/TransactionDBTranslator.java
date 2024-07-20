package com.vbl.clanily.backend.connection.sqllite.transaction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.vbl.clanily.backend.connection.ClanilyDBOperation;
import com.vbl.clanily.backend.connection.sqllite.AbstractSqlLiteOperationManager;
import com.vbl.clanily.backend.connection.sqllite.account.AccountDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.search.TransactionSearchCriteria;
import com.vbl.clanily.backend.vo.transaction.Transaction;
import com.vbl.clanily.backend.vo.transaction.TransactionFile;

public class TransactionDBTranslator extends AbstractSqlLiteOperationManager implements ClanilyDBOperation {

	private static final TransactionDBTranslator thisInstance = new TransactionDBTranslator();

	public static TransactionDBTranslator getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Transaction> search(SearchCriteria searchCriteria) throws Exception {
		SearchResult<Transaction> result = new SearchResult<Transaction>();
		Transaction transaction = null;
		Statement st = null;
		TransactionSearchCriteria search = (TransactionSearchCriteria) searchCriteria;
		st = connection.createStatement();
		String query = "SELECT TRANSACTIONS.*, CATEGORY_NAME , USER_NAME,  PAYEE_NAME , OBJECTIVE_NAME, PROJECT_NAME, LOANS.SUMMARY AS LOAN_NAME, ACCOUNT_NAME, ";

		if ("Date".equals(search.groupBy)) {
			query += " SUM(TRANSACTION_AMOUNT) OVER(PARTITION BY strftime('%d-%m-%Y', EFFECTIVE_DATE / 1000, 'unixepoch') ) AS TOTAL_VALUE, ";

		} else {
			query += " SUM(TRANSACTION_AMOUNT) OVER(PARTITION BY " + search.searchGroupName + ") AS TOTAL_VALUE, ";
		}

		query += " SUM(TRANSACTION_AMOUNT) OVER(PARTITION BY " + search.searchGroupName + ") AS TOTAL_VALUE, ";
		query += " (SELECT SUM(TRANSACTION_AMOUNT) FROM TRANSACTIONS AS GROUP_T WHERE  GROUP_T.GROUP_PARENT_ID = TRANSACTIONS.TRANSACTION_ID) AS SUM_OF_GROUP, "
				+ " (SELECT SUM(TRANSACTION_AMOUNT) FROM TRANSACTIONS AS GROUP_P WHERE  GROUP_P.SPLIT_PARENT_ID = TRANSACTIONS.TRANSACTION_ID) AS SUM_OF_SPLIT "
				+ " FROM  TRANSACTIONS, CATEGORY, ACCOUNTS , USERS, PAYEE  , OBJECTIVES, PROJECTS , LOANS "
				+ " WHERE TRANSACTIONS.CATEGORY_ID = CATEGORY.CATEGORY_ID "
				+ " AND TRANSACTIONS.ACCOUNT_ID = ACCOUNTS.ACCOUNT_ID   "
				+ " AND TRANSACTIONS.TRANSACTION_USER_ID = USERS.USER_ID "
				+ " AND (TRANSACTIONS.PAYEE_ID <>-1  AND TRANSACTIONS.PAYEE_ID = PAYEE.PAYEE_ID) "
				+ " AND TRANSACTIONS.OBJECTIVE_ID = OBJECTIVES.OBJECTIVE_ID "
				+ " AND TRANSACTIONS.PROJECT_ID = PROJECTS.PROJECT_ID " + " AND TRANSACTIONS.LOAN_ID = LOANS.LOAN_ID ";

		if (containsValue(search.summary)) {
			query += " AND ( UPPER(SUMMARY) LIKE '%" + search.summary.toUpperCase() + "%' ";
			query += " AND UPPER(TRANSACTIONS.NOTES) LIKE '%" + search.summary.toUpperCase() + "%' ";
			query += " AND  UPPER(IMPORTED_NOTES) LIKE '%" + search.summary.toUpperCase() + "%' )";
		} else {
			if (search.fromDate != null && search.toDate != null) {
				query += " AND ";
				query += " TRANSACTION_DATE  >= " + search.fromDate.getTime() + " AND TRANSACTION_DATE <= "
						+ search.toDate.getTime();

			}

			if (containsValue(search.loanIds)) {
				query += " AND TRANSACTIONS.LOAN_ID IN ( ";
				for (int loanId : search.loanIds) {
					query += "" + loanId + ",";
				}
				query = query.substring(0, query.length() - 1);
				query += " ) ";
			}

			if (containsValue(search.payeeIds)) {
				query += " AND ";
				query += " TRANSACTIONS.PAYEE_ID IN  (";

				for (int value : search.payeeIds) {
					query += value;
					query += ",";
				}
				query = query.substring(0, query.length() - 1);
				query += " ) ";
			}
			if (containsValue(search.objectiveIds)) {
				query += " AND ";
				query += " TRANSACTIONS.OBJECTIVE_ID IN  (";

				for (int value : search.objectiveIds) {
					query += value;
					query += ",";
				}
				query = query.substring(0, query.length() - 1);
				query += " ) ";
			}
			if (containsValue(search.projectIds)) {
				query += " AND ";
				query += " TRANSACTIONS.PROJECT_ID IN  (";

				for (int value : search.projectIds) {
					query += value;
					query += ",";
				}
				query = query.substring(0, query.length() - 1);
				query += " ) ";
			}
			if (containsValue(search.transactionTypes)) {
				query += " AND ";
				query += " TRANSACTIONS.TRANSACTION_TYPE IN  (";

				for (String value : search.transactionTypes) {
					query += "'";
					query += value;
					query += "',";
				}
				query = query.substring(0, query.length() - 1);
				query += " ) ";
			}
			if (search.amount != 0.0F) {
				query += " AND TRANSACTION_AMOUNT  = " + search.amount;
			}

			if (containsValue(search.accountIds)) {
				query += " AND (";
				query += " TRANSACTIONS.ACCOUNT_ID IN ( ";
				for (int value : search.accountIds) {
					query += value + ",";
				}
				query = query.substring(0, query.length() - 1);
				query += " ) ";
				
				query += " ) ";

			}

			if (containsValue(search.categoryIds)) {
				query += " AND TRANSACTIONS.CATEGORY_ID IN ( ";
				for (int id : search.categoryIds) {
					query += id + ",";
				}
				query = query.substring(0, query.length() - 1);
				query += " ) ";
			}

			/*
			 * if (containsValue(search.groupParentIds)) { query +=
			 * " AND TRANSACTIONS.SPLIT_PARENT_ID IN ( "; for (int id :
			 * search.splitParentIds) { query += id + ","; } query = query.substring(0,
			 * query.length() - 1); query += " ) "; }
			 * 
			 * if (containsValue(search.splitParentIds)) { query +=
			 * " AND TRANSACTIONS.GROUP_PARENT_ID IN ( "; for (int id :
			 * search.groupParentIds) { query += id + ","; } query = query.substring(0,
			 * query.length() - 1); query += " ) "; }
			 * 
			 */

			if (search.transactionId > 0) {
				query += " AND TRANSACTION_ID = " + search.transactionId;
			}

			if (!search.includePending) {
				query += " AND CLEARED = '1' ";
			}

			query += " AND (TRANSACTIONS.GROUP_PARENT_ID = 0)";

		}

		/*
		 * if (isValid(search.searchGroupName)) { query += " GROUP BY " +
		 * search.searchGroupName; }
		 */

		query += " ORDER BY " + search.searchGroupName + " " + ((search.asc) ? "ASC" : "DESC");
		System.out.println(query);

		ResultSet rs = st.executeQuery(query);
		while (rs.next()) {
			transaction = copyTransaction(null, rs);
			amendGroupTransactionIds(transaction);	
			result.add(transaction);
		}
		rs.close();

		return result;
	}

	public float sumOfTransactions(List<Integer> transactionIds) throws Exception {

		float sum = 0.0f;

		String query = "SELECT SUM(TRANSACTION_AMOUNT) AS SUM_OF_IDS FROM TRANSACTIONS WHERE TRANSACTION_ID IN ( ";
		for (int id : transactionIds)
			query += id + ",";
		query = query.substring(0, query.length() - 1);
		query += ")";
		

		Statement s = connection.createStatement();

		ResultSet rs = s.executeQuery(query);
		while (rs.next()) {
			sum = rs.getFloat("SUM_OF_IDS");
		}

		s.close();
		return sum;
	}

	private void amendGroupTransactionIds(Transaction transaction) throws Exception {
		Statement st1 = connection.createStatement();
		String query = " SELECT TRANSACTION_ID FROM TRANSACTIONS WHERE GROUP_PARENT_ID = " + transaction.transactionId;
		ResultSet rs1 = st1.executeQuery(query);
		while (rs1.next()) {
			transaction.addGroupTransactionId(rs1.getInt("TRANSACTION_ID"));
		}
		rs1.close();
	}

	@Override
	public Transaction getById(int id) throws Exception {
		Transaction t = new Transaction();
		Statement st = connection.createStatement();
		String query = "SELECT TRANSACTIONS.*, CATEGORY_NAME , USER_NAME,  PAYEE_NAME , OBJECTIVE_NAME, PROJECT_NAME, LOANS.SUMMARY AS LOAN_NAME, ACCOUNT_NAME, ";

		query += " (SELECT SUM(TRANSACTION_AMOUNT) FROM TRANSACTIONS AS GROUP_T WHERE  GROUP_T.GROUP_PARENT_ID = TRANSACTIONS.TRANSACTION_ID) AS SUM_OF_GROUP, "
				+ " (SELECT SUM(TRANSACTION_AMOUNT) FROM TRANSACTIONS AS GROUP_P WHERE  GROUP_P.SPLIT_PARENT_ID = TRANSACTIONS.TRANSACTION_ID) AS SUM_OF_SPLIT "
				+ " FROM  TRANSACTIONS, CATEGORY, ACCOUNTS , USERS, PAYEE  , OBJECTIVES, PROJECTS , LOANS "
				+ " WHERE TRANSACTIONS.CATEGORY_ID = CATEGORY.CATEGORY_ID "
				+ " AND TRANSACTIONS.ACCOUNT_ID = ACCOUNTS.ACCOUNT_ID   "
				+ " AND TRANSACTIONS.TRANSACTION_USER_ID = USERS.USER_ID "
				+ " AND (TRANSACTIONS.PAYEE_ID <>-1  AND TRANSACTIONS.PAYEE_ID = PAYEE.PAYEE_ID) "
				+ " AND TRANSACTIONS.OBJECTIVE_ID = OBJECTIVES.OBJECTIVE_ID "
				+ " AND TRANSACTIONS.PROJECT_ID = PROJECTS.PROJECT_ID " + " AND TRANSACTIONS.LOAN_ID = LOANS.LOAN_ID "
				+ " AND TRANSACTIONS.TRANSACTION_ID = " + id;

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			// transaction = copyTransaction(null, rs);
			t.transactionId = rs.getInt("TRANSACTION_ID");
			t.summary = rs.getString("SUMMARY");
			t.transactionDate = new Date(rs.getLong("TRANSACTION_DATE"));
			t.effectiveDate = new Date(rs.getLong("EFFECTIVE_DATE"));
			t.categoryId = rs.getInt("CATEGORY_ID");
			t.categoryName = rs.getString("CATEGORY_NAME");
			t.transactionAmount = rs.getFloat("TRANSACTION_AMOUNT");
			t.transactionType = rs.getString("TRANSACTION_TYPE");
			t.accountId = rs.getInt("ACCOUNT_ID");
			t.accountName = AccountDBTranslator.getInstance().getById(t.accountId).accountName;

			t.payeeId = rs.getInt("PAYEE_ID");
			t.payeeName = rs.getString("PAYEE_NAME");
			t.transactionUserId = rs.getString("TRANSACTION_USER_ID");
			t.notes = rs.getString("NOTES");
			t.importedNotes = rs.getString("IMPORTED_NOTES");
			t.insertionType = rs.getString("INSERT_TYPE");
			t.cleared = rs.getBoolean("CLEARED");
			t.projectId = rs.getInt("PROJECT_ID");
			t.projectName = rs.getString("PROJECT_NAME");
			t.objectiveId = rs.getInt("OBJECTIVE_ID");
			t.objectiveName = rs.getString("OBJECTIVE_NAME");
			t.beneficiary = rs.getString("BENEFICIARY");
			t.splitParentId = rs.getInt("SPLIT_PARENT_ID");
			t.loanId = rs.getInt("LOAN_ID");
			t.loanName = rs.getString("LOAN_NAME");
			t.groupParentId = rs.getInt("GROUP_PARENT_ID");
			t.transferTransactionId = rs.getInt("TRANSFER_TRANSACTION_ID");
			t.recurrenceId = rs.getInt("RECURRENCE_ID");
			t.customField1 = rs.getString("CUSTOM_FIELD_1");
			t.customField2 = rs.getString("CUSTOM_FIELD_2");
			t.customField3 = rs.getString("CUSTOM_FIELD_3");
			t.transactionUserName = rs.getString("USER_NAME");

			break;
		}

		rs.close();
		

		// associate transaction file metadata
		t.transactionFilesMetaData = getTransactionFilesMetadata(t.transactionId).values() ;
		System.out.println("Printing - " + t.transactionFilesMetaData.size());
		
		amendGroupTransactionIds(t);
		
		

		return t;
	}
	
	

	/*
	 * public List<Transaction> getByGroupId(int groupParentId) throws Exception {
	 * List<Integer> groupIds = new ArrayList<Integer>(); List<Transaction>
	 * parentTransactions = null; Statement st = connection.createStatement();
	 * String query =
	 * "SELECT TRANSACTION_ID FROM TRANSACTIONS WHERE GROUP_PARENT_ID =  " +
	 * groupParentId;
	 * 
	 * ResultSet rs = st.executeQuery(query);
	 * 
	 * while (rs.next()) { groupIds.add(rs.getInt("TRANSACTION_ID")); }
	 * 
	 * if(isValid(groupIds)) { TransactionSearchCriteria search = new
	 * TransactionSearchCriteria();
	 * 
	 * } rs.close();
	 * 
	 * return groupIds; }
	 * 
	 */

	@Override
	public Transaction getByUniqueName(String name) throws Exception {
		throw new OperationNotSupportedException("Get by name for a transaction is not supported.");
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		int rowId = -1;
		Transaction t = (Transaction) value;

		String query = "INSERT INTO TRANSACTIONS (SUMMARY, TRANSACTION_DATE, CATEGORY_ID, TRANSACTION_TYPE, ACCOUNT_ID, TRANSACTION_AMOUNT, PAYEE_ID, NOTES, PROJECT_ID, TRANSACTION_USER_ID, OBJECTIVE_ID, SPLIT_PARENT_ID, IMPORTED_NOTES, INSERT_TYPE, LOAN_ID, CLEARED, GROUP_PARENT_ID, TRANSFER_TRANSACTION_ID, RECURRENCE_ID, CUSTOM_FIELD_1,CUSTOM_FIELD_2,CUSTOM_FIELD_3, BENEFICIARY, EFFECTIVE_DATE) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement s = connection.prepareStatement(query);

		s.setString(1, t.summary);
		s.setLong(2, t.transactionDate.getTime());
		s.setInt(3, t.categoryId);
		s.setString(4, t.transactionType);
		s.setInt(5, t.accountId);
		s.setFloat(6, t.transactionAmount);
		s.setInt(7, t.payeeId);
		s.setString(8, t.notes);
		s.setInt(9, t.projectId);
		s.setString(10, t.transactionUserId);
		s.setInt(11, t.objectiveId);
		s.setInt(12, t.splitParentId);
		s.setString(13, t.importedNotes);
		s.setString(14, t.insertionType);
		s.setInt(15, t.loanId);
		s.setBoolean(16, t.cleared);
		s.setInt(17, t.groupParentId);
		s.setInt(18, t.transferTransactionId);
		s.setInt(19, t.recurrenceId);
		s.setString(20, t.customField1);
		s.setString(21, t.customField1);
		s.setString(22, t.customField3);
		s.setString(23, t.beneficiary);
		s.setLong(24, t.effectiveDate.getTime());
		s.executeUpdate();

		query = "SELECT MAX(TRANSACTION_ID) AS TRANS_ID FROM TRANSACTIONS ";
		s = connection.prepareStatement(query);
		ResultSet rs = s.executeQuery();
		while (rs.next()) {
			rowId = rs.getInt("TRANS_ID");
			break;
		}
		s.close();
		return rowId;
	}
	
	public TransactionFile getTransactionFile(int transactionFileId) throws Exception {
		throw new UnsupportedOperationException("This method is currently not supported");
	}
	
	public SearchResult<TransactionFile> getTransactionFilesMetadata(int transactionId) throws Exception {
		SearchResult<TransactionFile> result = new SearchResult<TransactionFile>();
		String query = "SELECT TRANSACTION_FILE_ID, TRANSACTION_ID, FILE_NAME, DATE_ADDED, DESCRIPTION, FILE_TYPE, SUMMARY FROM TRANSACTION_FILES WHERE TRANSACTION_ID = " + transactionId;
		Statement s = connection.createStatement();
		ResultSet rs = s.executeQuery(query);
		while(rs.next()) {
			TransactionFile file = new TransactionFile();
			file.dateAdded = new Date(rs.getLong("DATE_ADDED"));
			file.description = rs.getString("DESCRIPTION");
			file.fileName = rs.getString("FILE_NAME");
			file.fileType = rs.getString("FILE_TYPE");
			file.summary = rs.getString("SUMMARY");
			file.transactionFileId = rs.getInt("TRANSACTION_FILE_ID");
			file.transactionId = rs.getInt("TRANSACTION_ID");
			result.add(file);
					
		}
		return result;
	}
	
	public void deleteAttachment(int transactionFileId) throws Exception {

		String query = "delete from TRANSACTION_FILES where TRANSACTION_FILE_ID = " + transactionFileId;

		PreparedStatement s = connection.prepareStatement(query);
		s.executeUpdate();
		s.close();

	}
	
	public void attachFile(TransactionFile file) throws Exception {

		String query = "INSERT INTO TRANSACTION_FILES (TRANSACTION_ID, FILE_NAME, FILE_TYPE, SUMMARY,  FILE_OBJECT, DATE_ADDED, DESCRIPTION) VALUES (?,?,?,?,?,?,?)";

		PreparedStatement s = connection.prepareStatement(query);

		s.setInt(1, file.transactionId);
		s.setString(2, file.fileName);
		s.setString(3, file.fileType);
		s.setString(4, file.summary);
		s.setBytes(5, file.file);
		s.setLong(6, new Date().getTime());
		s.setString(7, file.description);
		s.executeUpdate();
		s.close();

	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		throw new OperationNotSupportedException("Insert all for transactions is not supported.");
	}

	public void unmergeTransaction(int transactionId) throws Exception {
		String query = "UPDATE TRANSACTIONS SET GROUP_PARENT_ID=0 WHERE GROUP_PARENT_ID=?";
		PreparedStatement s = connection.prepareStatement(query);
		s.setInt(1, transactionId);
		s.executeUpdate();
		s.close();
	}

	public void mergeTransaction(int transactionId, List<Integer> children) throws Exception {
		unmergeTransaction(transactionId);
		String query = "UPDATE TRANSACTIONS SET GROUP_PARENT_ID=? WHERE TRANSACTION_ID=?";
		PreparedStatement s = connection.prepareStatement(query);
		for (int child : children) {
			s.setInt(1, transactionId);
			s.setInt(2, child);
			s.executeUpdate();
		}
		s.close();
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		String query = "UPDATE TRANSACTIONS SET SUMMARY = ?, TRANSACTION_DATE=?, CATEGORY_ID = ?, TRANSACTION_TYPE=?, ACCOUNT_ID=?, TRANSACTION_AMOUNT=?, PAYEE_ID=?, NOTES=?, PROJECT_ID=?, TRANSACTION_USER_ID=?, OBJECTIVE_ID=?,  SPLIT_PARENT_ID=?, LOAN_ID = ?, CLEARED = ?, GROUP_PARENT_ID=?, TRANSFER_TRANSACTION_ID=?, RECURRENCE_ID=?, CUSTOM_FIELD_1=?, CUSTOM_FIELD_2=?, CUSTOM_FIELD_3=?, BENEFICIARY=?, EFFECTIVE_DATE=? WHERE TRANSACTION_ID=?";
		boolean result = false;
		Transaction t = (Transaction) value;

		PreparedStatement s = connection.prepareStatement(query);
		s.setString(1, t.summary);
		s.setLong(2, t.transactionDate.getTime());
		s.setInt(3, t.categoryId);
		s.setString(4, t.transactionType);
		s.setInt(5, t.accountId);
		s.setFloat(6, t.transactionAmount);
		s.setInt(7, t.payeeId);
		s.setString(8, t.notes);
		s.setInt(9, t.projectId);
		s.setString(10, t.transactionUserId);
		s.setInt(11, t.objectiveId);
		s.setInt(12, t.splitParentId);
		s.setInt(13, t.loanId);
		s.setBoolean(14, t.cleared);
		s.setInt(15, t.groupParentId);
		s.setInt(16, t.transferTransactionId);
		s.setInt(17, t.recurrenceId);
		s.setString(18, t.customField1);
		s.setString(19, t.customField1);
		s.setString(20, t.customField3);
		s.setString(21, t.beneficiary);
		s.setLong(22, t.effectiveDate.getTime());

		s.setInt(23, t.transactionId);

		int rows = s.executeUpdate();
		if (rows == 1)
			result = true;
		s.close();

		return result;

	}

	@Override
	public boolean updateAll(List<ValueObject> values) throws Exception {
		throw new OperationNotSupportedException("Update all for transactions is not supported.");
	}

	@Override
	public boolean delete(int id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		throw new OperationNotSupportedException("Delete all for transactions is not supported.");
	}

}
