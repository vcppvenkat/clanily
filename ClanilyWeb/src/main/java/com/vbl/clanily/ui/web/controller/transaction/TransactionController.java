package com.vbl.clanily.ui.web.controller.transaction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.account.Account;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.AccountSearchCriteria;
import com.vbl.clanily.backend.vo.search.ObjectiveSearchCriteria;
import com.vbl.clanily.backend.vo.search.PayeeSearchCriteria;
import com.vbl.clanily.backend.vo.search.TransactionSearchCriteria;
import com.vbl.clanily.backend.vo.settings.Category;
import com.vbl.clanily.backend.vo.settings.Objective;
import com.vbl.clanily.backend.vo.settings.Payee;
import com.vbl.clanily.backend.vo.settings.User;
import com.vbl.clanily.backend.vo.transaction.Transaction;
import com.vbl.clanily.service.account.AccountService;
import com.vbl.clanily.service.settings.CategoryService;
import com.vbl.clanily.service.settings.ObjectiveService;
import com.vbl.clanily.service.settings.PayeeService;
import com.vbl.clanily.service.transaction.TransactionService;
import com.vbl.clanily.service.user.UserService;
import com.vbl.clanily.ui.web.ClanilyLogger;
import com.vbl.clanily.ui.web.ControllerAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/transactions")
public class TransactionController implements ControllerAttributes {

	private static final String SEARCH_CRITERIA = "transactionSearchCriteria";

	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	@GetMapping("/")
	public ModelAndView loadTransactionSummaryPage(HttpSession session, RedirectAttributes rad, ModelAndView mav)
			throws Exception {
		mav.setViewName("/transactions/transactionSummary");
		TransactionSearchCriteria search = (TransactionSearchCriteria) getSearchCriteria(session);

		try {
			List<AccountDisplayUnit> accountDisplayUnits = extractAccountsAndGroups(search);
			mav.addObject("units", accountDisplayUnits);
			SearchResult<Transaction> result = getTransactions(search);
			mav.addObject("transactions", result.values());

		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
		} finally {

			session.setAttribute(SEARCH_CRITERIA, search);
			session.setAttribute(THIS_PAGE, "transaction");
			mav.addObject("search", search);

		}

		return mav;
	}

	@GetMapping("/loadNewTransactionPage")
	public ModelAndView loadNewTransactionPage(Transaction transaction, HttpSession session, RedirectAttributes rad,
			ModelAndView mav) {
		mav.setViewName("/transactions/newTransaction");

		try {
			if (!transaction.isAlreadyInitialized) {
				System.out.println("its not null");

				transaction.isAlreadyInitialized = true;
				transaction.transactionType = "Expense";
				transaction.transactionAmountString = "0.00";
				transaction.cleared = true;
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				transaction.transactionDateString = dateFormat.format(new Date());

			} else {

			}

			mav.addObject("transaction", transaction);

			// load categories
			SearchResult<Category> categorySearchResult = CategoryService.getInstance().getAllExpenseCategories();
			mav.addObject("expenseCategories", categorySearchResult.values());

			categorySearchResult = CategoryService.getInstance().getAllIncomeCategories();
			mav.addObject("incomeCategories", categorySearchResult.values());

			categorySearchResult = CategoryService.getInstance().getAllTransferCategories();
			mav.addObject("transferCategories", categorySearchResult.values());

			// load accounts
			SearchResult<Account> accounts = AccountService.getInstance().search(new AccountSearchCriteria());
			mav.addObject("accounts", accounts.values());

			// load users
			SearchResult<User> users = UserService.getInstance().search(null);
			mav.addObject("users", users.values());

			// load objective
			ObjectiveSearchCriteria objectiveSearchCriteria = new ObjectiveSearchCriteria();
			objectiveSearchCriteria.includeInternal = true;
			SearchResult<Objective> objectiveSearchResult = ObjectiveService.getInstance()
					.search(objectiveSearchCriteria);
			mav.addObject("objectives", objectiveSearchResult.values());

			// load payee
			PayeeSearchCriteria payeeSearchCriteria = new PayeeSearchCriteria();
			payeeSearchCriteria.includeInternal = true;
			SearchResult<Payee> payeeSearchResult = PayeeService.getInstance().search(payeeSearchCriteria);
			mav.addObject("payees", payeeSearchResult.values());

		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
		}
		return mav;
	}

