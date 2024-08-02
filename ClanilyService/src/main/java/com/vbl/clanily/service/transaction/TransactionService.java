package com.vbl.clanily.service.transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.vbl.clanily.backend.connection.sqllite.transaction.TransactionDBTranslator;
import com.vbl.clanily.backend.vo.ValueObject;
import com.vbl.clanily.backend.vo.account.Account;
import com.vbl.clanily.backend.vo.response.SearchResult;
import com.vbl.clanily.backend.vo.search.SearchCriteria;
import com.vbl.clanily.backend.vo.search.TransactionSearchCriteria;
import com.vbl.clanily.backend.vo.settings.Category;
import com.vbl.clanily.backend.vo.settings.Objective;
import com.vbl.clanily.backend.vo.settings.Payee;
import com.vbl.clanily.backend.vo.settings.User;
import com.vbl.clanily.backend.vo.transaction.Transaction;
import com.vbl.clanily.backend.vo.transaction.TransactionFile;
import com.vbl.clanily.service.ClanilyService;
import com.vbl.clanily.service.account.AccountService;
import com.vbl.clanily.service.settings.CategoryService;
import com.vbl.clanily.service.settings.ObjectiveService;
import com.vbl.clanily.service.settings.PayeeService;
import com.vbl.clanily.service.user.UserService;

public class TransactionService extends ClanilyService {

	private static final TransactionService thisInstance = new TransactionService();

	public static TransactionService getInstance() {
		return thisInstance;
	}

	public List<Transaction> getGroupChildren(int transactionId) {
		return null;
	}

	public List<Transaction> getSplitChildren(int transactionId) {
		return null;
	}

	public void unmergeTransaction(int parentTransactionId) throws Exception {
		Transaction parent = getById(parentTransactionId);
		if (parent == null)
			throw new Exception("Invalid parent transaction id: " + parentTransactionId);

		if (!parent.hasMergedChildren()) {
			for (int childId : parent.mergeTransactionIds) {
				Transaction child = TransactionDBTranslator.getInstance().getById(childId);
				if (child != null) {
					child.mergeParentId = 0;
					TransactionDBTranslator.getInstance().update(child);
				}
				// remove merge parent
				boolean deleted = TransactionDBTranslator.getInstance().delete(parentTransactionId);
				if (!deleted)
					throw new Exception("There was an issue while urmerging transaction");

			}
		}

	}

	public void mergeTransaction(Transaction parent, List<Integer> children) throws Exception {

		if (parent == null) {
			throw new Exception("Invalid parent transaction details. Input is null");
		}
		// create parent transaction
		parent.insertionType = "MERGE_PARENT";
		parent.transactionId = insert(parent);

		float childrenSum = TransactionDBTranslator.getInstance().sumOfTransactions(children);

		float sum = 0.0f;
		for (int child : children) {
			Transaction t = TransactionDBTranslator.getInstance().getById(child);
			// check id is valid
			if (t == null)
				throw new Exception("Invalid child : " + child);

			// check if the given id is already a children
			if (t.mergeParentId > 0 && t.mergeParentId != parent.transactionId) {
				throw new Exception("An existing merged member cannot be re-merged : " + t.summary);
			}

			if (t.splitParentId > 0 && t.splitParentId != parent.transactionId) {
				throw new Exception("An existing split member cannot be grouped : " + t.summary);
			}

			sum += t.transactionAmount;

			// Validate sum of all ids

			if (childrenSum != parent.transactionAmount) {
				throw new Exception(
						"Sum of children cannot exceed transaction total. Tip: Adjust with additional income / expense");
			}

		}

		if (sum != parent.transactionAmount) {
			throw new Exception("Sum of children is nott equal to parent. Please adjust the amount accordingly");
		}

		TransactionDBTranslator.getInstance().mergeTransaction(parent.transactionId, children);
	}

