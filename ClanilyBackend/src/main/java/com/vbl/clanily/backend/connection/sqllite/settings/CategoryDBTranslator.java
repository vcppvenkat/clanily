package com.vbl.clanily.backend.connection.sqllite.settings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.vbl.clanily.backend.connection.ClanilyDBOperation;
import com.vbl.clanily.backend.connection.sqllite.AbstractSqlLiteOperationManager;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.CategorySearchCriteria;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Category;

public class CategoryDBTranslator extends AbstractSqlLiteOperationManager implements ClanilyDBOperation {

	private static final CategoryDBTranslator thisInstance = new CategoryDBTranslator();

	public static CategoryDBTranslator getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Category> search(SearchCriteria searchCriteria) throws Exception {

		SearchResult<Category> result = new SearchResult<Category>();
		Category c = null;
		CategorySearchCriteria search = (CategorySearchCriteria) searchCriteria;
		String query = "SELECT * FROM CATEGORY WHERE 1 = 1 ";
		if(!search.includeInternal) {
			query += " AND INTERNAL = '0' ";
		}

		// Set category name
		if (isValid(search.categoryName)) {
			query += " AND CATEGORY_NAME LIKE '%" + search.categoryName + "%'";
		}

		// set category type
		if (isValid(search.categoryDescription)) {
			query += " AND CATEGORY_DESCRIPTION LIKE '%" + search.categoryDescription + "%'";
		}

		if (isValid(search.categoryType)) {
			query += " AND CATEGORY_TYPE = '" + search.categoryType + "'";
		}

		if (isValid(search.categoryTag)) {
			query += " AND TAG = ";
			query += " '" + search.categoryTag + "' ";

		}

		if (search.activeStatus != null) {
			query += " AND ACTIVE = ";
			if (search.activeStatus) {
				query += " '1' ";
			} else {
				query += " '0' ";
			}
		}

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			c = copyCategory(null, rs);
			result.add(c);
		}
		rs.close();
		System.out.println(query);

		return result;
	}

	public SearchResult<Category> getAllExpenseCategories() throws Exception {

		SearchResult<Category> result = new SearchResult<Category>();
		Category c = null;
		String query = "SELECT * FROM CATEGORY WHERE CATEGORY_TYPE =  'Expense' AND ACTIVE = '1' ";

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			c = copyCategory(null, rs);
			result.add(c);
		}
		rs.close();

		return result;
	}
	
	public SearchResult<Category> getAllIncomeCategories() throws Exception {

		SearchResult<Category> result = new SearchResult<Category>();
		Category c = null;
		String query = "SELECT * FROM CATEGORY WHERE CATEGORY_TYPE =  'Income' AND ACTIVE = '1' ";

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			c = copyCategory(null, rs);
			result.add(c);
		}
		rs.close();

		return result;
	}
	
	public SearchResult<Category> getAllTransferCategories() throws Exception {

		SearchResult<Category> result = new SearchResult<Category>();
		Category c = null;
		String query = "SELECT * FROM CATEGORY WHERE CATEGORY_TYPE =  'Transfer' AND ACTIVE = '1' ";

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			c = copyCategory(null, rs);
			result.add(c);
		}
		rs.close();

		return result;
	}

	@Override
	public Category getById(int id) throws Exception {

		Category category = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM CATEGORY WHERE CATEGORY_ID = " + id;

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			category = copyCategory(null, rs);
			break;
		}
		rs.close();

		return category;
	}

	@Override
	public Category getByUniqueName(String name) throws Exception {

		Category category = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM CATEGORY WHERE CATEGORY_NAME = '" + name + "'";

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			category = copyCategory(null, rs);
			break;
		}
		rs.close();

		return category;
	}

	@Override
	public int insert(ValueObject value) throws Exception {

		int pk = -1;
		Category c = (Category) value;
		String query = "INSERT INTO CATEGORY (CATEGORY_NAME, CATEGORY_DESCRIPTION, CATEGORY_TYPE, TAG, ACTIVE, INTERNAL) VALUES (?,?,?,?,?,?)";

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, c.categoryName);
		stmt.setString(2, c.description);
		stmt.setString(3, c.type);
		stmt.setString(4, c.tag);
		stmt.setBoolean(5, c.active);
		stmt.setBoolean(6, c.internal);
		stmt.executeUpdate();

		query = "SELECT MAX(CATEGORY_ID) AS MAX_ID FROM CATEGORY ";
		stmt = connection.prepareStatement(query);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			pk = rs.getInt("MAX_ID");
			break;
		}

		stmt.close();

		return pk;
	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {

		throw new OperationNotSupportedException();
	}

	@Override
	public boolean update(ValueObject value) throws Exception {

		Category c = (Category) value;
		String query = "UPDATE CATEGORY SET CATEGORY_NAME = ?, CATEGORY_TYPE = ?, TAG=?, ACTIVE=? , CATEGORY_DESCRIPTION = ? WHERE CATEGORY_ID=?";
		boolean result = false;

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, c.categoryName);
		stmt.setString(2, c.type);
		stmt.setString(3, c.tag);
		stmt.setBoolean(4, c.active);
		stmt.setString(5, c.description);
		stmt.setInt(6, c.categoryId);
		int rows = stmt.executeUpdate();
		if (rows == 1)
			result = true;
		stmt.close();

		return result;
	}

	@Override
	public boolean updateAll(List<ValueObject> values) throws Exception {
		throw new OperationNotSupportedException();
	}

	@Override
	public boolean delete(int id) throws Exception {
		boolean result = false;
		String query = " DELETE FROM CATEGORY WHERE CATEGORY_ID = " + id;
		Statement st = connection.createStatement();
		int rows = st.executeUpdate(query);
		if (rows == 1)
			result = true;
		return result;
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		throw new OperationNotSupportedException();
	}

}