	@PostMapping("/addNewTransaction")
	public ModelAndView addNewTransaction(Transaction transaction, HttpSession session, RedirectAttributes rad,
			ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		
		try {
			transaction.transactionAmount = Float.parseFloat(transaction.transactionAmountString);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			transaction.transactionDate = dateFormat.parse(transaction.transactionDateString);
			TransactionService.getInstance().insert(transaction);
		} catch (Exception e) {

			rad.addFlashAttribute("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
			mav.setViewName("redirect:/transactions/loadNewTransactionPage");
		} finally {
			rad.addFlashAttribute("transaction", transaction);
		}

		return mav;
	}
	@GetMapping("/viewTransaction")
	public ModelAndView viewTransaction(int transactionId, HttpSession session, RedirectAttributes rad,
			ModelAndView mav) {
		mav.setViewName("/transactions/viewTransaction");
		Transaction t = null;
		try {
			t = TransactionService.getInstance().getById(transactionId);
			
		} catch (Exception e) {

			rad.addFlashAttribute("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
			mav.setViewName("redirect:/transactions/");
		} finally {
			mav.addObject("transaction", t);
		}

		return mav;
	}

	@GetMapping("/resetAllFilters")
	public ModelAndView resetAllFilters(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionView = "Income, Expense";
		search.groupBy = "Date";
		search.includePending = true;

		return mav;
	}

	@GetMapping("/filter/includePending")
	public ModelAndView filterIncludePending(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.includePending = !search.includePending;
		return mav;
	}

	@GetMapping("/searchTransactions")
	public ModelAndView searchTransactions(TransactionSearchCriteria searchInput, HttpSession session,
			RedirectAttributes rad, ModelAndView mav) {
		
		
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		if (searchInput != null && !searchInput.summary.isEmpty()) {
			search.summary = searchInput.summary;
		} else {
			search.summary = "";
		}
		return mav;
	}

	@GetMapping("/group/category")
	public ModelAndView groupByCategory(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionGroup = "Category";
		search.asc = true;
		return mav;
	}

	@GetMapping("/group/payee")
	public ModelAndView groupByPayee(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionGroup = "Payee";
		search.asc = true;
		return mav;
	}

	@GetMapping("/group/loan")
	public ModelAndView groupByLoan(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionGroup = "Loan";
		search.asc = true;
		return mav;
	}

	@GetMapping("/group/date")
	public ModelAndView groupByDate(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionGroup = "Date";
		search.asc = true;
		return mav;
	}

	@GetMapping("/group/noGroup")
	public ModelAndView groupByNone(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionGroup = "None";
		search.asc = false;
		return mav;
	}

	@GetMapping("/filter/view/income")
	public ModelAndView filterViewIncome(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionView = "Income";
		return mav;
	}

	@GetMapping("/filter/view/expense")
	public ModelAndView filterViewExpense(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionView = "Expense";
		return mav;
	}

	@GetMapping("/filter/view/incomeAndExpense")
	public ModelAndView filterViewIncomeAndExpense(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionView = "Income, Expense";
		return mav;
	}

	@GetMapping("/filter/view/transfer")
	public ModelAndView filterViewTransfer(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentTransactionView = "Transfer";
		return mav;
	}

	@GetMapping("/filter/account/allAccounts")
	public ModelAndView filterAllAccounts(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.allSelected = true;
		search.selectedAccount = 0;
		search.selectedGroup = null;
		return mav;
	}

	@GetMapping("/filter/account/accountGroup")
	public ModelAndView filterAccountGroup(String accountGroup, HttpSession session, RedirectAttributes rad,
			ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.allSelected = false;
		search.selectedAccount = 0;
		search.selectedGroup = accountGroup;
		return mav;
	}

	@GetMapping("/filter/account")
	public ModelAndView filterAccountGroup(int accountId, HttpSession session, RedirectAttributes rad,
			ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.allSelected = false;
		search.selectedAccount = accountId;
		search.selectedGroup = null;
		return mav;
	}

	@GetMapping("/filter/date")
	public ModelAndView filterByDate(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentDateRange = "Date";
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);

		search.fromDate = c.getTime();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		search.toDate = c.getTime();
		return mav;
	}

	@GetMapping("/filter/month")
	public ModelAndView filterByMonth(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentDateRange = "Month";

		Calendar c = Calendar.getInstance();

		c.set(Calendar.DATE, 1);

		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		search.fromDate = c.getTime();

		c = Calendar.getInstance();

		c.set(Calendar.HOUR, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DATE, -1);

		search.toDate = c.getTime();

		return mav;
	}

	@GetMapping("/filter/week")
	public ModelAndView filterByWeek(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentDateRange = "Week";
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
			c.add(Calendar.DATE, -1);
		}

		search.fromDate = c.getTime();

		c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);

