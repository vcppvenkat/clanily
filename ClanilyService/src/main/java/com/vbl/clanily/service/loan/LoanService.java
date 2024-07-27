package com.vbl.clanily.service.loan;

import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.loan.LoanDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.loan.Loan;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.service.ClanilyService;

public class LoanService extends ClanilyService {

	private static final LoanService thisInstance = new LoanService();

	public static LoanService getInstance() {
		return thisInstance;
	}

	private LoanService() {

	}

	@Override
	public SearchResult<Loan> search(SearchCriteria search) throws Exception {
		return LoanDBTranslator.getInstance().search(search);
	}

	@Override
	public ValueObject getById(int id) throws Exception {
		Loan loan = LoanDBTranslator.getInstance().getById(id);
		if (loan != null) {
			loan.totalPaid = setTotalPaidByLoan(loan);
		}
		return loan;
	}

	@Override
	public ValueObject getByUniqueName(String name) throws Exception {
		return LoanDBTranslator.getInstance().getByUniqueName(name);
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		if(value == null)
			throw new Exception("Input object cannot be null");
		Loan loan = (Loan) value;
		
		
		return LoanDBTranslator.getInstance().insert(loan);
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

	private float setTotalPaidByLoan(Loan loan) throws Exception {
		float totalPaid = 0.0f;
		if ("Borrow".equals(loan.loanType)) {
			totalPaid = LoanDBTranslator.getInstance().getTotalPaidByBorrowedLoan(loan.loanId);
		} else if ("Lend".equals(loan.loanType)) {
			totalPaid = LoanDBTranslator.getInstance().getTotalPaidByLentLoan(loan.loanId);
		}
		return totalPaid;
	}
	
	
}
