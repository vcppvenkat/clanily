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
					<h2 class="font-light m-b-xs"><a href="/category/">Manage Categories</a></h2>
					<small>View category</small>
				</div>
			</div>
		</div>

		<div class="content">

			<jsp:include page="../showMessage.jsp"></jsp:include>
			
			<div class="row">

                <div class="col-lg-8">
                    <div class="hpanel">
                        <div class="panel-body">

                            <div class="pull-right">
                                <a class="btn btn-success btn-xs" href="#"> Over the time</a>
                                <a class="btn btn-default btn-xs" href="#"> Year</a>
                                <a class="btn btn-default btn-xs" href="#"> Month</a>
                                <a class="btn btn-default btn-xs" href="#"> YTD</a>
                            </div>
                            <h4 class="m-t-none">Money spent with this category</h4>
                            <small>
                                Make use of this chart to view the total amount spent with this category on the selected
                                period. This chart does not include any pending or incomplete transactions.
                            </small>
                            <div class="m-t-md">
                                <canvas id="lineOptions" height="80"></canvas>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-12 font-bold m-b-sm">
                            Users used this category
                        </div>
                        <div class="col-md-4">

                            <div class="hpanel">
                                <div class="panel-body text-center">
                                    <img alt="logo" class="img-circle img-small" src="images/a1.jpg">
                                    <div class="m-t-sm">
                                        <strong>Mark Newon</strong>
                                        <p class="small"><a href="#">13 Transactions</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">

                            <div class="hpanel">
                                <div class="panel-body text-center">
                                    <img alt="logo" class="img-circle img-small" src="images/a1.jpg">
                                    <div class="m-t-sm">
                                        <strong>Mark Newon</strong>
                                        <p class="small"><a href="#">1 Transactions</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-4">

                            <div class="hpanel">
                                <div class="panel-body text-center">
                                    <img alt="logo" class="img-circle img-small" src="images/a1.jpg">
                                    <div class="m-t-sm">
                                        <strong>Mark Newon</strong>
                                        <p class="small"><a href="#">145 Transactions</a></p>
                                    </div>
                                </div>
                            </div>
                        </div>





                    </div>

                </div>

                <div class="col-lg-4">
                    <div class="hpanel stats">
                        <div class="panel-body">

                            <div>
                                <i class="pe-7s-cash fa-4x"></i>
                                <h1 class="text-success">1,20,820</h1>
                            </div>

                            <p>
                                <strong>Total money spent</strong> between the selected period in the left chart is
                                given below. Adjust the period to view various values
                            </p>
                            <div class="row">
                                <div class="col-xs-6">
                                    <small class="stat-label">Month</small>
                                    <h4>170,20 <i class="fa fa-level-up text-success"></i></h4>
                                </div>
                                <div class="col-xs-6">
                                    <small class="stat-label">Last week</small>
                                    <h4>$580,90 <i class="fa fa-level-up text-success"></i></h4>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-6">
                                    <small class="stat-label">Today</small>
                                    <h4>$620,20 <i class="fa fa-level-up text-success"></i></h4>
                                </div>
                                <div class="col-xs-6">
                                    <small class="stat-label">Last week</small>
                                    <h4>$140,70 <i class="fa fa-level-up text-success"></i></h4>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="hpanel">
                        <div class="panel-body">
                            <div class="text-center">
                                <h2 class="m-b-xs">Categories</h2>
                                <p class="font-bold text-info">Manage Categories</p>
                                <div class="m">
                                    <i class="pe-7s-science fa-5x"></i>
                                </div>
                                <p class="small">
                                    Manage your Categories by adding, modifying, view the complete report of a Category,
                                    or remove it if
                                    you dont need it anymore.
                                </p>
                                <a class="btn btn-primary btn-sm" href="NewCategory.html">Add new category</a>
                            </div>
                        </div>
                    </div>

                </div>

            </div>
			
		</div>





		<jsp:include page="../footer.jsp"></jsp:include>
	</div>




	<jsp:include page="../scripts.jsp"></jsp:include>

	<script>

        $(function () {

            $('#datepicker').datepicker();
            $("#datepicker").on("changeDate", function (event) {
                $("#my_hidden_input").val(
                    $("#datepicker").datepicker('getFormattedDate')
                )
            });

            $('#datapicker2').datepicker();
            $('.input-group.date').datepicker({});
            $('.input-daterange').datepicker({});

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
                validate: function (value) {
                    if ($.trim(value) == '') return 'This field is required';
                }
            });

            $('#sex').editable({
                prepend: "Savings",
                source: [
                    { value: 1, text: 'Savings' },
                    { value: 2, text: 'Investment' },
                    { value: 3, text: 'Credit Card' },
                    { value: 4, text: 'Online Wallet' }
                ],
                display: function (value, sourceData) {
                    var colors = { "": "gray", 1: "green", 2: "blue" },
                        elem = $.grep(sourceData, function (o) { return o.value == value; });

                    if (elem.length) {
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
                    { value: 1, text: 'banana' },
                    { value: 2, text: 'peach' },
                    { value: 3, text: 'apple' },
                    { value: 4, text: 'watermelon' },
                    { value: 5, text: 'orange' }
                ]
            });

            $('#user .editable').on('hidden', function (e, reason) {
                if (reason === 'save' || reason === 'nochange') {
                    var $next = $(this).closest('tr').next().find('.editable');
                    if ($('#autoopen').is(':checked')) {
                        setTimeout(function () {
                            $next.editable('show');
                        }, 300);
                    } else {
                        $next.focus();
                    }
                }
            });

            // ClockPicker
            $('.clockpicker').clockpicker({ autoclose: true });

            // DateTimePicker
            $('#datetimepicker1').datetimepicker();

        });

    </script>
    <script>

        $(function () {


            /**
             * Options for Line chart
             */

            var lineData = {
                labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [

                    {
                        label: "Dt 1",
                        backgroundColor: 'rgba(98,203,49, 0.5)',
                        pointBorderWidth: 1,
                        pointBackgroundColor: "rgba(98,203,49,1)",
                        pointRadius: 3,
                        pointBorderColor: '#ffffff',
                        borderWidth: 1,
                        data: [16, 32, 18, 26, 42, 33, 44]
                    },
                    {
                        label: "Dt 2",
                        backgroundColor: 'rgba(220,220,220,0.5)',
                        borderColor: "rgba(220,220,220,0.7)",
                        pointBorderWidth: 1,
                        pointBackgroundColor: "rgba(220,220,220,1)",
                        pointRadius: 3,
                        pointBorderColor: '#ffffff',
                        borderWidth: 1,
                        data: [22, 44, 67, 43, 76, 45, 12]
                    }
                ]
            };

            var lineOptions = {
                responsive: true
            };

            var ctx = document.getElementById("lineOptions").getContext("2d");
            new Chart(ctx, { type: 'line', data: lineData, options: lineOptions });

            /**
             * Options for Sharp Line chart
             */
            var sharpLineData = {
                labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [
                    {
                        label: "Dt 1",
                        backgroundColor: "rgba(98,203,49,0.5)",
                        data: [33, 48, 40, 19, 54, 27, 54],
                        lineTension: 0,
                        pointBorderWidth: 1,
                        pointBackgroundColor: "rgba(98,203,49,1)",
                        pointRadius: 3,
                        pointBorderColor: '#ffffff',
                        borderWidth: 1
                    }
                ]
            };

            var sharpLineOptions = {
                responsive: true,
                legend: {
                    display: false
                }
            };

            var ctx = document.getElementById("sharpLineOptions").getContext("2d");
            new Chart(ctx, { type: 'line', data: sharpLineData, options: sharpLineOptions });


            /**
             * Options for Bar chart
             */
            var barOptions = {
                responsive: true
            };

            /**
             * Data for Bar chart
             */
            var barData = {
                labels: ["January", "February", "March", "April", "May", "June", "July"],
                datasets: [
                    {
                        label: "Dt 1",
                        backgroundColor: "rgba(220,220,220,0.5)",
                        borderColor: "rgba(220,220,220,0.8)",
                        highlightFill: "rgba(220,220,220,0.75)",
                        highlightStroke: "rgba(220,220,220,1)",
                        borderWidth: 1,
                        data: [65, 59, 80, 81, 56, 55, 40]
                    },
                    {
                        label: "Dt 2",
                        backgroundColor: "rgba(98,203,49,0.5)",
                        borderColor: "rgba(98,203,49,0.8)",
                        highlightFill: "rgba(98,203,49,0.75)",
                        highlightStroke: "rgba(98,203,49,1)",
                        borderWidth: 1,
                        data: [28, 48, 40, 19, 86, 27, 90]
                    }
                ]
            };

            var ctx = document.getElementById("barOptions").getContext("2d");
            new Chart(ctx, { type: 'bar', data: barData, options: barOptions });

            /**
             * Options for Bar chart
             */
            var singleBarOptions = {
                responsive: true,
                legend: {
                    display: true
                }
            };

            /**
             * Data for Bar chart
             */
            var singleBarData = {
                labels: ["January", "February", "March"],
                datasets: [
                    {
                        label: "Category",
                        backgroundColor: "rgba(98,203,49,0.5)",
                        borderColor: "rgba(98,203,49,0.8)",
                        highlightFill: "rgba(98,203,49,0.75)",
                        highlightStroke: "rgba(98,203,49,1)",
                        borderWidth: 1,
                        data: [15, 20, 30]
                    }
                ]
            };

            var ctx = document.getElementById("singleBarOptions").getContext("2d");
            new Chart(ctx, { type: 'bar', data: singleBarData, options: singleBarOptions });


            var polarData = {
                datasets: [{
                    data: [
                        120,
                        130,
                        100
                    ],
                    backgroundColor: [
                        "#62cb31",
                        "#80dd55",
                        "#a3e186"
                    ],
                    label: 'Dt 1' // for legend
                }],
                labels: [
                    "Homer",
                    "Inspinia",
                    "Luna"
                ]
            }

            var polarOptions = {
                responsive: true

            };

            var ctx = document.getElementById("polarOptions").getContext("2d");
            new Chart(ctx, { type: 'polarArea', data: polarData, options: polarOptions });


            var doughnutData = {
                labels: [
                    "App",
                    "Software",
                    "Laptop"
                ],
                datasets: [
                    {
                        data: [20, 120, 100],
                        backgroundColor: [
                            "#62cb31",
                            "#91dc6e",
                            "#a3e186"
                        ],
                        hoverBackgroundColor: [
                            "#57b32c",
                            "#57b32c",
                            "#57b32c"
                        ]
                    }]
            }


            var doughnutOptions = {
                responsive: true
            };

            var ctx = document.getElementById("doughnutChart").getContext("2d");
            new Chart(ctx, { type: 'doughnut', data: doughnutData, options: doughnutOptions });

            var radarData = {
                labels: ["Eating", "Drinking", "Sleeping", "Designing", "Coding", "Cycling", "Running"],
                datasets: [
                    {
                        label: "Dt 1",
                        backgroundColor: "rgba(98,203,49,0.2)",
                        borderColor: "rgba(98,203,49,1)",
                        pointBackgroundColor: "rgba(98,203,49,1)",
                        pointBorderColor: "#fff",
                        pointHoverBackgroundColor: "#fff",
                        pointHoverBorderColor: "#62cb31",
                        borderWidth: 1,
                        data: [65, 59, 66, 45, 56, 55, 40]
                    },
                    {
                        label: "Dt 2",
                        backgroundColor: "rgba(98,203,49,0.4)",
                        borderColor: "rgba(98,203,49,1)",
                        pointBackgroundColor: "rgba(98,203,49,1)",
                        pointBorderColor: "#fff",
                        pointHoverBackgroundColor: "#fff",
                        pointHoverBorderColor: "#62cb31",
                        borderWidth: 1,
                        data: [28, 12, 40, 19, 63, 27, 87]
                    }
                ]
            };

            var radarOptions = {
                responsive: true
            };


            var ctx = document.getElementById("radarChart").getContext("2d");
            new Chart(ctx, { type: 'radar', data: radarData, options: radarOptions });
        });

    </script>
	

</body>
</html>