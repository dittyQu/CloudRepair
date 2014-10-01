package com.ibm.bluemix.demo.dto;

import java.text.ParseException;
import java.util.Date;

import com.ibm.bluemix.demo.utils.Constants;
import com.ibm.bluemix.demo.utils.StringUtils;

public class Failure {
	private String failure_id;
	private CustomerProduct product;
	private String status;
	private String failure_info;
	private String repr_plan_dt;
	private String repr_end_dt;
	private Worker worker;
	private RepairCompany company;
	private int repair_cost;

	public String getFailure_id() {
		return failure_id;
	}
	public void setFailure_id(String failure_id) {
		this.failure_id = failure_id;
	}
	public CustomerProduct getProduct() {
		return product;
	}
	public void setProduct(CustomerProduct product) {
		this.product = product;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFailure_info() {
		return failure_info;
	}
	public void setFailure_info(String failure_info) {
		this.failure_info = failure_info;
	}
	public String getRepr_plan_dt() {
		return repr_plan_dt;
	}
	public void setRepr_plan_dt(String repr_plan_dt) {
		this.repr_plan_dt = repr_plan_dt;
	}
	public String getRepr_end_dt() {
		return repr_end_dt;
	}
	public void setRepr_end_dt(String repr_end_dt) {
		this.repr_end_dt = repr_end_dt;
	}
	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}
	public int getRepair_cost() {
		return repair_cost;
	}
	public void setRepair_cost(int repair_cost) {
		this.repair_cost = repair_cost;
	}
	public RepairCompany getCompany() {
		return company;
	}
	public void setCompany(RepairCompany company) {
		this.company = company;
	}

	public String getRepair_cost_txt() {
		return StringUtils.formatMoney(repair_cost);
	}
	
	public String getRepr_end_dt_txt() {
		if (repr_end_dt != null) {		
			try {
				Date date = StringUtils.FORMAT_INPUT.parse(repr_end_dt);
				return StringUtils.FORMAT_DISPLAY_TIME.format(date);
			} catch (ParseException e) {
				//NOP
			}
		}

		return Constants.VAL_BLANK;
	}
	
	public String getRepr_plan_dt_txt() {
		if (repr_plan_dt != null) {		
			try {
				Date date = StringUtils.FORMAT_INPUT.parse(repr_plan_dt);
				return StringUtils.FORMAT_DISPLAY_TIME.format(date);
			} catch (ParseException e) {
				//NOP
			}
		}

		return Constants.VAL_BLANK;
	}
	
	public String getRepr_dt_txt() {
		if (repr_end_dt != null) {
			return getRepr_end_dt_txt();
		}
		
		if (repr_plan_dt != null) {
			return getRepr_plan_dt_txt();
		}
		
		return Constants.VAL_BLANK;
	}
}
