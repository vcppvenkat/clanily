<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ page session="true"%>

<html>
<head>

<jsp:include page="../header.jsp"></jsp:include>

</head>
<body class="fixed-navbar sidebar-scroll">



	<jsp:include page="../PageTopBanner.jsp"></jsp:include>
	<jsp:include page="../PageLeftMenu.jsp"></jsp:include>

	<div id="wrapper">

		<div class="normalheader small-header">
			<div class="hpanel">
				<div class="panel-body">
					<h2 class="font-light m-b-xs">Manage Categories</h2>
					<small>Add new category</small>
				</div>
			</div>
		</div>

		<div class="content">


			<jsp:include page="../showMessage.jsp"></jsp:include>




			<div class="row">
				<form:form action="/transactions/addNewTransaction" method="post" cssClass="form-horizontal" modelAttribute="transaction">
					<div class="col-lg-6">
						<div class="hpanel">

							<div class="panel-heading">

								<div class="form-group">
									<label class="col-sm-3 control-label">Summary</label>

									<div class="col-sm-9">
										<form:input path="summary" id="summaryTxt" cssClass="form-control" placeholder="Maximum 50 chars" />

									</div>
								</div>
								<div class="hr-line-dashed"></div>
								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-3">
												<label class=" control-label pull-right">Type</label>
											</div>

											<div class="col-lg-9">
												<form:hidden path="transactionType" id="currentTransactionType" />
												<c:if test="${transaction.transactionType eq 'Income' }">
													<a class="btn btn-primary2 m-r-sm " id="transactionTypeIncome">Income</a>
												</c:if>
												<c:if test="${transaction.transactionType ne 'Income' }">
													<a class="btn btn-primary2 btn-outline m-r-sm " id="transactionTypeIncome">Income</a>
												</c:if>

												<c:if test="${transaction.transactionType eq 'Expense' }">
													<a class="btn btn-primary2  m-r-sm" id="transactionTypeExpense">Expense</a>
												</c:if>
												<c:if test="${transaction.transactionType ne 'Expense' }">
													<a class="btn btn-primary2 btn-outline m-r-sm " id="transactionTypeExpense">Expense</a>
												</c:if>

												<c:if test="${transaction.transactionType eq 'Transfer' }">
													<a class="btn btn-primary2 m-r-sm " id="transactionTypeTransfer">Transfer</a>
												</c:if>
												<c:if test="${transaction.transactionType ne 'Transfer' }">
													<a class="btn btn-primary2 btn-outline m-r-sm " id="transactionTypeTransfer">Transfer</a>
												</c:if>




											</div>
										</div>
									</div>

								</div>
								<div class="hr-line-dashed"></div>
								<div class="form-group">
									<label class="col-sm-3 control-label">Account</label>

									<div class="col-sm-9">
										<div class="col-lg-6 no-padding">
											<form:select path="accountId" id="fromAccountSelect" cssClass="js-source-states" style="width: 85%;">

												<c:forEach items="${accounts}" var="account">
													<form:option value="${account.accountId}">${account.accountName}</form:option>
												</c:forEach>

											</form:select>

										</div>
										<div class="col-lg-6" style="padding-right: 0%;">

											<form:select path="toAccountId" id="toAccountSelect" cssClass="js-source-states" style="width: 100%;">

												<c:forEach items="${accounts}" var="account">
													<form:option value="${account.accountId}">${account.accountName}</form:option>
												</c:forEach>

											</form:select>

										</div>

									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">Amount</label>

									<div class="col-sm-9">
										<form:input path="transactionAmountString" cssClass="form-control" placeholder="Eligible currency value {0.00}"></form:input>


									</div>

								</div>
								<div class="form-group">
									<label class="col-sm-3 control-label">Date</label>

									<div class="col-sm-9">
										<div class="input-group input-daterange" id="datepicker1">
											<span class="input-group-addon"> <span class="fa fa-calendar"></span>
											</span>
											<form:input path="transactionDateString" readonly="true" cssClass="form-control"></form:input>
										</div>

									</div>

								</div>

								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-3">
												<label class=" control-label pull-right">Category</label>
											</div>

											<div class="col-lg-9">
												<form:select path="categoryId" id="transactionTypeSelect" cssClass="js-source-states" style="width: 85%;">
													<c:if test="${transaction.transactionType eq 'Income'}">
														<c:forEach items="${incomeCategories}" var="category">
															<form:option value="${category.categoryId}">${category.categoryName}</form:option>
														</c:forEach>
													</c:if>

													<c:if test="${transaction.transactionType eq 'Expense'}">
														<c:forEach items="${expenseCategories}" var="category">
															<form:option value="${category.categoryId}">${category.categoryName}</form:option>
														</c:forEach>
													</c:if>

													<c:if test="${transaction.transactionType eq 'Transfer'}">
														<c:forEach items="${transferCategories}" var="category">
															<form:option value="${category.categoryId}">${category.categoryName}</form:option>
														</c:forEach>
													</c:if>

												</form:select>
											</div>
										</div>
									</div>

								</div>


								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-3">
												<label class=" control-label pull-right">Transaction User</label>
											</div>

											<div class="col-lg-9">
												<form:select path="transactionUserId" id="transactionUserIdSelect" cssClass="js-source-states" style="width: 85%;">

													<c:forEach items="${users}" var="user">
														<form:option value="${user.userId}">${user.userName}</form:option>
													</c:forEach>

												</form:select>

											</div>
										</div>
									</div>

								</div>

							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="hpanel">

							<div class="panel-heading">


								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-3">
												<label class=" control-label pull-right">Payee</label>
											</div>

											<div class="col-lg-9">

													<form:input path="payeeName" cssClass="form-control" list="payeeList"></form:input>
													<datalist id="payeeList"> 
													<c:forEach items="${payees}" var="payee">
														<option value="${payee.payeeName}"></option>
													</c:forEach> </datalist>

											</div>
										</div>
									</div>

								</div>

								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-3">
												<label class=" control-label pull-right">Objectives</label>
											</div>

											<div class="col-lg-9">
												<form:input path="objectiveName" cssClass="form-control" list="objectiveList"></form:input>
													<datalist id="objectiveList"> 
													<c:forEach items="${objectives}" var="objective">
														<option value="${objective.objectiveName}"></option>
													</c:forEach> </datalist>
											</div>
										</div>
									</div>

								</div>

								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-3">
												<label class=" control-label pull-right">Project</label>
											</div>

											<div class="col-lg-9">

												<select disabled="disabled" class="js-source-states" style="width: 85%;">
													<option value="AK">Unknown Project</option>

												</select> <a class="btn btn-link">Add</a>
											</div>
										</div>
									</div>

								</div>

								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-3">
												<label class=" control-label pull-right">Loan</label>
											</div>

											<div class="col-lg-9">
												<select name="loanId" disabled="disabled" class="js-source-states" style="width: 85%;">
													<optgroup label="Need">
														<option value="1">Home Daily Needs</option>
														<option value="HI">Home Maintenance</option>
														<option value="HI">Vehicle Maintenance</option>
													</optgroup>
													<optgroup label="Want">
														<option value="CA">EB Bill</option>
														<option value="NV">Home Rent</option>
													</optgroup>

													<optgroup label="Others">
														<option value="AL">Lend</option>
														<option value="AR">Subscriptions</option>
														<option value="IL">Personal Grooming</option>

													</optgroup>

												</select> <a class="btn btn-link">Add</a>
											</div>
										</div>
									</div>

								</div>

								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-3">
												<label class=" control-label pull-right">Clearence</label>
											</div>

											<div class="col-lg-9">
												<div class="checkbox checkbox-success">
													<input type="checkbox" name="cleared" id="checkbox3" <c:if test="${transaction.cleared}">
														checked
													</c:if> /> <label
														for="checkbox3"> Cleared </label>
												</div>
											</div>
										</div>
									</div>

								</div>

								<div class="form-group">
									<label class="col-sm-3 control-label">Notes</label>

									<div class="col-sm-9">
										<form:textarea path="notes" cssClass="form-control" placeholder="Maximum 5000 chars" rows="4" />

									</div>

								</div>

							</div>
						</div>

					</div>
					<div class="hr-line-solid"></div>
					<div class="col-lg-12">
						<div class="hpanel">
							<div class="panel-heading">
								<h5 class="font-extra-bold">Special parameters</h5>
							</div>
							<div class="col-lg-3">
								<div class="checkbox checkbox-success">
									<input id="field1" type="checkbox"> <label for="field1"> Field 1 </label>
								</div>
							</div>
							<div class="col-lg-3">
								<div class="checkbox checkbox-success">
									<input id="field2" type="checkbox"> <label for="field2"> Field 2 </label>
								</div>
							</div>
							<div class="col-lg-3">
								<div class="checkbox checkbox-success">
									<input id="field3" type="checkbox"> <label for="field3"> Field 3 </label>
								</div>
							</div>

						</div>
					</div>

					<div class="col-lg-12">
						<div class="hr-line-solid"></div>
						<div class="hpanel">
							<div class="panel-heading">
								<button class="btn btn-default" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false"
									aria-controls="collapseExample">Transaction Recurrence</button>
							</div>

							<div class="collapse" id="collapseExample">
								<div class="panel-heading">

									<div class="form-group">
										<label class="col-sm-2 control-label">Repeat Every</label>

										<div class="col-sm-10">
											<button class="btn btn-primary2">Day</button>
											&nbsp;
											<button class="btn btn-primary2 btn-outline">Week</button>
											&nbsp;
											<button class="btn btn-primary2 btn-outline">On a Week Day</button>
											&nbsp;
											<button class="btn btn-primary2 btn-outline">Month</button>
											&nbsp;
											<button class="btn btn-primary2 btn-outline">3 Months</button>
											&nbsp;

											<button class="btn btn-primary2 btn-outline">6 Months</button>
											&nbsp;
											<button class="btn btn-primary2 btn-outline">Year</button>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">Period</label>

										<div class="col-sm-4">
											<div class="input-daterange input-group" id="datepicker">
												<input type="text" class="input-sm form-control" name="start"> <span class="input-group-addon">to</span> <input type="text"
													class="input-sm form-control" name="end">
											</div>
										</div>

									</div>

									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<label class="col-sm-2 control-label">Summary</label>

										<div class="col-sm-10">

											<label class="font-bold m-t-xs"> Occurs every weekday Sunday from 01-Jan-2017 to 31-Dec-2023 </label>
										</div>
									</div>


								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-12">
						<div class="hr-line-solid"></div>
						<div class="hpanel">
							<div class="panel-heading">
								<div class="form-group">
									<div class="col-lg-12">
										<a class="btn btn-default" href="/transactions/">Discard</a>


										<div class="pull-right">
											<button type="submit" onclick="addTransactionForm();return true;" class="btn btn-primary pull-right m-l-sm">Add Transaction</button>
											<div class="checkbox checkbox-success pull-right">
												<input id="addAnotherTransactionCheck" type="checkbox"> <label for="addAnotherTransactionCheck"> Add another transaction </label>
											</div>

										</div>

									</div>
								</div>

							</div>
						</div>
					</div>
				</form:form>
			</div>

		</div>





		<jsp:include page="../footer.jsp"></jsp:include>
	</div>

	<jsp:include page="../scripts.jsp"></jsp:include>


	<script>
		$(function() {

			$(".js-source-states").select2();

			$('#transactionTypeIncome')
					.on(
							'click',
							function() {

								$('#transactionTypeSelect').empty();

								$('#transactionTypeIncome').removeClass(
										'btn-outline');
								$('#transactionTypeExpense').addClass(
										'btn-outline');
								$('#transactionTypeTransfer').addClass(
										'btn-outline');
								document
										.getElementById('currentTransactionType').value = 'Income';

								<c:forEach items="${incomeCategories}" var="category">
								$('#transactionTypeSelect')
										.append(
												'<option value="${category.categoryId}">${category.categoryName}</option>');
								</c:forEach>

							});

			$('#transactionTypeExpense')
					.on(
							'click',
							function() {
								$('#transactionTypeIncome').addClass(
										'btn-outline');
								$('#transactionTypeExpense').removeClass(
										'btn-outline');
								$('#transactionTypeTransfer').addClass(
										'btn-outline');
								document
										.getElementById('currentTransactionType').value = 'Expense';

								$('#transactionTypeSelect').empty();
								<c:forEach items="${expenseCategories}" var="category">
								$('#transactionTypeSelect')
										.append(
												'<option value="${category.categoryId}">${category.categoryName}</option>');
								</c:forEach>
							});

			$('#transactionTypeTransfer')
					.on(
							'click',
							function() {
								$('#transactionTypeIncome').addClass(
										'btn-outline');
								$('#transactionTypeExpense').addClass(
										'btn-outline');
								$('#transactionTypeTransfer').removeClass(
										'btn-outline');
								document
										.getElementById('currentTransactionType').value = 'Transfer';

								$('#transactionTypeSelect').empty();
								<c:forEach items="${transferCategories}" var="category">
								$('#transactionTypeSelect')
										.append(
												'<option value="${category.categoryId}">${category.categoryName}</option>');
								</c:forEach>
							});

			$('.input-daterange').datepicker({});

		});

		function addTransactionForm() {
			document.transaction.action = '/transactions/addNewTransaction';
		}
		function addNewPayeeForm() {
			document.transaction.action = '/transactions/addNewPayee';
		}
	</script>

</body>
</html>