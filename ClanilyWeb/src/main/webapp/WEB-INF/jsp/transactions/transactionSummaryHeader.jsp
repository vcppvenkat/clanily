<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<div class="row  m-b-sm">
	<div class="col-lg-2">
		<div class="btn-group">
			<button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
				Action <span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li><a href="/transactions/loadNewTransactionPage">New transaction</a></li>
				<li><a href="/importTransactions/loadImportTransactionPage">Import files</a></li>
				<li><a href="#">Sync all accounts</a></li>
				<li class="divider"></li>
				<li><a href="#">Edit (10)</a></li>
			</ul>
		</div>
	</div>

	<div class="col-lg-2">
		<div class="btn-group">
			<button data-toggle="dropdown" class="btn btn-primary btn-outline dropdown-toggle">
				Filters <span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li class="dropdown-header active">View</li>
				<c:if test="${search.currentTransactionView eq 'Income'}">
					<li class="active"><a href="/transactions/filter/view/income">Income</a></li>
				</c:if>
				<c:if test="${search.currentTransactionView ne 'Income'}">
					<li><a href="/transactions/filter/view/income">Income</a></li>
				</c:if>
				
				<c:if test="${search.currentTransactionView eq 'Expense'}">
					<li class="active"><a href="/transactions/filter/view/expense">Expense</a></li>
				</c:if>
				<c:if test="${search.currentTransactionView ne 'Expense'}">
					<li><a href="/transactions/filter/view/expense">Expense</a></li>
				</c:if>
				
				<c:if test="${search.currentTransactionView eq 'Income, Expense'}">
					<li class="active"><a href="/transactions/filter/view/incomeAndExpense">Income, Expense</a></li>
				</c:if>
				<c:if test="${search.currentTransactionView ne 'Income, Expense'}">
					<li><a href="/transactions/filter/view/incomeAndExpense">Income, Expense</a></li>
				</c:if>
				
				<c:if test="${search.currentTransactionView eq 'Transfers'}">
					<li class="active"><a href="/transactions/filter/view/transfer">Transfers</a></li>
				</c:if>
				<c:if test="${search.currentTransactionView ne 'Transfers'}">
					<li><a href="/transactions/filter/view/transfer">Transfers</a></li>
				</c:if>
				



				<li class="divider"></li>
				<li class="dropdown-header">Group</li>
				
				<c:if test="${search.currentTransactionGroup eq 'Date'}">
					<li class="active"><a href="/transactions/group/date">Date</a></li>
				</c:if>
				<c:if test="${search.currentTransactionGroup ne 'Date'}">
					<li><a href="/transactions/group/date">Date</a></li>
				</c:if>
				
				<c:if test="${search.currentTransactionGroup eq 'Category'}">
					<li class="active"><a href="/transactions/group/category">Category</a></li>
				</c:if>
				<c:if test="${search.currentTransactionGroup ne 'Category'}">
					<li><a href="/transactions/group/category">Category</a></li>
				</c:if>
				
				<c:if test="${search.currentTransactionGroup eq 'Payee'}">
					<li class="active"><a href="/transactions/group/payee">Payee</a></li>
				</c:if>
				<c:if test="${search.currentTransactionGroup ne 'Payee'}">
					<li><a href="/transactions/group/payee">Payee</a></li>
				</c:if>
				
				<c:if test="${search.currentTransactionGroup eq 'Loan'}">
					<li class="active"><a href="/transactions/group/loan">Loan</a></li>
				</c:if>
				<c:if test="${search.currentTransactionGroup ne 'Loan'}">
					<li><a href="/transactions/group/loan">Loan</a></li>
				</c:if>
				
				<c:if test="${search.currentTransactionGroup eq 'No groups'}">
					<li class="active"><a href="/transactions/group/noGroup">No groups</a></li>
				</c:if>
				<c:if test="${search.currentTransactionGroup ne 'No groups'}">
					<li><a href="/transactions/group/noGroup">No groups</a></li>
				</c:if>
				

				<li class="divider"></li>
				
				<c:if test="${search.includePending eq true}">
					<li class="active"><a href="/transactions/filter/includePending">Include Pending</a></li>
				</c:if>
				<c:if test="${search.includePending eq false}">
					<li><a href="/transactions/filter/includePending">Include Pending</a></li>
				</c:if>
				


				<li class="divider"></li>
				
				<li><a href="/transactions/resetAllFilters">Reset to default</a></li>
			</ul>
		</div>
	</div>


	<div class="col-lg-5" align="center">

		<div class="input-group m-r-sm">

			<a href="/transactions/filter/calendar/previous" class="btn btn-default"> < </a>
			<div class="btn-group">
				<button data-toggle="dropdown" class="btn btn-primary2 dropdown-toggle btn-block">${search.currentDateRange }</button>
				<ul class="dropdown-menu">
					<li><a href="/transactions/filter/date">Date</a></li>
					<li><a href="/transactions/filter/month">Month</a></li>
					<li><a href="/transactions/filter/year">Year</a></li>
					<li><a href="/transactions/filter/week">Week</a></li>
					<li class="divider"></li>
					<li><a href="#">Custom</a></li>
					<li><a href="/transactions/filter/resetCalendarFilter">Reset to default</a></li>
				</ul>
			</div>
			<a href="/transactions/filter/calendar/next" class="btn btn-default"> > </a>
		</div>
	</div>



	<div class="col-lg-3" align="right">
		<div class="btn-group">
			<button data-toggle="dropdown" class="btn btn-primary btn-outline dropdown-toggle m-r-sm">
				Theme <span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<c:if test="${search.currentTransactionTheme eq 'Transactions'}">
					<li class="active"><a href="/transactions/">Transactions</a></li>
				</c:if>
				<c:if test="${search.currentTransactionTheme ne 'Transactions'}">
					<li><a href="/transactions/">Transactions</a></li>
				</c:if>
				
				<c:if test="${search.currentTransactionTheme eq 'Overview'}">
					<li class="active"><a href="transactions_overview.html">Overview</a></li>
				</c:if>
				<c:if test="${search.currentTransactionTheme ne 'Transactions'}">
					<li><a href="transactions_overview.html">Overview</a></li>
				</c:if>
				
				
				<li><a href="#">Recent imports</a></li>
				<li class="divider"></li>

				<li><a href="#">Trends</a></li>
				<li><a href="#">Set to default</a></li>
			</ul>
		</div>

	</div>



</div>