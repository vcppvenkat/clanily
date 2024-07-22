package com.vbl.clanily.ui.web.controller.settings;

import java.util.Date;

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
import com.vbl.clanily.backend.vo.search.BeneficiarySearchCriteria;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Beneficiary;
import com.vbl.clanily.service.settings.BeneficiaryService;
import com.vbl.clanily.ui.web.ControllerAttributes;

import jakarta.servlet.http.HttpSession;

/**
 * @author Gopalakrishnan
 */
@Controller
@RequestMapping("/beneficiary")
public class BeneficiaryController implements ControllerAttributes{

	private static final String SEARCH_CRITERIA = "beneficiarySearchCriteria";

	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	@GetMapping("/")
	public ModelAndView loadSummaryPage(HttpSession session, RedirectAttributes rad) throws Exception {
		ModelAndView mav = new ModelAndView("/settings/beneficiary");
		
		BeneficiarySearchCriteria search = getSearchCriteria(session);
		SearchResult<?> result = searchBeneficiaries(search);
		mav.addObject("values", result.values());
		
		session.setAttribute(SEARCH_CRITERIA, search);
		session.setAttribute(THIS_PAGE, "settings");
		
		return mav;
	}
	
	@GetMapping("/loadNewBeneficiaryPage")
	public ModelAndView loadNewBeneficiaryPage(HttpSession session, ModelAndView mav) {
		mav.setViewName("/settings/newBeneficiary");
		
		try {
			Beneficiary b = new Beneficiary();
			mav.addObject("beneficiary", b);
		} catch (Exception e) {
			mav.setViewName("redirect:/beneficiary/");
			e.printStackTrace();
		}
		finally {
			session.setAttribute(THIS_PAGE, "settings");
		}
		return mav;
	}
	
	@PostMapping("/addNewBeneficiary")
	public ModelAndView addNewBeneficiary(Beneficiary b, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		try {
			BeneficiaryService.getInstance().insert(b);
			rad.addFlashAttribute("successMessage", "Beneficiary added successfully");
			mav.setViewName("redirect:/beneficiary/");
		} catch (Exception e) {
			mav.setViewName("redirect:/beneficiary/loadNewBeneficiaryPage");
			rad.addFlashAttribute("errorMessage", e.getMessage());
			rad.addFlashAttribute("beneficiaryName", b.getBeneficiaryName());
			e.printStackTrace();
		} finally {
			session.setAttribute(THIS_PAGE, "settings");
		}

		return mav;
	}
	
	@PostMapping("/deleteBeneficiary")
	public ModelAndView deleteBeneficiary(int beneficiaryId, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		
		try {
			BeneficiaryService.getInstance().delete(beneficiaryId);
			rad.addFlashAttribute("successMessage", "Beneficiary deleted successfully");
			mav.setViewName("redirect:/beneficiary/");
		} catch (Exception e) {
			mav.setViewName("/settings/beneficiary");
			model.addAttribute("errorMessage", e.getMessage());
		} finally {
			session.setAttribute(THIS_PAGE, "settings");
		}

		return mav;
	}
	
	private BeneficiarySearchCriteria getSearchCriteria(HttpSession session) {
		if (session.getAttribute(SEARCH_CRITERIA) == null) {
			return new BeneficiarySearchCriteria();
		} else {
			return (BeneficiarySearchCriteria) session.getAttribute(SEARCH_CRITERIA);
		}
	}
	
	private SearchResult<?> searchBeneficiaries(SearchCriteria search) throws Exception {
		return BeneficiaryService.getInstance().search(search);
	}

}
