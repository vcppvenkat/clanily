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
					<h2 class="font-light m-b-xs">Loans</h2>
					<small>Manage your loans</small>
				</div>
			</div>
		</div>

		<div class="content">

			
			<jsp:include page="../showMessage.jsp"></jsp:include>
			
			<form:form method="post" class="form-horizontal" modelAttribute="loan" name="newLoanForm" action="/loans/addNewLoan">
				<div class="row">
					<div class="col-lg-12">
						<div class="hpanel">
							<div class="panel-heading">
								<h4>Add new loan</h4>
							</div>
							<div class="panel-heading">


								<div class="form-group">
									<label class="col-sm-2 control-label">Loan summary</label>
									<div class="col-sm-10">
										<form:input id="loanSummary" value="${loanSummary}" path="loanSummary" type="text" class="form-control" placeholder="Maximum 25 chars"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Loan Amount</label>
									<div class="col-sm-10">
										<form:input id="amount" value="${amount}" path="amount" type="number" class="form-control" placeholder="Valid Currency(0.00)"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Description</label>
									<div class="col-sm-10">
										<form:textarea  id="description" value="${description}" path="description" type="text" class="form-control" placeholder="Maximum 1000 chars"></form:textarea>
									</div>
								</div>
								<div class="hr-line-dashed"></div>
								<div class="form-group">
                                    <div class="col-lg-12">
                                        <div class="form-group">
                                        	<form:hidden path="loanType" id="loanType" value="${loanType}" class="form-control" />
                                            <label class="col-lg-2 control-label">Loan type</label>
                                            <div class="col-lg-4">
                                            	<c:if test="${loanType eq 'Borrow' }">
													<a class="btn btn-primary2 m-r-sm " id="loanTypeBorrow">Borrow</a>
												</c:if>
												<c:if test="${loanType ne 'Borrow' }">
													<a class="btn btn-primary2 btn-outline m-r-sm " id="loanTypeBorrow">Borrow</a>
												</c:if>

												<c:if test="${loanType eq 'Lend' }">
													<a class="btn btn-primary2  m-r-sm" id="loanTypeLend">Lend</a>
												</c:if>
												<c:if test="${loanType ne 'Lend' }">
													<a class="btn btn-primary2 btn-outline m-r-sm " id="loanTypeLend">Lend</a>
												</c:if>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                                <div class="hr-line-dashed"></div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Payee</label>
                                    <div class="col-sm-2">
                                        <form:select class="js-source-states" style="width: 85%;"
                                        	path="payeeId"  id="payeeId" value="${payeeId}">
                                        	<c:forEach  var="payee" items="${payees}">
                                        		<option value="${payee.payeeId}">${payee.payeeName}</option>
                                        	</c:forEach>
                                        </form:select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Start date</label>
                                    <div class="col-sm-2">
                                        <form:input id="startDate" type="text" path="startDate"  value="${startDate}" class="form-control"></form:input>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Repayment date</label>
                                    <div class="col-sm-2">
                                        <form:input id="endDate" type="text" path="endDate"  value="${endDate}" class="form-control"></form:input>
                                    </div>
                                   
                                    <div class="col-lg-4">
                                        <div><label class="m-t-xs">
                                                <div><label class="">
                                                        <div class="icheckbox_square-green checked" style="position: relative;"><div class="icheckbox_square-green" style="position: relative;"><input type="checkbox" class="i-checks" style="position: absolute; opacity: 0;"><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins></div><ins class="iCheck-helper" style="position: absolute; top: 0%; left: 0%; display: block; width: 100%; height: 100%; margin: 0px; padding: 0px; background: rgb(255, 255, 255); border: 0px; opacity: 0;"></ins>
                                                        </div> No end date
                                                    </label></div>
                                        </label></div>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">Interest per anum</label>
                                    <div class="col-sm-2">
                                        <form:input id="interestPerAnum" type="text" path="interestPerAnum"  value="${interestPerAnum}" class="form-control"></form:input>
                                    </div>
                                </div>
							</div>
						</div>
					</div>

					<div class="col-lg-12">
						<div class="form-group">
							<a class="btn btn-default" href="/loans/">Discard</a>
							<button type="submit" class="btn btn-primary pull-right">Save Changes</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>
		<jsp:include page="../footer.jsp"></jsp:include>
	</div>
	<jsp:include page="../scripts.jsp"></jsp:include>


	<script>
		$(function() {
			var input = $('#loanSummary');
			var len = input.val().length;
			input.focus();
			input[0].setSelectionRange(len, len);
		});
		
		$('#loanTypeBorrow')
		.on('click',
				function() {
					$('#loanTypeBorrow').removeClass(
							'btn-outline');
					$('#loanTypeLend').addClass(
							'btn-outline');
					document
							.getElementById('loanType').value = 'Borrow';
				}
		);

		$('#loanTypeLend')
		.on('click',
			function() {
				$('#loanTypeBorrow').addClass(
						'btn-outline');
				$('#loanTypeLend').removeClass(
						'btn-outline');
				document
						.getElementById('loanType').value = 'Lend';
			}
		);
		
	</script>

</body>
</html>