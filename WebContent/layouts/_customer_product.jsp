<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ibm.bluemix.demo.dto.Customer;" %>
<script>
	$(function() {
		var table = $('#productTable').dataTable({
			searching: false,
			ordering: false,
			paging: false,
			info: false,
			processing: true,
			/* aoColumnDefs: [
				{ sWidth: "100px", aTargets: [0] },
				{ sWidth: "150px", aTargets: [1] },
				{ sWidth: "150px", aTargets: [2] },
				{ sWidth: "80px", aTargets: [3] }, 
				{ sWidth: "150px", aTargets: [4] },
				{ sWidth: "50px", aTargets: [6] }
				], */
			language: {
				url: "layouts/others/datatable_jp.json"
		    }
		});
		var mainDiv = $("#main");
		$.ajax({
			type: "get",
			url: "call-center/cust/cust_prod_sch",
			data: {cust_id: "<%=request.getParameter("cust_id")%>"},
			success: function(msg){
				if(msg.result == true) {
					var data = {
						customer: msg.customer,
						products: msg.products
					};

					$("#cust_detail_div").html(tmpl("template-customer-detail", data));
		            $("#tbody_products").html(tmpl("template-products", data));
					if (data.products.length > 0) {
						 $("input[name=prod_detail_btn]").click(function(event){
							mainDiv.load("layouts/_product_detail.jsp", {
								cust_id: event.target.getAttribute("data-cust_id"),
								prod_id: event.target.getAttribute("data-prod_id"),
								ser_no: event.target.getAttribute("data-ser_no")
							});
						});
					}
				} else {
					alert(msg.err_msg);
				}
			},
			error: function(){
				//error handle
				alert("システムエラー、もう一度ください。");
			}
		});
	});
</script>

<div id="cust_detail_div">
    
</div>
<br/>
<div id="prod_sch_result_div">
	<h3>購入家電一覧</h3>
	<table id="productTable" class="display">
		<thead>
			<tr>
				<th>製品カテゴリー</th>
				<th>シリアル ID</th>
				<th>製品名</th>
				<th>購入日</th>
				<th>保障期間</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody id='tbody_products'>
		</tbody>
	</table>
</div>
<div style='display:none'>
	<%@ include file="templates/_customer_detail.jsp"%>
	<%@ include file="templates/_products.jsp"%>
</div>