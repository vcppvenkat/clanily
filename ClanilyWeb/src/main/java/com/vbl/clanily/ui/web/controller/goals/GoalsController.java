package com.vbl.clanily.ui.web.controller.goals;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vbl.clanily.ui.web.ControllerAttributes;

@Controller
@RequestMapping("/goals")
public class GoalsController implements ControllerAttributes {
	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	@GetMapping("/")
	public String loadBudgetMainPage(ModelMap model) {
		model.addAttribute(THIS_PAGE, "budget");
		return "/goals/goals";
	}
}
