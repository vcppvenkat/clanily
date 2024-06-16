package com.vbl.clanily.service.settings;

import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.settings.PayeeDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Payee;
import com.vbl.clanily.service.ClanilyService;

public class PayeeService extends ClanilyService {

	private static final PayeeService thisInstance = new PayeeService();

	public static PayeeService getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Payee> search(SearchCriteria search) throws Exception {
		if (search == null) {
			throw new Exception("Search criteria is null");
		}
		return PayeeDBTranslator.getInstance().search(search);
	}

	@Override
	public Payee getById(int id) throws Exception {
		if (id <= 0) {
			throw new Exception("Id cannot be less than or equal to zero");
		}
		return PayeeDBTranslator.getInstance().getById(id);
	}

	@Override
	public Payee getByUniqueName(String name) throws Exception {
		if (!isValid(name)) {
			throw new Exception("Payee name is null or empty to fetch its values");
		}
		return PayeeDBTranslator.getInstance().getByUniqueName(name);
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Given input object is null");
		}

		Payee _input = (Payee) value;
		if (!isValid(_input.payeeName)) {
			throw new Exception("Payee name is mandatory");
		}

		if (_input.payeeName.length() < 3 || _input.payeeName.length() > 25) {
			throw new Exception("Payee name must contain atleast 3 chars and max 25 in size");
		}

		if (isValid(_input.payeeOfficialName)) {
			if (_input.payeeOfficialName.length() < 3 || _input.payeeOfficialName.length() > 100) {
				throw new Exception("Payee official name must contain atleast 3 chars and max 100 in size");
			}
		} else {
			_input.payeeOfficialName = _input.payeeName;
		}

		if (_input.internal) {
			throw new Exception("Internal payees cannot be modified");
		}

		// name regex check
		// if (!_input.categoryName.matches("^[a-zA-Z0-9_]+$")) {
		// throw new Exception("Category name can contain only letters and numbers case
		// insensitive");
		// }

		// check if the description is empty
		if (!isValid(_input.description)) {
			_input.description = _input.payeeName;
		} else if (_input.description.length() > 250) {
			throw new Exception("Payee description should contain maximum 250 chars");
		}

		Payee payee = (Payee) PayeeDBTranslator.getInstance().getByUniqueName(_input.payeeName);

		// check if name is already used
		if (payee != null) {
			throw new Exception("Given payee name is already used. Please enter another name");
		}

		// set internal property
		_input.internal = false;

		return PayeeDBTranslator.getInstance().insert(_input);
	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Given payee object is null");
		}

		Payee _input = (Payee) value;

		Payee payee = PayeeDBTranslator.getInstance().getById(_input.getPayeeId());
		if (payee == null) {
			throw new Exception("No payee details found to edit");
		}

		// check if name is empty
		if (!isValid(_input.payeeName)) {
			throw new Exception("Payee name is mandatory");
		}

		// check length
		if (_input.payeeName.length() < 3 || _input.payeeName.length() > 25) {
			throw new Exception("Payee name must contain atleast 3 chars and max 25 in size");
		}

		if (_input.internal) {
			throw new Exception("Internal payee details cannot be modified");
		}

		// name regex check
		// if (!_input.categoryName.matches("^[a-zA-Z0-9_]+$")) {
		// throw new Exception("Category name can contain only letters and numbers case
		// insensitive");
		// }

		payee = PayeeDBTranslator.getInstance().getByUniqueName(_input.payeeName);

		// check if name is already used
		if (payee != null && payee.payeeId != _input.payeeId) {
			throw new Exception("Given payee name is already used. Please enter another name");
		}

		// check if the description is empty
		if (!isValid(_input.description)) {
			_input.description = _input.payeeName;
		} else if (_input.description.length() > 250) {
			throw new Exception("Payee description should contain maximum 250 chars");
		}

		return PayeeDBTranslator.getInstance().update(_input);
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
