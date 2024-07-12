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
        	<jsp:include page="../showMessage.jsp"></jsp:include>
            <div class="row">
            	<form:form action="/transactions/groupTransactionSearch" method="post" cssClass="form-horizontal" modelAttribute="groupTransactionSearchCriteria">
            		<form:hidden path="transactionId" value="${transaction.transactionId}" />
            		<form:hidden path="currentTransactionView" value="${groupTransactionSearchCriteria.currentTransactionView}" />
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
	                                    <div class="input-daterange input-group" id="datepicker1">
	                                        <form:input path="searchFromDateString" id="searchFromDateString" type="text" class="input-sm form-control" />
	                                     </div>
	                                     <span class="input-group-addon">to</span>
	                                     <div class="input-daterange input-group" id="datepicker2">
	                                        <form:input path="searchToDateString" id="searchToDateString" type="text" class="input-sm form-control" />
	                                    </div>
	                                    
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Transaction Type</label>
	                                <div class="input-group col-lg-12">
	                                    <a 
	                                    	href="/transactions/onClickTransactionTypeButton?transactionId=${transaction.transactionId}&transactionType=Income"
		                                    class='btn btn-primary2 ${groupTransactionSearchCriteria.currentTransactionView.contains("Income") ? "" : "btn-outline" } '> 
		                                    Income
	                                    </a>&nbsp;
	                                    <a 
	                                    	href="/transactions/onClickTransactionTypeButton?transactionId=${transaction.transactionId}&transactionType=Expense"
	                                    	class='btn btn-primary2 ${groupTransactionSearchCriteria.currentTransactionView.contains("Expense") ? "" : "btn-outline" } '>
	                                    	Expense
	                                    </a>
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Category</label>
	                                <div class="input-group col-lg-12">
	                                    <form:select path="categoryIds" id="categoryIds" class="js-source-states-2" multiple="multiple" style="width: 100%">
	                                    	<c:forEach items="${categories}" var="category">
	                                    		<option value="${category.categoryId}" 
	                                    			${groupTransactionSearchCriteria.categoryIds.contains(category.categoryId) ? "selected" : ""}
	                                    		>
	                                    			${category.categoryName}
	                                    		</option>
	                                    	</c:forEach>
	                                	</form:select>
	                                </div>
	                                
	                            </div>
	                            <div class="form-group">
	                                <label class="control-label">Account</label>
	                                <div class="input-group col-lg-12">
	                                    <form:select path="accountIds" id="accountIds" class="js-source-states-2" multiple="multiple" style="width: 100%">
	                                    	<c:forEach items="${accounts}" var="account">
	                                    		<option value="${account.accountId}"
	                                    			${groupTransactionSearchCriteria.accountIds.contains(account.accountId) ? "selected" : ""}
	                                    		>
	                                    			${account.accountName}
	                                    		</option>
	                                    	</c:forEach>
	                                	</form:select>
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
                                        
                                        <!-- 
                                        <a class="btn btn-primary btn-outline" href="AddTransaction.html">Add New Transaction</a>&ensp;
                                         -->
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
	                                    <td>${groupTransaction.transactionDateString}</td>
	                                    <c:choose>
											<c:when test="${groupTransaction.transactionType eq 'Expense'}">
												<td class="text-danger">${groupTransaction.transactionAmountString}</td>
											</c:when>
											<c:when test="${groupTransaction.transactionType eq 'Income' }">
												<td class="text-success">${groupTransaction.transactionAmountString}</td>
											</c:when>
											<c:when test="${groupTransaction.transactionType eq 'Incoming Transfer' or  t.transactionType eq 'Outgoing Transfer'}">
												<td class="text-info">${groupTransaction.transactionAmountString}</td>
											</c:when>
										</c:choose>
	                                    
	                                    <td>${ groupTransaction.categoryName }</td>
	                                    <td>${ groupTransaction.accountName }</td>
	                                </tr>
                                </c:forEach>
                                
                                <tr>
                                    <td><a href="#"><i class="fa fa-minus"></i></a></td>
                                    <td>
                                        <input class="input-sm" placeholder="Summary" style="width: 100%;" />
                                    </td>
                                    <td>
                                        <div class="input-group date" style="width: 75%;">
                                            <input type="text" class="form-control input-sm" ><span class="input-group-addon"><i class="glyphicon glyphicon-th"></i></span>
                                        </div>
                                    </td>
                                    <td class="text-danger">
                                        <input class="input-sm" placeholder="0.00" style="width: 100%;" value="189.75"/>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <select class="js-source-states-2" style="width: 85%;">
                                                <optgroup label="Need">
                                                    <option value="AK">Home
                                                        Daily
                                                        Needs</option>
                                                    <option value="HI">Home
                                                        Maintenance</option>
                                                    <option value="HI">Vehicle
                                                        Maintenance</option>
                                                </optgroup>
                                                <optgroup label="Want">
                                                    <option value="CA">EB
                                                        Bill</option>
                                                    <option value="NV">Home
                                                        Rent</option>
                                                </optgroup>
        
                                                <optgroup label="Others">
                                                    <option value="AL">Lend</option>
                                                    <option value="AR">Subscriptions</option>
                                                    <option value="IL">Personal
                                                        Grooming</option>
        
                                                </optgroup>
        
                                            </select>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="form-group">
                                            <select class="js-source-states-2" style="width: 85%;">
                                                <optgroup label="Need">
                                                    <option value="AK">Home
                                                        Daily
                                                        Needs</option>
                                                    <option value="HI">Home
                                                        Maintenance</option>
                                                    <option value="HI">Vehicle
                                                        Maintenance</option>
                                                </optgroup>
                                                <optgroup label="Want">
                                                    <option value="CA">EB
                                                        Bill</option>
                                                    <option value="NV">Home
                                                        Rent</option>
                                                </optgroup>
        
                                                <optgroup label="Others">
                                                    <option value="AL">Lend</option>
                                                    <option value="AR">Subscriptions</option>
                                                    <option value="IL">Personal
                                                        Grooming</option>
        
                                                </optgroup>
        
                                            </select>
                                        </div>
                                    </td>
                                </tr>
                            </tbody>
                            <tfoot>
                                <tr class="bg-silver text-white font-extra-bold">
                                    <td></td>
                                    <td colspan="2">Total</td>
                                    <td>${ sumOfGroupedTransactionAmount } / ${ transaction.transactionAmountString }</td>
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
	                                    <td>${ transactionItr.transactionDateString }</td>
	                                    <c:choose>
											<c:when test="${transactionItr.transactionType eq 'Expense'}">
												<td class="text-danger">${transactionItr.transactionAmountString}</td>
											</c:when>
											<c:when test="${transactionItr.transactionType eq 'Income' }">
												<td class="text-success">${transactionItr.transactionAmountString}</td>
											</c:when>
											<c:when test="${transactionItr.transactionType eq 'Incoming Transfer' or  t.transactionType eq 'Outgoing Transfer'}">
												<td class="text-info">${transactionItr.transactionAmountString}</td>
											</c:when>
										</c:choose>
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
	
	<script>

        $(function(){

            $('#datepicker').datepicker();
            $("#datepicker").on("changeDate", function(event) {
                $("#my_hidden_input").val(
                        $("#datepicker").datepicker('getFormattedDate')
                )
            });

            $('#datapicker2').datepicker();
            $('.input-group.date').datepicker({ });
            $('.input-daterange').datepicker({ });

            $("#demo1").TouchSpin({
                min: 0,
                max: 100,
                step: 0.1,
                decimals: 2,
                boostat: 5,
                maxboostedstep: 10,
            });

            $("#demo2").TouchSpin({
                verticalbuttons: true
            });

            $("#demo3").TouchSpin({
                postfix: '%'
            });

            $("#demo4").TouchSpin({
                postfix: "a button",
                postfix_extraclass: "btn btn-default"
            });

            $(".js-source-states").select2();
            $(".js-source-states-2").select2();

            //turn to inline mode
            $.fn.editable.defaults.mode = 'inline';

            //defaults
            $.fn.editable.defaults.url = '#';

            //editables
            $('#username').editable({
                url: '#',
                type: 'text',
                pk: 1,
                name: 'username',
                title: 'Enter username'
            });

            $('#firstname').editable({
                validate: function(value) {
                    if($.trim(value) == '') return 'This field is required';
                }
            });

            $('#sex').editable({
                prepend: "not selected",
                source: [
                    {value: 1, text: 'Male'},
                    {value: 2, text: 'Female'}
                ],
                display: function(value, sourceData) {
                    var colors = {"": "gray", 1: "green", 2: "blue"},
                            elem = $.grep(sourceData, function(o){return o.value == value;});

                    if(elem.length) {
                        $(this).text(elem[0].text).css("color", colors[value]);
                    } else {
                        $(this).empty();
                    }
                }
            });

            $('#dob').editable();

            $('#event').editable({
                placement: 'right',
                combodate: {
                    firstItem: 'name'
                }
            });

            $('#comments').editable({
                showbuttons: 'bottom'
            });

            $('#fruits').editable({
                pk: 1,
                limit: 3,
                source: [
                    {value: 1, text: 'banana'},
                    {value: 2, text: 'peach'},
                    {value: 3, text: 'apple'},
                    {value: 4, text: 'watermelon'},
                    {value: 5, text: 'orange'}
                ]
            });

            $('#user .editable').on('hidden', function(e, reason){
                if(reason === 'save' || reason === 'nochange') {
                    var $next = $(this).closest('tr').next().find('.editable');
                    if($('#autoopen').is(':checked')) {
                        setTimeout(function() {
                            $next.editable('show');
                        }, 300);
                    } else {
                        $next.focus();
                    }
                }
            });

            // ClockPicker
            $('.clockpicker').clockpicker({autoclose: true});

            // DateTimePicker
            $('#datetimepicker1').datetimepicker();

        });

    </script>

</body>

</html>