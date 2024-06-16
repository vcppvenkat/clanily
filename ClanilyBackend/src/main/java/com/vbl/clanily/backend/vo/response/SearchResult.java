package com.vbl.clanily.backend.vo.response;

import java.util.ArrayList;
import java.util.List;

import com.vbl.clanily.backend.vo.ValueObject;

public class SearchResult<T extends ValueObject> {

	private List<T> values;

	public SearchResult() {
		values = new ArrayList<>();
	}

	public T get(int Id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addAll(List<T> values) {

		if (values != null && !values.isEmpty())
			this.values.addAll(values);
	}

	public void clear() {

		values.clear();

	}

	public void add(T value) {

		values.add(value);

	}

	public List<T> values() {

		return values;
	}
	
	public int count() {
		if(values != null) 
			return values.size();
		else 
			return 0;
	}

}
