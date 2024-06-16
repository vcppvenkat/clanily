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
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Objective;

public class ObjectiveDBTranslator extends AbstractSqlLiteOperationManager implements ClanilyDBOperation {

	private ObjectiveDBTranslator() {

	}

	private static final ObjectiveDBTranslator thisInstance = new ObjectiveDBTranslator();

	public static ObjectiveDBTranslator getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Objective> search(SearchCriteria search) throws Exception {
		SearchResult<Objective> result = new SearchResult<Objective>();
		Objective obj = null;

		if (search == null) {
			throw new Exception("Search criteria is null or empty");
		}

		String query = "SELECT * FROM OBJECTIVES WHERE 1 = 1 ";
		if(!search.includeInternal) {
			query += " AND INTERNAL = '0' ";
		}

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			obj = copyObjective(null, rs);
			result.add(obj);
		}
		rs.close();
		System.out.println(query);
		return result;
	}

	@Override
	public Objective getById(int id) throws Exception {
		Objective obj = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM OBJECTIVES WHERE OBJECTIVE_ID = " + id;

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			obj = copyObjective(null, rs);
			break;
		}
		rs.close();

		return obj;
	}

	@Override
	public Objective getByUniqueName(String name) throws Exception {
		Objective obj = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM OBJECTIVES WHERE OBJECTIVE_NAME = '" + name + "'";

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			obj = copyObjective(null, rs);
			break;
		}
		rs.close();

		return obj;
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		String query = "INSERT INTO OBJECTIVES (OBJECTIVE_NAME, OBJECTIVE_DESCRIPTION, INTERNAL) VALUES (?,?,?)";
		int pk = -1;
		PreparedStatement stmt = connection.prepareStatement(query);
		Objective obj = (Objective) value;
		stmt.setString(1, obj.objectiveName);
		stmt.setString(2, obj.objectiveDescription);
		stmt.setBoolean(3, obj.internal);
		stmt.executeUpdate();

		query = "SELECT MAX(OBJECTIVE_ID) AS MAX_ID FROM OBJECTIVES ";
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
		String query = "UPDATE OBJECTIVES SET OBJECTIVE_NAME=?, OBJECTIVE_DESCRIPTION = ? WHERE OBJECTIVE_ID=?";
		boolean result = false;
		PreparedStatement stmt = connection.prepareStatement(query);
		Objective obj = (Objective) value;
		stmt.setString(1, obj.objectiveName);
		stmt.setString(2, obj.objectiveDescription);
		stmt.setInt(3, obj.objectiveId);
		int rows = stmt.executeUpdate();

		stmt.close();

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
		throw new OperationNotSupportedException();
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		throw new OperationNotSupportedException();
	}

}
