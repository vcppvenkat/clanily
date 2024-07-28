<!DOCTYPE html>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@page session="true" import="java.math.BigDecimal,java.math.RoundingMode"%>


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
					<h2 class="font-light m-b-xs">Loans</h2>
					<small>Manage your loans</small>
				</div>
			</div>
		</div>
		<div class="content">

			<div class="row">



				<div class="col-lg-12">
					<div class="col-lg-3">
						<input class="form-control" type="text" placeholder="Search summary or description">
					</div>
					<div class="col-lg-1">
                    </div>
					<div class="col-lg-3">
						<a href="AddLoan.html" class="btn btn-primary">Start a new loan</a>
						
					</div>
					<div class="col-lg-3">
						<div class="btn-group">
                            <button data-toggle="dropdown" class="btn btn-primary2 btn-outline dropdown-toggle" aria-expanded="false">Active </button>
                            <ul class="dropdown-menu">
                                <li><a href="#">New</a></li>
                                <li><a href="#">Active</a></li>
                                <li><a href="#">Overdue</a></li>
                                <li><a href="#">Completed</a></li>
                                <li class="divider"></li>
                                <li><a href="#">Cancelled</a></li>
                            </ul>
                        </div>
					</div>
					<div class="col-lg-2">
						<a href="#" class="btn btn-primary2">Borrowed</a>&nbsp; <a
							href="#" class="btn btn-primary2 btn-outline">Lent</a>&nbsp;
					</div>

				</div>

			</div>
			<div class="hr-line-dashed"></div>
			<div class="row">
				<c:forEach var="loan" items="${values}">
					<div class="col-lg-4">
						<div class="hpanel stats">
							<div class="panel-body h-200">
								<div class="stats-title pull-left">
									<a href="ViewLoan.html"><h4>${loan.loanSummary}</h4></a>
								</div>
	
								<div class="m-t-xl">
									<h3 class="m-b-xs">${loan.amount}</h3>
									<span class="text-muted no-margins"> ${loan.description} </span>
	
									<div class="progress m-t-xs full progress-small">
										<div style="width: 55%" aria-valuemax="100" aria-valuemin="0"
											aria-valuenow="55" role="progressbar"
											class=" progress-bar progress-bar-success">
											<span class="sr-only">${loan.loanStatus}</span>
										</div>
									</div>
	
									<div class="row">
										<div class="col-xs-4">
											<small class="stats-label">Paid</small>
											<h4>${loan.totalPaid}</h4>
										</div>
	
										<div class="col-xs-4">
											<small class="stats-label">Remaining</small>
											<h4>${loan.totalPending}</h4>
										</div>
										<div class="col-xs-4">
											<small class="stats-label">% Completed</small>
											<h4>${BigDecimal.valueOf((loan.totalPaid /loan.amount) * 100).setScale(2, RoundingMode.HALF_UP)}</h4>
										</div>
									</div>
								</div>
							</div>
	
						</div>
					</div>
				</c:forEach>
			</div>


		</div>

		<!-- Right sidebar -->


		<!-- Footer-->
		<jsp:include page="../footer.jsp"></jsp:include>
	</div>


	<jsp:include page="../scripts.jsp"></jsp:include>
	<script>
		$(function() {

			$('#datepicker').datepicker();
			$("#datepicker").on(
					"changeDate",
					function(event) {
						$("#my_hidden_input")
								.val(
										$("#datepicker").datepicker(
												'getFormattedDate'))
					});

			$('#datapicker2').datepicker();
			$('.input-group.date').datepicker({});
			$('.input-daterange').datepicker({});

			$("#demo1").TouchSpin({
				min : 0,
				max : 100,
				step : 0.1,
				decimals : 2,
				boostat : 5,
				maxboostedstep : 10,
			});

			$("#demo2").TouchSpin({
				verticalbuttons : true
			});

			$("#demo3").TouchSpin({
				postfix : '%'
			});

			$("#demo4").TouchSpin({
				postfix : "a button",
				postfix_extraclass : "btn btn-default"
			});

			$(".js-source-states").select2();
			$(".js-source-states-2").select2();

			//turn to inline mode
			$.fn.editable.defaults.mode = 'inline';

			//defaults
			$.fn.editable.defaults.url = '#';

			//editables
			$('#username').editable({
				url : '#',
				type : 'text',
				pk : 1,
				name : 'username',
				title : 'Enter username'
			});

			$('#firstname').editable({
				validate : function(value) {
					if ($.trim(value) == '')
						return 'This field is required';
				}
			});

			$('#sex').editable({
				prepend : "not selected",
				source : [ {
					value : 1,
					text : 'Male'
				}, {
					value : 2,
					text : 'Female'
				} ],
				display : function(value, sourceData) {
					var colors = {
						"" : "gray",
						1 : "green",
						2 : "blue"
					}, elem = $.grep(sourceData, function(o) {
						return o.value == value;
					});

					if (elem.length) {
						$(this).text(elem[0].text).css("color", colors[value]);
					} else {
						$(this).empty();
					}
				}
			});

			$('#dob').editable();

			$('#event').editable({
				placement : 'right',
				combodate : {
					firstItem : 'name'
				}
			});

			$('#comments').editable({
				showbuttons : 'bottom'
			});

			$('#fruits').editable({
				pk : 1,
				limit : 3,
				source : [ {
					value : 1,
					text : 'banana'
				}, {
					value : 2,
					text : 'peach'
				}, {
					value : 3,
					text : 'apple'
				}, {
					value : 4,
					text : 'watermelon'
				}, {
					value : 5,
					text : 'orange'
				} ]
			});

			$('#user .editable').on('hidden', function(e, reason) {
				if (reason === 'save' || reason === 'nochange') {
					var $next = $(this).closest('tr').next().find('.editable');
					if ($('#autoopen').is(':checked')) {
						setTimeout(function() {
							$next.editable('show');
						}, 300);
					} else {
						$next.focus();
					}
				}
			});

			// ClockPicker
			$('.clockpicker').clockpicker({
				autoclose : true
			});

			// DateTimePicker
			$('#datetimepicker1').datetimepicker();

		});
	</script>

</body>

</html>