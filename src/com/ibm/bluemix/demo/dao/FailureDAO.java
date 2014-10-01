package com.ibm.bluemix.demo.dao;

import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibm.bluemix.demo.dto.ActionReport;
import com.ibm.bluemix.demo.dto.CustomerProduct;
import com.ibm.bluemix.demo.dto.Failure;
import com.ibm.bluemix.demo.dto.RepairCompany;
import com.ibm.bluemix.demo.dto.Worker;
import com.ibm.bluemix.demo.utils.Constants;
import com.ibm.bluemix.demo.utils.StringUtils;

public class FailureDAO extends SQLDBHelper {
	// 顧客購入製品リスト検索SQL
	private final static String SQL_FAIL_SEARCH_BY_CUST = "select FAILURE_ID, FAILURE_INFO, REPR_PLAN_DT, REPR_END_DT, STATUS, F.COMP_ID, COMP_NM, F.WORKER_ID, WORKER_NM, REPAIR_COST "
	+ " from "+ Constants.DB_SCHEMA_NAME +".FAILURE F, "+ Constants.DB_SCHEMA_NAME +".M_REP_COMP R, "+ Constants.DB_SCHEMA_NAME +".M_WORKER W "
	+ " where F.COMP_ID = R.COMP_ID and F.WORKER_ID = W.WORKER_ID and F.PROD_ID=? and F.SER_NO=?";

	private final static String SQL_FAIL_SEARCH_ALL = "select FAILURE_ID, FAILURE_INFO, REPR_PLAN_DT, REPR_END_DT, STATUS, F.COMP_ID, COMP_NM, F.WORKER_ID, WORKER_NM, REPAIR_COST, CUST_NM "
	+ " from "+ Constants.DB_SCHEMA_NAME +".FAILURE F, "+ Constants.DB_SCHEMA_NAME +".M_REP_COMP R, "+ Constants.DB_SCHEMA_NAME +".M_WORKER W, "+ Constants.DB_SCHEMA_NAME +".M_CUST C "
	+ " where F.COMP_ID = R.COMP_ID and F.WORKER_ID = W.WORKER_ID and F.CUST_ID = C.CUST_ID order by FAILURE_ID desc";
	
	private final static String SQL_FAIL_SEARCH_BY_ID = "select FAILURE_ID, FAILURE_INFO, REPR_PLAN_DT, REPR_END_DT, STATUS, F.COMP_ID, COMP_NM, F.WORKER_ID, WORKER_NM, REPAIR_COST "
			+ " from "+ Constants.DB_SCHEMA_NAME +".FAILURE F, "+ Constants.DB_SCHEMA_NAME +".M_REP_COMP R, "+ Constants.DB_SCHEMA_NAME +".M_WORKER W "
			+ " where F.COMP_ID = R.COMP_ID and F.WORKER_ID = W.WORKER_ID and F.FAILURE_ID=?";

	private final static String SQL_CREATE_FAIL = "insert into " + Constants.DB_SCHEMA_NAME +".FAILURE "
			+ "(FAILURE_ID, STATUS, CUST_ID, PROD_ID, SER_NO, REPR_PLAN_DT, COMP_ID, WORKER_ID, FAILURE_INFO, REPAIR_COST) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	// 顧客購入製品リスト検索SQL
	private final static String SQL_CHK_EVIDENCE = "select FAILURE_ID, STR_PHOTO, END_PHOTO "
			+ " from "+ Constants.DB_SCHEMA_NAME +".ACTION_REPORT where FAILURE_ID=? order by REPORT_ID desc";
	private final static String SQL_GET_EVIDENCE_0 = "select FAILURE_ID, STR_PHOTO "
		+ " from "+ Constants.DB_SCHEMA_NAME +".ACTION_REPORT where FAILURE_ID=? order by REPORT_ID desc";
	private final static String SQL_GET_EVIDENCE_1 = "select FAILURE_ID, END_PHOTO "
			+ " from "+ Constants.DB_SCHEMA_NAME +".ACTION_REPORT where FAILURE_ID=? order by REPORT_ID desc";
	private final static String SQL_GET_REPORT = "select FAILURE_ID, FAILURE_INFO_DTL, FAILURE_CAUSE, ACTION_INFO, ACTION_RSLT, MEMO "
			+ " from "+ Constants.DB_SCHEMA_NAME +".ACTION_REPORT where FAILURE_ID=?";

