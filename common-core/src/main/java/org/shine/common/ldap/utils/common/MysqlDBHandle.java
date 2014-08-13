package org.shine.common.ldap.utils.common;

//package utils;
//
//import java.io.InputStream;
//import java.io.Reader;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.sql.Connection;
//import java.sql.DatabaseMetaData;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.Properties;
//
//import org.apache.log4j.Logger;
//
//public class MysqlDBHandle {
//	private static Logger logger = Logger.getLogger(MysqlDBHandle.class);
//
//	private static boolean transactionsSupported;
//	private static boolean subqueriesSupported;
//	private static boolean scrollResultsSupported;
//	private static boolean batchUpdatesSupported;
//	private static boolean streamTextRequired;
//	private static boolean maxRowsSupported;
//	private static boolean fetchSizeSupported;
//
//	public static Connection getConnection() {
//		Properties config = new Properties();
//		Connection conn = null;
//		try {
//			InputStream in = MysqlDBHandle.class
//					.getResourceAsStream("db.properties");
//			config.load(in);
//			Class.forName(config.getProperty("mysql_driver"));
//			conn = DriverManager.getConnection(config.getProperty("mysql_url"),
//					config.getProperty("mysql_user"), config
//							.getProperty("mysql_password"));
//			in.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return conn;
//	}
//
//	public static Statement getStatement() {
//		Statement stmt = null;
//		try {
//			stmt = getConnection()
//					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
//							ResultSet.CONCUR_READ_ONLY);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return stmt;
//	}
//
//	public static PreparedStatement getPreparedStatement(String sql) {
//		PreparedStatement pstmt = null;
//		try {
//			pstmt = getConnection().prepareStatement(sql);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return pstmt;
//	}
//
//	public static Connection getTransactionConnection() throws SQLException {
//		Connection con = getConnection();
//		if (isTransactionsSupported()) {
//			con.setAutoCommit(false);
//		}
//		return con;
//	}
//
//	public static void closeTransactionConnection(PreparedStatement pstmt,
//			Connection con, boolean abortTransaction) {
//		try {
//			if (pstmt != null) {
//				pstmt.close();
//			}
//		} catch (Exception e) {
//			logger.debug(e);
//		}
//		closeTransactionConnection(con, abortTransaction);
//	}
//
//	public static void closeTransactionConnection(Connection con,
//			boolean abortTransaction) {
//		if (con == null) {
//			return;
//		}
//
//		if (isTransactionsSupported()) {
//			try {
//				if (abortTransaction) {
//					con.rollback();
//				} else {
//					con.commit();
//				}
//			} catch (Exception e) {
//				logger.debug(e);
//			}
//		}
//		try {
//			if (isTransactionsSupported()) {
//				con.setAutoCommit(true);
//			}
//		} catch (Exception e) {
//			logger.debug(e);
//		}
//		try {
//			con.close();
//		} catch (Exception e) {
//			logger.debug(e);
//		}
//	}
//
//	public static void closeResultSet(ResultSet rs) {
//		try {
//			if (rs != null) {
//				rs.close();
//			}
//		} catch (SQLException e) {
//			logger.debug(e);
//		}
//	}
//
//	public static void closeStatement(Statement stmt) {
//		try {
//			if (stmt != null) {
//				stmt.close();
//			}
//		} catch (Exception e) {
//			logger.debug(e);
//		}
//	}
//
//	public static void closeConnection(ResultSet rs, Statement stmt,
//			Connection con) {
//		closeResultSet(rs);
//		closeStatement(stmt);
//		closeConnection(con);
//	}
//
//	public static void closeConnection(Statement stmt, Connection con) {
//		try {
//			if (stmt != null) {
//				stmt.close();
//			}
//		} catch (Exception e) {
//			logger.debug(e);
//		}
//		closeConnection(con);
//	}
//
//	public static void closeConnection(Connection con) {
//		try {
//			if (con != null) {
//				con.close();
//			}
//		} catch (Exception e) {
//			logger.debug(e);
//		}
//	}
//
//	public static Statement createScrollableStatement(Connection con)
//			throws SQLException {
//		if (isScrollResultsSupported()) {
//			return con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_READ_ONLY);
//		} else {
//			return con.createStatement();
//		}
//	}
//
//	public static PreparedStatement createScrollablePreparedStatement(
//			Connection con, String sql) throws SQLException {
//		if (isScrollResultsSupported()) {
//			return con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_READ_ONLY);
//		} else {
//			return con.prepareStatement(sql);
//		}
//	}
//
//	public static void scrollResultSet(ResultSet rs, int rowNumber)
//			throws SQLException {
//		if (isScrollResultsSupported()) {
//			if (rowNumber > 0) {
//				rs.setFetchDirection(ResultSet.FETCH_FORWARD);
//				try {
//					rs.relative(rowNumber);
//				} catch (SQLException e) {
//					for (int i = 0; i < rowNumber; i++) {
//						rs.next();
//					}
//				}
//			}
//		} else {
//			for (int i = 0; i < rowNumber; i++) {
//				rs.next();
//			}
//		}
//	}
//
//	public static String getLargeTextField(ResultSet rs, int columnIndex)
//			throws SQLException {
//		if (isStreamTextRequired()) {
//			Reader bodyReader = null;
//			String value = null;
//			try {
//				bodyReader = rs.getCharacterStream(columnIndex);
//				if (bodyReader == null) {
//					return null;
//				}
//				char[] buf = new char[256];
//				int len;
//				StringWriter out = new StringWriter(256);
//				while ((len = bodyReader.read(buf)) >= 0) {
//					out.write(buf, 0, len);
//				}
//				value = out.toString();
//				out.close();
//			} catch (Exception e) {
//				logger.debug(e);
//				throw new SQLException("Failed to load text field");
//			} finally {
//				try {
//					if (bodyReader != null) {
//						bodyReader.close();
//					}
//				} catch (Exception e) {
//
//				}
//			}
//			return value;
//		} else {
//			return rs.getString(columnIndex);
//		}
//	}
//
//	
//	public static void setLargeTextField(PreparedStatement pstmt,
//			int parameterIndex, String value) throws SQLException {
//		if (isStreamTextRequired()) {
//			Reader bodyReader;
//			try {
//				bodyReader = new StringReader(value);
//				pstmt.setCharacterStream(parameterIndex, bodyReader, value
//						.length());
//			} catch (Exception e) {
//				logger.debug(e);
//				throw new SQLException("Failed to set text field.");
//			}
//			
//		} else {
//			pstmt.setString(parameterIndex, value);
//		}
//	}
//
//	public static void setMaxRows(Statement stmt, int maxRows) {
//		if (isMaxRowsSupported()) {
//			try {
//				stmt.setMaxRows(maxRows);
//			} catch (Throwable t) {
//				maxRowsSupported = false;
//			}
//		}
//	}
//
//	
//	public static void setFetchSize(ResultSet rs, int fetchSize) {
//		if (isFetchSizeSupported()) {
//			try {
//				rs.setFetchSize(fetchSize);
//			} catch (Throwable t) {
//				fetchSizeSupported = false;
//			}
//		}
//	}
//
//	public static boolean isTransactionsSupported() {
//		return transactionsSupported;
//	}
//
//	public static boolean isStreamTextRequired() {
//		return streamTextRequired;
//	}
//
//	public static boolean isMaxRowsSupported() {
//		return maxRowsSupported;
//	}
//
//	public static boolean isFetchSizeSupported() {
//		return fetchSizeSupported;
//	}
//
//	public static boolean isSubqueriesSupported() {
//		return subqueriesSupported;
//	}
//
//	public static boolean isScrollResultsSupported() {
//		return scrollResultsSupported;
//	}
//
//	public static boolean isBatchUpdatesSupported() {
//		return batchUpdatesSupported;
//	}
//
//	private static void setMetaData(Connection con) throws SQLException {
//		DatabaseMetaData metaData = con.getMetaData();
//		transactionsSupported = metaData.supportsTransactions();
//		subqueriesSupported = metaData.supportsCorrelatedSubqueries();
//		try {
//			scrollResultsSupported = metaData
//					.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE);
//		} catch (Exception e) {
//			scrollResultsSupported = false;
//		}
//		batchUpdatesSupported = metaData.supportsBatchUpdates();
//		streamTextRequired = false;
//		maxRowsSupported = true;
//		fetchSizeSupported = true;
//		transactionsSupported = false;
//
//	}
//
//	public static String getTestSQL(String driver) {
//		return "select 1";
//	}
//
//	private MysqlDBHandle() {
//
//	}
//
// }
