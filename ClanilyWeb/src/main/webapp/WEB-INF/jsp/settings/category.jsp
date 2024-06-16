
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@page session="true"%>

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
					<small>View summary of categories</small>

				</div>
			</div>
		</div>

		<div class="content">

			<jsp:include page="../showMessage.jsp"></jsp:include>




			<div class="row">
				<div class="col-lg-9">

					<div>
						<input type="text" class="form-control m-b-md" id="filter" placeholder="Search in table">
						<table id="example1" class="footable table table-stripped toggle-arrow-tiny" data-page-size="100" data-filter=#filter>
							<thead>
								<tr>
									<th class="text-left" data-toggle="true">Category Name</th>
									<th>Tag</th>
									<th class="text-center">Status</th>
									<th class="text-center">Transactions</th>
									<th class="text-center">Amount</th>
									<th class="text-center"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="category" items="${values}">
									<c:choose>
										<c:when test="${not category.active}">
											<tr class="font-strikethrough">
										</c:when>
										<c:otherwise>
											<tr>
										</c:otherwise>
									</c:choose>
									<td><a href="/category/viewCategory?categoryId=${category.categoryId}">${category.categoryName}</a></td>
									<td class="font-bold"><c:choose>
											<c:when test="${category.tag ne null}">
												${category.tag}
											</c:when>
											<c:otherwise>
													...
												</c:otherwise>
										</c:choose></td>
									<td class="text-center"><c:choose>
											<c:when test="${not category.active}">
												<span class="badge badge-danger">Inactive</span>

											</c:when>
											<c:otherwise>
												<span class="badge badge-success">Active</span>
											</c:otherwise>
										</c:choose></td>
									<td class="text-center">1,276</td>
									<td class="text-center">12,678.28</td>
									<td class="text-center">

										<div class="btn-group">
											<a data-toggle="dropdown" class="link dropdown-toggle">...</a>
											<ul class="dropdown-menu">
												<li><a href="/category/viewCategory?categoryId=${category.categoryId}">View</a></li>
												<li><a href="/category/loadEditCategory?categoryId=${category.categoryId}">Edit</a></li>
												<li><c:choose>
														<c:when test="${category.active}">
															<a href="/category/inactivate?categoryId=${category.categoryId }">Inactivate</a>
														</c:when>
														<c:otherwise>
															<a href="/category/activate?categoryId=${category.categoryId }">Activate</a>
														</c:otherwise>
													</c:choose></li>
												<li class="divider"></li>

												<li><a href="#" data-toggle="modal" data-target="#deleteInfoModal">Delete</a></li>
											</ul>
										</div>
									</td>
									</tr>
								</c:forEach>



							</tbody>
							<tfoot>
								<tr>
									<td colspan="7">
										<ul class="pagination pull-right"></ul>
									</td>
								</tr>
							</tfoot>
						</table>
					</div>



				</div>
				<div class="col-lg-3">

					<div class="hpanel">
						<div class="panel-body">

							<ul class="mailbox-list">
								<li class="m-b-md"><a href="/category/loadNewCategoryPage" class="btn btn-primary">+ New Category</a></li>
							</ul>

							<ul class="mailbox-list">

								<c:choose>
									<c:when test="${search.categoryType.equals('Income')}">
										<li class="active">
									</c:when>
									<c:otherwise>
										<li>
									</c:otherwise>
								</c:choose>


								<a href="/category/filterIncome">Income</a>

								</li>

								<c:choose>
									<c:when test="${search.categoryType.equals('Expense')}">
										<li class="active">
									</c:when>
									<c:otherwise>
										<li>
									</c:otherwise>
								</c:choose>

								<a href="/category/filterExpense">Expense</a>

								</li>

							</ul>
							<hr>
							<ul class="mailbox-list">
								<c:choose>
									<c:when test="${empty search.activeStatus}">
										<li class="active">
									</c:when>
									<c:otherwise>
										<li>
									</c:otherwise>
								</c:choose>
								<a href="/category/filterStatusNothing"> Show all status </a>
								</li>

								<c:choose>
									<c:when test="${search.activeStatus == true}">
										<li class="active">
									</c:when>
									<c:otherwise>
										<li>
									</c:otherwise>
								</c:choose>
								<a href="/category/filterStatusActive"> Active </a>
								</li>
								<c:choose>
									<c:when test="${search.activeStatus == false}">
										<li class="active">
									</c:when>
									<c:otherwise>
										<li>
									</c:otherwise>
								</c:choose>
								<a href="/category/filterStatusInactive"> Inactive </a>
								</li>

							</ul>

							<hr>

							<ul class="mailbox-list">

								<c:choose>
									<c:when test="${empty search.categoryTag}">
										<li class="active">
									</c:when>
									<c:otherwise>
										<li>
									</c:otherwise>
								</c:choose>
								<a href="/category/clearTagFilter"> Show all tags </a>
								</li>

								<c:forEach items="${categoryTags }" var="currentTag">
									<c:choose>
										<c:when test="${currentTag.equals(search.categoryTag)}">
											<li class="active">
										</c:when>
										<c:otherwise>
											<li>
										</c:otherwise>
									</c:choose>

									<a href="/category/filterTag?tag=${currentTag}"> ${currentTag } </a>
									</li>

								</c:forEach>




							</ul>

						</div>

					</div>
				</div>
			</div>
		</div>





		<jsp:include page="../footer.jsp"></jsp:include>
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
						<strong>Deleting a category</strong> will make some data uncertain. Hence the application restricts deleting the category information as of now. This feature may be included in the future.
					</p>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<script>
		$(function() {

			// Initialize Example 1
			$('#example1').footable();

			// Initialize Example 2
			$('#example2').footable();

		});
	</script>


</body>
</html>