package com.vbl.clanily.service.account;

import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.account.AccountDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.account.Account;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.service.ClanilyService;

public class AccountService extends ClanilyService {

	private static final AccountService thisInstance = new AccountService();

	public static AccountService getInstance() {
		return thisInstance;
	}

	public boolean hideAccount(int accountId) throws Exception {
		Account account = AccountDBTranslator.getInstance().getById(accountId);
		if (account == null) {
			throw new Exception("No account information found to update");
		}
		if (!account.accountVisible) {
			throw new Exception("Account is already hidden");
		}
		account.accountVisible = false;
		return AccountDBTranslator.getInstance().update(account);
	}

	public boolean showAccount(int accountId) throws Exception {
		Account account = AccountDBTranslator.getInstance().getById(accountId);
		if (account == null) {
			throw new Exception("No account information found to update");
		}
		if (account.accountVisible) {
			throw new Exception("Account is already visible");
		}
		account.accountVisible = true;
		return AccountDBTranslator.getInstance().update(account);
	}

	@Override
	public SearchResult<Account> search(SearchCriteria search) throws Exception {
		return AccountDBTranslator.getInstance().search(search);
	}

	@Override
	public Account getById(int id) throws Exception {
		return AccountDBTranslator.getInstance().getById(id);
	}

	@Override
	public Account getByUniqueName(String name) throws Exception {
		return AccountDBTranslator.getInstance().getByUniqueName(name);
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Given account object is null");
		}
		Account _input = (Account) value;

		// check if name is empty
		if (!isValid(_input.accountName)) {
			throw new Exception("Account name is mandatory");
		}

		// check length
		if (_input.accountName.length() < 3 || _input.accountName.length() > 25) {
			throw new Exception("Account name must contain atleast 3 chars and max 25 in size");
		}

		// name regex check
		// if (!_input.categoryName.matches("^[a-zA-Z0-9_]+$")) {
		// throw new Exception("Category name can contain only letters and numbers case
		// insensitive");
		// }

		if (isValid(_input.accountGroup)) {
			if (containsProperty("ACCOUNT_GROUPS", _input.accountGroup)) {
				// log statement
			} else {
				throw new Exception("Invalid account group");
			}
		} else {
			throw new Exception("Account group is mandatory");
		}

		if (_input.autoSync) {
			if (!isValid(_input.bankName)) {
				throw new Exception("Bank / Wallet name is mandatory");
			} else if (_input.bankName.length() < 3 || _input.bankName.length() > 50) {
				throw new Exception("Bank name length must be between 3 and 50");
			}
		}

		if (!isValid(_input.accountHolderId)) {
			throw new Exception("Account holder is mandatory");
		}

		if (_input.currentBalance < 0 || _input.currentBalance >= Float.MAX_VALUE) {
			throw new Exception("Invalid current balance amount. Please enter a valid amount");
		}

		if (isValid(_input.notes)) {
			if (_input.notes.length() > 500) {
				throw new Exception("Notes field can accomodate maximum 500 chars");
			}
		}

		Account account = AccountDBTranslator.getInstance().getByUniqueName(_input.accountName);
		if (account != null) {
			throw new Exception("Account name is already chosen. Please enter a different name");
		}

		_input.displayOrder = 0;
		_input.internal = false;

		return AccountDBTranslator.getInstance().insert(_input);
	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		return AccountDBTranslator.getInstance().insertAll(values);
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Given account object is null");
		}
		Account _input = (Account) value;

		// check if name is empty
		if (!isValid(_input.accountName)) {
			throw new Exception("Account name is mandatory");
		}

		// check length
		if (_input.accountName.length() < 3 || _input.accountName.length() > 25) {
			throw new Exception("Account name must contain atleast 3 chars and max 25 in size");
		}

		// name regex check
		// if (!_input.categoryName.matches("^[a-zA-Z0-9_]+$")) {
		// throw new Exception("Category name can contain only letters and numbers case
		// insensitive");
		// }

		if (isValid(_input.accountGroup)) {
			if (containsProperty("ACCOUNT_GROUPS", _input.accountGroup)) {
				// log statement
			} else {
				throw new Exception("Invalid account group : " + _input.accountGroup);
			}
		} else {
			throw new Exception("Account group is mandatory");
		}

		if (isValid(_input.bankName)) {
			if (_input.bankName.length() < 3 || _input.bankName.length() > 50) {
				throw new Exception("Bank name length must be between 3 and 50");
			}
		}

		if (!isValid(_input.accountHolderId)) {
			throw new Exception("Account holder is mandatory");
		}

		if (_input.currentBalance < 0 || _input.currentBalance >= Float.MAX_VALUE) {
			throw new Exception("Invalid current balance amount. Please enter a valid amount");
		}

		if (isValid(_input.notes)) {
			if (_input.notes.length() > 500) {
				throw new Exception("Notes field can accomodate maximum 500 chars");
			}
		}

		Account account = AccountDBTranslator.getInstance().getByUniqueName(_input.accountName);
		if (account != null && _input.accountId != account.accountId) {
			throw new Exception("Account name is already chosen. Please enter a different name");
		}

		return AccountDBTranslator.getInstance().update(_input);
	}

	@Override
	public boolean updateAll(List<ValueObject> values) throws Exception {
		return AccountDBTranslator.getInstance().updateAll(values);
	}

	@Override
	public boolean delete(int id) throws Exception {
		return AccountDBTranslator.getInstance().delete(id);
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		return AccountDBTranslator.getInstance().deleteAll(ids);
	}

	public SearchResult<Account> getByAccountGroup(String accountGroup) throws Exception {
		return AccountDBTranslator.getInstance().getByAccountGroup(accountGroup);
	}
	
	public List<String> getUniqueAccountGroups() throws Exception {
		return AccountDBTranslator.getInstance().getUniqueAccountGroups();
	}

}
