package com.vbl.clanily.backend.vo.settings;

import java.util.Comparator;

import com.vbl.clanily.backend.vo.ValueObject;

public class Category implements ValueObject, Comparable<Category> {

	public int categoryId;
	public String categoryName;
	public String description;
	public String type;
	public String tag;
	public boolean internal;
	public boolean active;

	public int totalTransactions;
	public float totalTransactionValue;
	private final static CategoryNameSorter categoryNameSorter = new CategoryNameSorter();

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public boolean isInternal() {
		return internal;
	}

	public void setInternal(boolean internal) {
		this.internal = internal;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getTotalTransactions() {
		return totalTransactions;
	}

	public void setTotalTransactions(int totalTransactions) {
		this.totalTransactions = totalTransactions;
	}

	public float getTotalTransactionValue() {
		return totalTransactionValue;
	}

	public void setTotalTransactionValue(float totalTransactionValue) {
		this.totalTransactionValue = totalTransactionValue;
	}

	@Override
	public int compareTo(Category o) {
		if(this.getCategoryId() < o.getCategoryId())
			return -1;
		else if(this.getCategoryId() > o.getCategoryId())
			return 1;
		else
			return 0;
	}

	public static Comparator<Category> getCategoryNameSorter() {
		return categoryNameSorter;
	}

	private static class CategoryNameSorter implements Comparator<Category> {
		@Override
		public int compare(Category o1, Category o2) {
			return o1.getCategoryName().compareTo(o2.getCategoryName());
		}
		
	}
}
