package com.vbl.clanily.backend.connection;

import java.util.List;

import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;

public interface ClanilyDBOperation {

	public <T extends ValueObject> SearchResult<T> search(SearchCriteria search) throws Exception;

	public ValueObject getById(int id) throws Exception;

	public ValueObject getByUniqueName(String name) throws Exception;

	public int insert(ValueObject value) throws Exception;

	public List<Integer> insertAll(List<ValueObject> values) throws Exception;

	public boolean update(ValueObject value) throws Exception;

	public boolean updateAll(List<ValueObject> values) throws Exception;

	public boolean delete(int id) throws Exception;

	public boolean deleteAll(List<Integer> ids) throws Exception;
}
