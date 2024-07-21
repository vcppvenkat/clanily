package com.vbl.clanily.backend.connection.sqllite.settings;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import com.vbl.clanily.backend.connection.ClanilyDBOperation;
import com.vbl.clanily.backend.connection.sqllite.AbstractSqlLiteOperationManager;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.BeneficiarySearchCriteria;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Beneficiary;

public class BeneficiaryDBTranslator extends AbstractSqlLiteOperationManager implements ClanilyDBOperation {

	private static final BeneficiaryDBTranslator thisInstance = new BeneficiaryDBTranslator();
	

	public static BeneficiaryDBTranslator getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Beneficiary> search(SearchCriteria searchCriteria) throws Exception {
		SearchResult<Beneficiary> result = new SearchResult<Beneficiary>();
		Beneficiary b = null;
		BeneficiarySearchCriteria search = (BeneficiarySearchCriteria) searchCriteria;
		String query = "SELECT * FROM BENEFICIARY WHERE 1 = 1 ";
		if (!search.includeInternal) {
			query += " AND INTERNAL = '0' ";
		} else {
			query += " AND INTERNAL <> 0";
		}

		// Set category name
		if (isValid(search.beneficiaryName)) {
			query += " AND BENEFICIARY_NAME LIKE '%" + search.beneficiaryName + "%'";
		}

		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			b = copyBeneficiary(null, rs);
			result.add(b);
		}
		rs.close();

		return result;
	}

	@Override
	public Beneficiary getById(int id) throws Exception {
		Beneficiary b = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM BENEFICIARY WHERE BENEFICIARY_ID = " + id;

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			b = copyBeneficiary(null, rs);
			break;
		}
		rs.close();

		return b;
	}

	@Override
	public Beneficiary getByUniqueName(String name) throws Exception {
		Beneficiary b = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM BENEFICIARY WHERE UPPER(BENEFICIARY_NAME) = '" + name.toUpperCase() + "'";

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			b = copyBeneficiary(null, rs);
			break;
		}
		rs.close();

		return b;
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		int pk = -1;
		Beneficiary b = (Beneficiary) value;
		String query = "INSERT INTO BENEFICIARY (BENEFICIARY_NAME, CREATED_TS, INTERNAL) VALUES (?,?,?)";

		PreparedStatement stmt = connection.prepareStatement(query);
		stmt.setString(1, b.beneficiaryName);
		stmt.setLong(2, b.createdDate.getTime());
		stmt.setBoolean(3, b.internal);
		stmt.executeUpdate();

		query = "SELECT MAX(BENEFICIARY_ID) AS MAX_ID FROM BENEFICIARY ";
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
		boolean result = false;
		String query = " DELETE FROM BENEFICIARY WHERE BENEFICIARY_ID = " + id;
		Statement st = connection.createStatement();
		int rows = st.executeUpdate(query);
		if (rows == 1)
			result = true;
		return result;
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

}
