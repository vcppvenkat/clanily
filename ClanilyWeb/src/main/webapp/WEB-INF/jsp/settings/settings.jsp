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

	<!-- Main Wrapper -->
	<div id="wrapper">
		<div class="content">

			<div class="row">
				<div class="col-md-4">
					<div class="hpanel">
						<div class="panel-body">
							<div class="text-center">
								<h2 class="m-b-xs">Categories</h2>
								<p class="font-bold text-info">Add, edit and view categories</p>
								<div class="m">
									<i class="pe-7s-star fa-5x"></i>
								</div>
								<p class="small">Manage your Categories by adding, modifying, view the complete report of a Category, or remove it if you dont need it anymore.</p>
								<a class="btn btn-primary btn-sm" href="/category/">Manage Categories</a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="hpanel">
						<div class="panel-body">
							<div class="text-center">
								<h2 class="m-b-xs">Payees</h2>
								<p class="font-bold text-info">Manage Payees</p>
								<div class="m">
									<i class="pe-7s-science fa-5x"></i>
								</div>
								<p class="small">Manage your payees list by adding, modifying, view the complete report of a payees, or remove it if you dont need it anymore.</p>
								<a class="btn btn-primary btn-sm" href="/payee/">Manage Payees</a>
							</div>
						</div>
					</div>
				</div>

				<div class="col-md-4">
					<div class="hpanel">
						<div class="panel-body">
							<div class="text-center">
								<h2 class="m-b-xs">Objectives</h2>
								<p class="font-bold text-info">Manage objectives</p>
								<div class="m">
									<i class="pe-7s-science fa-5x"></i>
								</div>
								<p class="small">Manage your objectives list by adding, modifying, view the complete report of an objective, or remove it if you dont need it anymore.</p>
								<a class="btn btn-primary btn-sm" href="/objective/">Manage Objectives</a>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="hpanel">
						<div class="panel-body">
							<div class="text-center">
								<h2 class="m-b-xs">Beneficiaries</h2>
								<p class="font-bold text-info">Manage beneficiaries</p>
								<div class="m">
									<i class="pe-7s-science fa-5x"></i>
								</div>
								<p class="small">
									Beneficiaries are those who actually get benefitted from a transaction. it may be our family members or someone else.	
								</p>
								<a class="btn btn-primary btn-sm" href="/beneficiary/">Manage Beneficiaries</a>
							</div>
						</div>
					</div>
				</div>
			</div>


		</div>

		<jsp:include page="../footer.jsp"></jsp:include>
	</div>

	<jsp:include page="../scripts.jsp"></jsp:include>


</body>

</html>