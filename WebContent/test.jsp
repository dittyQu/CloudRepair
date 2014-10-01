<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>故障修理マッチングシステム</title>
    <link rel="stylesheet" href="stylesheets/jquery-ui/jquery-ui.css"  type="text/css"/>
    <link rel="stylesheet" href="stylesheets/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="stylesheets/jquery.dataTables.css" type="text/css" />
	<link rel="stylesheet" href="stylesheets/dataTables.autoFill.css" type="text/css" />

    <script type="text/javascript" src="javascripts/jquery-1.11.1.js"></script>
    <script type="text/javascript" src="javascripts/jquery-ui-1.11.0.js"></script>
	<script type="text/javascript" src="javascripts/jquery.dataTables.js"></script>
	<script type="text/javascript" src="javascripts/dataTables.autoFill.js"></script>
	<script type="text/javascript" src="javascripts/tmpl.js"></script>
	<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false&language=ja"></script>

    <script type="text/javascript">
	$(function() {
		//ログイン
		$("a[id='nav_login']").click(function(event){
			$("#login_div" ).dialog("open");
		});

		//ログオフ
		$("a[id='nav_logoff']").click(function(event){
			$.ajax({
				type: "get",
				url: "call-center/logoff",
				//data: user,
				success: function(msg){
					if(msg.result == true) {
						window.location = "./application.jsp";
					}
				}
			});
		});
		
		//顧客新規
		$("a[id='nav_new_cust']").click(function(event){
			$("#main").load("layouts/_new_customer.jsp");
		});
		
		//顧客検索
		$("a[id='nav_cust_sch']").click(function(event){
			$("#main").load("layouts/_customer_search.jsp");
		});
		
		//修理チェック
		$("a[id='nav_issues_chk']").click(function(event){
			$("#main").load("layouts/_issues_check.jsp");
		});
		
		$("#btn_testmap").click(function(event){
			$("#new_failure_div").attr("data-cust_id", "1000001");
			$("#new_failure_div").attr("data-prod_id", "x");
			$("#new_failure_div").attr("data-ser_no", "y");
			$("#new_failure_div" ).dialog("open");
		});
	});
	
	
    </script>
</head>
<body>
	<%@ include file="layouts/_header.jsp"%>
	
	<!-- Main -->
	<div id="main">
	    <input type="button" id="btn_testmap" value="map" onclick="testmap();"/>
	    <div style='display:none'>
			<%@ include file="layouts/templates/_product_detail.jsp"%>
			<%@ include file="layouts/templates/_failures.jsp"%>
			<%@ include file="layouts/dialogs/_new_failure.jsp"%>
			<%@ include file="layouts/dialogs/_failure_evidence.jsp"%>
		</div>
	</div>
	<!-- End Main -->
	
	<div style='display:none'>
	<%@ include file="layouts/_login.jsp"%>
	</div>
	
	<%@ include file="layouts/_footer.jsp"%>
</body>
</html>