	public void splitTransaction(int parentId, List<Transaction> children) throws Exception {

		List<ValueObject> output = new ArrayList<ValueObject>();
		Transaction parent = TransactionDBTranslator.getInstance().getById(parentId);
		if (parent == null) {
			throw new Exception("Invalid parent transaction details. Input is null");
		}

		float childrenSum = 0.0f;
		for (Transaction child : children) {

			if (child == null) {
				throw new Exception("Invalid child as input");
			}

			// if this is a child of another parent
			if (child.splitParentId > 0) {
				throw new Exception("Cannot split a child of another splitted parent. New child: " + child.summary);
			}

			if (child.mergeParentId > 0) {
				throw new Exception("Cannot split a child of another merged parent. New child: " + child.summary);
			}

			if (child.hasMergedChildren()) {
				throw new Exception("An already merged parent cannot become child. Existing parent: " + child.summary);
			}

			if (child.hasSplitChildren()) {
				throw new Exception("An already split parent cannot become child. Existing parent: " + child.summary);
			}

			if (!parent.transactionType.equals(child.transactionType)) {
				throw new Exception(child.summary + ": Child does not have the same type of the parent.");
			}

			// Validate sum of all ids
			childrenSum += child.transactionAmount;
			output.add(child);
			
			child.splitParentId = parentId;
			child.mergeParentId = 0;
		}

		if (childrenSum != parent.transactionAmount) {
			throw new Exception(
					"Sum of children cannot exceed transaction total. Tip: Adjust with additional income / expense");
		}

		TransactionDBTranslator.getInstance().insertAll(output);

	}
	
	public void unsplitTransaction(int parentId) throws Exception {
		Transaction parent = TransactionDBTranslator.getInstance().getById(parentId);
		if(parent == null) {
			throw new Exception("Invalid input. Given parent id does not exist: Parent Id: " + parentId);
		}
		
		if(!parent.hasSplitChildren()) {
			throw new Exception("Given parent does not have any children to split.");
		}
		
		TransactionDBTranslator.getInstance().deleteAll(parent.splitTransactionIds);
		
	}

	public void detachFile(int transactionFileId) throws Exception {
		TransactionDBTranslator.getInstance().detachFile(transactionFileId);
	}

	public void attachFile(TransactionFile file) throws Exception {
		if (file == null)
			throw new Exception("Input object is null");
		if (!isValid(file.summary)) {
			throw new Exception("File summary is mandatory to search in the future");
		}
		if (file.summary.length() > 50)
			throw new Exception("File summary cannot exceed 50 chars");
		if (isValid(file.description) && file.description.length() > 250)
			throw new Exception("File description cannot exceed 250 chars");

		if (file.transactionId < 1)
			throw new Exception("Invalid transaction id : " + file.transactionId);

		// DB check
		Transaction t = getById(file.transactionId);
		if (t == null) {
			throw new Exception("Invalid transaction id : " + file.transactionId);
		}
		if (!isValid(file.fileName) || !isValid(file.fileType)) {
			throw new Exception("Invalid file metadata");
		}
		TransactionDBTranslator.getInstance().attachFile(file);
	}

