package com.vbl.clanily.backend.connection.sqllite.loan;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vbl.clanily.backend.connection.ClanilyDBOperation;
import com.vbl.clanily.backend.connection.sqllite.AbstractSqlLiteOperationManager;
import com.vbl.clanily.backend.connection.sqllite.account.AccountDBTranslator;
import com.vbl.clanily.backend.connection.sqllite.transaction.TransactionDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.loan.Loan;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.LoanSearchCriteria;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.search.TransactionSearchCriteria;
import com.vbl.clanily.backend.vo.transaction.Transaction;

public class LoanDBTranslator extends AbstractSqlLiteOperationManager implements ClanilyDBOperation {

	private static final LoanDBTranslator thisInstance = new LoanDBTranslator();

	public static LoanDBTranslator getInstance() {
		return thisInstance;
	}

	private LoanDBTranslator() {

	}

	@Override
	public SearchResult<Loan> search(SearchCriteria searchCriteria) throws Exception {
		SearchResult<Loan> result = new SearchResult<Loan>();
		Loan l = null;
		LoanSearchCriteria search = (LoanSearchCriteria) searchCriteria;
		String query = "SELECT * FROM LOANS WHERE 1 = 1 ";
		if (!search.includeInternal) {
			query += " AND INTERNAL = '0' ";
		} else {
			query += " AND INTERNAL <> 0";
		}

		// Set category name
		if (isValid(search.searchText)) {
			query += " AND ( SUMMARY LIKE '%" + search.searchText + "%' ";
			query += " OR DESCRIPTION LIKE '%" + search.searchText + "%' ) ";
		}

		if (isValid(search.loanType)) {
			query += " AND LOAN_TYPE = '" + search.loanType + "' ";
		}

		if (isValid(search.loanStatus)) {
			query += " AND LOAN_STATUS = '" + search.loanStatus + "' ";
		}

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			l = copyLoan(null, rs);
			result.add(l);
		}
		rs.close();

