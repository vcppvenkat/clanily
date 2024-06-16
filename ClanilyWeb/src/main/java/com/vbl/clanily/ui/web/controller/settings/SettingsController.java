package com.vbl.clanily.ui.web.controller.settings;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.vbl.clanily.ui.web.ControllerAttributes;

@Controller
@RequestMapping("/settings")
public class SettingsController implements ControllerAttributes{

	@InitBinder
    public void initBinder(WebDataBinder binder) {
        
    }

	@GetMapping("/")
	public String loadSettingsMainPage(ModelMap model) {
		model.addAttribute(THIS_PAGE, "settings");
		return "/settings/settings"; 
	}
}
