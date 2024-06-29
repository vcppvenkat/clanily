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

        <div class="normalheader small-header">
            <div class="hpanel">
                <div class="panel-body">
                    <h2 class="font-light m-b-xs">
                        Group Transaction
                    </h2>
                    <small>When you group transactions, then the total value of all transactions will be adjusted with the original parent transaction. You can use this feature when you spend some amount and if there is a cash back, or if you want to group some transactions of same / different categories with some unique expense / income parameters. </small>
                </div>
            </div>
        </div>


        <div class="content">
            <div class="row">
            	<form:form action="/transactions/groupTransactionSearch" method="post" cssClass="form-horizontal" modelAttribute="groupTransactionSearchCriteria">
            		<form:hidden path="transactionId" value="${transaction.transactionId}" />
	                <div class="col-lg-3">
	                    <div class="hpanel">
	                        <div class="panel-body">
	                            <div class="m-b-md">
	                                <h4>
	                                    Filters
	                                </h4>
	                                <small>
	                                    Filter your transactions basend on diferent options below.
	                                </small>
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Summary</label>
	                                <div class="input-group col-lg-12">
	                                    <form:input path="summary" id="summaryTxt"  type="text" class="form-control "
	                                        placeholder="Any part of summary"/>
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Date Range</label>
	                                <div class="input-group col-lg-12">
	                                    <div class="input-daterange input-group" id="datepicker">
	                                        <input type="text" class="input-sm form-control" name="start">
	                                        <span class="input-group-addon">to</span>
	                                        <input type="text" class="input-sm form-control" name="end">
	                                    </div>
	                                    
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Transaction Type</label>
	                                <div class="input-group col-lg-12">
	                                    <a class="btn btn-primary2 btn-outline btn-sm">Income</a>&nbsp;
	                                    <a class="btn btn-primary2 btn-sm">Expense</a>
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Category</label>
	                                <div class="input-group col-lg-12">
	                                    <select class="js-source-states-2" multiple="multiple" style="width: 100%">
	                                        <option value="Blue">Home Maintenance</option>
	                                        <option value="Red">Home Daily Needs</option>
	                                        <option value="Green">Vehicle Maintenance</option>
	                                        <option value="Maroon">Shopping</option>
	                                        <option value="1">Healthcare</option>
	                                        <option value="2">Personal Grooming</option>
	                                        <option value="3">Family needs</option>
	                                        <option value="4">Lend</option>
	                                </select>
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Account</label>
	                                <div class="input-group col-lg-12">
	                                    <select class="js-source-states-2" multiple="multiple" style="width: 100%">
	                                        <option value="Blue">Home Maintenance</option>
	                                        <option value="Red">Home Daily Needs</option>
	                                        <option value="Green">Vehicle Maintenance</option>
	                                        <option value="Maroon">Shopping</option>
	                                        <option value="1">Healthcare</option>
	                                        <option value="2">Personal Grooming</option>
	                                        <option value="3">Family needs</option>
	                                        <option value="4">Lend</option>
	                                </select>
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Amount range</label>
	                                <div class="input-group col-lg-12">
	                                    <div class="input-group">
	                                        <input type="text" class="input-sm form-control">
	                                        <span class="input-group-addon">to</span>
	                                        <input type="text" class="input-sm form-control">
	                                    </div>
	                                    
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Notes / Imported Notes</label>
	                                <div class="input-group col-lg-12">
	                                    <input type="text" class="form-control "
	                                        placeholder="Any part of the notes">
	                                </div>
	                                
	                            </div>
	                            
	                            
	        
	                            <button class="btn btn-success btn-block">Apply</button>
	        
	                        </div>
	        
	                    </div>
	                </div>
                </form:form>
                <div class="col-lg-9">
                    <div class="row">
                        <div class="hpanel">
                            <div class="panel-body">
                                <div class="form-group">
                                    <div class="col-lg-12">
                                        <a class="btn btn-default" href="/transactions/viewTransaction?transactionId=${transaction.transactionId}">Discard</a>&ensp;
                                        <a class="btn btn-primary btn-outline" href="AddTransaction.html">Add New Transaction</a>&ensp;
                                        <a class="btn btn-primary pull-right" href="/transactions/saveGroupTransaction?transactionId=${transaction.transactionId}">Save changes</a>
                                    </div>
                                </div>
    
    
                            </div>
                        </div>
                    </div>
                    <div class="hr-line-dashed"></div>
                    <h3>Selected Transactions</h3>
                    
                    <div class="col-lg-12">
                        <table class="table toggle-arrow-tiny">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Summary</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Category</th>
                                    <th>Account</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${transaction.groupTransactions}" var="groupTransaction">
                                	<tr>
	                                    <td><a href="/transactions/popGroupTransaction?transactionId=${transaction.transactionId}&groupTransactionId=${groupTransaction.transactionId}"><i class="fa fa-minus"></i></a></td>
	                                    <td>${ groupTransaction.summary }</td>
	                                    <td>17-Dec-2-23</td>
	                                    <td class="text-success">${ groupTransaction.transactionAmountString }</td>
	                                    <td>${ groupTransaction.categoryName }</td>
	                                    <td>${ groupTransaction.accountName }</td>
	                                </tr>
                                </c:forEach>
                            </tbody>
                            <tfoot>
                                <tr class="bg-silver text-white font-extra-bold">
                                    <td></td>
                                    <td colspan="2">Total</td>
                                    <td>1000 / 1000</td>
                                    <td colspan="2"></td>
                                </tr>
                            </tfoot>

                        </table>
                    </div>
                    <hr>
                    <h3>Search Results</h3>
                    <p><label class="text-warning">Search results are restricted to 20. Filter with more search criteria for better results</label></p>
                    <div class="col-lg-12">
                        <table class="table toggle-arrow-tiny">
                            <thead>
                                <tr>
                                    <th></th>
                                    <th>Summary</th>
                                    <th>Date</th>
                                    <th>Amount</th>
                                    <th>Category</th>
                                    <th>Account</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${searchResult}" var="transactionItr">
	                                <tr>
	                                    <td><a href="/transactions/pushGroupTransaction?transactionId=${transaction.transactionId}&groupTransactionId=${transactionItr.transactionId}"><i class="fa fa-plus"></i></a></td>
	                                    <td>${transactionItr.summary }</td>
	                                    <td>17-Dec-2-23</td>
	                                    <td class="text-danger">${transactionItr.transactionAmountString }</td>
	                                    <td>${ transactionItr.categoryName }</td>
	                                    <td>${ transactionItr.accountName }</td>
	                                </tr>
                                </c:forEach>
                            </tbody>

                        </table>
                    </div>
                </div>
            </div>
            
        </div>





        <!-- Footer-->
        <jsp:include page="../footer.jsp"></jsp:include>

    </div>

    

	<jsp:include page="../scripts.jsp"></jsp:include>

</body>

</html>