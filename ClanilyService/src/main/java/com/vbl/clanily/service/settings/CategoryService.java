package com.vbl.clanily.service.settings;

import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.settings.CategoryDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Category;
import com.vbl.clanily.service.ClanilyService;

public class CategoryService extends ClanilyService {

	private static final CategoryService thisInstance = new CategoryService();

	public static CategoryService getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Category> search(SearchCriteria search) throws Exception {

		if (search == null) {
			throw new Exception("Search criteria is null");
		}
		return CategoryDBTranslator.getInstance().search(search);
	}

	@Override
	public Category getById(int id) throws Exception {
		if (id <= 0) {
			throw new Exception("Id cannot be less than or equal to zero");
		}
		return CategoryDBTranslator.getInstance().getById(id);
	}

	@Override
	public Category getByUniqueName(String name) throws Exception {

		if (isValid(name)) {
			throw new Exception("Category name is null or empty to search");
		}
		return CategoryDBTranslator.getInstance().getByUniqueName(name);
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Given category object is null");
		}

		Category _input = (Category) value;

		// check if name is empty
		if (!isValid(_input.categoryName)) {
			throw new Exception("Category name is mandatory");
		}

		// check length
		if (_input.categoryName.length() < 3 || _input.categoryName.length() > 25) {
			throw new Exception("Category name must contain atleast 3 chars and max 25 in size");
		}

		// name regex check
		// if (!_input.categoryName.matches("^[a-zA-Z0-9_]+$")) {
		// throw new Exception("Category name can contain only letters and numbers case
		// insensitive");
		// }

		if (!isValid(_input.type)) {
			throw new Exception("Category type is mandatory");
		} else {
			if (containsProperty("CATEGORY_TYPES", _input.type)) {
				// Log statement
			} else {
				throw new Exception("Category type must be either Expense or Income. No other types are allowed");
			}
		}

		if (isValid(_input.tag)) {
			if ("Expense".equals(_input.type) && !containsProperty("EXPENSE_CATEGORY_TAGS", _input.tag)) {
				throw new Exception(
						"Invalid tag name given for expense type. Tag name must be one of the available option");
			} else if ("Income".equals(_input.type) && !containsProperty("INCOME_CATEGORY_TAGS", _input.tag)) {
				throw new Exception(
						"Invalid tag name given for income type. Tag name must be one of the available option");
			}
		}
		Category c = CategoryDBTranslator.getInstance().getByUniqueName(_input.categoryName);

		// check if name is already used
		if (c != null) {
			throw new Exception("Given category name is already used. Please enter another name");
		}

		// set internal property
		_input.internal = false;

		// check if the description is empty
		if (!isValid(_input.description)) {
			_input.description = _input.categoryName;
		} else if (_input.description.length() > 250) {
			throw new Exception("Category description should contain maximum 250 chars");
		}

		return CategoryDBTranslator.getInstance().insert(_input);
	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Given category object is null");
		}

		Category _input = (Category) value;

		Category c = CategoryDBTranslator.getInstance().getById(_input.getCategoryId());
		if (c == null) {
			throw new Exception("No category details found to edit");
		}

		// check if name is empty
		if (!isValid(_input.categoryName)) {
			throw new Exception("Category name is mandatory");
		}

		// check length
		if (_input.categoryName.length() < 3 || _input.categoryName.length() > 25) {
			throw new Exception("Category name must contain atleast 3 chars and max 25 in size");
		}

		if (_input.internal) {
			throw new Exception("Internal categories cannot be modified");
		}

		// name regex check
		// if (!_input.categoryName.matches("^[a-zA-Z0-9_]+$")) {
		// throw new Exception("Category name can contain only letters and numbers case
		// insensitive");
		// }

		if (isValid(_input.tag)) {
			if ("Expense".equals(_input.type) && !containsProperty("EXPENSE_CATEGORY_TAGS", _input.tag)) {
				throw new Exception(
						"Invalid tag name given for expense type. Tag name must be one of the available option");
			} else if ("Income".equals(_input.type) && !containsProperty("INCOME_CATEGORY_TAGS", _input.tag)) {
				throw new Exception(
						"Invalid tag name given for income type. Tag name must be one of the available option");
			}
		}

		c = CategoryDBTranslator.getInstance().getByUniqueName(_input.categoryName);

		// check if name is already used
		if (c != null && c.categoryId != _input.categoryId) {
			throw new Exception("Given category name is already used. Please enter another name");
		}

		// check if the description is empty
		if (!isValid(_input.description)) {
			_input.description = _input.categoryName;
		} else if (_input.description.length() > 250) {
			throw new Exception("Category description should contain maximum 250 chars");
		}

		_input.type = c.type;

		return CategoryDBTranslator.getInstance().update(_input);
	}

	@Override
	public boolean updateAll(List<ValueObject> values) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int id) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean activate(int categoryId) throws Exception {
		Category c = CategoryDBTranslator.getInstance().getById(categoryId);
		if (c != null) {
			if (c.active) {
				throw new Exception("Category is already active");
			} else {
				c.active = true;
				return CategoryDBTranslator.getInstance().update(c);
			}
		} else {
			throw new Exception("No category details found with the given category id = " + categoryId);
		}
	}

	public boolean inactivate(int categoryId) throws Exception {
		Category c = CategoryDBTranslator.getInstance().getById(categoryId);
		if (c != null) {
			if (!c.active) {
				throw new Exception("Category is already inactive");
			} else {
				c.active = false;
				return CategoryDBTranslator.getInstance().update(c);
			}
		} else {
			throw new Exception("No category details found with the given category id = " + categoryId);
		}
	}
	
	public SearchResult<Category> getAllTransferCategories() throws Exception {
		return CategoryDBTranslator.getInstance().getAllTransferCategories();
	}
	
	public SearchResult<Category> getAllExpenseCategories() throws Exception {
		return CategoryDBTranslator.getInstance().getAllExpenseCategories();
	}
	
	public SearchResult<Category> getAllIncomeCategories() throws Exception {
		return CategoryDBTranslator.getInstance().getAllIncomeCategories();
	}

}
