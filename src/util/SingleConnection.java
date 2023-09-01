package util;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * @author Sobreira Criado em 26/01/2007
 */
public class SingleConnection implements Connection {
	private Connection connection;

	public SingleConnection(Connection connection) {
		super();
		this.connection = connection;
	}

	public void clearWarnings() throws SQLException {
		connection.clearWarnings();
	}

	public void close() throws SQLException {
	}

	public void commit() throws SQLException {
		connection.commit();
	}

	public Statement createStatement() throws SQLException {
		return connection.createStatement();
	}

	public Statement createStatement(int arg0, int arg1, int arg2)
			throws SQLException {
		return connection.createStatement(arg0, arg1, arg2);
	}

	public Statement createStatement(int arg0, int arg1) throws SQLException {
		return connection.createStatement(arg0, arg1);
	}

	public boolean getAutoCommit() throws SQLException {
		return connection.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return connection.getCatalog();
	}

	public int getHoldability() throws SQLException {
		return connection.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return connection.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return connection.getTransactionIsolation();
	}

	public Map getTypeMap() throws SQLException {
		return connection.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return connection.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		return connection.isClosed();
	}

	public boolean isReadOnly() throws SQLException {
		return connection.isReadOnly();
	}

	public String nativeSQL(String arg0) throws SQLException {
		return connection.nativeSQL(arg0);
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		return connection.prepareCall(arg0, arg1, arg2, arg3);
	}

	public CallableStatement prepareCall(String arg0, int arg1, int arg2)
			throws SQLException {
		return connection.prepareCall(arg0, arg1, arg2);
	}

	public CallableStatement prepareCall(String arg0) throws SQLException {
		return connection.prepareCall(arg0);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2,
			int arg3) throws SQLException {
		return connection.prepareStatement(arg0, arg1, arg2, arg3);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1, int arg2)
			throws SQLException {
		return connection.prepareStatement(arg0, arg1, arg2);
	}

	public PreparedStatement prepareStatement(String arg0, int arg1)
			throws SQLException {
		return connection.prepareStatement(arg0, arg1);
	}

	public PreparedStatement prepareStatement(String arg0, int[] arg1)
			throws SQLException {
		return connection.prepareStatement(arg0, arg1);
	}

	public PreparedStatement prepareStatement(String arg0, String[] arg1)
			throws SQLException {
		return connection.prepareStatement(arg0, arg1);
	}

	public PreparedStatement prepareStatement(String arg0) throws SQLException {
		return connection.prepareStatement(arg0);
	}

	public void releaseSavepoint(Savepoint arg0) throws SQLException {
		connection.releaseSavepoint(arg0);
	}

	public void rollback() throws SQLException {
		connection.rollback();
	}

	public void rollback(Savepoint arg0) throws SQLException {
		connection.rollback(arg0);
	}

	public void setAutoCommit(boolean arg0) throws SQLException {
		connection.setAutoCommit(arg0);
	}

	public void setCatalog(String arg0) throws SQLException {
		connection.setCatalog(arg0);
	}

	public void setHoldability(int arg0) throws SQLException {
		connection.setHoldability(arg0);
	}

	public void setReadOnly(boolean arg0) throws SQLException {
		connection.setReadOnly(arg0);
	}

	public Savepoint setSavepoint() throws SQLException {
		return connection.setSavepoint();
	}

	public Savepoint setSavepoint(String arg0) throws SQLException {
		return connection.setSavepoint(arg0);
	}

	public void setTransactionIsolation(int arg0) throws SQLException {
		connection.setTransactionIsolation(arg0);
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Array createArrayOf(String typeName, Object[] elements)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSchema(String schema) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSchema() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void abort(Executor executor) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setNetworkTimeout(Executor executor, int milliseconds)
			throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
