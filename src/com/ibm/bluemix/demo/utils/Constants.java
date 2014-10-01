package com.ibm.bluemix.demo.utils;

import java.util.List;
import java.util.Map;

import com.ibm.bluemix.demo.dao.MasterDAO;
import com.ibm.bluemix.demo.dto.RepairCompany;
import com.ibm.bluemix.demo.dto.Worker;

public class Constants {
	public static final String DB_SCHEMA_NAME = "DEMO";
	public static final String VAL_BLANK = "";
	public static final String VAL_MALE = "M";
	public static final String VAL_FEMALE = "F";
	public static final String VAL_MALE_TXT = "男性";
	public static final String VAL_FEMALE_TXT = "女性";
	
	public static enum Code {
		Status("001"),
		FAIL_INFO_DTL("002"),
		FAIL_CAUSE("003"),
		ACTION_INFO("004"),
		ACTION_RSLT("005");
		
		private String cd;
		private Code(String cd) {
			this.cd = cd;
		}
		
		public String getCode() {
			return cd;
		}
	}

	private static Map<String, Map<String, String>>codesMap = null;
	private static List<RepairCompany> companyList = null;

	public static enum Sex {
		Male("M", "Male", "男性"),
		Female("F", "Female", "女性");

		public static Sex valOf(String val) {
			for (Sex sex : Sex.values()) {
				if (sex.getValue().equals(val)) {
					return sex;
				}
			}

			return null;
		}
		
		private String val;
		private String enTxt;
		private String jpTxt;

		private Sex(String val, String enTxt, String jpTxt) {
			this.val = val;
			this.enTxt = enTxt;
			this.jpTxt = jpTxt;
		}
		
		public String getValue() {
			return val;
		}
		public String getJpTxt() {
			return jpTxt;
		}
		public String getEnTxt() {
			return enTxt;
		}
	}

	public static String getCodeNM(String cd_id, String cd) {
		if (cd == null || codesMap == null || !codesMap.containsKey(cd_id)) {
			return VAL_BLANK;
		}

		Map<String, String> codeMap = codesMap.get(cd_id);
		if (!codeMap.containsKey(cd)) {
			return VAL_BLANK;
		}

		return codesMap.get(cd_id).get(cd);
	}
	
	public static List<RepairCompany> getCompanyList() {
		return companyList;
	}
	
	public static List<Worker> getWorkerList(String comp_id, String date) {
		List<Worker> workers = null;
		for (RepairCompany comp : companyList) {
			if (comp.getComp_id().equals(comp_id)) {
				workers = comp.getWorkers();
				if (!StringUtils.isEmpty(date)) {
					if (workers.size() < 2) {
						return workers;
					} else if (DateUtils.isWorkDay(date)) {
						return workers.subList(0, workers.size() - 1);
					} else {
						return workers.subList(workers.size() - 1, workers.size());
					}
				}
			}
		}
		
		return workers;
	}
	
	static {
		MasterDAO dao = new MasterDAO();
		codesMap = dao.getCodeMap();
		companyList = dao.getCompanyList();
	}
}
