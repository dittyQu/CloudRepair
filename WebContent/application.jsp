<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>故障修理マッチングシステム</title>
    <link rel="stylesheet" href="stylesheets/jquery-ui/jquery-ui.css"  type="text/css"/>
    <link rel="stylesheet" href="stylesheets/style.css" type="text/css" media="all"/>
    <link rel="stylesheet" href="stylesheets/dataTables.jqueryui.css" type="text/css" />
    <link rel="stylesheet" href="stylesheets/jquery-ui-timepicker-addon.css" type="text/css" />

    <script src="javascripts/jquery-1.11.1.js"></script>
    <script src="javascripts/jquery-ui-1.11.0.js"></script>
    <script src="javascripts/jquery.dataTables.js"></script>
    <script src="javascripts/dataTables.jqueryui.js"></script>
    <script src="javascripts/jquery-ui-timepicker-addon.js"></script>
	<!-- <script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?sensor=false&language=ja"></script> -->
	<script type="text/javascript" src="http://api.map.baidu.com/api?key=&v=1.1&services=true"></script>
    <script>
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
		$("a[id='nav_fail_all']").click(function(event){
			$("#main").load("layouts/_failure_search.jsp");
		});
	});
    </script>
</head>
<body>
	<%@ include file="layouts/_header.jsp"%>
	
	<!-- Main -->
	<div id="main">
	    <% if(session.getAttribute("current_user") != null) { %>
	    	<%@ include file="layouts/_welcome.jsp"%>
	    <%} else {%>
	    	<%@ include file="layouts/_index.jsp"%>
	   	<%}%>
	</div>
	<!-- End Main -->
	
	<div style='display:none'>
	<%@ include file="layouts/_login.jsp"%>
	</div>
	
	<%@ include file="layouts/_footer.jsp"%>

	<script src="javascripts/tmpl.js"></script>
	<script src="javascripts/_baidu_select.js"></script>
</body>
</html>