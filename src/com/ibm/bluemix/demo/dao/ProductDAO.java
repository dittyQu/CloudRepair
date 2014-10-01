package com.ibm.bluemix.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ibm.bluemix.demo.dto.CustomerProduct;
import com.ibm.bluemix.demo.dto.Product;
import com.ibm.bluemix.demo.utils.Constants;

public class ProductDAO extends SQLDBHelper {

	// 顧客購入製品リスト検索SQL
	private final static String SQL_PROD_SEARCH_BY_CUST = "select P.PROD_CAT, S.PROD_ID, P.PROD_NM, S.SER_NO, S.CUST_ID, S.SAL_DATE, P.ENSURE_PERIOD from "
			+ Constants.DB_SCHEMA_NAME + ".SALES S, "+ Constants.DB_SCHEMA_NAME +".M_PROD P "
			+ "where S.PROD_ID = P.PROD_ID and S.CUST_ID=? "
			+ "order by S.SAL_DATE desc";
	// 製品情報検索SQL
	private final static String SQL_PROD_SEARCH_BY_ID = "select P.PROD_CAT, S.PROD_ID, P.PROD_NM, S.SER_NO, S.CUST_ID, S.SAL_DATE, P.ENSURE_PERIOD from "
			+ Constants.DB_SCHEMA_NAME + ".SALES S, "+ Constants.DB_SCHEMA_NAME +".M_PROD P "
			+ "where S.PROD_ID = P.PROD_ID and S.PROD_ID=? and S.SER_NO=?"
			+ "order by S.SAL_DATE desc";
	/**
	 * 顧客購入製品一覧検索
	 * 
	 * @param cust_id
	 * @return
	 */
	public List<CustomerProduct> searchProductsByCustomer(String cust_id) {
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<CustomerProduct> records = new ArrayList<>();
		try {
			conn = getConnection();
			List<String> params = new ArrayList<>();
			params.add(cust_id);
			pstmt = conn.prepareStatement(SQL_PROD_SEARCH_BY_CUST);
			conn.setAutoCommit(true);
			rs = query(params, conn, pstmt);
			CustomerProduct record = null;
			Product prod = null;
			while (rs.next()) {
				record = new CustomerProduct();
				prod = new Product();
				prod.setProd_id(rs.getString("PROD_ID"));
				prod.setProd_cat(rs.getString("PROD_CAT"));
				prod.setProd_nm(rs.getString("PROD_NM"));
				prod.setEnsure_period(rs.getString("ENSURE_PERIOD"));
				record.setCust_id(cust_id);
				record.setProduct(prod);
				record.setSer_no(rs.getString("SER_NO"));
				record.setSal_date(rs.getDate("SAL_DATE"));
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

	/**
	 * 製品検索
	 * 
	 * @param cust_id
	 * @return
	 */
	public CustomerProduct searchProductById(String prod_id, String ser_no) {
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		CustomerProduct record = null;
		try {
			conn = getConnection();
			List<String> params = new ArrayList<>();
			params.add(prod_id);
			params.add(ser_no);
			pstmt = conn.prepareStatement(SQL_PROD_SEARCH_BY_ID);
			conn.setAutoCommit(true);
			rs = query(params, conn, pstmt);
			
			Product prod = null;
			if (rs.next()) {
				record = new CustomerProduct();
				prod = new Product();
				prod.setProd_id(rs.getString("PROD_ID"));
				prod.setProd_cat(rs.getString("PROD_CAT"));
				prod.setProd_nm(rs.getString("PROD_NM"));
				prod.setEnsure_period(rs.getString("ENSURE_PERIOD"));
				record.setProduct(prod);
				record.setSer_no(rs.getString("SER_NO"));
				record.setSal_date(rs.getDate("SAL_DATE"));
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
