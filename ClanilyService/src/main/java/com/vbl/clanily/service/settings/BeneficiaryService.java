package com.vbl.clanily.service.settings;

import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.settings.BeneficiaryDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.settings.Beneficiary;
import com.vbl.clanily.service.ClanilyService;

public class BeneficiaryService extends ClanilyService {

	private static final BeneficiaryService thisInstance = new BeneficiaryService();

	public static BeneficiaryService getInstance() {
		return thisInstance;
	}

	@Override
	public SearchResult<Beneficiary> search(SearchCriteria search) throws Exception {
		return BeneficiaryDBTranslator.getInstance().search(search);
	}

	@Override
	public Beneficiary getById(int id) throws Exception {
		return BeneficiaryDBTranslator.getInstance().getById(id);
	}

	@Override
	public Beneficiary getByUniqueName(String name) throws Exception {
		return BeneficiaryDBTranslator.getInstance().getByUniqueName(name);
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		return BeneficiaryDBTranslator.getInstance().insert(value);
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
		return BeneficiaryDBTranslator.getInstance().delete(id);
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}
}