	@Override
	public SearchResult<Transaction> search(SearchCriteria search) throws Exception {
		if (search != null) {
			TransactionSearchCriteria t = (TransactionSearchCriteria) search;
			if (isValid(t.currentTransactionGroup)) {
				switch (t.currentTransactionGroup) {
				case "Date":
				case "None": {

					t.searchGroupName = "EFFECTIVE_DATE";
					break;
				}
				case "Category": {
					t.searchGroupName = "CATEGORY.CATEGORY_NAME";
					break;
				}
				case "Payee": {
					t.searchGroupName = "PAYEE.PAYEE_NAME";
					break;
				}
				case "Objective": {
					t.searchGroupName = "OBJECTIVES.OBJECTIVE_NAME";
					break;
				}
				case "Loan": {
					t.searchGroupName = "LOANS.LOAN_NAME";
					break;
				}
				case "Beneficiary": {
					t.searchGroupName = "BENEFICIARY_NAME";
					break;
				}

				}
			}

			SearchResult<Transaction> result = TransactionDBTranslator.getInstance().search(search);
			if ("None".equals(t.currentTransactionGroup) || "Date".equals(t.currentTransactionGroup)) {
				String temp = "";
				for (Transaction trans : result.values()) {
					if (!temp.equals(trans.displayGroupValue)) {

					}
				}
			}

			// insert an attachment
			/*
			 * String file =
			 * "/Users/venkat/Downloads/4C89E125-7C20-4358-8C97-FA542164E737_1_102_o.jpeg";
			 * File image = new File(file); FileInputStream fis = new
			 * FileInputStream(image); ByteArrayOutputStream bos = new
			 * ByteArrayOutputStream(); byte[] buf = new byte[1024]; byte[] person_image =
			 * null; try { for (int readNum; (readNum = fis.read(buf)) != -1;) {
			 * bos.write(buf, 0, readNum); person_image = bos.toByteArray(); }
			 * 
			 * 
			 * for(Transaction temp : result.values()) { TransactionFile tFile = new
			 * TransactionFile(); tFile.dateAdded = new Date(); tFile.description =
			 * "This is added via auto code from service for testing"; tFile.file =
			 * person_image; tFile.fileName = temp.summary; tFile.fileType = "JPEG";
			 * tFile.summary = temp.summary; tFile.transactionId = temp.transactionId;
			 * TransactionDBTranslator.getInstance().attachFile(tFile); }
			 * 
			 * } catch (IOException ex) { System.err.println(ex.getMessage()); }
			 */

			return result;
		}

		throw new Exception("Input search criteria object is null");
	}

	@Override
	public Transaction getById(int id) throws Exception {
		return TransactionDBTranslator.getInstance().getById(id);
	}

	@Override
	public Transaction getByUniqueName(String name) throws Exception {
		return TransactionDBTranslator.getInstance().getByUniqueName(name);
	}

	@Override
	public int insert(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Input object is null. Unable to insert transaction values");
		}
		Transaction _input = (Transaction) value;
		if (!isValid(_input.summary)) {
			throw new Exception("Transaction summary cannot be empty");
		}
		if (_input.summary.length() > 50 || _input.summary.length() < 3) {
			throw new Exception("Summary length can be between 3 and 50");
		}

		// check if valid transaction type
		if (!containsProperty("TRANSACTION_TYPES", _input.transactionType)) {
			throw new Exception("Transaction type can be either one of these ("
					+ String.join(",", getPropertyValues("TRANSACTION_TYPES")) + ")");
		}

		// check for valid account
		if (_input.accountId < 1) {
			throw new Exception("Invalid account details. Account must be a valid account with Clanily application");
		}

		Account account = AccountService.getInstance().getById(_input.getAccountId());
		if (account == null) {
			throw new Exception("Invalid account details. Account must be a valid account with Clanily application");
		}

		/*
		 * if ("Transfer".equals(_input.transactionType)) { if
		 * (_input.transferTransactionId < 1) { throw new Exception(
		 * "Invalid target account details. Target account must be a valid account with Clanily application"
		 * ); } account =
		 * AccountService.getInstance().getById(_input.transferTransactionId); if
		 * (account == null) { throw new Exception(
		 * "Invalid target account details. Target account must be a valid account with Clanily application"
		 * ); } }
		 * 
		 */
		// check for valid amount
		if (_input.transactionAmount != 0.00) {
			if (("Transfer".equals(_input.transactionType) || "Expense".equals(_input.transactionType))
					&& _input.transactionAmount > 0.00) {
				_input.transactionAmount *= -1;
			} else if ("Income".equals(_input.transactionType) && _input.transactionAmount < 0.00) {
				_input.transactionAmount *= -1;
			}
		} else {
			if (!_input.insertionType.equals("MERGE_PARENT") || !_input.insertionType.equals("SPLIT_PARENT"))
				throw new Exception("Transaction amount cannot be 0. It must be a valid currency value");
		}

