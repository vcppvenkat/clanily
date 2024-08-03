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
			
			<div class="row">
                <div class="col-lg-12 m-b-sm">
                    <h3>Loan summary goes here <span class="badge badge-success">Active</span></h3>
                    <span>Loan description goes here. It can grow until 5 rows as maximum supported chars are 1000. Beyond
                        5 lines, the look and feel will not be good. that is the reason, we have restricted to 4 to 5 lines.
                        but ideally, users wont type more than 4 to 5 lines.</span>
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
                            <div style="width: 90%" aria-valuemax="100" aria-valuemin="0" aria-valuenow="90" role="progressbar" class=" progress-bar progress-bar-success">
                                90%
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
                                    <h3 class="text-info font-bold">1,00,00,00,000</h3>
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
                                    <h3 class="text-success font-bold">1,00,000</h3>
    
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
                                    <h3 class="text-danger font-bold">1,00,00,000</h3>
    
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
                                    <h3 class="text-primary2 font-bold">12-Jul-2024</h3>
    
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
                                        <td>This is sample summary</td>
                                    </tr>
                                    <tr>
                                        <td>Start By</td>
                                        <td>12-Jan-2024</td>
                                    </tr>
                                    <tr>
                                        <td>End On</td>
                                        <td>12-Jul-2024</td>
                                    </tr>
                                    <tr>
                                        <td>Loan Amount</td>
                                        <td>1,00,00,00,000</td>
                                    </tr>
                                    <tr>
                                        <td>Paid</td>
                                        <td>1,28,29,080.29</td>
                                    </tr>
                                    <tr>
                                        <td>Remaining</td>
                                        <td>23,23,43,291.80</td>
                                    </tr>
                                    <tr>
                                        <td>Status</td>
                                        <td>Active</td>
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
                                        <tr>
                                            <td>Sample summary of transactions</td>
                                            <td>12-Dec-2023</td>
                                            <td class="text-danger">Expense</td>
                                            <td>18,000</td>
                                            <td>HDFC</td>
                                        </tr>
                                        <tr>
                                            <td>Sample summary of transactions</td>
                                            <td>12-Dec-2023</td>
                                            <td class="text-success">Income</td>
                                            <td>2,000</td>
                                            <td>HDFC</td>
                                        </tr>

                                        <tr>
                                            <td>Sample summary of transactions</td>
                                            <td>12-Dec-2023</td>
                                            <td class="text-success">Income</td>
                                            <td>2,000</td>
                                            <td>KOTAK</td>
                                        </tr>
                                        <tr>
                                            <td>Sample summary of transactions</td>
                                            <td>12-Dec-2023</td>
                                            <td class="text-success">Income</td>
                                            <td>2,000</td>
                                            <td>SBI - Venkat</td>
                                        </tr>
                                        <tr>
                                            <td>Sample summary of transactions</td>
                                            <td>12-Dec-2023</td>
                                            <td class="text-danger">Expense</td>
                                            <td>2,000</td>
                                            <td>Cash</td>
                                        </tr>
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