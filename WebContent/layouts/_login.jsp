<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	$(function() {
		var login_user_id = $( "#login_user_id" ),
		login_user_pwd = $( "#login_user_pwd" ),
		allFields = $( [] ).add( login_user_id ).add( login_user_pwd ),
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
	
		$( "#login_div" ).dialog({
			autoOpen: false,
			height: 260,
			width: 340,
			modal: true,
			buttons: {
				"ログイン": function() {
					var bValid = true;
					allFields.removeClass( "ui-state-error" );
					bValid = true;
					if ( bValid ) {
						var currentDialog = $( this );
						var user = $("#login_form").serialize();
						$.ajax({
							type: "get",
							url: "call-center/login",
							data: user,
							success: function(msg){
								console.log(msg);
								if(msg.result == true) {
									currentDialog.dialog( "close" );
									window.location = "./application.jsp"
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
			}
		});
	});
		
</script>
<div id="login_div" title="ログイン">
	<form id="login_form">
		<p class="validateTips"></p>
		<fieldset>
			<table>
				<tr>
				<td>
				<label for="login_user_id">ログインID *</label>
				</td>
				<td>
				<input type="text" name="login_user_id" id="login_user_id" class="text ui-widget-content ui-corner-all" />
				</td>
				</tr>
				<tr>
				<td>
					<label for="login_user_pwd">パスワード *</label>
				</td>
				<td>
					<input type="password" name="login_user_pwd" id="login_user_pwd" value="" class="text ui-widget-content ui-corner-all" />
				</td>
				</tr>
			</table>
		</fieldset>
	</form>
</div>