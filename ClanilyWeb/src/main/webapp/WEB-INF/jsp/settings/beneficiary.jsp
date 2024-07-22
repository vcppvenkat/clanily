
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
					<h2 class="font-light m-b-xs">MANAGE BENEFICIARIES</h2>
					<small>Manage your beneficiaries</small>

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
									<th class="text-left" data-toggle="true">Beneficiary Name</th>
									<th class="text-center">Contribution</th>
									<th class="text-center">Transactions</th>
									<th class="text-center">Amount</th>
									<th class="text-center"></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="beneficiary" items="${values}">
								<tr>
									<td class="text-left">${beneficiary.beneficiaryName }</td>
									<td class="text-center">98</td>
									<td class="text-center">1,276</td>
									<td class="text-center">12,678.28</td>
									<td class="text-center">
										<a href="#" onclick="deleteBeneficiary(${beneficiary.beneficiaryId})"><strong><i class="fa fa-remove font-extra-bold"></i></strong></a>
									</td>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<td colspan="5">
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
								<li class="m-b-md"><a href="/beneficiary/loadNewBeneficiaryPage" class="btn btn-primary">New Beneficiary</a></li>
							</ul>
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
                                <small>
                                    Total payees including the default unknown payee, of which
                                    <strong>0</strong> rupees were spent in the overall time.
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

	<script>
		$(function() {

			// Initialize Example 1
			$('#example1').footable();

		});
		
		function deleteBeneficiary(beneficiaryId) {
			var newForm = $('<form>', {
				'action': '/beneficiary/deleteBeneficiary',
				'target': '_top',
				'method': 'post'
			}).append($('<input>', {
				'name': 'beneficiaryId',
				'value': beneficiaryId,
				'type': 'hidden'
			}));
			newForm.appendTo('body');
			newForm.submit();
			newForm.remove();
		}
	</script>
</body>
</html>