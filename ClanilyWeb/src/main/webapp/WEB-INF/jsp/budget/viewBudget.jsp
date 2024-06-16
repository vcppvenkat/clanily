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

<body class="fixed-navbar sidebar-scroll">

	<jsp:include page="../PageTopBanner.jsp"></jsp:include>
	<jsp:include page="../PageLeftMenu.jsp"></jsp:include>

	<!-- Main Wrapper -->
	<div id="wrapper">
		<div class="normalheader small-header">
			<div class="hpanel">
				<div class="panel-body">
					<h2 class="font-light m-b-xs">Budget</h2>
					<small>Manage your budgets</small>
				</div>
			</div>
		</div>
		<div class="content">

			<div class="row">
				<div class="col-lg-12">
					<a class="btn btn-primary2" href="budget.html">Category Budget</a>
					<a class="btn btn-default" href="budget_yearly.html">Zero
						Balance Budget</a> <a class="btn btn-default"
						href="budget_analytics.html">Analytics</a> <a
						class="btn btn-primary pull-right" href="budget_add.html">Add
						New Budget</a>
				</div>

			</div>
			<div class="hr-line-dashed"></div>
			<div class="row ">
				<div class="col-lg-4">
					<div class="hpanel stats animated flipInX">
						<div class="panel-body h-200">
							<div class="stats-title pull-left">
								<h4 class="text-primary">Monthly Groceries</h4>
							</div>

							<div class="m-t-xl">
								<h3 class="font-bold  m-b-xs">15,000</h3>
								<span class="no-margins"> Current progress </span>

								<div class="progress m-t-xs full progress-small">
									<div style="width: 55%" aria-valuemax="100" aria-valuemin="0"
										aria-valuenow="55" role="progressbar"
										class=" progress-bar progress-bar-success">
										<span class="sr-only">35% Complete (success)</span>
									</div>
								</div>

								<div class="row">
									<div class="col-xs-4">
										<small class="stats-label">Completed</small>
										<h4>7,569.00</h4>
									</div>

									<div class="col-xs-4">
										<small class="stats-label">Remaining</small>
										<h4>7,456.00</h4>
									</div>
									<div class="col-xs-4">
										<small class="stats-label">% </small>
										<h4>55 %</h4>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-footer">You are inline with this budget
							this month</div>
					</div>
				</div>


				<div class="col-lg-4">
					<div class="hpanel stats animated flipInX">
						<div class="panel-body h-200">
							<div class="stats-title pull-left">
								<h4 class="text-primary">Outside food</h4>
							</div>

							<div class="m-t-xl">
								<h3 class="font-bold  m-b-xs">5,000</h3>
								<span class="no-margins"> Current progress </span>

								<div class="progress m-t-xs full progress-small">
									<div style="width: 100%" aria-valuemax="100" aria-valuemin="0"
										aria-valuenow="125" role="progressbar"
										class=" progress-bar progress-bar-danger">
										<span class="sr-only">35% Complete (success)</span>
									</div>
								</div>

								<div class="row">
									<div class="col-xs-4">
										<small class="stats-label">Completed</small>
										<h4>7,569.00</h4>
									</div>

									<div class="col-xs-4">
										<small class="stats-label">Remaining</small>
										<h4>7,456.00</h4>
									</div>
									<div class="col-xs-4">
										<small class="stats-label">% </small>
										<h4>125 %</h4>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-footer">You have spent more than 1,235
							this month</div>
					</div>
				</div>


				<div class="col-lg-4">
					<div class="hpanel stats animated flipInX">
						<div class="panel-body h-200">
							<div class="stats-title pull-left">
								<h4 class="text-primary">Monthly Groceries</h4>
							</div>

							<div class="m-t-xl">
								<h3 class="font-bold  m-b-xs">15,000</h3>
								<span class="no-margins"> Current progress </span>

								<div class="progress m-t-xs full progress-small">
									<div style="width: 55%" aria-valuemax="100" aria-valuemin="0"
										aria-valuenow="55" role="progressbar"
										class=" progress-bar progress-bar-success">
										<span class="sr-only">35% Complete (success)</span>
									</div>
								</div>

								<div class="row">
									<div class="col-xs-4">
										<small class="stats-label">Completed</small>
										<h4>7,569.00</h4>
									</div>

									<div class="col-xs-4">
										<small class="stats-label">Remaining</small>
										<h4>7,456.00</h4>
									</div>
									<div class="col-xs-4">
										<small class="stats-label">% </small>
										<h4>55 %</h4>
									</div>
								</div>
							</div>
						</div>
						<div class="panel-footer">You are inline with this budget
							this month</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../footer.jsp"></jsp:include>
	</div>
	<jsp:include page="../scripts.jsp"></jsp:include>
</body>

</html>