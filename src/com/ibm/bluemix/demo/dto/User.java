package com.ibm.bluemix.demo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "user")  
public class User implements Serializable {
	/***/
	private static final long serialVersionUID = 1L;

	static Map<String, User> users;
	public static enum Function {
		New_Customer,
		Customer_Search,
		Issue_Check;
	}

	public static enum Role {
		Operator(new Function[]{Function.New_Customer, Function.Customer_Search}),
		Repairor(new Function[]{Function.Issue_Check}),
		Admin(new Function[]{Function.New_Customer, Function.Customer_Search, Function.Issue_Check});
		
		private List<Function> functions;
		private Role(Function[] functions) {
			this.functions = new ArrayList<>();
			for (Function func : functions) {
				this.functions.add(func);
			}
		}

		/**
		 * 機能リストを戻す
		 * @return
		 */
		public List<Function> getFunctions() {
			return this.functions;
		}
	}

	
	static {
		users = new HashMap<>();
		User user = new User("YAMADA", "123456", Role.Operator);
		users.put(user.getUserid(), user);

		user = new User("TANAKA", "123456", Role.Repairor);
		users.put(user.getUserid(), user);
		
		user = new User("KAYABACHO", "123456", Role.Admin);
		users.put(user.getUserid(), user);
	}

	/**
	 * ユーザーのログイン情報を確認する
	 *
	 * @param userid
	 * @param passwd
	 * @return
	 */
	public static User validateUser(String userid, String passwd) {
		if (users.containsKey(userid)) {
			User user = users.get(userid);
			if (user.getPasswd().equals(passwd)) {
				return user;
			}
		}
		
		return null;
	}

	private String userid;
	private String passwd;
	private Role role;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	private User(String userid, String passwd, Role role) {
		this.userid = userid;
		this.passwd = passwd;
		this.role = role;
	}
}
