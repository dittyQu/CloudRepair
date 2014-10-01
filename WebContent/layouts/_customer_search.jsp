<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	$(function() {
		$( "input[type=button]" )
	      .button()
	      .click(function( event ) {
	        event.preventDefault();
	      });
		$( "#cust_age" ).spinner({
		      spin: function( event, ui ) {
		        if ( ui.value < 0 ) {
		          $( this ).spinner( "value", 0 );
		          return false;
		        }
		      }
		});
		$( "#cust_sex" ).buttonset();
		
		var data = {"customers": []};
        $("#tbody_customers").html(tmpl("template-customer", data));
		var table = $('#customersTable').dataTable({
			searching: false,
			ordering: false,
			paging: false,
			info: false,
			processing: true,
			aoColumnDefs: [
				{ sWidth: "100px", aTargets: [0] },
				{ sWidth: "150px", aTargets: [1] },
				{ sWidth: "150px", aTargets: [2] },
				{ sWidth: "80px", aTargets: [3] }, 
				{ sWidth: "150px", aTargets: [4] },
				{ sWidth: "50px", aTargets: [6] }
				],
			language: {
				url: "layouts/others/datatable_jp.json"
		    }
		});
		
		$("#cust_sch_btn").click(function(event){
			var condition = $("#cust_sch_form").serialize();

			//test
			//var rs = {"result":true,"data":[{"cust_sex_txt":"xxx","cust_birth":"19800101","cust_sex":"M","cust_nm":"xxxx","cust_kn":"xxxxx","cust_id":"1","cust_addr":"xxxxxx"}]};
			//var data = {"customers": rs.data};
            //$("#tbody_customers").html(tmpl("template-customer", data));
			//var table = $('#customersTable').DataTable();
			//new $.fn.dataTable.AutoFill(table);
			var mainDiv = $("#main");
			 $.ajax({
				type: "get",
				url: "call-center/cust/cust_sch",
				data: condition,
				success: function(msg){
					if(msg.result == true) {
						var data = {
							customers: msg.data
						};
			            $("#tbody_customers").html(tmpl("template-customer", data));
						if (data.customers.length > 0) {
							$("input[name=cust_detail_btn]").click(function(event){
								mainDiv.load("layouts/_customer_product.jsp", {cust_id: event.target.getAttribute("data-id")});
							});
						}
					} else {
						updateTips(msg.err_msg);
					}
				},
				error: function(){
					//error handle
					updateTips("システムエラー、もう一度ください。");
				}
			});
		});
	});
		
</script>

<div id="cust_sch_div">
    <h3>顧客検索</h3>
    <form id="cust_sch_form">
        <p class="validateTips"></p>
        <table>
            <tr>
                <td width="70px"><label for="cust_id">顧客ID</label></td>
                <td><input type="text" name="cust_id" id="cust_id" class="text ui-widget-content ui-corner-all" /></td>
                <td>&nbsp;</td>
                <td width="90px"><label for="cust_age">年齢</label></td>
                <td width="150px"><input type="text" name="cust_age" id="cust_age" class="text ui-widget-content ui-corner-all" style="width:90%"/></td>
            </tr>
            <tr>
                <td><label for="cust_nm">名前(漢字)</label></td>
                <td><input type="text" name="cust_nm" id="cust_nm" class="text ui-widget-content ui-corner-all" /></td>
                <td>&nbsp;</td>
                <td><label for="cust_kn">名前(カタカナ)</label></td>
                <td><input type="text" name="cust_kn" id="cust_kn" class="text ui-widget-content ui-corner-all" style="width:100%"/></td>
            </tr>
            <tr>
                <td><label for="cust_addr">住所</label></td>
                <td colspan="4"><input type="text" name="cust_addr" id="cust_addr" class="text ui-widget-content ui-corner-all" style="width:100%"/></td>
            </tr>
            <tr>
                <td><label for="cust_sex">性別</label></td>
                <td colspan="2">
                	<div id="cust_sex">
                    <input type="radio" name="cust_sex" id="cust_sex_m" value="M"/><label for="cust_sex_m">男性</label>
                    <input type="radio" name="cust_sex" id="cust_sex_f" value="F"/><label for="cust_sex_f">女性</label>
                    </div>
                </td>
                <td colspan="2" style="text-align:right;">
                	<input type="button" id="cust_sch_btn" value="検索"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<br/>
<div id="cust_sch_result_div">
	<h3>顧客検索結果</h3>
	<table id="customersTable" class="display">
		<thead>
			<tr>
				<th>顧客ID</th>
				<th>名前（漢字）</th>
				<th>名前（カタカナ）</th>
				<th>性別</th>
				<th>生年月日</th>
				<th>住所</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody id='tbody_customers'>
		</tbody>
	</table>
</div>
<div style='display:none'>
	<%@ include file="templates/_customer.jsp"%>
</div>