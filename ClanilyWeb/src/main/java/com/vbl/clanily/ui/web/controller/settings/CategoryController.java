package com.vbl.clanily.ui.web.controller.settings;

import java.util.List;

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
import com.vbl.clanily.backend.vo.search.CategorySearchCriteria;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Category;
import com.vbl.clanily.service.ClanilyService;
import com.vbl.clanily.service.settings.CategoryService;
import com.vbl.clanily.ui.web.ControllerAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/category")
public class CategoryController implements ControllerAttributes {

	private static final String SEARCH_CRITERIA = "categorySearchCriteria";

	@InitBinder
	public void initBinder(WebDataBinder binder) {

	}

	@GetMapping("/")
	public ModelAndView loadSummaryPage(HttpSession session, RedirectAttributes rad) throws Exception {
		ModelAndView mav = new ModelAndView("/settings/category");
		CategorySearchCriteria search = getSearchCriteria(session);
		if (search.categoryType == null) {
			search.categoryType = "Expense";
		}
		try {
			if("Expense".equals(search.categoryType)) {
				List<String> expenseCategoryTags = ClanilyService.getPropertyValues("EXPENSE_CATEGORY_TAGS");
				mav.addObject("categoryTags", expenseCategoryTags);
			} else if("Income".equals(search.categoryType)) {
				List<String> incomeCategoryTags = ClanilyService.getPropertyValues("INCOME_CATEGORY_TAGS");
				mav.addObject("categoryTags", incomeCategoryTags);
			}
			
			SearchResult result = searchCategories(search);
			if (result.values() == null || result.values().isEmpty()) {
				throw new Exception("No categories found");
			}

			mav.addObject("values", result.values());

		} catch (Exception e) {
			mav.addObject("errorMessage", e.getMessage());
			e.printStackTrace();
		} finally {

			session.setAttribute(SEARCH_CRITERIA, search);
			session.setAttribute(THIS_PAGE, "settings");
			mav.addObject("search", search);

		}

		return mav;
	}

	@GetMapping("/filterIncome")
	public String filterIncome(HttpSession session) {
		CategorySearchCriteria search = getSearchCriteria(session);
		search.categoryType = "Income";

		return "redirect:/category/";
	}

	@GetMapping("/filterExpense")
	public String filterExpense(HttpSession session) {
		CategorySearchCriteria search = getSearchCriteria(session);
		search.categoryType = "Expense";

		return "redirect:/category/";
	}

	@GetMapping("/filterTag")
	public String filterTagNeed(String tag, HttpSession session) {
		CategorySearchCriteria search = getSearchCriteria(session);
		search.categoryTag = tag;

		return "redirect:/category/";
	}

	

	@GetMapping("/clearTagFilter")
	public String filterTagNoTag(HttpSession session) {
		CategorySearchCriteria search = getSearchCriteria(session);
		search.categoryTag = null;

		return "redirect:/category/";
	}

	@GetMapping("/filterStatusActive")
	public String filterStatusActive(HttpSession session) {
		CategorySearchCriteria search = getSearchCriteria(session);
		search.activeStatus = true;

		return "redirect:/category/";
	}

	@GetMapping("/filterStatusInactive")
	public String filterStatusInactive(HttpSession session) {
		CategorySearchCriteria search = getSearchCriteria(session);
		search.activeStatus = false;

		return "redirect:/category/";
	}

	@GetMapping("/filterStatusNothing")
	public String filterStatusNothing(HttpSession session) {
		CategorySearchCriteria search = getSearchCriteria(session);
		search.activeStatus = null;

		return "redirect:/category/";
	}

	@GetMapping("/activate")
	public String activate(int categoryId, HttpSession session, RedirectAttributes rda) {
		boolean updateResult = false;
		String returnString = "redirect:/category/";

		try {

			updateResult = CategoryService.getInstance().activate(categoryId);
			if (updateResult) {
				rda.addFlashAttribute("successMessage", "Category is now active");
			}

		} catch (Exception e) {
			e.printStackTrace();
			rda.addFlashAttribute("errorMessage", e.getMessage());
		} finally {

		}

		return returnString;
	}

