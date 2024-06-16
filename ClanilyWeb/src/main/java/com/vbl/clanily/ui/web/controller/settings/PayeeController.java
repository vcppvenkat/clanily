package com.vbl.clanily.ui.web.controller.settings;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.PayeeSearchCriteria;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Payee;
import com.vbl.clanily.service.settings.PayeeService;
import com.vbl.clanily.ui.web.ClanilyLogger;
import com.vbl.clanily.ui.web.ControllerAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/payee")
public class PayeeController implements ControllerAttributes {

	private static final String SEARCH_CRITERIA = "payeeSearchCriteria";

	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	private PayeeSearchCriteria getSearchCriteria(HttpSession session) {
		if (session.getAttribute(SEARCH_CRITERIA) == null) {
			return new PayeeSearchCriteria();
		} else {
			return (PayeeSearchCriteria) session.getAttribute(SEARCH_CRITERIA);
		}

	}

	private SearchResult searchPayees(SearchCriteria search) throws Exception {
		return PayeeService.getInstance().search(search);
	}

	@GetMapping("/")
	public ModelAndView loadSummaryPage(HttpSession session, RedirectAttributes rad, ModelAndView mav)
			throws Exception {

		mav.setViewName("/settings/payees");
		PayeeSearchCriteria search = (PayeeSearchCriteria) getSearchCriteria(session);

		try {
			SearchResult result = searchPayees(search);
			if (result == null || result.values() == null || result.values().isEmpty()) {
				throw new Exception("No Payees found");
			}
			
			mav.addObject("values", result.values());
		} catch (Exception e) {
			
			mav.addObject("errorMessage", e.getMessage());
			
			ClanilyLogger.LogMessage(getClass(), e);
		} finally {

			session.setAttribute(SEARCH_CRITERIA, search);
			session.setAttribute(THIS_PAGE, "settings");

		}

		return mav;
	}
	
	@GetMapping("/loadNewPayeePage")
	public ModelAndView loadNewCategoryPage(HttpSession session, ModelAndView mav, RedirectAttributes rad) {
		mav.setViewName("/settings/newPayee");

		try {
			Payee payee = new Payee();


			mav.addObject("payee", payee);
		} catch (Exception e) {
			rad.addFlashAttribute("errorMessage", e.getMessage());
			mav.setViewName("redirect:/payee/");
			
			ClanilyLogger.LogMessage(getClass(), e);
		}

		return mav;
	}
	
	@PostMapping("/addNewPayee")
	public ModelAndView addNewObjective(Payee payee, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {

			
			PayeeService.getInstance().insert(payee);

			rad.addFlashAttribute("successMessage", "Payee added successfully");
			mav.setViewName("redirect:/payee/");
		} catch (Exception e) {
			mav.setViewName("redirect:/payee/loadNewPayeePage");
			rad.addFlashAttribute("payee", payee);
			rad.addFlashAttribute("errorMessage", e.getMessage());
			
			ClanilyLogger.LogMessage(getClass(), e);
		} finally {

		}

		return mav;
	}
	
	@GetMapping("/loadEditPayeePage")
	public ModelAndView loadEditPayeePage(int payeeId, HttpSession session, ModelAndView mav) {

		Payee payee;
		try {
			payee = (Payee) PayeeService.getInstance().getById(payeeId);
			mav.setViewName("/settings/editPayee");
			mav.addObject("payee", payee);
			
		} catch (Exception e) {
			mav.setViewName("redirect:/payee/");
			mav.addObject("errorMessage", e.getMessage());
			
			ClanilyLogger.LogMessage(getClass(), e);
		}

		return mav;
	}
	
	@GetMapping("/viewPayee")
	public ModelAndView viewPayee(int payeeId, HttpSession session, ModelAndView mav, RedirectAttributes rad) {
		try {
			Payee payee = (Payee) PayeeService.getInstance().getById(payeeId);
			if(payee == null) {
				throw new Exception("Payee not found to view or change");
			}
			mav.setViewName("/settings/viewPayee");
		} catch (Exception e) {
			rad.addFlashAttribute("errorMessage", e.getMessage());
			mav.setViewName("redirect:/payee/");
			

			ClanilyLogger.LogMessage(getClass(), e);
		}
		
		return mav;
	}

	@PostMapping("/editPayee")
	public ModelAndView editCategory(Payee payee, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		System.out.println(payee.description);
		try {
			PayeeService.getInstance().update(payee);
			rad.addFlashAttribute("successMessage", "Payee updated successfully");
			mav.setViewName("redirect:/payee/");
		} catch (Exception e) {
			mav.setViewName("/settings/editPayee");
			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute(payee);
			
			ClanilyLogger.LogMessage(getClass(), e);
		} finally {

		}

		return mav;
	}

}
