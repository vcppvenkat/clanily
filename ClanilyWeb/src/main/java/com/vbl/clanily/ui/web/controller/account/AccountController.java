package com.vbl.clanily.ui.web.controller.account;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vbl.clanily.backend.vo.account.Account;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.AccountSearchCriteria;
import com.vbl.clanily.backend.vo.settings.User;
import com.vbl.clanily.service.ClanilyService;
import com.vbl.clanily.service.account.AccountService;
import com.vbl.clanily.service.user.UserService;
import com.vbl.clanily.ui.web.ClanilyLogger;
import com.vbl.clanily.ui.web.ControllerAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/accounts")
public class AccountController implements ControllerAttributes {

	private static final String SEARCH_CRITERIA = "accountSearchCriteria";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}
	
	@GetMapping("/")
	public ModelAndView loadAccountsMainPage(HttpSession session, RedirectAttributes rad) throws Exception {
		ModelAndView mav = new ModelAndView("/account/accountSummary");
		AccountSearchCriteria search = (AccountSearchCriteria) getSearchCriteria(session);
		
		try {
			
			SearchResult<Account> result = AccountService.getInstance().search(new AccountSearchCriteria());
			if (result.values() == null || result.values().isEmpty()) {
				throw new Exception("No accounts found");
			} 
			
			mav.addObject("values", result.values());
			
			Account account = AccountService.getInstance().getById(search.getCurrentAccountId());
			if(account == null) {
				search.currentAccountId = ((Account)result.values().get(0)).accountId;
				account = AccountService.getInstance().getById(search.getCurrentAccountId());
			} 

			mav.addObject("account", account);
			
			// load all account groups
			List<String> accountGroups = ClanilyService.getPropertyValues("ACCOUNT_GROUPS");
			mav.addObject("accountGroups", accountGroups);
			
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
		} finally {

			session.setAttribute(SEARCH_CRITERIA, search);
			session.setAttribute(THIS_PAGE, "accounts");
			mav.addObject("search", search);

		}

		return mav;
	}
	
	@GetMapping("/changeAccountGroup")
	public ModelAndView changeAccountGroup(int accountId,String accountGroup, HttpSession session,ModelAndView mav, RedirectAttributes rad){
		mav.setViewName("redirect:/accounts/");
		try {
			AccountSearchCriteria search = getSearchCriteria(session);
			
			Account account = AccountService.getInstance().getById(accountId);
			if(account == null) {
				throw new Exception("No account information found to display the details");
			}
			search.setCurrentAccountId(accountId);
			
			account.accountGroup = accountGroup;
			AccountService.getInstance().update(account);
			rad.addFlashAttribute("successMessage", "Account group updated successfully");
			
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
		}
		return mav;
	}
	
	@GetMapping("/loadAccount")
	public ModelAndView loadAccount(int accountId, HttpSession session,ModelAndView mav, RedirectAttributes rad){
		mav.setViewName("redirect:/accounts/");
		try {
			AccountSearchCriteria search = getSearchCriteria(session);
			
			Account account = AccountService.getInstance().getById(accountId);
			if(account == null) {
				throw new Exception("No account information found to display the details");
			}
			search.setCurrentAccountId(accountId);
			
		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
		}
		return mav;
	}
	
	@GetMapping("/loadEditAccountPage")
	public ModelAndView loadEditAccountPage(int accountId, HttpSession session, RedirectAttributes rad, ModelAndView mav){
		
		mav.setViewName("/account/editAccount");

		try {
			Account account = AccountService.getInstance().getById(accountId);
			if(account == null)
				throw new Exception("No account information found to edit");
			
			mav.addObject("account", account);
			
			SearchResult<User> result = UserService.getInstance().search(null);
			mav.addObject("users", result.values());

			List<String> accountGroups = ClanilyService.getPropertyValues("ACCOUNT_GROUPS");
			
			mav.addObject("accountGroups", accountGroups);
			
		} catch (Exception e) {
			mav.setViewName("redirect:/accounts/");
			rad.addAttribute("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
		} finally {
			session.setAttribute(THIS_PAGE, "accounts");
		}

		return mav;
	}

	
	@PostMapping("/editAccount")
	public ModelAndView editAccount(Account account, HttpSession session, RedirectAttributes rad, ModelAndView mav){
		System.out.println(account.accountId);
		System.out.println(account.accountName);
		try {
			AccountService.getInstance().update(account);

			rad.addFlashAttribute("successMessage", "Account updated successfully");
			mav.setViewName("redirect:/accounts/");
		} catch (Exception e) {
			mav.setViewName("redirect:/accounts/loadEditAccountPage");
			rad.addFlashAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		} finally {

		}

		return mav;
	}
	
	@GetMapping("/loadNewAccountPage")
	public ModelAndView loadAddAccountPage(HttpSession session, RedirectAttributes rad, ModelAndView mav){
		
		mav.setViewName("/account/newAccount");

		try {
			Account account = new Account();
			account.accountVisible = true;
			mav.addObject("account", account);
			
			SearchResult<User> result = UserService.getInstance().search(null);
			mav.addObject("users", result.values());

			List<String> accountGroups = ClanilyService.getPropertyValues("ACCOUNT_GROUPS");
			mav.addObject("accountGroups", accountGroups);
			
			
		} catch (Exception e) {
			mav.setViewName("redirect:/accounts/");
			rad.addAttribute("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
		} finally {
			session.setAttribute(THIS_PAGE, "accounts");
		}

		return mav;
	}
	
	@PostMapping("/addAccount")
	public ModelAndView addAccount(Account account, HttpSession session, RedirectAttributes rad, ModelAndView mav){

		try {
			AccountService.getInstance().insert(account);

			rad.addFlashAttribute("successMessage", "Account added successfully");
			mav.setViewName("redirect:/accounts/");
		} catch (Exception e) {
			mav.setViewName("redirect:/accounts/loadNewAccountPage");
			rad.addFlashAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		} finally {

		}

		return mav;
	}
	
	@GetMapping("/hideAccount")
	public ModelAndView hideAccount(int accountId, HttpSession session, RedirectAttributes rad, ModelAndView mav){
		mav.setViewName("redirect:/accounts/");
		try {
			AccountService.getInstance().hideAccount(accountId);
			getSearchCriteria(session).currentAccountId = accountId;
			rad.addFlashAttribute("successMessage", "Account is now hidden");
		} catch(Exception e) {
			
			rad.addFlashAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	@GetMapping("/showAccount")
	public ModelAndView showAccount(int accountId, HttpSession session, RedirectAttributes rad, ModelAndView mav){
		mav.setViewName("redirect:/accounts/");
		try {
			AccountService.getInstance().showAccount(accountId);
			getSearchCriteria(session).currentAccountId = accountId;
			rad.addFlashAttribute("successMessage", "Account is now visible");
		} catch(Exception e) {
			
			rad.addFlashAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		return mav;
	}
	
	
	private AccountSearchCriteria getSearchCriteria(HttpSession session) {
		if (session.getAttribute(SEARCH_CRITERIA) == null) {
			return new AccountSearchCriteria();
		} else {
			return (AccountSearchCriteria) session.getAttribute(SEARCH_CRITERIA);
		}

	}
}
