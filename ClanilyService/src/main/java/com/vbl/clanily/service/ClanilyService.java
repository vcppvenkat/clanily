package com.vbl.clanily.service;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;

public abstract class ClanilyService {

	private static Properties staticValues = new Properties();

	static {
		staticValues.put("ACCOUNT_TYPES", "Savings,Loan,Credit Cards,Investment,Cash,Online Wallet");
		staticValues.put("PROJECT_TYPES", "Tour,Investment,Savings,Ad-Hoc");
		staticValues.put("CATEGORY_TYPES", "Expense,Income");
		staticValues.put("EXPENSE_CATEGORY_TAGS", "Need,Want,Save");
		staticValues.put("INCOME_CATEGORY_TAGS", "Primary,Secondary,Additional");
		staticValues.put("PAYEE_TYPES", "Shopping,OTT,Communication,Household,Others");
		staticValues.put("ACCOUNT_GROUPS", "Cash,Savings,E-Wallet,Credit Card,Loan,Investment,Mutual Funds,Stocks");
		staticValues.put("TRANSACTION_TYPES", "Income,Expense,Incoming Transfer,Outgoing Transfer");
	}

	public static boolean containsProperty(String key, String property) {
		boolean contains = false;
		if (key != null && !key.isEmpty() && property != null && !property.isEmpty()) {
			String values = staticValues.getProperty(key);
			List<String> valuesList = Arrays.asList(values.split(","));
			if (!valuesList.isEmpty() && valuesList.contains(property)) {
				contains = true;
			}
		}
		return contains;
	}

	public static List<String> getPropertyValues(String property) throws Exception {
		if (staticValues == null || staticValues.isEmpty()) {
			throw new Exception("Unable to find the static values. It might not have been properly loaded");
		}

		if (!staticValues.containsKey(property)) {
			throw new Exception("No static value found with the given name = " + property);
		}

		String values = staticValues.getProperty(property);
		if (values == null || values.isEmpty()) {
			throw new Exception("No values present in the given property = " + property);
		}

		return Arrays.asList(values.split(","));
	}

	public abstract SearchResult search(SearchCriteria search) throws Exception;

	public abstract ValueObject getById(int id) throws Exception;

	public abstract ValueObject getByUniqueName(String name) throws Exception;

	public abstract int insert(ValueObject value) throws Exception;

	public abstract List<Integer> insertAll(List<ValueObject> values) throws Exception;

	public abstract boolean update(ValueObject value) throws Exception;

	public abstract boolean updateAll(List<ValueObject> values) throws Exception;

	public abstract boolean delete(int id) throws Exception;

	public abstract boolean deleteAll(List<Integer> ids) throws Exception;

	protected boolean isValid(String str) {
		return (str != null && !str.isEmpty());
	}

	protected boolean isValid(List<?> list) {
		return (list != null && !list.isEmpty());
	}

}