		// validate transaction date
		if (_input.transactionDate != null) {
			if (_input.transactionDate.after(new Date())) {
				_input.cleared = false;
			} else if (!_input.cleared) {
				_input.cleared = true;
			} else if (_input.transactionDate.before(new Date())) {
				_input.cleared = true;
			}
		}

		// validate category
		if (_input.categoryId > 0) {
			Category c = CategoryService.getInstance().getById(_input.categoryId);
			if (c == null) {
				throw new Exception("Transaction category must be a valid category from Clanily application. "
						+ _input.categoryId + " : is not a valid category");
			}
			/*
			 * else if (!_input.transactionType.equals(c.type)) { throw new
			 * Exception("Transaction type must match with the category type. Given transaction type is "
			 * + _input.transactionType + " and Category type is " + c.type); }
			 */
		} else {
			throw new Exception("Transaction category must be a valid category from Clanily application. "
					+ _input.categoryId + " : is not a valid category");
		}

		// validate transaction user
		if (isValid(_input.transactionUserId)) {
			User user = UserService.getInstance().getById(_input.transactionUserId);
			if (user == null) {
				throw new Exception("Transaction user cannot be empty. It must be a valid Clanily user id");
			}
		} else {
			throw new Exception("Transaction user cannot be empty.");
		}

		// validate payee details
		if (_input.payeeId > 0) {
			Payee p = PayeeService.getInstance().getById(_input.payeeId);
			if (p == null) {
				throw new Exception("Payee details must be a registered payee of Clanily");
			}
		} else {
			if (_input.payeeName != null && !_input.payeeName.isEmpty()) {
				Payee p = PayeeService.getInstance().getByUniqueName(_input.payeeName);
				if (p != null) {
					_input.payeeId = p.payeeId;
				} else {
					// add payee

					p = new Payee();
					p.payeeName = _input.payeeName;
					p.payeeOfficialName = _input.payeeName;
					p.internal = false;
					int payeeId = PayeeService.getInstance().insert(p);
					_input.payeeId = payeeId;

				}
			} else {
				_input.payeeId = 1;
			}
		}

		// validate objective details
		if (_input.objectiveId > 0) {
			Objective o = ObjectiveService.getInstance().getById(_input.objectiveId);
			if (o == null) {
				throw new Exception("Objective details must be a registered Objective of Clanily");
			}
		} else {
			if (_input.objectiveName != null && !_input.objectiveName.isEmpty()) {
				Objective o = ObjectiveService.getInstance().getByUniqueName(_input.objectiveName);
				if (o != null) {
					_input.objectiveId = o.objectiveId;
				} else {
					// add objective

					o = new Objective();
					o.objectiveName = _input.objectiveName;
					o.objectiveDescription = _input.objectiveName;
					o.internal = false;
					int objectiveId = ObjectiveService.getInstance().insert(o);
					_input.objectiveId = objectiveId;

				}
			} else {
				_input.objectiveId = 1;
			}
		}

		// validate loan details
		if (_input.loanId == 0)
			_input.loanId = 1;

		// insert type
		if (!isValid(_input.insertionType)) {
			_input.insertionType = "MANUAL";
		}

		if (_input.effectiveDate == null) {
			_input.effectiveDate = _input.transactionDate;
		}

