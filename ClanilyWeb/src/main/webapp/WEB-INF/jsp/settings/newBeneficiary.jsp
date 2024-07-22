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
					<h2 class="font-light m-b-xs">Manage Beneficiary</h2>
					<small>Manage your payees</small>
				</div>
			</div>
		</div>

		<div class="content">

			
			<jsp:include page="../showMessage.jsp"></jsp:include>
			
			<form:form method="post" class="form-horizontal" modelAttribute="beneficiary" name="newBeneficiaryForm" action="/beneficiary/addNewBeneficiary">
				<div class="row">
					<div class="col-lg-12">
						<div class="hpanel">
							<div class="panel-heading">
								<h4>Add new Beneficiary</h4>
							</div>
							<div class="panel-heading">


								<div class="form-group">
									<label class="col-sm-2 control-label">Beneficiary name</label>

									<div class="col-sm-10">
										<form:input id="beneficiaryName" value="${beneficiaryName}" path="beneficiaryName" type="text" class="form-control" placeholder="Maximum 25 chars"></form:input>
									</div>
								</div>
								<div class="hr-line-dashed"></div>
							</div>
						</div>
					</div>

					<div class="col-lg-12">
						<div class="form-group">
							<a class="btn btn-default" href="/beneficiary/">Discard</a>
							<button type="submit" class="btn btn-primary pull-right">Add Beneficiary</button>
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
			$('#beneficiaryName').focus();
		});
	</script>

</body>
</html>