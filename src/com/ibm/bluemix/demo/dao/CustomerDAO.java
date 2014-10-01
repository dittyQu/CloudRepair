package com.ibm.bluemix.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ibm.bluemix.demo.dto.Customer;
import com.ibm.bluemix.demo.utils.Constants;
import com.ibm.bluemix.demo.utils.StringUtils;

public class CustomerDAO extends SQLDBHelper {
	// 顧客検索SQL
	private final static String SQL_CUSTOMER_SEARCH = "select * from " + Constants.DB_SCHEMA_NAME + ".M_CUST ";

	/**
	 * 顧客検索
	 *
	 * @param condition
	 * @return
	 */
	public List<Customer> searchCustomer(Customer condition) {
		StringBuilder sql = new StringBuilder(SQL_CUSTOMER_SEARCH);

		//SQLの検索条件部を準備する
		prepareSearchCondition(sql, condition);
		System.out.println(sql.toString());
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Customer> customers = new ArrayList<>();
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			conn.setAutoCommit(true);
			rs = query(conn, pstmt);
			Customer record = null;
			while (rs.next()) {
				record = new Customer();
				record.setCust_id(rs.getString("CUST_ID"));
				record.setCust_nm(rs.getString("CUST_NM"));
				record.setCust_kn(rs.getString("CUST_KN"));
				record.setCust_birth(rs.getString("CUST_BIRTH"));
				record.setCust_sex(rs.getString("CUST_SEX"));
				record.setCust_addr(rs.getString("CUST_ADDR"));
				record.setCust_point_x(rs.getString("CUST_POINT_X"));
				record.setCust_point_y(rs.getString("CUST_POINT_Y"));
				customers.add(record);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}

		return customers;
	}
	
	private static void prepareSearchCondition(StringBuilder sql, Customer condition) {
		boolean emptyCondition = true;
		//顧客ID
		if (!StringUtils.isEmpty(condition.getCust_id())) {
			if (emptyCondition) {
				sql.append(" WHERE ");
			} else {
				sql.append(" AND ");
			}

			sql.append(" CUST_ID like '%").append(condition.getCust_id()).append("%' \n");
			emptyCondition = false;
		}
		//名前（漢字）
		if (!StringUtils.isEmpty(condition.getCust_nm())) {
			if (emptyCondition) {
				sql.append(" WHERE ");
			} else {
				sql.append(" AND ");
			}

			sql.append(" CUST_NM like '%").append(condition.getCust_nm()).append("%' \n");
			emptyCondition = false;
		}
		//名前（カタカナ）
		if (!StringUtils.isEmpty(condition.getCust_kn())) {
			if (emptyCondition) {
				sql.append(" WHERE ");
			} else {
				sql.append(" AND ");
			}

			sql.append(" CUST_KN like '%").append(condition.getCust_kn()).append("%' \n");
			emptyCondition = false;
		}
		//生年月日
		if (!StringUtils.isEmpty(condition.getCust_birth())) {
			if (emptyCondition) {
				sql.append(" WHERE ");
			} else {
				sql.append(" AND ");
			}

			sql.append(" CUST_BIRTH like '").append(condition.getCust_birth()).append("%' \n");
			emptyCondition = false;
		}

		//性別
		if (!StringUtils.isEmpty(condition.getCust_sex())) {
			if (emptyCondition) {
				sql.append(" WHERE ");
			} else {
				sql.append(" AND ");
			}

			sql.append(" CUST_SEX = '").append(condition.getCust_sex()).append("' \n");
			emptyCondition = false;
		}

		//住所
		if (!StringUtils.isEmpty(condition.getCust_addr())) {
			if (emptyCondition) {
				sql.append(" WHERE ");
			} else {
				sql.append(" AND ");
			}

			sql.append(" CUST_ADDR like '%").append(condition.getCust_addr()).append("%' \n");
			emptyCondition = false;
		}
	}

	public Customer searchCustomerById(String cust_id) {
		StringBuilder sql = new StringBuilder(SQL_CUSTOMER_SEARCH);
		sql.append(" WHERE CUST_ID = '").append(cust_id).append("'");
		System.out.println(sql);
		// Define Connection and statement
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Customer customer = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql.toString());
			conn.setAutoCommit(true);
			rs = query(conn, pstmt);
			if (rs.next()) {
				customer = new Customer();
				customer.setCust_id(rs.getString("CUST_ID"));
				customer.setCust_nm(rs.getString("CUST_NM"));
				customer.setCust_kn(rs.getString("CUST_KN"));
				customer.setCust_birth(rs.getString("CUST_BIRTH"));
				customer.setCust_sex(rs.getString("CUST_SEX"));
				customer.setCust_addr(rs.getString("CUST_ADDR"));
				customer.setCust_point_x(rs.getString("CUST_POINT_X"));
				customer.setCust_point_y(rs.getString("CUST_POINT_Y"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			// close connections.
			close(conn, pstmt, rs);
		}

		return customer;
	}
}
