package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

	private String username = "root";
	private String password = "";
	private String hostname = "127.0.0.1";
	private String port = "3306";
	private String database = "casadellatires";
	private String classname = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database;
	private Connection con;

	public Conexion() {
		try {
			Class.forName(classname);
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public Connection getConnection() {
		return con;
	}
	
	public boolean cerrarConexion() {
		try {
			if(con != null) {
				con.close();
				return true;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
		return false;	
	}

}
