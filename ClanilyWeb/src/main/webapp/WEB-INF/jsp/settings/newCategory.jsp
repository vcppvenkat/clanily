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
					<h2 class="font-light m-b-xs">Manage Categories</h2>
					<small>Add new category</small>
				</div>
			</div>
		</div>

		<div class="content">

			
			<jsp:include page="../showMessage.jsp"></jsp:include>
			
			<form:form method="post" class="form-horizontal" modelAttribute="category" name="newCategoryForm" action="/category/addNewCategory">
				<div class="row">
					<div class="col-lg-12">
						<div class="hpanel">
							<div class="panel-heading">
								<h4>Add new category</h4>
							</div>
							<div class="panel-heading">


								<div class="form-group">
									<label class="col-sm-2 control-label">Category name</label>

									<div class="col-sm-10">
										<form:input path="categoryName" type="text" class="form-control" placeholder="Maximum 25 chars"></form:input>
									</div>
								</div>
								<div class="hr-line-dashed"></div>

								<div class="form-group">
									<label class="col-sm-2 control-label">Description</label>

									<div class="col-sm-10">
										<form:textarea path="description" placeholder="Maximum 250 chars" class="form-control"></form:textarea>
									</div>

								</div>
								<div class="hr-line-dashed"></div>

								<div class="form-group">
									<div class="col-lg-6">
										<div class="form-group">
											<form:hidden path="type" id="currentType"></form:hidden>
											<label class="col-lg-4 control-label">Category type</label>

											<div class="col-lg-8">

												<c:choose>
													<c:when test="${category.type.equals('Expense') }">
														<a href="#" class="btn btn-primary2 btn-outline" id="categoryTypeIncome">Income</a> &nbsp; 
														<a href="#" class="btn btn-primary2" id="categoryTypeExpense">Expense</a>
													</c:when>
													<c:when test="${category.type.equals('Income') }">
														<a href="#" class="btn btn-primary2 " id="categoryTypeIncome">Income</a> &nbsp; 
														<a href="#" class="btn btn-primary2 btn-outline" id="categoryTypeExpense">Expense</a>
													</c:when>
													<c:otherwise>
														<a href="#" class="btn btn-primary2 btn-outline" id="categoryTypeIncome">Income</a> &nbsp; 
														<a href="#" class="btn btn-primary2" id="categoryTypeExpense">Expense</a>
													</c:otherwise>
												</c:choose>

											</div>
										</div>
									</div>
									<div class=" col-lg-6">
										<form:hidden path="active" id="currentStatus"></form:hidden>
										<div class="form-group">
											<label class="col-lg-4 control-label">Active status</label>

											<div class="col-lg-8">
												<c:choose>
													<c:when test="${category.active}">
														<a class="btn btn-primary2 btn-outline" id="categoryStatusInactive">Inactive</a> &nbsp; 
														<a class="btn btn-primary2 " id="categoryStatusActive">Active</a>
													</c:when>
													<c:when test="${not category.active}">
														<a class="btn btn-primary2" id="categoryStatusInactive">Inactive</a> &nbsp; 
														<a class="btn btn-primary2 btn-outline" id="categoryStatusActive">Active</a>
													</c:when>
												</c:choose>

											</div>
										</div>
									</div>
								</div>

								<div class="form-group">

									<div class="col-sm-2">
										<label class=" control-label pull-right">Tag</label>
									</div>

									<div class="col-sm-4">

										<form:select path="tag" class="js-source-states" id="categoryTags" style="width: 85%;">
											<form:option value="noTag">No Tag</form:option>

											<c:if test="${category.type.equals('Income')}">

												<c:forEach items="${incomeCategoryTags}" var="categoryTag">
													<form:option value="${categoryTag }">${categoryTag }</form:option>
												</c:forEach>

											</c:if>
											<c:if test="${category.type.equals('Expense')}">
												<c:forEach items="${expenseCategoryTags}" var="categoryTag">
													<form:option value="${categoryTag }">${categoryTag }</form:option>
												</c:forEach>
											</c:if>


										</form:select>

									</div>


								</div>


							</div>
						</div>
					</div>

					<div class="col-lg-12">
						<div class="form-group">
							<a class="btn btn-default" href="/category/">Discard</a>
							<button type="submit" class="btn btn-primary pull-right">Save changes</button>
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

			$('#categoryStatusInactive').on('click', function() {
				$('#categoryStatusInactive').removeClass('btn-outline');
				$('#categoryStatusActive').addClass('btn-outline');
				document.getElementById('currentStatus').value = 'false';
			});

			$('#categoryStatusActive').on('click', function() {
				$('#categoryStatusActive').removeClass('btn-outline');
				$('#categoryStatusInactive').addClass('btn-outline');
				document.getElementById('currentStatus').value = 'true';
			});

			$(".js-source-states").select2();

			$('#categoryTypeIncome')
					.on(
							'click',
							function() {
								$('#categoryTags').empty();
								$('#categoryTags')
										.append(
												'<option value="noTag" selected="selected">No Tag</option>');
								<c:forEach var="categoryTag" items="${incomeCategoryTags}">
								$('#categoryTags')
										.append(
												'<option value="${categoryTag}">${categoryTag}</option>');
								</c:forEach>

								$('#categoryTypeIncome').removeClass(
										'btn-outline');
								$('#categoryTypeExpense').addClass(
										'btn-outline');
								document.getElementById('currentType').value = 'Income';

							});

			$('#categoryTypeExpense')
					.on(
							'click',
							function() {
								$('#categoryTags').empty();
								$('#categoryTags')
										.append(
												'<option value="noTag" selected="selected">No Tag</option>');
								<c:forEach var="categoryTag" items="${expenseCategoryTags}" varStatus="loop">
								$('#categoryTags')
										.append(
												'<option value="${categoryTag}">${categoryTag}</option>');
								</c:forEach>

								$('#categoryTypeExpense').removeClass(
										'btn-outline');
								$('#categoryTypeIncome')
										.addClass('btn-outline');

								document.getElementById('currentType').value = 'Expense';
							});

		});
	</script>

</body>
</html>