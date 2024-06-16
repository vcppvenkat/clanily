package com.vbl.clanily.ui.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vbl.clanily.backend.connection.sqllite.SQLLiteConnectionManager;

@SpringBootApplication
@EnableAutoConfiguration
public class ClanilyApplication {

	@Override
	protected void finalize() throws Throwable {

		try {

			// Close Sql-lite connection
			SQLLiteConnectionManager.closeConnection();
			System.out.println("Sqllite connection is closed");
			
		} catch (Exception e) {
			System.out.println(
					"There was an issue while closing the database. Make sure the db file is available in the desire location.");
			e.printStackTrace();
			
		} finally {

		}

	}

	public static void main(String[] args) {

		try {

			// Initialize Sql-lite connection
			SQLLiteConnectionManager.prepareConnection();

			if (SQLLiteConnectionManager.isConnected()) {
				System.out.println("Connection successful with sqllite database ");
				System.out.println("Continue to load web application");
			} else {
				System.out.println("Connection unsuccessful with sqllite database ");
				System.out.println("Please fix connection issues with sqllite DB");
				System.out.println("Exiting clanily application");
				System.exit(0);
			}
		} catch (ClassNotFoundException e) {
			System.out.println("JDBC Class not found in classpath. Make sure that it is available");
			e.printStackTrace();
			System.exit(0);

		} catch (Exception e) {
			System.out.println(
					"There was an issue while loading the database. Make sure the db file is available in the desire location.");
			e.printStackTrace();
			System.exit(0);
		} finally {

		}

		SpringApplication.run(ClanilyApplication.class, args);

	}

}
