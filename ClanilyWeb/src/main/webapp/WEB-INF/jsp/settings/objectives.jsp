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
					<h2 class="font-light m-b-xs">Manage Objectives</h2>
					<small>View summary of objective details</small>

				</div>
			</div>
		</div>

		<div class="content">

			<jsp:include page="../showMessage.jsp"></jsp:include>




			<div class="row">
				<div class="col-lg-9">

					<c:if test="${empty values}">
	
						<div class="error-container">

							<h1 class="h-100 text-danger">No data found</h1>
							<div class="font-extra-bold">
								<p>Sorry. There are no data available for this page</p>
							</div>
							<p class="text-muted">You can add new objective information by clicking the + New Objective button at the right side of this page.</p>
						</div>

					</c:if>

					<c:if test="${not empty values }">

						<div>
							<input type="text" class="form-control m-b-md" id="filter" placeholder="Search in table">
							<table id="example1" class="footable table table-stripped toggle-arrow-tiny" data-page-size="100" data-filter=#filter>
								<thead>
									<tr>
										<th class="text-left" data-toggle="true">Objective Name</th>
										<th class="text-center">Transactions</th>
										<th class="text-center">Amount</th>
										<th class="text-center"></th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="obj" items="${values}">

										<td><a href="/objective/viewobjective?objectiveId=${obj.objectiveId}">${obj.objectiveName}</a></td>

										<td class="text-center">1,276</td>
										<td class="text-center">12,678.28</td>
										<td class="text-center">

											<div class="btn-group">
												<a data-toggle="dropdown" class="link dropdown-toggle">...</a>
												<ul class="dropdown-menu">
													<li><a href="/objective/viewObjective?objectiveId=${obj.objectiveId}">View</a></li>
													<li><a href="/objective/loadEditObjectivePage?objectiveId=${obj.objectiveId}">Edit</a></li>

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
					</c:if>


				</div>
				<div class="col-lg-3">

					<div class="hpanel">
						<div class="panel-heading">
							<a class="btn btn-primary btn-block" href="/objective/loadNewObjectivePage">+ New Objective</a>
						</div>
					</div>
					<div class="hpanel">
						<div class="panel-body">
							<div class="stats-title pull-left">
								<h4>Total</h4>
							</div>
							<div class="stats-icon pull-right">
								<i class="pe-7s-shuffle fa-4x"></i>
							</div>
							<div class="m-t-xl">
								<h1 class="text-info">0</h1>
								<small> Total objectives including the default unknown objective, of which <strong>1,23,234.89</strong> rupees were spent in the overall time.
								</small>
							</div>
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
					<small class="font-bold">Objective delete may harm with some data integration</small>
				</div>
				<div class="modal-body">
					<p>
						<strong>Deleting an objective</strong> will make some data uncertain. Hence the application restricts deleting the category information as of now. This feature
						may be included in the future.
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