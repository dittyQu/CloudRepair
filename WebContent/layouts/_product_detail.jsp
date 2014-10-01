<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ibm.bluemix.demo.dto.Customer;" %>
<script type="text/javascript">
	$(function() {
		var table = $('#failureTable').dataTable({
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
		
		function loadData() {
			var mainDiv = $("#main");
			$.ajax({
				type: "get",
				url: "call-center/rep/fail_sch",
				data: {
					cust_id: '<%=request.getParameter("cust_id")%>',
					prod_id: '<%=request.getParameter("prod_id")%>', 
					ser_no: '<%=request.getParameter("ser_no")%>'},
				success: function(msg){
					if(msg.result == true) {
						var data = {
							product: msg.product,
							failures: msg.failures
						};

						$("#prod_detail_div").html(tmpl("template-prod-detail", data));
			            $("#tbody_failures").html(tmpl("template-failures", data));
						if (data.failures.length > 0) {
							$("input[name=evidence_btn]").click(function(event){
								$("#failure_evicence_div").attr("data-id", event.target.getAttribute("data-id"));
								$("#failure_evicence_div").dialog("open");
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
		}

		loadData();
	});
	
	$("#new_failure").click(function(event){
		$("#new_failure_div").attr("data-cust_id", "<%=request.getParameter("cust_id")%>");
		$("#new_failure_div").attr("data-prod_id", "<%=request.getParameter("prod_id")%>");
		$("#new_failure_div").attr("data-ser_no", "<%=request.getParameter("ser_no")%>");
		$("#new_failure_div" ).dialog("open");
	});
</script>

<div id="prod_detail_div">
    
</div>
<br/>
<div id="failure_sch_result_div">
	<h3>修理履歴一覧</h3>
	<table id="failureTable" class="display">
		<thead>
			<tr>
				<th>修理日</th>
				<th>修理ステータス</th>
				<th>修理業者</th>
				<th>修理担当者</th>
				<th>修理費用</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody id='tbody_failures'>
		</tbody>
	</table>
	<input type="button" id="new_failure" value="新規修理"/>
	
</div>
<div style='display:none'>
	<%@ include file="templates/_product_detail.jsp"%>
	<%@ include file="templates/_failures.jsp"%>
	<%@ include file="dialogs/_new_failure.jsp"%>
	<%@ include file="dialogs/_failure_evidence.jsp"%>
</div>