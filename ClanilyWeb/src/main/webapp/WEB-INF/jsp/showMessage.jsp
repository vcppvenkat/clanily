<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@page session="true"%>

<c:choose>
	<c:when test="${errorMessage ne null}">
		<div class="row">
			<div class="alert alert-danger">
				<h5>${errorMessage}</h5>
			</div>
		</div>
	</c:when>
	<c:when test="${successMessage ne null}">
		<div class="row">
			<div class="alert alert-success">
				<h5>${successMessage}</h5>
			</div>
		</div>
	</c:when>

</c:choose>