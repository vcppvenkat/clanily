<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
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
					<small>View summary of transactions</small>

				</div>
			</div>
		</div>

		<div class="content">

			<jsp:include page="../showMessage.jsp"></jsp:include>

			<div class="row">
				<div class="col-lg-9">

					<!-- Page level actions -->
					<jsp:include page="transactionSummaryHeader.jsp"></jsp:include>


					<form:form action="/transactions/searchTransactions" method="get" modelAttribute="search">
						<div class="row m-t-md">
							<div class="input-group">
								<span class="input-group-addon"> Search </span>
								<form:input class="form-control" path="summary"></form:input>
								<span class="input-group-btn">
									<button type="submit" class="btn btn-primary">Go!</button>
								</span>
							</div>
						</div>
					</form:form>


					<div class="row m-t-md">

						<table class="table toggle-arrow-tiny">
							<thead>
								<tr class="bg-primary">
									<th></th>
									<th><input type="checkbox" /></th>
									<th><a class="text-white" href="#">Summary </a></th>

									<c:if test="${search.currentTransactionGroup ne 'Date' }">
										<th><a class="text-white" href="#">Date </a></th>
									</c:if>

									<th><a class="text-white" href="#">Amount </a></th>
									<c:if test="${search.currentTransactionGroup ne 'Category' }">
										<th><a class="text-white" href="#">Category </a></th>
									</c:if>


									<th></th>
								</tr>
							</thead>
							<tbody>

								<c:set var="tempGroupValue" value="abcd"></c:set>
								<c:set var="groupRowCount" value="0" scope="page"></c:set>
								<c:forEach items="${transactions}" var="t">

									<c:if test="${ tempGroupValue ne t.displayGroupValue and search.currentTransactionGroup ne 'None'}">
										<c:set var="groupRowCount" value="${groupRowCount + 1}" scope="page" />
										<tr class="bg-yellow-light font-bold">
											<td><a name="GroupRow${groupRowCount}" class="GroupRow"><i class="fa fa-minus"></i></a></td>
											<td><input type="checkbox" /></td>

											<td class="text-primary">${t.displayGroupValue}</td>
											<c:if test="${search.currentTransactionGroup ne 'Date' }">
												<td></td>
											</c:if>

											<td>${t.sumOfMergedTransactions }</td>
											<td></td>
											<c:if test="${search.currentTransactionGroup ne 'Category' }">
												<td></td>
											</c:if>

										</tr>
										<c:set var="tempGroupValue" value="${t.displayGroupValue}"></c:set>


									</c:if>

									<c:if test="${t.cleared eq true }">
										<tr class=" bg-light GroupRow${groupRowCount}" id="">
									</c:if>
									<c:if test="${t.cleared eq false }">
										<tr class="text-muted font-strikethrough bg-light GroupRow${groupRowCount}" id="">
									</c:if>

										
										<c:choose>
											<c:when test="${fn:length(t.mergeTransactions) > 0}">
												<td><a name="GroupRow-MT-${t.transactionId}" class="GroupRow"><i class="fa fa-minus"></i></a></td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
										<td><input type="checkbox" /></td>
										<td><a href="/transactions/viewTransaction?transactionId=${t.transactionId}">${t.summary}</a></td>
										<c:if test="${search.currentTransactionGroup ne 'Date' }">
											<td>${t.transactionDateString}</td>
										</c:if>
	
										<c:choose>
											<c:when test="${t.transactionType eq 'Expense'}">
												<td class="text-danger">${t.transactionAmountString}</td>
											</c:when>
											<c:when test="${t.transactionType eq 'Income' }">
												<td class="text-success">${t.transactionAmountString}</td>
											</c:when>
											<c:when test="${t.transactionType eq 'Incoming Transfer' or  t.transactionType eq 'Outgoing Transfer'}">
												<td class="text-info">${t.transactionAmountString}</td>
											</c:when>
										</c:choose>
	
										<c:if test="${search.currentTransactionGroup ne 'Category' }">
											<td>${t.categoryName}</td>
										</c:if>
	
	
										<td>
											<div class="btn-group">
												<a data-toggle="dropdown" class="link dropdown-toggle">...</a>
												<ul class="dropdown-menu">
													<li><a href="#">Edit Transaction</a></li>
													<li><a href="#">Copy and Clone</a></li>
													<li><a href="#">Mark as Cleared</a></li>
													<li class="divider"></li>
													<li><a href="#">Group</a></li>
													<li><a href="#">Split</a></li>
													<li class="divider"></li>
													<li><a href="#">Delete</a></li>
												</ul>
											</div>
										</td>
									</tr>

									<c:if test="${fn:length(t.mergeTransactions) > 0}">
										<c:forEach items="${t.mergeTransactions}" var="mergedTransaction">
											<tr class="bg-light GroupRow${groupRowCount} GroupRow-MT-${t.transactionId}">
												<td></td>
												<td><input type="checkbox" /></td>
												<td><a href="/transactions/viewTransaction?transactionId=${t.transactionId}">${mergedTransaction.summary}</a></td>
												<c:if test="${search.currentTransactionGroup ne 'Date' }">
													<td>${mergedTransaction.transactionDateString}</td>
												</c:if>
			
												<c:choose>
													<c:when test="${mergedTransaction.transactionType eq 'Expense'}">
														<td class="text-danger">${mergedTransaction.transactionAmountString}</td>
													</c:when>
													<c:when test="${mergedTransaction.transactionType eq 'Income' }">
														<td class="text-success">${mergedTransaction.transactionAmountString}</td>
													</c:when>
													<c:when test="${mergedTransaction.transactionType eq 'Incoming Transfer' or  mergedTransaction.transactionType eq 'Outgoing Transfer'}">
														<td class="text-info">${mergedTransaction.transactionAmountString}</td>
													</c:when>
												</c:choose>
			
												<c:if test="${search.currentTransactionGroup ne 'Category' }">
													<td>${mergedTransaction.categoryName}</td>
												</c:if>
			
			
												<td>
													<div class="btn-group">
														<a data-toggle="dropdown" class="link dropdown-toggle">...</a>
														<ul class="dropdown-menu">
															<li><a href="#">Edit Transaction</a></li>
															<li><a href="#">Copy and Clone</a></li>
															<li><a href="#">Mark as Cleared</a></li>
															<li class="divider"></li>
															<li><a href="#">Group</a></li>
															<li><a href="#">Split</a></li>
															<li class="divider"></li>
															<li><a href="#">Delete</a></li>
														</ul>
													</div>
												</td>
											</tr>
										</c:forEach>
									</c:if>




								</c:forEach>



							</tbody>

						</table>

					</div>
				</div>
				<div class="col-lg-3" style="margin-right: 0%; padding-right: 0%;">
					<jsp:include page="accountSummary.jsp"></jsp:include>
				</div>
			</div>

		</div>

		<jsp:include page="./transactionFooter.jsp"></jsp:include>
	</div>

	<jsp:include page="../scripts.jsp"></jsp:include>

	<div class="modal fade" id="deleteInfoModal" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="color-line"></div>
				<div class="modal-header text-center">
					<h4 class="modal-title">Feature not supported</h4>
					<small class="font-bold">Category delete may harm with some data integration</small>
				</div>
				<div class="modal-body">
					<p>
						<strong>Deleting a category</strong> will make some data uncertain. Hence the application restricts deleting the category information as of now. This
						feature may be included in the future.
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(document).on("click", ".GroupRow", function() {
			var clickedID = $(this).attr('name');
			$('.' + clickedID).toggleClass("hide");

		});
	</script>
</body>
</html>