	@GetMapping("/inactivate")
	public String inactivate(int categoryId, HttpSession session, RedirectAttributes rda) {
		boolean updateResult = false;
		String returnString = "redirect:/category/";

		try {

			updateResult = CategoryService.getInstance().inactivate(categoryId);
			if (updateResult) {
				rda.addFlashAttribute("successMessage", "Category is now inactive");
			}

		} catch (Exception e) {
			e.printStackTrace();
			rda.addFlashAttribute("errorMessage", e.getMessage());
		} finally {

		}

		return returnString;
	}

	@GetMapping("/loadNewCategoryPage")
	public ModelAndView loadNewCategoryPage(HttpSession session, ModelAndView mav) {
		mav.setViewName("/settings/newCategory");

		try {
			Category c = new Category();
			c.type = "Expense";

			List<String> expenseCategoryTags = ClanilyService.getPropertyValues("EXPENSE_CATEGORY_TAGS");
			mav.addObject("expenseCategoryTags", expenseCategoryTags);

			List<String> incomeCategoryTags = ClanilyService.getPropertyValues("INCOME_CATEGORY_TAGS");
			mav.addObject("incomeCategoryTags", incomeCategoryTags);

			mav.addObject("category", c);
		} catch (Exception e) {
			mav.setViewName("redirect:/category/");
			e.printStackTrace();
		}

		return mav;
	}

	@PostMapping("/addNewCategory")
	public ModelAndView addNewCategory(Category c, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();

		try {

			if ("noTag".equals(c.tag)) {
				c.tag = null;
			}
			CategoryService.getInstance().insert(c);

			rad.addFlashAttribute("successMessage", "Category added successfully");
			mav.setViewName("redirect:/category/");
		} catch (Exception e) {
			mav.setViewName("redirect:/category/loadNewCategoryPage");
			rad.addFlashAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		} finally {

		}

		return mav;
	}

	@GetMapping("/loadEditCategory")
	public ModelAndView loadEditCategory(int categoryId, HttpSession session, ModelAndView mav) {

		Category c;
		try {
			c = (Category) CategoryService.getInstance().getById(categoryId);
			mav.setViewName("/settings/editCategory");
			mav.addObject("category", c);
			
			List<String> expenseCategoryTags = ClanilyService.getPropertyValues("EXPENSE_CATEGORY_TAGS");
			mav.addObject("expenseCategoryTags", expenseCategoryTags);

			List<String> incomeCategoryTags = ClanilyService.getPropertyValues("INCOME_CATEGORY_TAGS");
			mav.addObject("incomeCategoryTags", incomeCategoryTags);

		} catch (Exception e) {
			mav.setViewName("redirect:/category/");
			mav.addObject("errorMessage", e.getMessage());
			e.printStackTrace();
		}

		return mav;
	}
	
	@GetMapping("/viewCategory")
	public ModelAndView viewCategory(int categoryId, HttpSession session, ModelAndView mav, RedirectAttributes rad) {

		try {
			Category c = (Category) CategoryService.getInstance().getById(categoryId);
			if(c == null) {
				throw new Exception("Category not found to view or change");
			}
			mav.setViewName("/settings/viewCategory");
		} catch (Exception e) {
			rad.addFlashAttribute("errorMessage", e.getMessage());
			mav.setViewName("redirect:/category/");
			e.printStackTrace();
		}
		
		return mav;
	}

	@PostMapping("/editCategory")
	public ModelAndView editCategory(Category c, ModelMap model, RedirectAttributes rad, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		System.out.println(c.tag);
		try {
			CategoryService.getInstance().update(c);
			rad.addFlashAttribute("successMessage", "Category updated successfully");
			mav.setViewName("redirect:/category/");
		} catch (Exception e) {
			mav.setViewName("/settings/editCategory");
			model.addAttribute("errorMessage", e.getMessage());
			model.addAttribute(c);
		} finally {

		}

		return mav;
	}

	private CategorySearchCriteria getSearchCriteria(HttpSession session) {
		if (session.getAttribute(SEARCH_CRITERIA) == null) {
			return new CategorySearchCriteria();
		} else {
			return (CategorySearchCriteria) session.getAttribute(SEARCH_CRITERIA);
		}

	}

	private SearchResult searchCategories(SearchCriteria search) throws Exception {
		return CategoryService.getInstance().search(search);
	}
}
