package com.vbl.clanily.service.loan;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.vbl.clanily.backend.vo.loan.Loan;

// TODO remove Disabled annotation once Unit tests are ready
@Disabled
class LoanServiceTest {

	@Test
	void testGetById() {
		fail("Not yet implemented");
	}

	@Test
	void testGetByUniqueName() {
		fail("Not yet implemented");
	}

	@Test
	void testInsert() {
		Loan loan = new Loan();
		loan.amount = 0;
		int rows;
		try {
			rows = LoanService.getInstance().insert(loan);
			assertTrue(rows == 0);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		

	}

	@Test
	void testInsertAll() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateAll() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteAll() {
		fail("Not yet implemented");
	}

	@Test
	void testGetInstance() {
		fail("Not yet implemented");
	}

	@Test
	void testSearchSearchCriteria() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllBorrowedLoans() {
		fail("Not yet implemented");
	}

	@Test
	void testGetAllLentLoans() {
		fail("Not yet implemented");
	}

	@Test
	void testCancelLoan() {
		fail("Not yet implemented");
	}

}
