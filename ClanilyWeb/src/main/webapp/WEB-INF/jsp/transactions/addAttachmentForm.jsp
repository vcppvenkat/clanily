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

<body class="fixed-navbar sidebar-scroll fixed-footer">

	<jsp:include page="../PageTopBanner.jsp"></jsp:include>
	<jsp:include page="../PageLeftMenu.jsp"></jsp:include>

    <!-- Main Wrapper -->
    <div id="wrapper">
        <div class="normalheader small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <h2 class="font-light m-b-xs">
                        Manage Transactions
                    </h2>
                    <small>Provide some more details to your attachment file</small>
                </div>
            </div>
        </div>
        <div class="content">
			<jsp:include page="../showMessage.jsp"></jsp:include>
            <div class="row">
                <div class="row">
                	<form:form id="addAttachmentForm"
									action="/transactions/addAttachment" method="post"
									enctype="multipart/form-data" class="form-horizontal">
                    <div class="col-lg-12">
                        <div class="hpanel">
    
                            <div class="panel-heading">
									<input type="hidden" name="transactionId"
										value="${transaction.transactionId}" />

									<div class="form-group">
										<label class="col-sm-2 control-label">Choose File</label>

										<div class="col-sm-10">
											<input type="file" name="transactionFile"
												class="form-control">

										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">File Summary</label>

										<div class="col-sm-10">
											<input name="summary" type="text" class="form-control"
												placeholder="Maximum 50 chars">
										</div>
									</div>

									<div class="form-group">
										<label class="col-sm-2 control-label">Description</label>

										<div class="col-sm-10">
											<textarea name="description" placeholder="Maximum 250 chars"
												class="form-control"></textarea>
										</div>

									</div>
								
							</div>
                        </div>
    
                    </div>
                    <div class="col-lg-12">
                        <div class="hpanel">
                            <div class="panel-body">
                                <div class="form-group">
                                    <a class="btn btn-default" href="/transactions/viewTransaction?transactionId=${transaction.transactionId}">Discard</a>&nbsp;&nbsp;&nbsp;&nbsp;
                                    <button type="submit" class="btn btn-primary pull-right">Save changes</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    </form:form>
                </div>
                
                

            </div>


        </div>

        <!-- Right sidebar -->


        <jsp:include page="../footer.jsp"></jsp:include>
    </div>

    <jsp:include page="../scripts.jsp"></jsp:include>

</body>

</html>