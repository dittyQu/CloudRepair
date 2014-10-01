package com.ibm.bluemix.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import com.ibm.nosql.json.api.BasicDBList;
import com.ibm.nosql.json.api.BasicDBObject;
import com.ibm.nosql.json.util.JSON;

public class SQLDBHelper {

	private static String url = null;
	private static String databaseHost = null;
	private static String port = null;
	private static String user = null;
	private static String password = null;
	private static String databaseUrl = null;
	private static String databaseName = null;
	
	private static String thekey = null;
	private static boolean dbSetup = false;
	
	protected static void dbSetup() throws Exception{
		
		if(dbSetup) return; //one time variable setting
		String VCAP_SERVICES = System.getenv("VCAP_SERVICES");

		if (VCAP_SERVICES != null) {
			// parse the VCAP JSON structure
			BasicDBObject obj = (BasicDBObject) JSON.parse(VCAP_SERVICES);
			Set<String> keys = obj.keySet();
			// Look for the VCAP key that holds SQLDB information, it will pick the last one
			for (String eachkey : keys) {
				if (eachkey.contains("sqldb")) {
					thekey = eachkey;
				}
			}
			if (thekey == null) {
				throw new Exception("Key is null");
			}

			// Parsing the parameters out of the VCAP JSON document
			BasicDBList list = (BasicDBList) obj.get(thekey);
			obj = (BasicDBObject) list.get("0");
			obj = (BasicDBObject) obj.get("credentials");			
			url = (String) obj.get("jdbcurl");
			user = (String) obj.get("username");
		    password = (String) obj.get("password");

			if (url == null) {
			    databaseHost = (String) obj.get("host");
			    databaseName = (String) obj.get("db");
			    port = (String) obj.get("port").toString();
			    
			    // Use the jdbcurl or construct your own
			    databaseUrl = "jdbc:db2://" + databaseHost + ":" + port + "/" + databaseName;
			} else {
				databaseUrl = url;
			}

			dbSetup = true;
		} else {
			ResourceBundle config = ResourceBundle.getBundle("config");
			try {
			url = config.getString("jdbcurl");
			} catch(Exception e) {
				//NOP
			}

			if (url == null) {
				databaseHost = config.getString("host");
			    databaseName = config.getString("db");
			    port = config.getString("port");
			    user = config.getString("username");
			    password = config.getString("password");
			    // Use the jdbcurl or construct your own
			    databaseUrl = "jdbc:db2://" + databaseHost + ":" + port + "/" + databaseName;
			} else {
				databaseUrl = url;
			}

		    dbSetup = true;
		}
	}

	protected static Connection getConnection() throws Exception{
		dbSetup();
		 Connection con = null;
		  try {
		    Class.forName("com.ibm.db2.jcc.DB2Driver");
		    con = DriverManager.getConnection(databaseUrl, user, password);
		    con.setAutoCommit(false);
		    return con;
		  } catch (SQLException e) {
		    System.out.println("SQL Exception: " + e);
		    return con;
		  } catch (ClassNotFoundException e) {
			System.out.println("ClassNotFound Exception: " + e);
		    return con;
		  }
	}

	/**
	 * Don't forget We need to use this way to close pstmt and connection in persistence layer.
	 * @param sql
	 * @param mapper SQL execution parameters
	 * @param conn
	 * @param pstmt
	 * @return
	 */
	
	public static int executeUpdate(List<String> paramList, Connection conn, PreparedStatement pstmt) {
		int returnCode = -100;
		try{
			Iterator<String> paramIter = paramList.iterator();
			int i=1;
			while(paramIter.hasNext()){
				String eachParam= (String)paramIter.next();
				pstmt.setString(i, eachParam);
				i++;
			}
			returnCode = pstmt.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnCode;
	}
	
	public static int executeUpdate(PreparedStatement pstmt) {
		int returnCode = -100;
		try{
			returnCode = pstmt.executeUpdate();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return returnCode;
	}
	
	/**
	 * Don't forget: After execute query, we need to close resultset, prepared statement and connection in
	 * persistence layer
	 * @param mapper query parameters
	 * @param conn
	 * @param pstmt
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet query(List<String> paramList, Connection conn, PreparedStatement pstmt )throws SQLException{
		ResultSet result = null;
		try{
			Iterator<String> paramIter = paramList.iterator();
			int i=1;
			while(paramIter.hasNext()){
				String eachParam= paramIter.next();
				pstmt.setString(i, eachParam);
				i++;
			}
			result = pstmt.executeQuery();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Don't forget: After execute query, we need to close resultset, prepared statement and connection in
	 * persistence layer
	 * @param mapper query parameters
	 * @param conn
	 * @param pstmt
	 * @return
	 * @throws SQLException
	 */
	public static ResultSet query(Connection conn, PreparedStatement pstmt )throws SQLException{
		ResultSet result = null;
		try{
			result = pstmt.executeQuery();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return result;
	}
	
	public static void close(Connection conn, PreparedStatement pstmt, ResultSet result){
		//close connections.
		if(result != null){
			try{
				result.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(pstmt != null){
			try{
				pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Connection conn, PreparedStatement pstmt){
		//close connections.
		if(pstmt != null){
			try{
				pstmt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void close(Connection conn){
		if(conn != null){
			try{
				conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public static String generateId_10() {
		return String.valueOf(System.currentTimeMillis()).substring(3);
	}
	
//	/**
//	 * Write BLOB Column's Value for DB2
//	 * @param pstmt
//	 * @param colIndex
//	 * @param filePath
//	 * @throws IOException
//	 * @throws SerialException
//	 * @throws SQLException
//	 */
//	public static void writeBlob(PreparedStatement pstmt, int colIndex, String filePath) throws IOException, SerialException, SQLException {
//		File file = new File(filePath);
//		FileInputStream fin = null;
//		ByteArrayOutputStream out = null;
//		try {
//			fin = new FileInputStream(file);
//			out = new ByteArrayOutputStream();
//			byte[] b = new byte[1000];
//			int n;
//			while ((n = fin.read(b)) != -1){
//				out.write(b, 0, n);
//			}
//			pstmt.setBlob(colIndex, new SerialBlob(out.toByteArray()));
//		} finally {
//			try {
//				if (fin != null) fin.close();
//			} catch (Exception e) {
//				//NOP
//			}
//			try {
//				if (out != null) out.close();
//			} catch (Exception e) {
//				//NOP
//			}
//		}
//		
//	}
}