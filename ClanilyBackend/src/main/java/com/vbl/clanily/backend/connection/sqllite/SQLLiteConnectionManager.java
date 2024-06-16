package com.vbl.clanily.backend.connection.sqllite;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLLiteConnectionManager extends AbstractSqlLiteManager {

	public static void prepareConnection() throws ClassNotFoundException, Exception {

		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:" +DB_FILE_PATH + "Clanily.db");

	}

	public static void closeConnection() throws SQLException {

		connection.close();

	}
	
	public static boolean isConnected() throws Exception {
		
		return (connection != null && !connection.isClosed() && connection.isValid(1000));
	}

}
