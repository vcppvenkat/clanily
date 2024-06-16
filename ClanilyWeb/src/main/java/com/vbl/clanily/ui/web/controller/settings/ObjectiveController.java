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
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Objective;
import com.vbl.clanily.service.settings.ObjectiveService;
import com.vbl.clanily.ui.web.ClanilyLogger;
import com.vbl.clanily.ui.web.ControllerAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/objective")
public class ObjectiveController implements ControllerAttributes {

	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	private SearchResult searchObjectives(SearchCriteria search) throws Exception {
		return ObjectiveService.getInstance().search(search);
	}

	@GetMapping("/")
	public ModelAndView loadSummaryPage(HttpSession session, RedirectAttributes rad, ModelAndView mav)
			throws Exception {

		mav.setViewName("/settings/objectives");

		try {
			SearchResult result = searchObjectives(new SearchCriteria());
			if (result == null || result.values() == null || result.values().isEmpty()) {
				throw new Exception("No objectives found");
			}

			mav.addObject("values", result.values());
		} catch (Exception e) {
			ClanilyLogger.LogMessage(getClass(), e);
			mav.addObject("errorMessage", e.getMessage());
		} finally {
			session.setAttribute(THIS_PAGE, "settings");
		}

		return mav;
	}

	@GetMapping("/loadNewObjectivePage")
	public ModelAndView loadNewObjectivePage(HttpSession session, ModelAndView mav, RedirectAttributes rad) {
		mav.setViewName("/settings/newObjective");

		try {
			Objective obj = new Objective();
			mav.addObject("obj", obj);
		} catch (Exception e) {
			rad.addFlashAttribute("errorMessage", e.getMessage());
			mav.setViewName("redirect:/objective/");
			ClanilyLogger.LogMessage(getClass(), e);
		}

		return mav;
	}

	@PostMapping("/addNewObjective")
	public ModelAndView addNewObjective(Objective obj, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {

			ObjectiveService.getInstance().insert(obj);

			rad.addFlashAttribute("successMessage", "Objective added successfully");
			mav.setViewName("redirect:/objective/");
		} catch (Exception e) {
			mav.setViewName("redirect:/objective/loadNewObjectivePage");
			rad.addFlashAttribute("obj", obj);
			rad.addFlashAttribute("errorMessage", e.getMessage());

			ClanilyLogger.LogMessage(getClass(), e);
		} finally {

		}

		return mav;
	}

	@GetMapping("/loadEditObjectivePage")
	public ModelAndView loadEditObjectivePage(int objectiveId, HttpSession session, ModelAndView mav) {

		Objective obj;
		try {
			obj = (Objective) ObjectiveService.getInstance().getById(objectiveId);
			mav.setViewName("/settings/editObjective");
			mav.addObject("obj", obj);

		} catch (Exception e) {
			mav.setViewName("redirect:/objective/");
			mav.addObject("errorMessage", e.getMessage());

			ClanilyLogger.LogMessage(getClass(), e);

		}

		return mav;
	}

	@GetMapping("/viewObjective")
	public ModelAndView viewObjective(int objectiveId, HttpSession session, ModelAndView mav, RedirectAttributes rad) {
		try {
			Objective obj = (Objective) ObjectiveService.getInstance().getById(objectiveId);
			if (obj == null) {
				throw new Exception("Objective not found to view or change");
			}
			mav.setViewName("/settings/viewObjective");
		} catch (Exception e) {
			rad.addFlashAttribute("errorMessage", e.getMessage());
			mav.setViewName("redirect:/objective/");

			ClanilyLogger.LogMessage(getClass(), e);
		}

		return mav;
	}

	@PostMapping("/editObjective")
	public ModelAndView editObjective(Objective obj, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
			ObjectiveService.getInstance().update(obj);
			rad.addFlashAttribute("successMessage", "Objective details updated successfully");
			mav.setViewName("redirect:/objective/");
		} catch (Exception e) {
			mav.setViewName("/settings/editObjective");
			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute(obj);

			ClanilyLogger.LogMessage(getClass(), e);
		} finally {

		}

		return mav;
	}

}
