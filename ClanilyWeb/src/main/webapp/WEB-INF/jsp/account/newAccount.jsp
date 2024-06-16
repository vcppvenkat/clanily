
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
					<small>Add new account of any type</small>

				</div>
			</div>
		</div>

		<div class="content">
		
			<jsp:include page="../showMessage.jsp"></jsp:include>			

			<form:form cssClass="form-horizontal" action="/accounts/addAccount" method="post" modelAttribute="account" cssStyle="form-horizontal">
				<div class="row">
					<div class="col-lg-12">
						<div class="hpanel">

							<div class="panel-heading">

								<div class="form-group">
									<label class="col-sm-2 control-label">Account name</label>

									<div class="col-sm-10">
										<form:input path="accountName" type="text" class="form-control" placeholder="Maximum 25 chars"></form:input>
									</div>
								</div>
								<div class="form-group">
									<label class="col-sm-2 control-label">Current Balance</label>

									<div class="col-sm-10">
										<form:input path="currentBalance" type="text" class="form-control" placeholder="Eligible currency value (Eg. 1234.34)"></form:input>
									</div>
								</div>

								<div class="form-group">
									<label class="col-sm-2 control-label">Notes</label>

									<div class="col-sm-10">
										<form:textarea path="notes" placeholder="Maximum 250 chars" class="form-control" />
									</div>

								</div>

								<div class="form-group">
									<div class="col-lg-12">
										<div class="form-group">
											<div class="col-lg-2">
												<label class=" control-label pull-right">Account group</label>
											</div>
											<div class="col-lg-4">

												<form:select path="accountGroup" class="js-source-states" style="width: 85%;">

													<c:forEach items="${accountGroups}" var="group">
														<form:option value="${group}">${group}</form:option>
													</c:forEach>
												</form:select>
											</div>
										</div>
									</div>

								</div>
								<div class="form-group">
									<div class="col-sm-2">
										<label class=" control-label pull-right">Account Holder</label>
									</div>

									<div class="col-sm-4">

										<form:select path="accountHolderId" class="js-source-states" style="width: 85%;">

											<c:forEach items="${users}" var="user">
												<form:option value="${user.userId }">${user.userName}</form:option>
											</c:forEach>


										</form:select>


									</div>


								</div>
								<div class="hr-line-dashed"></div>
								<div class="form-group">
									<div class="col-sm-2">
										<label class=" control-label pull-right">Account Visibility</label>
									</div>

									<div class="col-sm-10">

										<form:hidden path="accountVisible" id="accountVisibilityHidden"></form:hidden>
										<a class="btn btn-primary2 btn-outline m-r-sm" id="accountVisibilityHide">Hide</a> <a class="btn btn-primary2  m-r-sm" id="accountVisibilityShow">Show</a>

									</div>
								</div>
							</div>
						</div>
						<div class="hpanel">
							<div class="panel-heading">
								<button class="btn btn-primary2 btn-outline" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false"
									aria-controls="collapseExample">Show Additional settings</button>
							</div>

							<div class="collapse" id="collapseExample">
								<div class="panel-heading">
									<div class="form-group">
										<label class="col-sm-2 control-label">Account Number</label>

										<div class="col-sm-10">

											<form:input path="accountNumber" type="text" class="form-control" placeholder="Maximum 25 chars"></form:input>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">Web login address</label>

										<div class="col-sm-10">
											<form:input path="webUrl" type="text" class="form-control" placeholder="Eligible web url"></form:input>
										</div>
									</div>
									<div class="form-group">
										<label class="col-sm-2 control-label">Bank login id</label>

										<div class="col-sm-10">
											<form:input path="bankLoginId" type="text" class="form-control" placeholder="Maximum 50 chars"></form:input>
										</div>
									</div>


									<div class="form-group">
										<label class="col-sm-2 control-label">Bank Name</label>

										<div class="col-sm-10">
											<form:input path="bankName" type="text" class="form-control" placeholder="Actual bank / wallet name"></form:input>
										</div>
									</div>
									<div class="hr-line-dashed"></div>
									<div class="form-group">
										<div class="col-lg-12">
											<div class="form-group">
												<div class="col-lg-2">
													<label class=" control-label pull-right">Auto Sync</label>
												</div>


												<div class="col-lg-10">

													<form:hidden path="autoSync" id="autoSyncHidden"></form:hidden>
													<a class="btn btn-primary2  m-r-sm" id="doNotSyncOption">Do not sync</a> <a class="btn btn-primary2 btn-outline  m-r-sm " id="autoSyncOption">Auto
														Sync</a>



												</div>
											</div>
										</div>

									</div>

								</div>
							</div>
						</div>

					</div>
				</div>

				<div class="row">
					<div class="hpanel">
						<div class="panel-body">
							<div class="form-group">
								<div class="col-lg-12">
									<a class="btn btn-default" href="/accounts/">Discard</a>
									<button type="submit" class="btn btn-primary pull-right">Save changes</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form:form>

		</div>





		<jsp:include page="../footer.jsp"></jsp:include>
	</div>





	<jsp:include page="../scripts.jsp"></jsp:include>


	


	<script>
		$(".js-source-states").select2();

		$('#accountVisibilityHide').on('click', function() {
			$('#accountVisibilityHide').removeClass('btn-outline');
			$('#accountVisibilityShow').addClass('btn-outline');
			document.getElementById('accountVisibilityHidden').value = 'false';
		});

		$('#accountVisibilityShow').on('click', function() {
			$('#accountVisibilityShow').removeClass('btn-outline');
			$('#accountVisibilityHide').addClass('btn-outline');
			document.getElementById('accountVisibilityHidden').value = 'true';
		});

		$('#autoSyncOption').on('click', function() {
			$('#autoSyncOption').removeClass('btn-outline');
			$('#doNotSyncOption').addClass('btn-outline');
			document.getElementById('autoSyncHidden').value = 'true';
		});

		$('#doNotSyncOption').on('click', function() {
			$('#doNotSyncOption').removeClass('btn-outline');
			$('#autoSyncOption').addClass('btn-outline');
			document.getElementById('autoSyncHidden').value = 'false';
		});
	</script>



</body>
</html>