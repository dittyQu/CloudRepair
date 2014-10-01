package com.ibm.bluemix.demo.service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.bluemix.demo.dao.CustomerDAO;
import com.ibm.bluemix.demo.dao.ProductDAO;
import com.ibm.bluemix.demo.dto.Customer;
import com.ibm.bluemix.demo.dto.CustomerProduct;
import com.ibm.bluemix.demo.utils.StringUtils;

@Path("/cust")
public class CustomersService extends ServiceHelper {
	//顧客ID
	private static String KEY_CUST_ID = "cust_id";
	//名前（漢字）
	private static String KEY_CUST_NM = "cust_nm";
	//名前（カタカナ）
	private static String KEY_CUST_KN = "cust_kn";
	//性別
	private static String KEY_CUST_SEX = "cust_sex";
	//生年月日
	private static String KEY_CUST_AGE = "cust_age";
	//住所
	private static String KEY_CUST_ADDR = "cust_addr";

    @Context protected HttpServletResponse response;  
    @Context protected HttpServletRequest request;

	/**
	 * 顧客検索
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Path("/cust_sch")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchCustomer() throws UnsupportedEncodingException {
		Customer condition = new Customer();
		condition.setCust_id(request.getParameter(KEY_CUST_ID));
		condition.setCust_nm(request.getParameter(KEY_CUST_NM));
		condition.setCust_kn(request.getParameter(KEY_CUST_KN));
		condition.setCust_sex(request.getParameter(KEY_CUST_SEX));
		String cust_age = request.getParameter(KEY_CUST_AGE);
		if (!StringUtils.isEmpty(cust_age)) {
			condition.setCust_birth(String.valueOf(new Date().getYear() + 1900 - Integer.valueOf(cust_age)));
		}
		condition.setCust_addr(request.getParameter(KEY_CUST_ADDR));
		
		CustomerDAO dao = new CustomerDAO();
		List<Customer> customerList = dao.searchCustomer(condition);

		Map<String, Object> msg = new HashMap<>();
		if (customerList != null) {
			msg.put("data", customerList);
			msg.put("result", Boolean.TRUE);
			return Response.ok(msg).build();
		} else {
			msg.put("result", Boolean.FALSE);
			msg.put("err_msg", "検索失敗。");
			return Response.ok(msg).build();
		}
	}

	/**
	 * 顧客購入製品一覧
	 * @return
	 */
	@Path("/cust_prod_sch")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchCustomerProducts() {
		String cust_id = request.getParameter(KEY_CUST_ID);
		CustomerDAO custDao = new CustomerDAO();
		Customer customer = custDao.searchCustomerById(cust_id);
		ProductDAO prodDao = new ProductDAO();
		List<CustomerProduct> prodList = prodDao.searchProductsByCustomer(cust_id);

		Map<String, Object> msg = new HashMap<>();
		if (prodList != null) {
			if (customer == null) {
				customer = new Customer();
			}
			msg.put("customer", customer);
			msg.put("products", prodList);
			msg.put("result", Boolean.TRUE);
			return Response.ok(msg).build();
		} else {
			msg.put("result", Boolean.FALSE);
			msg.put("err_msg", "検索失敗。");
			return Response.ok(msg).build();
		}
	}

	public static void main(String[] args) {
//		CustomerDAO dao = new ProductDAO();
////		Customer condition = new Customer();
////		condition.setCust_id("1");
////		condition.setCust_nm("佐藤");
////		condition.setCust_kn("サトウ");
////		condition.setCust_sex("M");
////		condition.setCust_addr("飯田橋");
////		condition.setCust_birth("19800101");
////		List<Customer> customerList = dao.searchCustomer(condition);
////		System.out.println(customerList.size());
//		
//		List<CustomerProduct> prodList = dao.searchProductsByCustomer("0000001");
//		System.out.println(prodList.size());
	}
}
