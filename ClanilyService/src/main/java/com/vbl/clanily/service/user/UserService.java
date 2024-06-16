package com.vbl.clanily.service.user;

import java.util.List;

import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.User;
import com.vbl.clanily.backend.vo.user.UserDBTranslator;
import com.vbl.clanily.service.ClanilyService;

public class UserService extends ClanilyService {

	private static final UserService thisInstance = new UserService();

	private UserService() {

	}

	public static UserService getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<User> search(SearchCriteria search) throws Exception {
		return UserDBTranslator.getInstance().search(search);
	}

	@Override
	public ValueObject getById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public User getById(String userId) throws Exception {
		return UserDBTranslator.getInstance().getById(userId);
	}

	@Override
	public ValueObject getByUniqueName(String name) throws Exception {
		return null;
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		// TODO Auto-generated method stub
		return false;
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

}
