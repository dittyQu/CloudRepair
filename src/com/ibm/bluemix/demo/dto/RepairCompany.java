package com.ibm.bluemix.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class RepairCompany {
	private String comp_id;
	private String comp_nm;
	private String comp_addr;
	private String comp_point_x;
	private String comp_point_y;

	List<Worker> workers = new ArrayList<>();
	public String getComp_id() {
		return comp_id;
	}
	public void setComp_id(String comp_id) {
		this.comp_id = comp_id;
	}
	public String getComp_nm() {
		return comp_nm;
	}
	public void setComp_nm(String comp_nm) {
		this.comp_nm = comp_nm;
	}
	public String getComp_addr() {
		return comp_addr;
	}
	public void setComp_addr(String comp_addr) {
		this.comp_addr = comp_addr;
	}
	public List<Worker> getWorkers() {
		return workers;
	}
	public void setWorkers(List<Worker> workers) {
		this.workers = workers;
	}
	public String getComp_point_x() {
		return comp_point_x;
	}
	public void setComp_point_x(String comp_point_x) {
		this.comp_point_x = comp_point_x;
	}
	public String getComp_point_y() {
		return comp_point_y;
	}
	public void setComp_point_y(String comp_point_y) {
		this.comp_point_y = comp_point_y;
	}
}
