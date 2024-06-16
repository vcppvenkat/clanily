package com.vbl.clanily.backend.connection.sqllite;

import java.sql.Connection;

public abstract class AbstractSqlLiteManager {

	// Holds connection object for the entire application
	protected static Connection connection = null;
	
	// path of the DB file. 
	protected static final String DB_FILE_PATH = "/Users/venkat/Documents/Home/Clanily Data/DEV/";
}
