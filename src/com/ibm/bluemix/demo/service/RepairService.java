package com.ibm.bluemix.demo.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
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
import com.ibm.bluemix.demo.dao.FailureDAO;
import com.ibm.bluemix.demo.dao.ProductDAO;
import com.ibm.bluemix.demo.dto.ActionReport;
import com.ibm.bluemix.demo.dto.Customer;
import com.ibm.bluemix.demo.dto.CustomerProduct;
import com.ibm.bluemix.demo.dto.Failure;
import com.ibm.bluemix.demo.dto.Product;
import com.ibm.bluemix.demo.dto.RepairCompany;
import com.ibm.bluemix.demo.dto.Worker;
import com.ibm.bluemix.demo.utils.Constants;
import com.ibm.bluemix.demo.utils.StringUtils;

@Path("/rep")
public class RepairService extends ServiceHelper {
	//顧客ID
	private static String KEY_CUST_ID = "cust_id";
	//製品ID
	private static String KEY_PROD_ID = "prod_id";
	//シリアルID
	private static String KEY_SER_NO = "ser_no";
	//修理業者ID
	private static String KEY_COMP_ID = "comp_id";
	//修理日
	private static String KEY_REP_DATE = "repr_plan_dt";
	//故障原因
	private static String KEY_FAIL_INFO = "fail_info";
	//修理費用
	private static String KEY_REPAIR_COST = "repair_cost";
	//修理担当者ID
	private static String KEY_WORKER_ID = "worker_id";
	//故障事件ID
	private static String KEY_FAIL_ID = "fail_id";
	//エビデンスCD
	private static String KEY_IMG_CD = "img_cd";
	
	@Context protected HttpServletResponse response;  
    @Context protected HttpServletRequest request;

	/**
	 * 故障事件検索
	 * @return
	 */
	@Path("/fail_sch")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchFailures() {
		String prod_id = request.getParameter(KEY_PROD_ID);
		String ser_no = request.getParameter(KEY_SER_NO);

		ProductDAO prodDao = new ProductDAO();
		CustomerProduct product = prodDao.searchProductById(prod_id, ser_no);

		FailureDAO failureDao = new FailureDAO();
		List<Failure> failureList = failureDao.searchFailures(prod_id, ser_no);

		Map<String, Object> msg = new HashMap<>();
		if (failureList != null) {
			msg.put("product", product);
			msg.put("failures", failureList);
			msg.put("result", Boolean.TRUE);
			return Response.ok(msg).build();
		} else {
			msg.put("result", Boolean.FALSE);
			msg.put("err_msg", "検索失敗。");
			return Response.ok(msg).build();
		}
	}
	/**
	 * 修理業者リストを取得する
	 * @return
	 */
	@Path("/getcomp")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCompanies() {
		String cust_id = request.getParameter(KEY_CUST_ID);

		List<RepairCompany> compList = Constants.getCompanyList();
		CustomerDAO custDao = new CustomerDAO();
		Customer cust = custDao.searchCustomerById(cust_id);
		Map<String, Object> msg = new HashMap<>();
		if (compList != null) {
			msg.put("cust", cust);
			msg.put("comp_list", compList);
			msg.put("result", Boolean.TRUE);
			return Response.ok(msg).build();
		} else {
			msg.put("result", Boolean.FALSE);
			msg.put("err_msg", "検索失敗。");
			return Response.ok(msg).build();
		}
	}

	/**
	 * 修理担当者リストを取得する
	 * @return
	 */
	@Path("/getwk")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWorkers() {
		String comp_id = request.getParameter(KEY_COMP_ID);
		String rep_date = request.getParameter(KEY_REP_DATE);
		List<Worker> workers = null;

		if (comp_id == null) {
			workers = new ArrayList<>(); 
		} else {
			workers = Constants.getWorkerList(comp_id, rep_date);
		}

		Map<String, Object> msg = new HashMap<>();
		if (workers != null) {
			msg.put("data", workers);
			msg.put("result", Boolean.TRUE);
			return Response.ok(msg).build();
		} else {
			msg.put("result", Boolean.FALSE);
			return Response.ok(msg).build();
		}
	}

