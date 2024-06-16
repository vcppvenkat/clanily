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
					<h2 class="font-light m-b-xs">Manage Objectives</h2>
					<small>Add new objective</small>
				</div>
			</div>
		</div>

		<div class="content">

			
			<jsp:include page="../showMessage.jsp"></jsp:include>
			
			<form:form method="post" class="form-horizontal" modelAttribute="obj" name="newObjectiveForm" action="/objective/addNewObjective">
				<div class="row">
					<div class="col-lg-12">
						<div class="hpanel">
							<div class="panel-heading">
								<h4>Add new objective</h4>
							</div>
							<div class="panel-heading">


								<div class="form-group">
									<label class="col-sm-2 control-label">Objective name</label>

									<div class="col-sm-10">
										<form:input path="objectiveName" type="text" class="form-control" placeholder="Short objective name for easy search. Maximum 25 chars. Eg. Milk, Provision, etc"></form:input>
									</div>
								</div>

								

								<div class="hr-line-dashed"></div>

								<div class="form-group">
									<label class="col-sm-2 control-label">Description</label>

									<div class="col-sm-10">
										<form:textarea path="objectiveDescription" placeholder="Maximum 250 chars" class="form-control"></form:textarea>
									</div>

								</div>







							</div>
						</div>
					</div>


				</div>
				<div class="row">
					<div class="col-lg-12">
						<div class="form-group">
							<a class="btn btn-default" href="/objective/">Discard</a>
							<button type="submit" class="btn btn-primary pull-right">Save changes</button>
						</div>
					</div>
				</div>
			</form:form>
		</div>

		<jsp:include page="../footer.jsp"></jsp:include>
	</div>

	<jsp:include page="../scripts.jsp"></jsp:include>

</body>
</html>