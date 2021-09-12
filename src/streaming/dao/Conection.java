package streaming.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conection {
	
	private final static String driver = "org.postgresql.Driver";
	private final static String URL = "jdbc:postgresql://localhost:5432/film_database";
	private final static String USER = "postgres";
	private final static String PASSWORD = "docker";
	
	private Connection connection;
	
	public Conection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("Conexão realizada com sucesso!");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Connection getConnection() {
		return connection;
	}

	public static void main(String[] args) {
		new Conection();
	}

}
