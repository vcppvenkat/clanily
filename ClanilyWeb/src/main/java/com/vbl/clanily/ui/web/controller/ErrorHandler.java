

package com.vbl.clanily.ui.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

import com.vbl.clanily.ui.web.ClanilyLogger;

@Controller
public class ErrorHandler {

	@GetMapping("/error2")
	@ExceptionHandler({Exception.class})
    public String handleException(Exception ex) {
		ClanilyLogger.LogMessage(getClass(), ex);
		
       return "error";
    }
}
