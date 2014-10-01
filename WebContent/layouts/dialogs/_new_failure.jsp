<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function() {
		
		$( "input[type=button]" )
	      .button()
	      .click(function( event ) {
	        event.preventDefault();
	      });

		$( "#repr_plan_dt" )
    		.datetimepicker({
	    		minDate: 0,
	    		hourMin: 10,
	    		hourMax: 18,
	    		showOn: "button",
		      	buttonText: "選択",
		      	timeFormat: 'HH:mm',
		      	dateFormat: "yy-mm-dd"
    	}).change(function(){
    		loadWorkers($( "#comp_id" ).val(), $( "#repr_plan_dt" ).val());
    	});
		
 		var repr_plan_dt = $( "#repr_plan_dt" ),
 		comp_id = $( "#comp_id" ),
 		worker_id = $("#worker_id"),
 		repair_cost = $("#repair_cost"),
 		fail_info = $("#fail_info"),
		allFields = $( [] ).add(repr_plan_dt).add(comp_id).add(worker_id).add(repair_cost).add(fail_info),
		tips = $( ".validateTips" );
		initTips("* 必入力項目"); 

		function initTips(t) {
			tips.text( t ).removeClass();
		}

		function updateTips( t ) {
			tips.text( t ).addClass( "ui-state-highlight" );
			setTimeout(function() {
				tips.removeClass( "ui-state-highlight", 1500 );
			}, 500 );
		}
	
		$( "#new_failure_div" ).dialog({
			autoOpen: false,
			height: 700,
			width: 700,
			modal: true,
			buttons: {
				"登録": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );
					bValid = true;
					if ( bValid ) {
						var currentDialog = $( this );
						var newdata = $("#new_failure_form").serialize();
						console.log(newdata);
						$.ajax({
							type: "get",
							url: "call-center/rep/new_fail",
							data: newdata,
							success: function(msg){
								console.log(msg);
								if(msg.result == true) {
									currentDialog.dialog( "close" );
									var newfailures = {failures: [msg.failure]};
									$("#tbody_failures").append(tmpl("template-failures", newfailures));
									$("input[name=evidence_btn]", "#tbody_failures").click(function(event){
										$("#failure_evicence_div").attr("data-id", event.target.getAttribute("data-id"));
										$("#failure_evicence_div").dialog("open");
									});
								} else {
									updateTips(msg.err_msg);
								}
							},
							error: function(){
								//error handle
								updateTips("システムエラー、もう一度ください。");
							}
						});
					}
				},
				"キャンセル": function() {
					$( this ).dialog( "close" );
				}
			},
			close: function() {
				initTips("* 必入力項目");
				allFields.val( "" ).removeClass( "ui-state-error" );
			},
			open: function() {
				allFields.val( "" ).removeClass( "ui-state-error" );
				$("#cust_id", "#new_failure_form").val($("#new_failure_div").attr("data-cust_id"));
				$("#prod_id", "#new_failure_form").val($("#new_failure_div").attr("data-prod_id"));
				$("#ser_no", "#new_failure_form").val($("#new_failure_div").attr("data-ser_no"));
				loadCompanies();
		    }
		});
	});

	function loadCompanies() {
		var cust_id = $("#cust_id", "#new_failure_form").val();
		$.ajax({
			type: "get",
			data: {cust_id: cust_id},
			url: "call-center/rep/getcomp",
			success: function(msg){
				if(msg.result == true) {
					$("#comp_id").empty();
					$("#comp_id").append("<option value=''></option>");
					$.each(msg.comp_list, function(index, comp){
						$("#comp_id").append("<option value='" + comp.comp_id + "'>" + comp.comp_nm + "</option>");
					});
					//$( "#comp_id" ).selectmenu();
					$( "#comp_id" ).change(function(){
						console.log("comp_id changed.");
						loadWorkers($( "#comp_id" ).val(), $( "#repr_plan_dt" ).val());
					});
					initMap(msg.cust, msg.comp_list);
				} else {
					updateTips(msg.err_msg);
				}
			},
			error: function(){
				console.log("get company list failed.");
			}
		});
	}
	function loadWorkers( comp_id, date ) {
		$.ajax({
			type: "get",
			data: {"comp_id": comp_id, "repr_plan_dt": date},
			url: "call-center/rep/getwk",
			success: function(msg){
				console.log(msg);
				if(msg.result == true) {
					$("#worker_id").empty();
					$.each(msg.data, function(index, wk){
						$("#worker_id").append("<option value='" + wk.worker_id + "'>" + wk.worker_nm + "</option>");
					});
				}
			},
			error: function(){
				console.log("get company list failed.");
			}
		});
	}

	function selectComp(comp_id) {
		$("#comp_id").val(comp_id);
		loadWorkers($( "#comp_id" ).val(), $( "#repr_plan_dt" ).val());
	}
</script>
<div id="new_failure_div" title="新規修理">
	<form id="new_failure_form">
		<input type="hidden" id="cust_id" name="cust_id"/>
		<input type="hidden" id="prod_id" name="prod_id"/>
		<input type="hidden" id="ser_no" name="ser_no"/>
		<p class="validateTips"></p>
		<table>
			<tr>
				<td width="100px">
				<label for="repr_plan_dt">修理日*</label>
				</td>
				<td width="200px">
				<input type="text" name="repr_plan_dt" id="repr_plan_dt" readonly class="text ui-widget-content ui-corner-all" style="width:150px"/>
				</td>
				<td width="20px">&nbsp;</td>
				<td width="100px">
					<label for="comp_id">修理業者*</label>
				</td>
				<td width="200px">
					<select name="comp_id" id="comp_id" style="width:100%"></select>
				</td>
			</tr>
			<tr>
				<td>
					<label for="worker_id">修理担当者*</label>
				</td>
				<td>
					<select name="worker_id" id="worker_id" style="width:150px"></select>
				</td>
				<td width="20px">&nbsp;</td>
				<td>
					<label for="repair_cost">修理費用</label>
				</td>
				<td>
					<input type="text" name="repair_cost" id="repair_cost" value="" class="text ui-widget-content ui-corner-all"  style="width:100%"/>
				</td>
			</tr>
			<tr>
				<td valign="top">
					<label for="fail_info">故障内容</label>
				</td>
				<td colspan="4">
					<textarea name="fail_info" id="fail_info" style="width:100%"></textarea>
				</td>
			</tr>
		</table>

		<div id="map_canvas" style="width:665px; height:500px"></div>
	</form>
</div>
<div style='display:none'>
	<%@ include file="../templates/_mapinfo_cust.jsp"%>
	<%@ include file="../templates/_mapinfo_comp.jsp"%>
</div>