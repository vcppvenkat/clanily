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
import com.vbl.clanily.backend.vo.search.PayeeSearchCriteria;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Payee;

public class PayeeDBTranslator extends AbstractSqlLiteOperationManager implements ClanilyDBOperation {

	private PayeeDBTranslator() {

	}

	private static final PayeeDBTranslator thisInstance = new PayeeDBTranslator();

	public static PayeeDBTranslator getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Payee> search(SearchCriteria searchCriteria) throws Exception {
		SearchResult<Payee> result = new SearchResult<Payee>();
		Payee p = null;
		if (searchCriteria == null) {
			throw new Exception("Search criteria is null or empty");
		}

		PayeeSearchCriteria search = (PayeeSearchCriteria) searchCriteria;
		String query = "SELECT * FROM PAYEE WHERE 1 = 1 ";
		if(!search.includeInternal) {
			query += " AND INTERNAL = '0' ";
		}
		 

		if (isValid(search.payeeType)) {
			query += " AND ";
			query += " PAYEE_TYPE = '" + search.payeeType + "'";
		}

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			p = copyPayee(null, rs);
			result.add(p);
		}
		rs.close();
		System.out.println(query);
		return result;
	}

	@Override
	public Payee getById(int id) throws Exception {
		Payee payee = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM PAYEE WHERE PAYEE_ID = " + id;

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			payee = copyPayee(null, rs);
			break;
		}
		rs.close();

		return payee;
	}

	@Override
	public Payee getByUniqueName(String name) throws Exception {
		Payee payee = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM PAYEE WHERE PAYEE_NAME = '" + name + "'";

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			payee = copyPayee(null, rs);
			break;
		}
		rs.close();

		return payee;
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		String query = "INSERT INTO PAYEE (PAYEE_NAME, OFFICIAL_NAME, WEBSITE ,PAYEE_TYPE, DESCRIPTION, INTERNAL) VALUES (?,?,?,?,?,?)";
		int pk = -1;
		PreparedStatement stmt = connection.prepareStatement(query);
		Payee p = (Payee) value;
		stmt.setString(1, p.payeeName);
		stmt.setString(2, p.payeeOfficialName);
		stmt.setString(3, p.webSite);
		stmt.setString(4, p.payeeType);
		stmt.setString(5, p.description);
		stmt.setBoolean(6, p.internal);
		stmt.executeUpdate();

		query = "SELECT MAX(PAYEE_ID) AS MAX_ID FROM PAYEE ";
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
		String query = "UPDATE PAYEE SET PAYEE_NAME=?, WEBSITE = ?, OFFICIAL_NAME=?, PAYEE_TYPE=?, DESCRIPTION=? WHERE PAYEE_ID=?";
		boolean result = false;
		PreparedStatement stmt = connection.prepareStatement(query);
		Payee p = (Payee) value;
		stmt.setString(1, p.payeeName);
		stmt.setString(2, p.webSite);
		stmt.setString(3, p.payeeOfficialName);
		stmt.setString(4, p.payeeType);
		stmt.setString(5, p.description);
		stmt.setInt(6, p.payeeId);
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
