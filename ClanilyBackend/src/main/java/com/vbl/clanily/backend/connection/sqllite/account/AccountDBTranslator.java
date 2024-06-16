package com.vbl.clanily.backend.connection.sqllite.account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.vbl.clanily.backend.connection.ClanilyDBOperation;
import com.vbl.clanily.backend.connection.sqllite.AbstractSqlLiteOperationManager;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.account.Account;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;

public class AccountDBTranslator extends AbstractSqlLiteOperationManager implements ClanilyDBOperation {

	private static final AccountDBTranslator thisInstance = new AccountDBTranslator();

	public static AccountDBTranslator getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Account> search(SearchCriteria search) throws Exception {
		SearchResult<Account> result = new SearchResult<Account>();
		Account account = null;
		String query = "SELECT * FROM ACCOUNTS WHERE 1 = 1 ";
		if(!search.includeInternal) {
			query += " AND INTERNAL = '0' ";
		}
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			account = copyAccount(null, rs);
			result.add(account);
		}
		rs.close();
		//System.out.println(query);

		return result;
	}

	@Override
	public Account getById(int id) throws Exception {
		Account account = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM ACCOUNTS WHERE ACCOUNT_ID = " + id;

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			account = copyAccount(null, rs);
			break;
		}
		rs.close();

		return account;
	}

	@Override
	public Account getByUniqueName(String name) throws Exception {
		Account account = null;
		Statement st = connection.createStatement();
		String query = "SELECT * FROM ACCOUNTS WHERE ACCOUNT_NAME = '" + name + "'";

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			account = copyAccount(null, rs);
			break;
		}
		rs.close();

		return account;
	}

	public SearchResult<Account> getByAccountGroup(String accountGroup) throws Exception {
		SearchResult<Account> result = new SearchResult<Account>();

		Account account = null;
		String query = "SELECT * FROM ACCOUNTS WHERE INTERNAL = 0 AND ACCOUNT_GROUP = '" + accountGroup + "' ";
		Statement st = connection.createStatement();
		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			account = copyAccount(null, rs);
			result.add(account);
		}
		rs.close();

		return result;
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		int pk = -1;
		Account account = (Account) value;
		String query = "INSERT INTO ACCOUNTS (ACCOUNT_NAME, BANK_NAME, ACCOUNT_HOLDER, ACCOUNTS_VISIBLE, CURRENT_BALANCE, ACCOUNT_GROUP, INTERNAL, AUTO_SYNC, DISPLAY_ORDER, ACCOUNT_NUMBER, BANK_LOGIN_ID, BANK_PASSWORD, NOTES, BANK_LOGIN_URL) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, account.accountName);
		ps.setString(2, account.bankName);
		ps.setString(3, account.accountHolderId);
		ps.setBoolean(4, account.accountVisible);
		ps.setFloat(5, account.currentBalance);
		ps.setString(6, account.accountGroup);
		ps.setBoolean(7, account.internal);
		ps.setBoolean(8, account.autoSync);
		ps.setInt(9, account.displayOrder);
		ps.setString(10, account.accountNumber);
		ps.setString(11, account.bankLoginId);
		ps.setString(12, account.bankPassword);
		ps.setString(13, account.notes);
		ps.setString(14, account.webUrl);
		ps.executeUpdate();

		query = "SELECT MAX(ACCOUNT_ID) AS MAX_ID FROM ACCOUNTS ";
		ps = connection.prepareStatement(query);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			pk = rs.getInt("MAX_ID");
			break;
		}

		ps.close();

		return pk;
	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		throw new OperationNotSupportedException();
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		Account account = (Account) value;
		String query = "UPDATE ACCOUNTS SET ACCOUNT_NAME = ?, BANK_NAME = ?, ACCOUNT_HOLDER=?, ACCOUNTS_VISIBLE=? , CURRENT_BALANCE = ?, ACCOUNT_GROUP = ?, INTERNAL = ?, AUTO_SYNC = ?, DISPLAY_ORDER = ?, ACCOUNT_NUMBER = ?, BANK_LOGIN_ID = ?, BANK_PASSWORD = ?, NOTES = ? , BANK_LOGIN_URL = ? WHERE ACCOUNT_ID=?";
		boolean result = false;

		PreparedStatement ps = connection.prepareStatement(query);
		ps.setString(1, account.accountName);
		ps.setString(2, account.bankName);
		ps.setString(3, account.accountHolderId);
		ps.setBoolean(4, account.accountVisible);
		ps.setFloat(5, account.currentBalance);
		ps.setString(6, account.accountGroup);
		ps.setBoolean(7, account.internal);
		ps.setBoolean(8, account.autoSync);
		ps.setInt(9, account.displayOrder);
		ps.setString(10, account.accountNumber);
		ps.setString(11, account.bankLoginId);
		ps.setString(12, account.bankPassword);
		ps.setString(13, account.notes);
		ps.setString(14, account.webUrl);
		ps.setInt(15, account.accountId);

		int rows = ps.executeUpdate();
		if (rows == 1)
			result = true;
		ps.close();

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

	public List<String> getUniqueAccountGroups() throws Exception {
		List<String> accountGroups = new ArrayList<String>();
		Statement st = connection.createStatement();
		String query = "SELECT DISTINCT(ACCOUNT_GROUP) FROM ACCOUNTS WHERE INTERNAL = '0' ";

		ResultSet rs = st.executeQuery(query);

		while (rs.next()) {
			accountGroups.add(rs.getString("ACCOUNT_GROUP"));

		}
		rs.close();

		return accountGroups;
	}

}
