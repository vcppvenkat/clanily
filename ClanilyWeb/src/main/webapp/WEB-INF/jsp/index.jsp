<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">


<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri="jakarta.tags.core" prefix="c"%>

<%@page session="true"%>






<html>

<head>



<jsp:include page="header.jsp"></jsp:include>

</head>

<body class="fixed-navbar sidebar-scroll animate-panel">


	<jsp:include page="PageTopBanner.jsp"></jsp:include>
	<jsp:include page="PageLeftMenu.jsp"></jsp:include>

	<!-- Main Wrapper -->
	<div id="wrapper">
		<div class="content">

			<div class="row">

				<div class="col-lg-9">
					<div class="hpanel">
						<div class="panel-heading">
							<div class="pull-right">
								<a href="#" class="btn btn-xs btn-default">Over time</a> <a href="#" class="btn btn-xs btn-primary2">YTD</a> <a href="#" class="btn btn-xs btn-default">Month</a>
							</div>
							<h4>Networth over YTD</h4>
						</div>
						<div class="panel-body">
							<div>
								<canvas id="sharpLineOptions" height="80"></canvas>
							</div>
						</div>

					</div>
				</div>
				<div class="col-lg-3">
					<div class="hpanel">
						<div class="panel-body text-center h-200">
							<i class="fa fa-rupee fa-4x"></i>

							<h1 class="m-xs">81,20,690</h1>

							<h3 class="no-margins text-success">Networth</h3>
							<small>Eighty one lakh twenty thousand six hundrend and ninety</small>
							<hr>
							<div class="row m-t-sm">
								<div class="col-lg-6">
									<h3 class="no-margins  text-success">300,102</h3>
									<div class="font-bold">
										98% <i class="fa fa-level-up text-success"></i>
									</div>
								</div>
								<div class="col-lg-6">
									<h3 class="no-margins text-danger">280,200</h3>
									<div class="font-bold">
										9% <i class="fa fa-level-down text-danger"></i>
									</div>
								</div>

							</div>
						</div>
						<div class="panel-footer">This is standard panel footer</div>
					</div>
				</div>

			</div>

			<div class="row">

				<div class="col-md-3">
					<div class="hpanel stats">
						<div class="panel-body">
							<div class="stats-title pull-left">
								<h4>Assets</h4>
							</div>
							<img src="piechart-1.png" height="220" width="250" />
						</div>

					</div>
				</div>

				<div class="col-md-3">
					<div class="hpanel stats">
						<div class="panel-body">
							<div class="stats-title pull-left">
								<h4>Liabilities</h4>
							</div>
							<img src="piechart-1.png" height="220" width="250" />
						</div>
					</div>
				</div>
				<div class="col-md-6">
					<div class="hpanel">
						<div class="panel-body">
							<div class="text-center">
								<h2 class="m-b-xs">Finance Portfolio</h2>
								<p class="font-bold text-success">Lorem ipsum</p>
								<div class="m">
									<i class="pe-7s-portfolio fa-5x"></i>
								</div>
								<p class="small">In case of any emergency, click the below button. You will see all finance related information including assets, liabilities, etc.
									You can see all information at one place. Please make use of this in case of any emergency.</p>
								<button class="btn btn-success btn-sm">Take me to Finance Portfolio</button>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="hpanel">
					<div class="panel-heading">
						<div class="pull-right">
							<a href="#" class="btn btn-xs btn-primary2">YTD</a> <a href="#" class="btn btn-xs btn-default">Month</a>
						</div>
						<h4>Cash flow over YTD</h4>
					</div>
					<div class="panel-body">
						<div>
							<canvas id="lineOptions" height="80"></canvas>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col-lg-12">
				<h4>Expense Vs Budget</h4>
				<img src="expensevsbudget.png" width="1200" height="600" />
			</div>



		</div>





		
		<jsp:include page="footer.jsp"></jsp:include>
	</div>


	<jsp:include page="scripts.jsp"></jsp:include>


	

</body>

</html>