		while (c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			c.add(Calendar.DATE, 1);
		}
		c.add(Calendar.DATE, -1);

		search.toDate = c.getTime();
		return mav;
	}

	@GetMapping("/filter/year")
	public ModelAndView filterByYear(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentDateRange = "Year";
		Calendar c = Calendar.getInstance();

		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		search.fromDate = c.getTime();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);

		c.set(Calendar.MONTH, Calendar.DECEMBER);
		c.set(Calendar.DATE, 31);

		search.toDate = c.getTime();
		return mav;
	}

	@GetMapping("/filter/resetCalendarFilter")
	public ModelAndView resetCalendarFilter(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");

		TransactionSearchCriteria search = getSearchCriteria(session);
		search.currentDateRange = "Month";
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		search.fromDate = c.getTime();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		c.set(Calendar.DATE, c.getActualMaximum(Calendar.DAY_OF_MONTH));

		search.toDate = c.getTime();
		return mav;
	}

	@GetMapping("/filter/calendar/previous")
	public ModelAndView previousCalendar(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		switch (search.currentDateRange) {
		case "Date": {
			Calendar c = Calendar.getInstance(); // this takes current date

			c.setTimeInMillis(search.fromDate.getTime());
			c.add(Calendar.DATE, -1);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			search.toDate = c.getTime();
			break;
		}
		case "Week": {

			Calendar c = Calendar.getInstance();
			c.setTime(search.fromDate);
			c.add(Calendar.DATE, -1);
			while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				c.add(Calendar.DATE, -1);
			}
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);

			c.add(Calendar.DATE, 6);

			search.toDate = c.getTime();

			break;
		}
		case "Month": {

			Calendar c = Calendar.getInstance();
			c.setTime(search.fromDate);

			c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
			c.set(Calendar.DATE, 1);

			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);

			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.DATE, -1);

			search.toDate = c.getTime();

			break;
		}
		case "Year": {

			Calendar c = Calendar.getInstance();
			c.setTime(search.fromDate);
			c.add(Calendar.YEAR, -1);
			c.set(Calendar.MONTH, Calendar.JANUARY);
			c.set(Calendar.DATE, 1);

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);

			c.set(Calendar.DATE, 31);
			c.set(Calendar.MONTH, Calendar.DECEMBER);

			search.toDate = c.getTime();

			break;
		}
		default: {
			Calendar c = Calendar.getInstance();
			c.setTime(search.fromDate);

			c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);

			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);

			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.DATE, -1);

			search.toDate = c.getTime();

			break;
		}
		}
		return mav;
	}

	@GetMapping("/filter/calendar/next")
	public ModelAndView nextCalendar(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("redirect:/transactions/");
		TransactionSearchCriteria search = getSearchCriteria(session);
		switch (search.currentDateRange) {
		case "Month" -> {
			Calendar c = Calendar.getInstance();
			c.setTime(search.fromDate);

			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 1);

			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.DATE, -1);

			search.toDate = c.getTime();
			break;

		}
		case "Date" -> {
			Calendar c = Calendar.getInstance(); // this takes current date

			c.setTime(search.fromDate);

			c.add(Calendar.DATE, 1);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			search.toDate = c.getTime();
			break;

		}
		case "Week" -> {
			Calendar c = Calendar.getInstance();
			c.setTime(search.fromDate);
			c.add(Calendar.DATE, 1);
			while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				c.add(Calendar.DATE, 1);
			}
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			c.add(Calendar.DATE, 6);

			search.toDate = c.getTime();
			break;
		}
		case "Year" -> {
			Calendar c = Calendar.getInstance();
			c.setTime(search.fromDate);
			c.add(Calendar.YEAR, 1);
			c.set(Calendar.MONTH, Calendar.JANUARY);
			c.set(Calendar.DATE, 1);
			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);

			c.set(Calendar.DATE, 31);
			c.set(Calendar.MONTH, Calendar.DECEMBER);

			search.toDate = c.getTime();
			break;
		}
		default -> {
			Calendar c = Calendar.getInstance();
			c.setTime(search.fromDate);

			c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);

			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.DATE, -1);

			search.toDate = c.getTime();
			break;
		}
		}
		return mav;
	}

	private List<AccountDisplayUnit> extractAccountsAndGroups(TransactionSearchCriteria search) throws Exception {
		AccountDisplayUnit unit = null;
		List<AccountDisplayUnit> accountDisplayUnits = new ArrayList<AccountDisplayUnit>();
		List<String> accountGroups = AccountService.getInstance().getUniqueAccountGroups();
		SearchResult<Account> result = AccountService.getInstance().search(new AccountSearchCriteria());
		if (result != null && result.values() != null && result.values().size() != 0) {
			unit = new AccountDisplayUnit(0, "All Accounts", 0, false);
			if (search.isNothingSelected() || search.allSelected) {
				unit.selected = true;
			}
			unit.account = true;
			unit.controllerMethod = "/transactions/filter/account/allAccounts";
			accountDisplayUnits.add(unit);
			for (String group : accountGroups) {
				unit = new AccountDisplayUnit(0, group, 0, false);
				unit.group = true;
				unit.controllerMethod = "/transactions/filter/account/accountGroup?accountGroup=" + group;
				if (search.selectedGroup != null && search.selectedGroup.equals(group)) {
					unit.selected = true;
				}
				accountDisplayUnits.add(unit);
				for (ValueObject obj : result.values()) {
					Account a = (Account) obj;
					if (a.accountGroup.equals(group)) {
						unit = new AccountDisplayUnit(a.accountId, a.accountName, a.currentBalance, false);
						unit.account = true;
						unit.controllerMethod = "/transactions/filter/account?accountId=" + a.accountId;
						if (search.selectedAccount == a.accountId) {
							unit.selected = true;
						}
						accountDisplayUnits.add(unit);
					}
				}
			}
		} else {
			throw new Exception("No accounts configured. Please add accounts to proceed");
		}
		return accountDisplayUnits;
	}

	private SearchResult<Transaction> getTransactions(TransactionSearchCriteria search) throws Exception {

		// Set transaction type
		search.clearTransactionTypes();
		switch (search.currentTransactionView) {
		case "Income, Expense" -> {
			search.addTransactionType("Income");
			search.addTransactionType("Expense");
		}
		case "Income" -> {
			search.addTransactionType("Income");
		}
		case "Expense" -> {
			search.addTransactionType("Expense");
		}
		case "Transfers" -> {
			search.addTransactionType("Incoming Transfer");
			search.addTransactionType("Outgoing Transfer");
		}
		default -> {
			search.addTransactionType("Income");
			search.addTransactionType("Expense");
		}
		}

		SearchResult<Transaction> result = TransactionService.getInstance().search(search);
		for (Transaction t : result.values()) {
			if (!"None".equals(search.currentTransactionGroup)) {

				switch (search.currentTransactionGroup) {
				case "Date": {
					t.displayGroupValue = t.getTransactionDateString();
					break;

				}
				case "Category": {
					t.displayGroupValue = t.categoryName;
					break;
				}
				case "Loan": {
					t.displayGroupValue = t.loanName;
					break;
				}
				case "Project": {
					t.displayGroupValue = t.projectName;
					break;
				}
				case "Objective": {
					t.displayGroupValue = t.objectiveName;
					break;
				}
				case "Beneficiary": {
					t.displayGroupValue = t.beneficiary;
					break;
				}
				case "Payee": {
					t.displayGroupValue = t.payeeName;
					break;
				}

				}

			}
		}

		return result;
	}

	private TransactionSearchCriteria getSearchCriteria(HttpSession session) {
		if (session.getAttribute(SEARCH_CRITERIA) == null) {
			TransactionSearchCriteria search = new TransactionSearchCriteria();
			Date today = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(today);

			c.set(Calendar.DATE, 1);

			c.set(Calendar.HOUR, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			search.fromDate = c.getTime();

			c = Calendar.getInstance();
			c.setTime(today);

			c.set(Calendar.HOUR, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			c.set(Calendar.MILLISECOND, 999);
			c.add(Calendar.MONTH, 1);
			c.set(Calendar.DAY_OF_MONTH, 1);
			c.add(Calendar.DATE, -1);

			search.toDate = c.getTime();
			return search;
		} else {
			return (TransactionSearchCriteria) session.getAttribute(SEARCH_CRITERIA);
		}

	}
}