		return result;
	}

	public SearchResult<Loan> getAllBorrowedLoans() throws Exception {
		SearchResult<Loan> result = new SearchResult<Loan>();
		Loan l = null;
		String query = "SELECT * FROM LOANS WHERE LOAN_TYPE = 'Borrow'";

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			l = copyLoan(null, rs);
			result.add(l);
		}
		rs.close();

		return result;
	}

	public SearchResult<Loan> getAllLentLoans() throws Exception {
		SearchResult<Loan> result = new SearchResult<Loan>();
		Loan l = null;
		String query = "SELECT * FROM LOANS WHERE LOAN_TYPE = 'Lend'";

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			l = copyLoan(null, rs);
			result.add(l);
		}
		rs.close();

		return result;
	}

	@Override
	public Loan getById(int id) throws Exception {
		Loan l = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM LOANS WHERE LOAN_ID = " + id;

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			l = copyLoan(null, rs);
			break;
		}
		rs.close();

		if (l != null) {
			List<Transaction> loanTransactions = getLoanTransactions(l.loanId);
			l.setLoanTransactions(loanTransactions);
		}

		return l;
	}

	private List<Transaction> getLoanTransactions(int loanId) throws Exception {
		List<Transaction> loanTransactions = new ArrayList<Transaction>();
		TransactionSearchCriteria search = new TransactionSearchCriteria();
		search.addLoanId(loanId);
		SearchResult<Transaction> result = TransactionDBTranslator.getInstance().search(search);
		loanTransactions = result.values();
		return loanTransactions;
	}

	@Override
	public Loan getByUniqueName(String name) throws Exception {
		Loan l = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM LOANS WHERE SUMMARY = '" + name + "' ";

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			l = copyLoan(null, rs);
			break;
		}
		rs.close();

		if (l != null) {
			List<Transaction> loanTransactions = getLoanTransactions(l.loanId);
			l.setLoanTransactions(loanTransactions);
		}

		return l;
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		int pk = -1;
		Loan loan = (Loan) value;
		String query = "INSERT INTO LOANS (SUMMARY, START_DATE, END_DATE, NO_END_DATE, LOAN_TYPE, AMOUNT, LOAN_STATUS, DESCRIPTION, PAYEE_ID, INTEREST_PER_ANUM, CREATED_TS, INTERNAL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement s = connection.prepareStatement(query);
		s.setString(1, loan.loanSummary);
		s.setLong(2, loan.startDate);
		s.setLong(3, loan.endDate);
		s.setBoolean(4, loan.noEndDate);
		s.setString(5, loan.loanType);
		s.setFloat(6, loan.amount);
		s.setString(7, loan.loanStatus);
		s.setString(8, loan.description);
		s.setInt(9, loan.payeeId);
		s.setFloat(10, loan.interestPerAnum);
		s.setLong(11, loan.createdTs);
		s.setBoolean(12, loan.internal);
		s.executeUpdate();

		query = "SELECT MAX(LOAN_ID) AS MAX_ID FROM LOANS ";
		s = connection.prepareStatement(query);
		ResultSet rs = s.executeQuery();
		while (rs.next()) {
			pk = rs.getInt("MAX_ID");
			break;
		}

		s.close();

		return pk;
	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		Loan loan = (Loan) value;
		String query = "UPDATE LOANS SET SUMMARY = ?, START_DATE = ?, END_DATE=?, NO_END_DATE=? , AMOUNT = ?, DESCRIPTION=?, PAYEE_ID=?, INTEREST_PER_ANUM=? WHERE LOAN_ID=?";
		boolean result = false;

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, loan.loanSummary);
		stmt.setLong(2, loan.startDate);
		stmt.setLong(3, loan.endDate);
		stmt.setBoolean(4, loan.noEndDate);
		stmt.setFloat(5, loan.amount);
		stmt.setString(6, loan.description);
		stmt.setInt(7, loan.payeeId);
		stmt.setFloat(8, loan.interestPerAnum);
		stmt.setInt(9, loan.loanId);
		int rows = stmt.executeUpdate();
		if (rows == 1)
			result = true;
		stmt.close();

		return result;
	}

	public boolean activateLoan(int loanId) throws Exception {

		String query = "UPDATE LOANS SET LOAN_STATUS=? WHERE LOAN_ID=?";
		boolean result = false;

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, "Active");
		stmt.setInt(2, loanId);
		int rows = stmt.executeUpdate();
		if (rows == 1)
			result = true;
		stmt.close();

		return result;
	}

	public boolean completeLoan(int loanId) throws Exception {

		String query = "UPDATE LOANS SET LOAN_STATUS=? WHERE LOAN_ID=?";
		boolean result = false;

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, "Completed");
		stmt.setInt(2, loanId);
		int rows = stmt.executeUpdate();
		if (rows == 1)
			result = true;
		stmt.close();

		return result;
	}

	public boolean overdueLoan(int loanId) throws Exception {

		String query = "UPDATE LOANS SET LOAN_STATUS=? WHERE LOAN_ID=?";
		boolean result = false;

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, "Overdue");
		stmt.setInt(2, loanId);
		int rows = stmt.executeUpdate();
		if (rows == 1)
			result = true;
		stmt.close();

		return result;
	}

	public boolean newLoan(int loanId) throws Exception {

		String query = "UPDATE LOANS SET LOAN_STATUS=? WHERE LOAN_ID=?";
		boolean result = false;

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, "New");
		stmt.setInt(2, loanId);
		int rows = stmt.executeUpdate();
		if (rows == 1)
			result = true;
		stmt.close();

		return result;
	}

	public boolean cancelLoan(int loanId) throws Exception {

		String query = "UPDATE LOANS SET LOAN_STATUS=? WHERE LOAN_ID=?";
		boolean result = false;

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, "Cancelled");
		stmt.setInt(2, loanId);
		int rows = stmt.executeUpdate();
		if (rows == 1)
			result = true;
		stmt.close();

		return result;
	}

	public float getTotalPaidByBorrowedLoan(int loanId) throws Exception {
		float sum = 0.0f;
		Statement st = connection.createStatement();
		String query = " SELECT SUM(ABS(TRANSACTION_AMOUNT)) AS SUM_OF_TRX FROM TRANSACTIONS WHERE TRANSACTION_TYPE = 'Expense' AND LOAN_ID = "
				+ loanId;
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			sum = rs.getFloat("SUM_OF_TRX");
			break;
		}

		rs.close();

		return sum;
	}

	public float getTotalPaidByLentLoan(int loanId) throws Exception {
		float sum = 0.0f;
		Statement st = connection.createStatement();
		String query = " SELECT SUM(ABS(TRANSACTION_AMOUNT) )AS SUM_OF_TRX FROM TRANSACTIONS WHERE TRANSACTION_TYPE = 'Income' AND LOAN_ID = "
				+ loanId;
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			sum = rs.getFloat("SUM_OF_TRX");
			break;
		}

		rs.close();

		return sum;
	}

	@Override
	public boolean updateAll(List<ValueObject> values) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
