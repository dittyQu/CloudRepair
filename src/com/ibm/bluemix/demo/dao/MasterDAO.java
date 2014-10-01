package com.ibm.bluemix.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.bluemix.demo.dto.RepairCompany;
import com.ibm.bluemix.demo.dto.Worker;
import com.ibm.bluemix.demo.utils.Constants;

public class MasterDAO extends SQLDBHelper {
	private static final String SQL_GET_CODES = "select CD_ID, CD_NM, CD, NM from " + Constants.DB_SCHEMA_NAME +".M_CODE order by CD_ID, CD asc";
	private static final String SQL_GET_COMP_WORKER = "select C.COMP_ID, C.COMP_NM, C.COMP_ADDR, C.COMP_POINT_X, C.COMP_POINT_Y, WORKER_ID, WORKER_NM from "
		+ Constants.DB_SCHEMA_NAME +".M_REP_COMP C, "
		+ Constants.DB_SCHEMA_NAME +".M_WORKER W "
		+ "where W.COMP_ID = C.COMP_ID order by C.COMP_ID, WORKER_ID asc";	

	
	public Map<String, Map<String, String>> getCodeMap() {
		Map<String, Map<String, String>> codesMap = new HashMap<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL_GET_CODES);
			conn.setAutoCommit(true);
			rs = query(conn, pstmt);
			Map<String, String> codeMap = null;
			while (rs.next()) {
				String cd_id = rs.getString("CD_ID");
				if (!codesMap.containsKey(cd_id)) {
					codeMap = new HashMap<>();
					codesMap.put(cd_id, codeMap);
				}
				
				String cd = rs.getString("CD");
				String nm = rs.getString("NM");
				codeMap.put(cd, nm);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}
		
		return codesMap;
	}

	public List<RepairCompany> getCompanyList() {
		List<RepairCompany> companyList = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(SQL_GET_COMP_WORKER);
			conn.setAutoCommit(true);
			rs = query(conn, pstmt);
			RepairCompany comp = null;
			Worker worker = null;
			while (rs.next()) {
				if (comp == null || comp.getComp_id() == null || !comp.getComp_id().equals(rs.getString("COMP_ID"))) {
					comp = new RepairCompany();
					companyList.add(comp);
					comp.setComp_id(rs.getString("COMP_ID"));
					comp.setComp_nm(rs.getString("COMP_NM"));
					comp.setComp_addr(rs.getString("COMP_ADDR"));
					comp.setComp_point_x(rs.getString("COMP_POINT_X"));
					comp.setComp_point_y(rs.getString("COMP_POINT_Y"));
				}

				worker = new Worker();
				comp.getWorkers().add(worker);
				worker.setWorker_id(rs.getString("WORKER_ID"));
				worker.setWorker_nm(rs.getString("WORKER_NM"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}
		
		return companyList;
	}
}
