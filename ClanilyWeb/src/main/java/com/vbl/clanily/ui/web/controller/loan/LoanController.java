package com.vbl.clanily.ui.web.controller.loan;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vbl.clanily.backend.vo.loan.Loan;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.LoanSearchCriteria;
import com.vbl.clanily.backend.vo.search.PayeeSearchCriteria;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Beneficiary;
import com.vbl.clanily.service.loan.LoanService;
import com.vbl.clanily.service.settings.BeneficiaryService;
import com.vbl.clanily.service.settings.PayeeService;
import com.vbl.clanily.ui.web.ControllerAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/loans")
public class LoanController implements ControllerAttributes {
	
	private static final String SEARCH_CRITERIA = "loansSearchCriteria";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	@GetMapping("/")
	public ModelAndView loadLoanSummaryPage(HttpSession session, RedirectAttributes rad) throws Exception {
		ModelAndView mav = new ModelAndView("/loans/loans");
		LoanSearchCriteria search = getSearchCriteria(session);
		SearchResult<?> result = searchLoans(search);
		
		mav.addObject("values", result.values());
				
		
		//TODO remove this hardcoded values once we get data from service 
//		Loan loan1 = new Loan();
//		loan1.loanSummary = "MURUGAN HOUSE CONTRUCTION - 2019";
//		loan1.amount = 75000;
//		loan1.totalPaid = 69000;
//		
//		Loan loan2 = new Loan();
//		loan2.loanSummary = "LOAN FROM PARENTS - JAN 2024";
//		loan2.amount = 114000;
//		loan2.totalPaid = 34000;
//		mav.addObject("values", Arrays.asList(loan1, loan2, loan1, loan2 , loan1));
		
		
		session.setAttribute(SEARCH_CRITERIA, search);
		session.setAttribute(THIS_PAGE, "loan");
		return mav;
	}
	
	@GetMapping("/loadNewLoanPage")
	public ModelAndView loadNewLoanPage(HttpSession session, ModelAndView mav) {
		mav.setViewName("/loans/newLoan");
		
		try {
			Loan loan = new Loan();
			mav.addObject("loan", loan);
			
			SearchResult<?> payees =  this.getAllPayees();
			mav.addObject("payees", payees.values());
			
		} catch (Exception e) {
			mav.setViewName("redirect:/loans/");
			e.printStackTrace();
		}
		finally {
			session.setAttribute(THIS_PAGE, "loan");
		}
		return mav;
	}
	
	@PostMapping("/addNewLoan")
	public ModelAndView addNewLoan(Loan loan, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
			
			if (loan.getLoanSummary() == null || loan.getLoanSummary().isEmpty()) {
				throw new RuntimeException("Loan Summary should not be empty");
			}
			
			if (loan.getAmount() <= 0) {
				throw new RuntimeException("Amount should not be zero or less than zero");
			}
			
			if (loan.getLoanType() == null ||loan.getLoanType().isEmpty()) {
				throw new RuntimeException("Loan type need to be selected");
			}
			
			
			loan.setLoanStatus("");
			LoanService.getInstance().insert(loan);
			rad.addFlashAttribute("successMessage", "Loan added successfully");
			mav.setViewName("redirect:/loans/");
		} catch (Exception e) {
			mav.setViewName("redirect:/loans/loadNewLoanPage");
			rad.addFlashAttribute("errorMessage", e.getMessage());
			rad.addFlashAttribute("loanSummary", loan.getLoanSummary());
			rad.addFlashAttribute("loanType", loan.getLoanType());
			rad.addFlashAttribute("amount", loan.getAmount());
			rad.addFlashAttribute("description", loan.getDescription());
			e.printStackTrace();
		}
		finally {
			session.setAttribute(THIS_PAGE, "loan");
		}
		
		return mav;
	}
	
	@GetMapping("/viewLoanPage")
	public ModelAndView viewLoanPage(int loanId, HttpSession session, ModelAndView mav) {
		mav.setViewName("/loans/viewLoan");
		
		try {
			Loan loan = (Loan) LoanService.getInstance().getById(loanId);
			mav.addObject("loan", loan);
		} catch (Exception e) {
			mav.setViewName("redirect:/loans/");
			e.printStackTrace();
		}
		finally {
			session.setAttribute(THIS_PAGE, "loan");
		}
		return mav;
	}
	
	private LoanSearchCriteria getSearchCriteria(HttpSession session) {
		if (session.getAttribute(SEARCH_CRITERIA) == null) {
			return new LoanSearchCriteria();
		} else {
			return (LoanSearchCriteria) session.getAttribute(SEARCH_CRITERIA);
		}
	}
	
	private SearchResult<?> searchLoans(SearchCriteria search) throws Exception {
		return LoanService.getInstance().search(search);
	}
	
	private SearchResult<?> getAllPayees() throws Exception {
		return PayeeService.getInstance().search(new PayeeSearchCriteria());
	}
}
