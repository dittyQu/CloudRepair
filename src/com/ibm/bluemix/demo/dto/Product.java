package com.ibm.bluemix.demo.dto;

public class Product {
	private String prod_id;
	private String prod_nm;
	private String prod_cat;
	private String sales_rnk;
	private String company;
	private long prod_price;
	private String ensure_period;

	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getProd_nm() {
		return prod_nm;
	}
	public void setProd_nm(String prod_nm) {
		this.prod_nm = prod_nm;
	}
	public String getProd_cat() {
		return prod_cat;
	}
	public void setProd_cat(String prod_cat) {
		this.prod_cat = prod_cat;
	}
	public String getSales_rnk() {
		return sales_rnk;
	}
	public void setSales_rnk(String sales_rnk) {
		this.sales_rnk = sales_rnk;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public long getProd_price() {
		return prod_price;
	}
	public void setProd_price(long prod_price) {
		this.prod_price = prod_price;
	}
	public String getEnsure_period() {
		return ensure_period;
	}
	public void setEnsure_period(String ensure_period) {
		this.ensure_period = ensure_period;
	}
}