		if (!isValid(_input.beneficiaryName)) {
			_input.beneficiaryName = "Family";
		}
		_input.beneficiaryId = 1;
		_input.projectId = 1;
		return TransactionDBTranslator.getInstance().insert(value);

	}

	@Override
	public List<Integer> insertAll(List<ValueObject> values) throws Exception {
		return TransactionDBTranslator.getInstance().insertAll(values);
	}

	@Override
	public boolean update(ValueObject value) throws Exception {
		if (value == null) {
			throw new Exception("Input object is null. Unable to insert transaction values");
		}
		Transaction _input = (Transaction) value;
		Transaction t = TransactionDBTranslator.getInstance().getById(_input.transactionId);
		if (!isValid(_input.summary)) {
			throw new Exception("Transaction summary cannot be empty");
		}
		if (_input.summary.length() > 50 || _input.summary.length() < 3) {
			throw new Exception("Summary length can be between 3 and 50");
		}

		// check if valid transaction type
		if (!containsProperty("TRANSACTION_TYPES", _input.transactionType)) {
			throw new Exception("Transaction type can be either one of these ("
					+ String.join(",", getPropertyValues("TRANSACTION_TYPES")));
		}

		// check for valid account
		if (t.accountId != _input.accountId) {
			if (_input.accountId < 1) {
				throw new Exception(
						"Invalid account details. Account must be a valid account with Clanily application");
			}
		}

		Account account = AccountService.getInstance().getById(_input.getAccountId());
		if (account == null) {
			throw new Exception("Invalid account details. Account must be a valid account with Clanily application");
		}

		if ("Transfer".equals(_input.transactionType)) {
			if (_input.transferTransactionId < 1) {
				throw new Exception(
						"Invalid target account details. Target account must be a valid account with Clanily application");
			}
			account = AccountService.getInstance().getById(_input.transferTransactionId);
			if (account == null) {
				throw new Exception(
						"Invalid target account details. Target account must be a valid account with Clanily application");
			}
		}

		// check for valid amount
		if (_input.transactionAmount != 0.00) {
			if ("Expense".equals(_input.transactionType) && _input.transactionAmount > 0.00) {
				_input.transactionAmount *= -1;
			} else if ("Income".equals(_input.transactionType) && _input.transactionAmount < 0.00) {
				_input.transactionAmount *= -1;
			} else if (_input.transactionAmount > 0.00) {
				_input.transactionAmount *= -1;
			}
		} else {
			throw new Exception("Transaction amount cannot be 0. It must be a valid currency value");
		}

		// validate transaction date
		if (_input.transactionDate != null) {
			if (t.autoSync && !_input.transactionDate.equals(t.transactionDate)) {
				throw new Exception("Transaction date cannot be modified for auto sync enabled accounts");
			} else if (_input.transactionDate.after(new Date())) {
				_input.cleared = false;
			} else if (!_input.cleared) {
				_input.cleared = true;
			} else if (_input.transactionDate.before(new Date())) {
				_input.cleared = true;
			}
		}

		// validate category
		if (_input.categoryId > 0) {
			Category c = CategoryService.getInstance().getById(_input.categoryId);
			if (c == null) {
				throw new Exception("Transaction category must be a valid category from Clanily application. "
						+ _input.categoryId + " : is not a valid category");
			} else if (!_input.transactionType.equals(c.type)) {
				throw new Exception("Transaction type must match with the category type. Given transaction type is "
						+ _input.transactionType + " and Category type is " + c.type);
			}
		} else {
			throw new Exception("Transaction category must be a valid category from Clanily application. "
					+ _input.categoryId + " : is not a valid category");
		}

		// validate transaction user
		if (isValid(_input.transactionUserId)) {
			User user = UserService.getInstance().getById(_input.transactionUserId);
			if (user == null) {
				throw new Exception("Transaction user cannot be empty. It must be a valid Clanily user id");
			}
		} else {
			throw new Exception("Transaction user cannot be empty.");
		}

		// validate payee details
		if (_input.payeeId > 0) {
			Payee p = PayeeService.getInstance().getById(_input.payeeId);
			if (p == null) {
				throw new Exception("Payee details must be a registered payee of Clanily");
			}
		} else {
			_input.payeeId = 1;
		}

		// validate objective details
		if (_input.objectiveId > 0) {
			Objective o = ObjectiveService.getInstance().getById(_input.objectiveId);
			if (o == null) {
				throw new Exception("Objective details must be a registered Objective of Clanily");
			}
		} else {
			_input.objectiveId = 1;
		}

		// validate loan details
		_input.loanId = 1;

		return TransactionDBTranslator.getInstance().update(value);
	}

	@Override
	public boolean updateAll(List<ValueObject> values) throws Exception {
		return TransactionDBTranslator.getInstance().updateAll(values);
	}

	@Override
	public boolean delete(int id) throws Exception {
		return TransactionDBTranslator.getInstance().delete(id);
	}

	@Override
	public boolean deleteAll(List<Integer> ids) throws Exception {
		return TransactionDBTranslator.getInstance().deleteAll(ids);
	}

	public void importTransactionsTemp(List<String[]> values) throws Exception {
		/*
		 * Transaction t = null; int index = 0; // 2023-12-17T06:30:00+00:00
		 * SimpleDateFormat dt = new SimpleDateFormat("yyyy-M-d'T'HH:mm:ss+HH:mm");
		 * 
		 * int accountId = 3; int payeeId = 1; int projectId = 1; String
		 * transactionUserId = "venkatramanp"; int objectiveId = 1; int loanId = 1;
		 * String beneficiary = "Family";
		 * 
		 * for (String[] array : values) { if (index == 0) { index++; continue; }
		 * 
		 * t = new Transaction();
		 * 
		 * t.summary = array[6]; t.transactionDate = dt.parse(array[0]); t.categoryId =
		 * findCategoryId(array[3], array[4]); t.transactionType = array[2]; t.accountId
		 * = accountId; t.transactionAmount = Float.parseFloat(array[4]); t.payeeId =
		 * payeeId; t.notes = t.summary; t.projectId = projectId; t.transactionUserId =
		 * transactionUserId; t.objectiveId = objectiveId; t.importedNotes = t.summary;
		 * 
		 * // reduce summary if (t.summary.length() > 50) { t.summary =
		 * t.summary.substring(0, 49); }
		 * 
		 * insert(t);
		 * 
		 * }
		 */

	}

	/*
	 * 
	 * private int findCategoryId(String c, String amount) { int cat = 1; if
	 * (!isValid(c)) { return 3; } switch (c) { case "Bank Interest": { cat = 75;
	 * break; } case "Gift Income": case "Benefits": case "Extra Income": { cat =
	 * 78; break; } case "Borrow": { cat = 7; break; } case "Lend Return": { cat =
	 * 6; break; }
	 * 
	 * case "Parents Payback": case "Return Income": { cat = 6; break; }
	 * 
	 * case "Rental Income": { cat = 76; break; }
	 * 
	 * case "Salary": { cat = 77; break; }
	 * 
	 * case "Donation": { cat = 59; break; } case "Education": { cat = 60; break; }
	 * case "Entertainment": { cat = 61; break;
	 * 
	 * } case "Family and Personal": { cat = 62; break; } case "Healthcare": { cat =
	 * 63; break; } case "Home Daily Needs": { cat = 64; break; } case
	 * "Home Maintenance": { cat = 65; break; } case "Lend": { cat = 5; break; }
	 * case "Mobile and Broadband": { cat = 66; break; } case "Outside Food": { cat
	 * = 67; break; } case "Parent Expense": { cat = 79; break; } case
	 * "Personal Grooming": { cat = 68; break; } case "Rental Expense": { cat = 69;
	 * break; } case "Shopping": { cat = 70; break; } case "Subscriptions": { cat =
	 * 71; break; } case "Tax and Surcharges": { cat = 72; break; } case
	 * "Temp transfer": { float value = Float.parseFloat(amount); if (value < 0) {
	 * cat = 4; } else { cat = 3; } } case "Travel": case "Trip": { cat = 73; break;
	 * } case "Vehicle Maintenance": { cat = 74; break; }
	 * 
	 * default: { float value = Float.parseFloat(amount); if (value < 0) { cat = 1;
	 * } else { cat = 2; } }
	 * 
	 * }
	 * 
	 * return cat; }
	 * 
	 */
}
