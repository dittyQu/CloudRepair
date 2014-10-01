package com.ibm.bluemix.demo.dto;

import com.ibm.bluemix.demo.utils.Constants;
import com.ibm.bluemix.demo.utils.StringUtils;

public class Customer {
	private String cust_id;
	private String cust_nm;
	private String cust_kn;
	private String cust_sex;
	private String cust_birth;
	private String cust_addr;
	private String cust_point_x;
	private String cust_point_y;

	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public String getCust_nm() {
		return cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}
	public String getCust_kn() {
		return cust_kn;
	}
	public void setCust_kn(String cust_kn) {
		this.cust_kn = cust_kn;
	}
	public String getCust_sex() {
		return cust_sex;
	}
	public void setCust_sex(String cust_sex) {
		this.cust_sex = cust_sex;
	}
	public String getCust_birth() {
		return cust_birth;
	}
	public void setCust_birth(String cust_birth) {
		this.cust_birth = cust_birth;
	}
	public String getCust_addr() {
		return cust_addr;
	}
	public void setCust_addr(String cust_addr) {
		this.cust_addr = cust_addr;
	}	
	public String getCust_birth_txt() {
		return StringUtils.formatBirth(cust_birth);
	}
	public String getCust_point_x() {
		return cust_point_x;
	}
	public void setCust_point_x(String cust_point_x) {
		this.cust_point_x = cust_point_x;
	}

	public String getCust_sex_txt() {
		if (cust_sex == null) {
			return Constants.VAL_BLANK;
		}

		return Constants.Sex.valOf(cust_sex).getJpTxt();
	}
	public String getCust_point_y() {
		return cust_point_y;
	}
	public void setCust_point_y(String cust_point_y) {
		this.cust_point_y = cust_point_y;
	}
}
