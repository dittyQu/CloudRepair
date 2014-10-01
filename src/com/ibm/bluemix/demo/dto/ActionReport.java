package com.ibm.bluemix.demo.dto;

import java.sql.Blob;
import java.util.Date;

public class ActionReport {
	private String report_id;
	private String failure_id;
	private String seq;
	private String worker_id;
	private String failure_info_dtl;
	private String failure_cause;
	private String action_info;
	private String action_rslt;
	private String memo;
	private Date act_str_dt;
	private Date act_end_dt;
	private String duration;
	private Blob str_photo;
	private Blob end_photo;

	public String getReport_id() {
		return report_id;
	}
	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}
	public String getFailure_id() {
		return failure_id;
	}
	public void setFailure_id(String failure_id) {
		this.failure_id = failure_id;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getWorker_id() {
		return worker_id;
	}
	public void setWorker_id(String worker_id) {
		this.worker_id = worker_id;
	}
	public String getFailure_info_dtl() {
		return failure_info_dtl;
	}
	public void setFailure_info_dtl(String failure_info_dtl) {
		this.failure_info_dtl = failure_info_dtl;
	}
	public String getFailure_cause() {
		return failure_cause;
	}
	public void setFailure_cause(String failure_cause) {
		this.failure_cause = failure_cause;
	}
	public String getAction_info() {
		return action_info;
	}
	public void setAction_info(String action_info) {
		this.action_info = action_info;
	}
	public String getAction_rslt() {
		return action_rslt;
	}
	public void setAction_rslt(String action_rslt) {
		this.action_rslt = action_rslt;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getAct_str_dt() {
		return act_str_dt;
	}
	public void setAct_str_dt(Date act_str_dt) {
		this.act_str_dt = act_str_dt;
	}
	public Date getAct_end_dt() {
		return act_end_dt;
	}
	public void setAct_end_dt(Date act_end_dt) {
		this.act_end_dt = act_end_dt;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Blob getStr_photo() {
		return str_photo;
	}
	public void setStr_photo(Blob str_photo) {
		this.str_photo = str_photo;
	}
	public Blob getEnd_photo() {
		return end_photo;
	}
	public void setEnd_photo(Blob end_photo) {
		this.end_photo = end_photo;
	}
}
