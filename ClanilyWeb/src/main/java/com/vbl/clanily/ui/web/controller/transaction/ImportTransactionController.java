package com.vbl.clanily.ui.web.controller.transaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
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

import com.vbl.clanily.service.transaction.TransactionService;
import com.vbl.clanily.ui.web.ClanilyLogger;
import com.vbl.clanily.ui.web.ControllerAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/importTransactions")
public class ImportTransactionController implements ControllerAttributes {

	private static final String SEARCH_CRITERIA = "importTransactionSearchCriteria";

	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	@GetMapping("/loadImportTransactionPage")
	public ModelAndView loadImportTransactionPage(HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		mav.setViewName("/transactions/importTransaction");

		return mav;
	}

	@PostMapping("/uploadAndReconcileTransactionFile")
	public ModelAndView uploadTransactionFile(@RequestParam("transactionFile") MultipartFile transactionFile,
			HttpSession session, RedirectAttributes rad, ModelAndView mav) {
		
		try {
			String extension = FilenameUtils.getExtension(transactionFile.getOriginalFilename());

			if (!"csv".equalsIgnoreCase(extension)) {
				throw new Exception("Please upload only CSV file");
			}

			//File convertedFile = new File(transactionFile.getOriginalFilename());
			File convertedFile = new File("/Users/venkat/Downloads/transaction export/transactions_export_hdfc.csv");
			// FileOutputStream fos = new FileOutputStream(convertedFile);
			// fos.write(transactionFile.getBytes());

			List<String[]> results = new ArrayList<>();

			BufferedReader reader = new BufferedReader(new FileReader(convertedFile));
			
			String line;
			while ((line = reader.readLine()) != null) {
				results.add(line.split(","));
			}
			reader.close();
			
			TransactionService.getInstance().importTransactionsTemp(results);
			
			
			mav.setViewName("/importTransactions/reconcileImports");
			
			

		} catch (Exception e) {
			mav.setViewName("redirect:/importTransactions/loadImportTransactionPage");
			rad.addFlashAttribute("errorMessage", e.getMessage());
			ClanilyLogger.LogMessage(getClass(), e);
		}

		return mav;
	}
}
