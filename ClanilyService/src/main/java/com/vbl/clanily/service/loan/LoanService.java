package com.vbl.clanily.service.loan;

import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.loan.LoanDBTranslator;
import com.vbl.clanily.backend.connection.sqllite.settings.PayeeDBTranslator;
import com.vbl.clanily.backend.connection.sqllite.transaction.TransactionDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.loan.Loan;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Payee;
import com.vbl.clanily.backend.vo.transaction.Transaction;
import com.vbl.clanily.service.ClanilyService;

public class LoanService extends ClanilyService {

	private static final LoanService thisInstance = new LoanService();

	public static LoanService getInstance() {
		return thisInstance;
	}

	private LoanService() {

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public SearchResult search(SearchCriteria search) throws Exception {
		return LoanDBTranslator.getInstance().search(search);

		// return (SearchResult<ValueObject>) loans;
	}

	@Override
	public ValueObject getById(int id) throws Exception {
		Loan loan = LoanDBTranslator.getInstance().getById(id);

		return loan;
	}

	@Override
	public ValueObject getByUniqueName(String name) throws Exception {
		return LoanDBTranslator.getInstance().getByUniqueName(name);
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		if (value == null)
			throw new Exception("Input object cannot be null");
		Loan loan = (Loan) value;
		if (loan.loanSummary == null) {
			throw new Exception("Loan summary is mandatory");
		}
		if (loan.loanSummary.length() < 5 || loan.loanSummary.length() > 50) {
			throw new Exception("Loan summary can be between 5 to 50 chars");
		}
		if (!loan.noEndDate && loan.endDate == 0)
			throw new Exception("End date is mandatory");

		if (loan.startDate == 0) {
			throw new Exception("Start date is mandatory");
		}

		if (loan.endDate < loan.startDate) {
			throw new Exception("End date cannot be less than start date");
		}
		if (loan.interestPerAnum < 0) {
			throw new Exception("Loan interest cannot be less than 0");
		}

		List<Integer> sourceTransactionIds = loan.sourceTransactionIds;
		if (sourceTransactionIds == null)
			throw new Exception("Source transaction cannot be empty. Atleast one transaction should be selected");

		Transaction t = null;
		String type = "";
		long startDate = 0;
		float loanAmount = 0.0f;
		for (Integer transactionId : sourceTransactionIds) {
			t = TransactionDBTranslator.getInstance().getById(transactionId);
			if (t == null)
				throw new Exception("Unable to find the given transaction: " + transactionId);

			if (t.splitParentId > 0)
				throw new Exception("A split child cannot be a root transaction for loan :" + transactionId);

			if (t.mergeParentId > 0)
				throw new Exception("A merge child cannot be a root transaction for loan :" + transactionId);

			if (!"Income".equals(t.transactionType) || !"Expense".equals(t.transactionType))
				throw new Exception("Loan transaction shall be either Income or Expense");

			if ("".equals(type))
				type = t.transactionType;
			if (!type.equals(t.transactionType))
				throw new Exception("All transactions should be of the same type");

			if (!t.cleared)
				throw new Exception("Un cleared transactions cannot be part of a loan: " + t.summary);

		}

		if (loan.endDate < startDate)
			throw new Exception("Start date cannot be later than end date");

		if (loan.payeeId == 0) {
			throw new Exception("Payee is mandatory");
		}

		Payee p = PayeeDBTranslator.getInstance().getById(loan.payeeId);
		if (p == null)
			throw new Exception("Invalid payee details. Please choose a valid payee");

		loanAmount += t.transactionAmount;

		if (startDate < t.transactionDate.getTime())
			startDate = t.transactionDate.getTime();

		if ("Expense".equals(type))
			loan.loanType = "Lend";
		else
			loan.loanType = "Borrow";

		loan.amount = loanAmount;
		loan.startDate = startDate;

		int loanId = LoanDBTranslator.getInstance().insert(loan);

		for (Integer sourceId : loan.sourceTransactionIds) {
			t = TransactionDBTranslator.getInstance().getById(sourceId);
			t.loanId = loanId;
			TransactionDBTranslator.getInstance().update(t);
		}
		return loanId;

	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		return LoanDBTranslator.getInstance().insertAll(values);
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		return LoanDBTranslator.getInstance().update(value);
	}

	@Override
	public boolean updateAll(List<ValueObject> values) throws Exception {
		return LoanDBTranslator.getInstance().updateAll(values);
	}

	@Override
	public boolean delete(int id) throws Exception {
		return LoanDBTranslator.getInstance().delete(id);
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		return LoanDBTranslator.getInstance().deleteAll(ids);
	}

	public SearchResult<Loan> getAllBorrowedLoans() throws Exception {
		return LoanDBTranslator.getInstance().getAllBorrowedLoans();
	}

	public SearchResult<Loan> getAllLentLoans() throws Exception {
		return LoanDBTranslator.getInstance().getAllLentLoans();
	}

	public boolean cancelLoan(int loanId) throws Exception {
		return LoanDBTranslator.getInstance().cancelLoan(loanId);
	}

}
