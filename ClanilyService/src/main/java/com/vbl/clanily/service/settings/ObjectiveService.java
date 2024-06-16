package com.vbl.clanily.service.settings;

import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.settings.ObjectiveDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Objective;
import com.vbl.clanily.service.ClanilyService;

public class ObjectiveService extends ClanilyService {

	private static final ObjectiveService thisInstance = new ObjectiveService();

	public static ObjectiveService getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Objective> search(SearchCriteria search) throws Exception {
		if (search == null) {
			throw new Exception("Search criteria is null");
		}
		return ObjectiveDBTranslator.getInstance().search(search);
	}

	@Override
	public Objective getById(int id) throws Exception {
		if (id <= 0) {
			throw new Exception("Id cannot be less than or equal to zero");
		}
		return ObjectiveDBTranslator.getInstance().getById(id);
	}

	@Override
	public Objective getByUniqueName(String name) throws Exception {
		if (!isValid(name)) {
			throw new Exception("Objective name is null or empty to fetch its values");
		}
		return ObjectiveDBTranslator.getInstance().getByUniqueName(name);
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Given input object is null");
		}

		Objective _input = (Objective) value;
		if (!isValid(_input.objectiveName)) {
			throw new Exception("Objective name is mandatory");
		}

		if (_input.objectiveName.length() < 3 || _input.objectiveName.length() > 25) {
			throw new Exception("Objective name must contain atleast 3 chars and max 25 in size");
		}

		if (_input.internal) {
			throw new Exception("Internal objectives cannot be modified");
		}

		// name regex check
		// if (!_input.categoryName.matches("^[a-zA-Z0-9_]+$")) {
		// throw new Exception("Category name can contain only letters and numbers case
		// insensitive");
		// }

		// check if the description is empty
		if (!isValid(_input.objectiveDescription)) {
			_input.objectiveDescription = _input.objectiveName;
		} else if (_input.objectiveDescription.length() > 250) {
			throw new Exception("Objective description should contain maximum 250 chars");
		}

		Objective obj = (Objective) ObjectiveDBTranslator.getInstance().getByUniqueName(_input.getObjectiveName());

		// check if name is already used
		if (obj != null) {
			throw new Exception("Given Objective name is already used. Please enter another name");
		}

		// set internal property
		_input.internal = false;

		return ObjectiveDBTranslator.getInstance().insert(_input);
	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		return ObjectiveDBTranslator.getInstance().insertAll(values);
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Given objective object is null");
		}

		Objective _input = (Objective) value;

		Objective obj = ObjectiveDBTranslator.getInstance().getById(_input.getObjectiveId());
		if (obj == null) {
			throw new Exception("No objective details found to edit");
		}

		// check if name is empty
		if (!isValid(_input.objectiveName)) {
			throw new Exception("Objective name is mandatory");
		}

		// check length
		if (_input.objectiveName.length() < 3 || _input.objectiveName.length() > 25) {
			throw new Exception("Objective name must contain atleast 3 chars and max 25 in size");
		}

		if (_input.internal) {
			throw new Exception("Internal objective details cannot be modified");
		}

		// name regex check
		// if (!_input.categoryName.matches("^[a-zA-Z0-9_]+$")) {
		// throw new Exception("Category name can contain only letters and numbers case
		// insensitive");
		// }

		obj = ObjectiveDBTranslator.getInstance().getByUniqueName(_input.objectiveName);

		// check if name is already used
		if (obj != null && obj.objectiveId != _input.objectiveId) {
			throw new Exception("Given objective name is already used. Please enter another name");
		}

		// check if the description is empty
		if (!isValid(_input.objectiveDescription)) {
			_input.objectiveDescription = _input.objectiveDescription;
		} else if (_input.objectiveDescription.length() > 250) {
			throw new Exception("Objective description should contain maximum 250 chars");
		}

		return ObjectiveDBTranslator.getInstance().update(_input);
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
