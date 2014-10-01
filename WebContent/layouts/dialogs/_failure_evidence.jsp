<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	$(function() {
		$( "#failure_evicence_div" ).dialog({
			autoOpen: false,
			height: 645,
			width: 890,
			modal: true,
			open: function() {
				var fail_id = $("#failure_evicence_div").attr("data-id");
				$("#fail_info").html("&nbsp;");
				$("#failure_info_dtl").html("&nbsp;");
				$("#failure_cause").html("&nbsp;");
				$("#action_info").html("&nbsp;");
				$("#action_rslt").html("&nbsp;");
				$("#memo").html("&nbsp;");
				//$("#img_div").hide();
				$("#img0").html("なし");
				$("#img1").html("なし");
				checkEvidence(fail_id);
		    }
		});
	});
	
	function checkEvidence(fail_id) {
		var cust_id = $("#cust_id", "#new_failure_form").val();
		$.ajax({
			type: "get",
			data: {fail_id: fail_id},
			url: "call-center/rep/chkimg",
			success: function(msg){
				if(msg.result == true) {
					$("#fail_info").html(msg.fail_info);
					$("#failure_info_dtl").html(msg.failure_info_dtl_txt);
					$("#failure_cause").html(msg.failure_cause_txt);
					$("#action_info").html(msg.action_info_txt);
					$("#action_rslt").html(msg.action_rslt_txt);
					$("#memo").html(msg.memo);

					if (msg.str_photo) {
						$("#img0").html('<img src="call-center/rep/getimg?img_cd=0&fail_id=' + fail_id+ '" width="400px" height="400px"/>');
					}
					if (msg.end_photo) {
						$("#img1").html('<img src="call-center/rep/getimg?img_cd=1&fail_id=' + fail_id+ '" width="400px" height="400px"/>');
					}
					if (msg.str_photo || msg.end_photo) {
						//$("#img_div").show();
					}
				} else {
					updateTips(msg.err_msg);
				}
			},
			error: function(){
				console.log("get company list failed.");
			}
		});
	}
</script>
<div id="failure_evicence_div" title="エビデンス">
	<table>
	<tr>
		<td><h4>障害内容：&nbsp;&nbsp;</h4></td>
		<td><span id="fail_info"></span></td>
	</tr>
	<tr>
		<td><h4>故障内容詳細：&nbsp;&nbsp;</h4></td>
		<td><span id="failure_info_dtl"></span></td>
	</tr>
	<tr>
		<td><h4>故障原因：&nbsp;&nbsp;</h4></td>
		<td><span id="failure_cause"></span></td>
	</tr>
	<tr>
	<td><h4>対応内容：&nbsp;&nbsp;</h4></td>
	<td><span id="action_info"></span></td>
	</tr>
	<tr>
	<td><h4>対応結果：&nbsp;&nbsp;</h4></td>
	<td><span id="action_rslt"></span></td>
	</tr>
	<tr>
	<td><h4>備考：&nbsp;&nbsp;</h4></td>
	<td><span id="memo"></span></td>
	</tr>
	</table>
	<br/>
	<div id="img_div">
	<table class="imgtable">
		<tr>
			<td width="400px" align="center" class="imgtd"><h4>修正前</h4></td>
			<td width="400px" align="center" class="imgtd"><h4>修正後</h4></td></tr>
		<tr>
		<td height="400px" class="imgtd" id="img0" align="center" valign="middle">
		<img id="img0" src="" width="400px" height="400px"/>
		</td>
		<td height="400px" class="imgtd" id="img1" align="center" valign="middle">
		<img id="img1" src="" width="400px" height="400px"/>
		</td>
		</tr>
	</table>
	</div>
</div>