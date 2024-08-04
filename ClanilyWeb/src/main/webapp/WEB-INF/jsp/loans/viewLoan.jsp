<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

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
			
			<div class="row">
                <div class="col-lg-12 m-b-sm">
                    <h3>${loan.loanSummary}<span class="badge badge-success">${loan.loanStatus}</span></h3>
                    <span>${loan.description}</span>
                </div>

                <div class="col-lg-12 m-b-md">
                    <a class="btn btn-primary m-r-md">Edit loan details</a>
                    <a class="btn btn-primary btn-outline m-r-md">Pre Close</a>
                    <a class="btn btn-primary btn-outline m-r-md">Cancel</a>
                    <a class="btn btn-primary btn-outline m-r-md">Refresh</a>
                </div>

                <div class="col-lg-12 m-b-md">
                    <h5>Overall progress of the loan</h5>
                        <div class="progress full progress-striped active">
                            <div style="width: ${BigDecimal.valueOf((loan.totalPaid /loan.amount) * 100).setScale(2, RoundingMode.HALF_UP)}%" aria-valuemax="100" aria-valuemin="0" 
                            	aria-valuenow="${BigDecimal.valueOf((loan.totalPaid /loan.amount) * 100).setScale(2, RoundingMode.HALF_UP)}" role="progressbar" class=" progress-bar progress-bar-success">
                                ${BigDecimal.valueOf((loan.totalPaid /loan.amount) * 100).setScale(2, RoundingMode.HALF_UP)}%
                            </div>
                        </div>
                </div>

                <div class="m-b-md">
                    <div class="col-md-3">
                        <div class="hpanel">
                            <div class="panel-body">
                                <div class="stats-title pull-left">
                                    <h4>Loan amount</h4>
                                </div>
                                <div class="m-t-xl">
                                    <h3 class="text-info font-bold">${loan.amount}</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="hpanel">
                            <div class="panel-body">
                                <div class="stats-title pull-left">
                                    <h4>Paid</h4>
                                </div>
    
                                <div class="m-t-xl">
                                    <h3 class="text-success font-bold">${loan.totalPaid}</h3>
    
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="hpanel">
                            <div class="panel-body">
                                <div class="stats-title pull-left">
                                    <h4>Pending</h4>
                                </div>
    
                                <div class="m-t-xl">
                                    <h3 class="text-danger font-bold">${loan.totalPending}</h3>
    
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-3">
                        <div class="hpanel">
                            <div class="panel-body">
                                <div class="stats-title pull-left">
                                    <h4>Repayment By</h4>
                                </div>
    
                                <div class="m-t-xl">
                                    <h3 class="text-primary2 font-bold">${loan.endDate}</h3>
    
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="col-lg-12">
                    <div class="hpanel">
                        <div class="panel-heading">
                            Repayment 
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="lineOptions" height="200"></canvas>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
            <div class="row">
                <div class="col-lg-4">
                    <div class="hpanel">
                        <div class="panel-heading">
                            Loan contruction to asset
                        </div>
                        <div class="panel-body">
                            <div>
                                <canvas id="doughnutChart" height="200"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="hpanel">
                        <div class="panel-heading">
                            <table class="table">
                                <tbody>
                                    <tr>
                                        <td>Summary</td>
                                        <td>${loan.loanSummary}</td>
                                    </tr>
                                    <tr>
                                        <td>Start By</td>
                                        <td>${loan.startDate}</td>
                                    </tr>
                                    <tr>
                                        <td>End On</td>
                                        <td>${loan.endDate}</td>
                                    </tr>
                                    <tr>
                                        <td>Loan Amount</td>
                                        <td>${loan.amount}</td>
                                    </tr>
                                    <tr>
                                        <td>Paid</td>
                                        <td>${loan.totalPaid}</td>
                                    </tr>
                                    <tr>
                                        <td>Remaining</td>
                                        <td>${loan.totalPending}</td>
                                    </tr>
                                    <tr>
                                        <td>Status</td>
                                        <td>${loan.loanStatus}</td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>


                </div>
                <div class="col-lg-8">
                    <div class="hpanel">
                        <div class="panel-heading">
                            Transaction list
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th>Summary</th>
                                            <th>Date</th>
                                            <th>Type</th>
                                            <th>Amount</th>
                                            <th>Account</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                    	<c:forEach  var="transaction" items="${loan.loanTransactions}">
                                    		<tr>
	                                            <td>${transaction.summary}</td>
	                                            <td>${transaction.transactionDateString}</td>
	                                            <td>${transaction.transactionType}</td>
	                                            <td>${transaction.transactionAmount}</td>
	                                            <td>${transaction.accountName}</td>
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


	<script>
	</script>

</body>
</html>