package com.ibm.bluemix.demo.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.bluemix.demo.dto.User;

@Path("/")
public class UsersService extends ServiceHelper {
	
	private static String KEY_LOGIN_USER_ID = "login_user_id";
	private static String KEY_LOGIN_PASSWD = "login_user_pwd";

    @Context protected HttpServletResponse response;  
    @Context protected HttpServletRequest request;

	/**
	 * ログイン処理
	 * @return
	 */
	@Path("/login")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response login() {
		String userid = request.getParameter(KEY_LOGIN_USER_ID);
		String passwd = request.getParameter(KEY_LOGIN_PASSWD);
		User current_user = User.validateUser(userid, passwd);
		Map<String, Object> msg = new HashMap<>();
		msg.put("result", Boolean.FALSE);
		
		if (current_user != null) {
			request.getSession().setAttribute(KEY_CURRENT_USER, current_user);

			msg.put("result", Boolean.TRUE);
			return Response.ok(msg).build();
		}

		msg.put("err_msg", "無効なユーザーIDとパスワード。");
		return Response.ok(msg).build();
	}

	/**
	 * ログオフ処理
	 * @return
	 */
	@Path("/logoff")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response logoff() {
		request.getSession().removeAttribute(KEY_CURRENT_USER);
		Map<String, Object> msg = new HashMap<>();
		msg.put("result", Boolean.TRUE);
		return Response.ok(msg).build();
	}
	
	public class LoginMsg {
		public LoginMsg(boolean result, String msg) {
			this.result = result;
			this.msg = msg;
		}

		private boolean result;
		private String msg;
		public boolean isResult() {
			return result;
		}
		public void setResult(boolean result) {
			this.result = result;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
	}
}