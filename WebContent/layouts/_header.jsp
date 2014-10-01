<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ibm.bluemix.demo.dto.User;" %>
<!-- Header -->
<div id="header">
    <div class="shell">
        <!-- Logo -->
        <h1 id="logo" class="notext"><a href="#"> </a></h1>
        <!-- End Logo -->
    </div>
    <!-- Navigation -->
	<div id="navigation">
		<ul>
			<%
			User user = (User)session.getAttribute("current_user");
			if (user == null) {
			%>
			<li><a id="nav_login" href="#"><span>ログイン</span></a></li>
			<%} else {
				if (user.getRole().getFunctions().contains(User.Function.New_Customer)) {%>
		    <li><a id="nav_new_cust" href="#"><span>新規顧客登録</span></a></li>
		    <%  }
		        if (user.getRole().getFunctions().contains(User.Function.Customer_Search)) {%>
		    <li><a id="nav_cust_sch" href="#"><span>顧客検索</span></a></li>
		    <%  }
				if (user.getRole().getFunctions().contains(User.Function.Issue_Check)) {%>
		    <li><a id="nav_fail_all" href="#"><span>修理チェック</span></a></li>
		    <%  } %>
		    <li><a id="nav_logoff" href="#"><span>ログオフ</span></a></li>
		    <%}%>
		</ul>
	</div>
	<!-- End Navigation -->
</div>
<!-- END Header -->