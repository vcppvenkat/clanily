package com.vbl.clanily.backend.vo.user;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.vbl.clanily.backend.connection.ClanilyDBOperation;
import com.vbl.clanily.backend.connection.sqllite.AbstractSqlLiteOperationManager;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.User;

public class UserDBTranslator extends AbstractSqlLiteOperationManager implements ClanilyDBOperation {

	private UserDBTranslator() {

	}

	public static UserDBTranslator getInstance() {
		return thisInstance;
	}

	private static final UserDBTranslator thisInstance = new UserDBTranslator();

	@Override
	public SearchResult<User> search(SearchCriteria searchCriteria) throws Exception {
		SearchResult<User> result = new SearchResult<User>();
		User user = null;
		String query = "SELECT * FROM USERS ";

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			user = copyUser(null, rs);
			result.add(user);
		}
		rs.close();
		System.out.println(query);

		return result;
	}

	@Override
	public ValueObject getById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public User getById(String userId) throws Exception {
		User user = null;
		PreparedStatement st = null;
		String query = "SELECT * FROM USERS WHERE USER_ID = ?";
		st = connection.prepareStatement(query);
		st.setString(1, userId);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			user = copyUser(null, rs);
			break;
		}
		rs.close();

		return user;
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

	@Override
	public ValueObject getByUniqueName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
