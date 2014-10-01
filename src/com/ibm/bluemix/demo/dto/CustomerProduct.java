package com.ibm.bluemix.demo.dto;

import java.util.Date;

import com.ibm.bluemix.demo.utils.Constants;
import com.ibm.bluemix.demo.utils.DateUtils;
import com.ibm.bluemix.demo.utils.StringUtils;

public class CustomerProduct {
	private Product product;
	private String ser_no;
	private String cust_id;
	private Date sal_date;
	private String cust_nm;

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getSer_no() {
		return ser_no;
	}
	public void setSer_no(String ser_no) {
		this.ser_no = ser_no;
	}
	public String getCust_id() {
		return cust_id;
	}
	public void setCust_id(String cust_id) {
		this.cust_id = cust_id;
	}
	public Date getSal_date() {
		return sal_date;
	}
	public void setSal_date(Date sal_date) {
		this.sal_date = sal_date;
	}
	public String getCust_nm() {
		return cust_nm;
	}
	public void setCust_nm(String cust_nm) {
		this.cust_nm = cust_nm;
	}
	public String getSal_date_txt() {
		if (sal_date == null) {
			return Constants.VAL_BLANK;
		}
		return StringUtils.FORMAT_DISPLAY.format(sal_date);
	}

	public boolean getExp_flg() {
		if (sal_date == null || product == null || product.getEnsure_period() == null) {
			return false;
		}

		return DateUtils.addMonth(sal_date, Integer.valueOf(product.getEnsure_period())).compareTo(new Date()) < 0;
	}
}
