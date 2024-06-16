
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
					<h2 class="font-light m-b-xs">Manage Accounts</h2>
					<small>View summary of account(s) and edit the details</small>

				</div>
			</div>
		</div>

		<div class="content">









			<c:if test="${empty values }">
				<div class="col-lg-12">
					<div class="error-container">

						<h1 class=" text-danger">No data found</h1>
						<div class="font-extra-bold">
							<p>Sorry. No accounts available in your clan</p>
						</div>
						<p class="text-muted h-100">You can manually add your account details with the below link</p>
						<a class="btn btn-primary btn-lg" href="/accounts/loadNewAccountPage">Add Account</a>
					</div>
				</div>
			</c:if>
			<c:if test="${not empty values  }">
				<div class="row">
					<div class="col-lg-9">

						<jsp:include page="../showMessage.jsp"></jsp:include>

						<div class="row">
							<div class="">
								<h3>
									<strong>${account.accountName}</strong>
								</h3>
								<p>${account.notes }</p>

							</div>

						</div>
						<div class="row">
							<div class="hr-line-solid"></div>
							<a class="btn btn-primary m-r-sm" href="/accounts/loadEditAccountPage?accountId=${account.accountId }">Edit Account Info</a>
							<div class="btn-group m-r-sm">
								<button data-toggle="dropdown disabled" class="btn btn-primary btn-outline dropdown-toggle">
									Sync <span class="caret"></span>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#">Sync now</a></li>
									<li><a href="#" class="font-bold">View sync history</a></li>
									<li><a href="#">Skip next schedule</a></li>
									<li class="divider"></li>
									<li><a href="#">Edit schedule</a></li>
									<li><a href="#">Turn off sync</a></li>
								</ul>
							</div>
							<c:if test="${account.accountVisible == true}">
								<a class="btn btn-primary btn-outline m-r-sm" href="/accounts/hideAccount?accountId=${account.accountId}"> Hide </a>
							</c:if>
							<c:if test="${account.accountVisible == false}">
								<a class="btn btn-primary btn-outline m-r-sm" href="/accounts/showAccount?accountId=${account.accountId}"> Show </a>
							</c:if>


							<div class="hr-line-solid"></div>
						</div>
						<div class="row">
							<div class="col-lg-3 col-md-6">
								<div class="text-center">
									<h5 class="">Current Balance</h5>
									<h3 class="text-primary2 font-bold">${account.currentBalance}</h3>
									<small>As on Not applicable</small>
								</div>
							</div>


							<div class="col-lg-3 col-md-6">
								<div class="text-center">
									<h5 class="">Categories</h5>
									<h3 class="text-primary2 font-bold">--</h3>
									<small>Taken from all transactions</small>
								</div>
							</div>
							<div class="col-lg-3 col-md-6">
								<div class="text-center">
									<h5 class="">Total transactions</h5>
									<h3 class="text-primary2 font-bold">--</h3>
									<small>Taken from all transactions</small>
								</div>
							</div>
							<div class="col-lg-3 col-md-6">
								<div class="text-center">
									<h5 class="">Sync schedule</h5>
									<h3 class="text-primary2 font-bold">---</h3>
									<small>---</small>
								</div>
							</div>

						</div>

						<div class="row">
							<div class="hr-line-solid"></div>
						</div>

						<div class="row">
							<div class="col-lg-6">
								<div class="hpanel animated-panel fadeIn">
									<div class="panel-heading">
										<h4>Basic Details</h4>
									</div>
									<div class="panel-heading no-padding">

										<table id="user" class="table table-responsive">
											<tbody>
												<tr class="m-b-md">
													<td width="35%">Bank Name</td>
													<td width="65%">${account.bankName }</td>
												</tr>
												<tr>
													<td>Account Holder</td>
													<td>${account.accountHolderId }</td>
												</tr>
												<tr>
													<td>Account Group</td>
													<td><span class="badge badge-warning">${account.accountGroup}</span>
														<div class="btn-group pull-right">
															<a data-toggle="dropdown" class=" text-info no-padding dropdown-toggle"> Change group </a>
															<ul class="dropdown-menu">
																<c:forEach items="${accountGroups}" var="accountGroup">
																	<li><a href="/accounts/changeAccountGroup?accountId=${account.accountId }&accountGroup=${accountGroup}">${accountGroup}</a></li>
																</c:forEach>

															</ul>
														</div></td>
												</tr>
												<tr>
													<td>Account Number</td>
													<td>${account.accountNumber }</td>
												</tr>
												<tr>
													<td>Bank Login Id</td>
													<td>${account.bankLoginId }</td>
												</tr>

												<tr>
													<td>Visibility</td>
													<td><c:if test="${account.accountVisible == true }">
															<div>
																<span class="badge badge-success">Visible</span> <a href="/accounts/hideAccount?accountId=${account.accountId}" class="text-info pull-right"><u>Hide</u></a>
															</div>
														</c:if> <c:if test="${account.accountVisible == false }">
															<span class="badge badge-danger"> Hidden </span>
															<a href="/accounts/showAccount?accountId=${account.accountId}" class="pull-right text-info"><u>Show</u></a>

														</c:if></td>
												</tr>

											</tbody>
										</table>
									</div>
								</div>
							</div>


							<div class="col-lg-6">
								<div class="hpanel animated-panel fadeIn">
									<div class="panel-heading">
										<h4 class="">
											<strong>Auto Sync</strong>
										</h4>
									</div>
									<div class="panel-heading no-padding">
										<table class="table table-responsive" style="clear: both">
											<tbody>
												<tr class="m-b-md">
													<td width="35%">Account Number</td>
													<td width="65%">${account.accountNumber }</td>
												</tr>
												<tr>
													<td>Auto Sync</td>
													<td>---</td>
												</tr>
												<tr>
													<td>Web URL</td>
													<td>${account.webUrl }</td>
												</tr>
												<tr>
													<td>Next schedule</td>
													<td><span class="text-muted">---</span> <a href="#" class="pull-right btn btn-link no-padding">Skip next sync</a></td>
												</tr>
												<tr>
													<td>Last sync time</td>
													<td>--- <a href="#" class="pull-right btn btn-link no-padding">Sync now</a>
													</td>
												</tr>
												<tr>
													<td>Last sync status</td>
													<td>---</td>
												</tr>


											</tbody>
										</table>
									</div>
								</div>
							</div>



						</div>


						<div class="row">
							<div class="col-lg-6">
								<div class="hpanel stats">
									<div class="panel-heading h-200">
										<div class="stats-title pull-left">
											<h3 class="font-bold">Cash flow</h3>
										</div>


										<div class="clearfix"></div>
										<p class="text-muted">
											<strong>Auto sync</strong> was done 6 hours before. You may not see the exact data by this time. Please sync your account for latest.
										</p>
										<div class="m-t-xs">

											<div class="row">
												<div class="col-xs-6" style="">
													<small class="stat-label">Today</small>
													<h4>
														$170,20 <i class="fa fa-level-up text-success"></i>
													</h4>
												</div>
												<div class="col-xs-6" style="">
													<small class="stat-label">Last week</small>
													<h4>
														$580,90 <i class="fa fa-level-up text-success"></i>
													</h4>
												</div>
											</div>
											<div class="row">
												<div class="col-xs-6" style="">
													<small class="stat-label">Today</small>
													<h4>
														$620,20 <i class="fa fa-level-up text-success"></i>
													</h4>
												</div>
												<div class="col-xs-6" style="">
													<small class="stat-label">Last week</small>
													<h4>
														$140,70 <i class="fa fa-level-up text-success"></i>
													</h4>
												</div>
											</div>
										</div>
									</div>

								</div>
							</div>



							<div class="col-lg-6">
								<div class="hpanel">

									<div class="panel-heading">

										<canvas id="lineOptions" height="150"></canvas>

									</div>


								</div>
							</div>
						</div>

						<div class="row">
							<div class="hr-line-solid"></div>
						</div>

						<div class="row">

							<div class="col-lg-6">
								<div class="hpanel">

									<div class="panel-heading">
										<div>
											<canvas id="lineOptions1" height="150"></canvas>
										</div>
									</div>
									<div class="panel-heading text-center">
										<h4>Asset contribution</h4>
									</div>
								</div>
							</div>




						</div>


						<div class="row">
							<div class="col-lg-12">


								<div class="hpanel animated-panel fadeIn">
									<div class="panel-heading">
										<h4 class="">
											<strong>Sync history</strong>
										</h4>
									</div>
									<div class="panel-heading no-padding">
										<h4>No sync history present</h4>
										<small class="text-muted"> There was no sync operation performed since beggining. Please turn on ayto sync or mark the account as dynamic. </small>
									</div>
								</div>


							</div>
						</div>



					</div>
					<div class="col-lg-3">
						<div class="hpanel">
							<div class="panel-body">

								<ul class="mailbox-list">
									<li class="m-b-md"><a href="/accounts/loadNewAccountPage" class="btn btn-primary">+ New Account</a></li>

									<c:forEach items="${values}" var="a">
										<li <c:if test="${a.accountId == account.accountId }">
										class="active"
									</c:if>><a
											href="/accounts/loadAccount?accountId=${a.accountId}">${a.accountName}</a>
									</c:forEach>


								</ul>

							</div>

						</div>
					</div>



				</div>
			</c:if>


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
		$(function() {

			/**
			 * Options for Line chart
			 */

			var lineData = {
				labels : [ "January", "February", "March", "April", "May",
						"June", "July" ],
				datasets : [

				{
					label : "Dt 1",
					backgroundColor : 'rgba(98,203,49, 0.5)',
					pointBorderWidth : 1,
					pointBackgroundColor : "rgba(98,203,49,1)",
					pointRadius : 3,
					pointBorderColor : '#ffffff',
					borderWidth : 1,
					data : [ 16, 32, 18, 26, 42, 33, 44 ]
				}, {
					label : "Dt 2",
					backgroundColor : 'rgba(220,220,220,0.5)',
					borderColor : "rgba(220,220,220,0.7)",
					pointBorderWidth : 1,
					pointBackgroundColor : "rgba(220,220,220,1)",
					pointRadius : 3,
					pointBorderColor : '#ffffff',
					borderWidth : 1,
					data : [ 22, 44, 67, 43, 76, 45, 12 ]
				} ]
			};

			var lineOptions = {
				responsive : true
			};

			var ctx = document.getElementById("lineOptions").getContext("2d");
			new Chart(ctx, {
				type : 'line',
				data : lineData,
				options : lineOptions
			});

			var ctx = document.getElementById("lineOptions1").getContext("2d");
			new Chart(ctx, {
				type : 'line',
				data : lineData,
				options : lineOptions1
			});

			/**
			 * Options for Sharp Line chart
			 */
			var sharpLineData = {
				labels : [ "January", "February", "March", "April", "May",
						"June", "July" ],
				datasets : [ {
					label : "Dt 1",
					backgroundColor : "rgba(98,203,49,0.5)",
					data : [ 33, 48, 40, 19, 54, 27, 54 ],
					lineTension : 0,
					pointBorderWidth : 1,
					pointBackgroundColor : "rgba(98,203,49,1)",
					pointRadius : 3,
					pointBorderColor : '#ffffff',
					borderWidth : 1
				} ]
			};

			var sharpLineOptions = {
				responsive : true,
				legend : {
					display : false
				}
			};

			var ctx = document.getElementById("sharpLineOptions").getContext(
					"2d");
			new Chart(ctx, {
				type : 'line',
				data : sharpLineData,
				options : sharpLineOptions
			});

			/**
			 * Options for Bar chart
			 */
			var barOptions = {
				responsive : true
			};

			/**
			 * Data for Bar chart
			 */
			var barData = {
				labels : [ "January", "February", "March", "April", "May",
						"June", "July" ],
				datasets : [ {
					label : "Dt 1",
					backgroundColor : "rgba(220,220,220,0.5)",
					borderColor : "rgba(220,220,220,0.8)",
					highlightFill : "rgba(220,220,220,0.75)",
					highlightStroke : "rgba(220,220,220,1)",
					borderWidth : 1,
					data : [ 65, 59, 80, 81, 56, 55, 40 ]
				}, {
					label : "Dt 2",
					backgroundColor : "rgba(98,203,49,0.5)",
					borderColor : "rgba(98,203,49,0.8)",
					highlightFill : "rgba(98,203,49,0.75)",
					highlightStroke : "rgba(98,203,49,1)",
					borderWidth : 1,
					data : [ 28, 48, 40, 19, 86, 27, 90 ]
				} ]
			};

			var ctx = document.getElementById("barOptions").getContext("2d");
			new Chart(ctx, {
				type : 'bar',
				data : barData,
				options : barOptions
			});

			/**
			 * Options for Bar chart
			 */
			var singleBarOptions = {
				responsive : true,
				legend : {
					display : true
				}
			};

			/**
			 * Data for Bar chart
			 */
			var singleBarData = {
				labels : [ "January", "February", "March" ],
				datasets : [ {
					label : "Category",
					backgroundColor : "rgba(98,203,49,0.5)",
					borderColor : "rgba(98,203,49,0.8)",
					highlightFill : "rgba(98,203,49,0.75)",
					highlightStroke : "rgba(98,203,49,1)",
					borderWidth : 1,
					data : [ 15, 20, 30 ]
				} ]
			};

			var ctx = document.getElementById("singleBarOptions").getContext(
					"2d");
			new Chart(ctx, {
				type : 'bar',
				data : singleBarData,
				options : singleBarOptions
			});

			var polarData = {
				datasets : [ {
					data : [ 120, 130, 100 ],
					backgroundColor : [ "#62cb31", "#80dd55", "#a3e186" ],
					label : 'Dt 1' // for legend
				} ],
				labels : [ "Homer", "Inspinia", "Luna" ]
			}

			var polarOptions = {
				responsive : true

			};

			var ctx = document.getElementById("polarOptions").getContext("2d");
			new Chart(ctx, {
				type : 'polarArea',
				data : polarData,
				options : polarOptions
			});

			var doughnutData = {
				labels : [ "App", "Software", "Laptop" ],
				datasets : [ {
					data : [ 20, 120, 100 ],
					backgroundColor : [ "#62cb31", "#91dc6e", "#a3e186" ],
					hoverBackgroundColor : [ "#57b32c", "#57b32c", "#57b32c" ]
				} ]
			}

			var doughnutOptions = {
				responsive : true
			};

			var ctx = document.getElementById("doughnutChart").getContext("2d");
			new Chart(ctx, {
				type : 'doughnut',
				data : doughnutData,
				options : doughnutOptions
			});

			var radarData = {
				labels : [ "Eating", "Drinking", "Sleeping", "Designing",
						"Coding", "Cycling", "Running" ],
				datasets : [ {
					label : "Dt 1",
					backgroundColor : "rgba(98,203,49,0.2)",
					borderColor : "rgba(98,203,49,1)",
					pointBackgroundColor : "rgba(98,203,49,1)",
					pointBorderColor : "#fff",
					pointHoverBackgroundColor : "#fff",
					pointHoverBorderColor : "#62cb31",
					borderWidth : 1,
					data : [ 65, 59, 66, 45, 56, 55, 40 ]
				}, {
					label : "Dt 2",
					backgroundColor : "rgba(98,203,49,0.4)",
					borderColor : "rgba(98,203,49,1)",
					pointBackgroundColor : "rgba(98,203,49,1)",
					pointBorderColor : "#fff",
					pointHoverBackgroundColor : "#fff",
					pointHoverBorderColor : "#62cb31",
					borderWidth : 1,
					data : [ 28, 12, 40, 19, 63, 27, 87 ]
				} ]
			};

			var radarOptions = {
				responsive : true
			};

			var ctx = document.getElementById("radarChart").getContext("2d");
			new Chart(ctx, {
				type : 'radar',
				data : radarData,
				options : radarOptions
			});

			var singleBarOptions = {
				responsive : true,
				legend : {
					display : true
				}
			};

			/**
			 * Data for Bar chart
			 */
			var singleBarData = {
				labels : [ "January", "February", "March", "April", "May",
						"June", "July" ],
				datasets : [ {
					label : "Dt 1",
					backgroundColor : "rgba(98,203,49,0.5)",
					borderColor : "rgba(98,203,49,0.8)",
					highlightFill : "rgba(98,203,49,0.75)",
					highlightStroke : "rgba(98,203,49,1)",
					borderWidth : 1,
					data : [ 15, 20, 30, 40, 30, 50, 60 ]
				} ]
			};

			var ctx = document.getElementById("singleBarOptions").getContext(
					"2d");
			new Chart(ctx, {
				type : 'bar',
				data : singleBarData,
				options : singleBarOptions
			});
		});
	</script>



</body>
</html>