	/**
	 * 故障事件登録
	 * @return
	 */
	@Path("/new_fail")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response newFailure() {
		String cust_id = request.getParameter(KEY_CUST_ID);
		String prod_id = request.getParameter(KEY_PROD_ID);
		String ser_no = request.getParameter(KEY_SER_NO);
		String comp_id = request.getParameter(KEY_COMP_ID);
		String worker_id = request.getParameter(KEY_WORKER_ID);
		String repr_plan_dt = request.getParameter(KEY_REP_DATE);
		String fail_info = request.getParameter(KEY_FAIL_INFO);
		String repair_cost = request.getParameter(KEY_REPAIR_COST);

		Failure failure = new Failure();
		RepairCompany company = new RepairCompany();
		failure.setCompany(company);
		CustomerProduct product = new CustomerProduct();
		failure.setProduct(product);
		Product prod = new Product();
		product.setProduct(prod);
		Worker worker = new Worker();
		failure.setWorker(worker);
		
		product.setCust_id(cust_id);
		prod.setProd_id(prod_id);
		product.setSer_no(ser_no);
		failure.setRepr_plan_dt(repr_plan_dt);
		failure.setFailure_info(fail_info);
		failure.setRepair_cost(StringUtils.isEmpty(repair_cost)? 0 : Integer.valueOf(repair_cost));
		company.setComp_id(comp_id);
		worker.setWorker_id(worker_id);
		failure.setStatus("02");

		FailureDAO failureDao = new FailureDAO();
		int rslt = failureDao.createFailure(failure);
		failure = failureDao.getFailureById(failure.getFailure_id());
		Map<String, Object> msg = new HashMap<>();
		if (rslt == 1) {
			msg.put("result", Boolean.TRUE);
			msg.put("failure", failure);
			return Response.ok(msg).build();
		} else {
			msg.put("result", Boolean.FALSE);
			msg.put("err_msg", "登録失敗。");
			return Response.ok(msg).build();
		}
	}

	/**
	 * 故障事件登録
	 * @return
	 */
	@Path("/chkimg")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkEvidence() {
		String failure_id = request.getParameter(KEY_FAIL_ID);
		
		FailureDAO dao = new FailureDAO();
		Map<String, Object> msg = dao.checkEvidence(failure_id);
		ActionReport report = dao.getReportById(failure_id);
		if (report != null) {
			msg.put("failure_info_dtl_txt", Constants.getCodeNM(Constants.Code.FAIL_INFO_DTL.getCode(), report.getFailure_info_dtl()));
			msg.put("failure_cause_txt", Constants.getCodeNM(Constants.Code.FAIL_CAUSE.getCode(), report.getFailure_cause()));
			msg.put("action_info_txt", Constants.getCodeNM(Constants.Code.ACTION_INFO.getCode(), report.getAction_info()));
			msg.put("action_rslt_txt", Constants.getCodeNM(Constants.Code.ACTION_RSLT.getCode(), report.getAction_rslt()));
			msg.put("memo", report.getMemo());
		}
		msg.put("result", true);
		Failure fail = dao.getFailureById(failure_id);
		msg.put("fail_info", fail.getFailure_info());

		return Response.ok(msg).build();
	}
	/**
	 * 故障事件登録
	 * @return
	 */
	@Path("/getimg")
	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getEvidence() {
		String img_cd = request.getParameter(KEY_IMG_CD);
		String fail_id = request.getParameter(KEY_FAIL_ID);
		
		FailureDAO dao = new FailureDAO();
		if (dao.getEvidence(fail_id, img_cd)) {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(new File("./" + fail_id + "_" + img_cd + ".jpg"));
			} catch (IOException e) {
				
			} 
			return Response.ok(fis).header("Content-disposition", "inline; filename=" + fail_id + ".jpg").build();
		} else {
			return Response.noContent().build();
		}
	}

	/**
	 * 故障事件チェック一覧検索
	 * @return
	 */
	@Path("/fail_all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchAllFailures() {
		FailureDAO failureDao = new FailureDAO();
		List<Failure> failureList = failureDao.searchFailures(null, null);

		Map<String, Object> msg = new HashMap<>();
		if (failureList != null) {
			msg.put("failures", failureList);
			msg.put("result", Boolean.TRUE);
			return Response.ok(msg).build();
		} else {
			msg.put("result", Boolean.FALSE);
			msg.put("err_msg", "検索失敗。");
			return Response.ok(msg).build();
		}
	}
}
