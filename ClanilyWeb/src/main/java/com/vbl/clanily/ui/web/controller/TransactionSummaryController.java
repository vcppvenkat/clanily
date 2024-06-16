package com.vbl.clanily.ui.web.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionSummaryController {

	@GetMapping("/types")
	public List<String> getTransactionTypes() {
		List<String> transactionTypes = Arrays.asList("Credit", "Debit");
		return transactionTypes;
	}
}
