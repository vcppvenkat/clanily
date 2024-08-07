<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@page session="true"%>

<html>
<head>

<jsp:include page="../header.jsp"></jsp:include>

</head>
<body class="fixed-navbar sidebar-scroll fixed-footer">



	<jsp:include page="../PageTopBanner.jsp"></jsp:include>
	<jsp:include page="../PageLeftMenu.jsp"></jsp:include>

	<div id="wrapper">

		<div class="normalheader small-header">
			<div class="hpanel">
				<div class="panel-body">
					<h2 class="font-light m-b-xs">Manage Transactions</h2>
					<small>View transactions details</small>

				</div>
			</div>
		</div>

		<div class="content">

			<jsp:include page="../showMessage.jsp"></jsp:include>

			<div class="row">
				<div class="col-lg-12">
					<a class="btn btn-primary m-r-sm">Edit Transaction</a>
				</div>
				<div class="hr-line-dashed"></div>
				<div class="col-lg-4">
					<div class="hpanel">

						<div class="panel-heading">
							<table class="table table-responsive">
								<thead>
									<th colspan="2">Basic details</th>
								</thead>
								<tbody>
									<tr>
										<td class="">Clearence</td>
										<td class=""><c:if test="${transaction.cleared }">Cleared</c:if>
											<c:if test="${not transaction.cleared }">Pending</c:if> <a
											href="/transactions/toggleTransactionClearance?transactionId=${transaction.transactionId}"
											class="pull-right link">Change</a></td>
									</tr>
									<tr>
										<td class="">Summary</td>
										<td class="">${transaction.summary }</td>
									</tr>
									<tr>
										<td>Type</td>
										<td><c:if
												test="${transaction.transactionType eq 'Expense' }">
												<span class="text-danger">
													${transaction.transactionType } </span>
											</c:if> <c:if test="${transaction.transactionType eq 'Income' }">
												<span class="text-success">
													${transaction.transactionType } </span>
											</c:if> <c:if
												test="${transaction.transactionType eq 'Incoming Transfer' or transaction.transactionType eq 'Outgoing Transfer' }">
												<span class="text-info">
													${transaction.transactionType } </span>
											</c:if></td>
									</tr>
									<tr>
										<td>Account</td>
										<td><span class="text-info">
												${transaction.accountName } </span> <c:if
												test="${transaction.transactionType eq 'Incoming Transfer' or transaction.transactionType eq 'Outgoing Transfer' }">
												-> <span class="text-info">
													${transaction.toAccountName } </span>
											</c:if></td>
									</tr>
									<tr>
										<td>Category</td>
										<td>${transaction.categoryName }</td>
									</tr>
									<tr>
										<td>Amount</td>
										<td>${transaction.transactionAmountString }</td>
									</tr>
									<tr>
										<td>Date</td>
										<td>${transaction.transactionDateString }</td>
									</tr>
									<tr>
										<td>User</td>
										<td>${transaction.transactionUserName }</td>
									</tr>
									<tr>
										<td>Payee</td>
										<td>${transaction.payeeName }</td>
									</tr>
									<tr>
										<td>Objective</td>
										<td>${transaction.objectiveName }</td>
									</tr>
									<tr>
										<td>Loan</td>
										<td>${transaction.loanName }</td>
									</tr>
									<tr>
										<td>Project</td>
										<td>${transaction.projectName }</td>
									</tr>

								</tbody>
							</table>
						</div>
						<div class="panel-heading">
							<h6 class="font-bold">Notes</h6>
							<small class="text-muted"> ${transaction.notes } </small>

						</div>
						<div class="panel-heading">
							<table class="table table-responsive">
								<thead>
									<th colspan="2">Meta data details</th>
								</thead>
								<tbody>
									<tr>
										<td>Transaction Id</td>
										<td>${transaction.transactionId }</td>
									</tr>
									<tr>
										<td class="">Addition Type</td>
										<td class="">${transaction.insertionType }</td>
									</tr>
									<tr>
										<td>Date Added</td>
										<td>${transaction.transactionDateString }</td>
									</tr>
									<tr>
										<td>Last Modified</td>
										<td>01-Jan-2024 09:28 AM</td>
									</tr>
									<tr>
										<td>Created User</td>
										<td>${transaction.transactionUserName }</td>
									</tr>



								</tbody>
							</table>
						</div>
						<div class="panel-heading">
							<h6 class="font-bold">Original Notes</h6>
							<small class="text-muted">${transaction.importedNotes } </small>
						</div>
					</div>
				</div>

				<div class="col-lg-8">
					<div class="col-lg-12">
						<div class="hpanel">

							<div class="panel-heading">
								<h5 class="font-bold">Group transactions</h5>
								<div class="hr-line-solid"></div>
								<table class="table table-responsive">
									<thead>
										<th>Summary</th>
										<th>Amount</th>
										<th>Account</th>
										<th>Category</th>
									</thead>
									<tbody>
										<c:forEach items="${transaction.groupTransactions}"
											var="groupTransaction">
											<tr>
												<td>${ groupTransaction.summary }</td>
												<c:if
													test="${groupTransaction.transactionType eq 'Income' }">
													<td class="text-success">${ groupTransaction.transactionAmountString }</td>
												</c:if>
												<c:if
													test="${groupTransaction.transactionType eq 'Expense' }">
													<td class="text-danger">${ groupTransaction.transactionAmountString }</td>
												</c:if>

												<td>${ groupTransaction.accountName }</td>
												<td>${ groupTransaction.categoryName }</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<td colspan="4"><a
												class="btn btn-primary2 btn-outline btn-sm"
												href="/transactions/groupTransaction?f=true&transactionId=${transaction.transactionId}">Group
													transaction</a></td>
										</tr>
									</tfoot>
								</table>
							</div>
							<div class="panel-heading">
								<h5 class="font-bold">Split transactions</h5>
								<div class="hr-line-solid"></div>
								<div class="well">No split transactions found. Please
									click below button to split the transaction</div>
								<a class="btn btn-primary2 btn-outline btn-sm" href="#">Split
									transaction</a>
							</div>

							<div class="panel-heading">
								<h4 class="font-bold">
									Attachments <a
										class="pull-right btn btn-primary2 btn-outline btn-sm"
										href="AttachFile_Details.html">Add attachments</a>
								</h4>
								<div class="hr-line-solid"></div>
								<table class="table table-responsive">
									<thead>
										<tr>
											<th>Summary</th>
											<th>Date added</th>
											<th>Type</th>
											<th></th>
										</tr>

									</thead>
									<tbody>
										<c:forEach items="${transaction.transactionFilesMetaData}"
											var="file">
											<tr>
												<td><a href="">${file.summary }</a></td>
												<td>${file.dateAddedString }</td>
												<td>${file.fileType }</td>
												<td><a href="#"><i class="text-danger pe-7s-trash"></i></a></td>
											</tr>
										</c:forEach>


									</tbody>
								</table>
							</div>
						</div>
					</div>

				</div>



			</div>

		</div>

		<jsp:include page="../footer.jsp"></jsp:include>
	</div>

	<jsp:include page="../scripts.jsp"></jsp:include>

</body>
</html>