	public List<Failure> searchFailures(String prod_id, String ser_no) {
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Failure> records = new ArrayList<>();
		try {
			conn = getConnection();
			if (prod_id == null && ser_no == null) {
				pstmt = conn.prepareStatement(SQL_FAIL_SEARCH_ALL);
				conn.setAutoCommit(true);
				rs = query(conn, pstmt);
			} else {
				List<String> params = new ArrayList<>();
				params.add(prod_id);
				params.add(ser_no);
				pstmt = conn.prepareStatement(SQL_FAIL_SEARCH_BY_CUST);
				conn.setAutoCommit(true);
				rs = query(params, conn, pstmt);
			}
			
			Failure record = null;
			Worker worker = null;
			RepairCompany company = null;
			while (rs.next()) {
				record = new Failure();
				record.setFailure_id(rs.getString("FAILURE_ID"));
				Date plan_dt = rs.getDate("REPR_PLAN_DT");
				record.setRepr_plan_dt(plan_dt != null ? StringUtils.FORMAT_INPUT.format(plan_dt) : null);
				Date end_dt = rs.getDate("REPR_END_DT");
				record.setRepr_end_dt(end_dt != null ? StringUtils.FORMAT_INPUT.format(end_dt) : null);
				record.setStatus(Constants.getCodeNM(Constants.Code.Status.getCode(), rs.getString("STATUS")));
				record.setRepair_cost(rs.getInt("REPAIR_COST"));

				worker = new Worker();
				record.setWorker(worker);
				worker.setWorker_id(rs.getString("WORKER_ID"));
				worker.setWorker_nm(rs.getString("WORKER_NM"));

				company = new RepairCompany();
				record.setCompany(company);
				company.setComp_id(rs.getString("COMP_ID"));
				company.setComp_nm(rs.getString("COMP_NM"));

				if (prod_id == null && ser_no == null) {
					CustomerProduct product = new CustomerProduct();
					product.setCust_nm(rs.getString("CUST_NM"));
					record.setProduct(product);
				}
				
				records.add(record);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}

		return records;
	}

	public int createFailure(Failure failure) {
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			failure.setFailure_id(generateId_10());
			pstmt = conn.prepareStatement(SQL_CREATE_FAIL);
			pstmt.setString(1, failure.getFailure_id());
			pstmt.setString(2, failure.getStatus());
			pstmt.setString(3, failure.getProduct().getCust_id());
			pstmt.setString(4, failure.getProduct().getProduct().getProd_id());
			pstmt.setString(5, failure.getProduct().getSer_no());
			pstmt.setString(6, failure.getRepr_plan_dt() + ":00.0");
			pstmt.setString(7, failure.getCompany().getComp_id());
			pstmt.setString(8, failure.getWorker().getWorker_id());
			pstmt.setString(9, String.valueOf(failure.getFailure_info()));
			pstmt.setString(10, String.valueOf(failure.getRepair_cost()));
			System.out.println(SQL_CREATE_FAIL);
			
			conn.setAutoCommit(true);
			return super.executeUpdate(pstmt);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt);
		}

		return -1;
	}

	public Failure getFailureById(String failure_id) {
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Failure record = null;
		try {
			conn = getConnection();
			List<String> params = new ArrayList<>();
			params.add(failure_id);
			pstmt = conn.prepareStatement(SQL_FAIL_SEARCH_BY_ID);
			conn.setAutoCommit(true);
			rs = query(params, conn, pstmt);
			Worker worker = null;
			RepairCompany company = null;
			if (rs.next()) {
				record = new Failure();
				record.setFailure_id(rs.getString("FAILURE_ID"));
				Date plan_dt = rs.getDate("REPR_PLAN_DT");
				record.setRepr_plan_dt(plan_dt != null ? StringUtils.FORMAT_INPUT.format(plan_dt) : null);
				Date end_dt = rs.getDate("REPR_END_DT");
				record.setRepr_end_dt(end_dt != null ? StringUtils.FORMAT_INPUT.format(end_dt) : null);
				record.setStatus(Constants.getCodeNM(Constants.Code.Status.getCode(), rs.getString("STATUS")));
				record.setFailure_info(rs.getString("FAILURE_INFO"));
				record.setRepair_cost(rs.getInt("REPAIR_COST"));

				worker = new Worker();
				record.setWorker(worker);
				worker.setWorker_id(rs.getString("WORKER_ID"));
				worker.setWorker_nm(rs.getString("WORKER_NM"));

				company = new RepairCompany();
				record.setCompany(company);
				company.setComp_id(rs.getString("COMP_ID"));
				company.setComp_nm(rs.getString("COMP_NM"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}

		return record;
	}

	public Map<String, Object> checkEvidence(String failure_id) {
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Map<String, Object> msg = new HashMap<>();
		msg.put("str_photo", false);
		msg.put("str_photo", false);

		try {
			conn = getConnection();
			List<String> params = new ArrayList<>();
			params.add(failure_id);
			pstmt = conn.prepareStatement(SQL_CHK_EVIDENCE);
			conn.setAutoCommit(true);
			rs = query(params, conn, pstmt);
			if (rs.next()) {
				msg.put("str_photo", rs.getBlob("STR_PHOTO") != null);
				msg.put("end_photo", rs.getBlob("END_PHOTO") != null);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}
		
		return msg;
	}

	public boolean getEvidence(String failure_id, String img_cd) {
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			List<String> params = new ArrayList<>();
			params.add(failure_id);
			if (img_cd.equals("0")) {
				pstmt = conn.prepareStatement(SQL_GET_EVIDENCE_0);
			} else {
				pstmt = conn.prepareStatement(SQL_GET_EVIDENCE_1);
			}

			conn.setAutoCommit(true);
			rs = query(params, conn, pstmt);
			Blob blob = null;
			if (rs.next()) {
				if (img_cd.equals("0")) {
					blob = rs.getBlob("STR_PHOTO");
				} else {
					blob = rs.getBlob("END_PHOTO");
				}

				FileOutputStream fout = new FileOutputStream("./" + failure_id + "_" + img_cd + ".jpg"); 
				fout.write(blob.getBytes(1, (int)blob.length()));  
				fout.flush();  
		        fout.close();

		        return true;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}

		return false;
	}

	public ActionReport getReportById(String failure_id) {
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ActionReport record = null;
		try {
			conn = getConnection();
			List<String> params = new ArrayList<>();
			params.add(failure_id);
			pstmt = conn.prepareStatement(SQL_GET_REPORT);
			conn.setAutoCommit(true);
			rs = query(params, conn, pstmt);
			if (rs.next()) {
				record = new ActionReport();
				record.setFailure_info_dtl(rs.getString("FAILURE_INFO_DTL"));
				record.setFailure_cause(rs.getString("FAILURE_CAUSE"));
				record.setMemo(rs.getString("MEMO"));
				record.setAction_info(rs.getString("ACTION_INFO"));
				record.setAction_rslt(rs.getString("ACTION_RSLT"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}

		return record;
	}
}
