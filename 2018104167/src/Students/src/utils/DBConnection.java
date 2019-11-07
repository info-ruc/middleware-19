package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	String DBDRIVER = "com.mysql.jdbc.Driver";
	String DBURL = "jdbc:mysql://localhost:3306/student?characterEncoding=gbk";
	String DBUSER = "root";
	String DBPWD = "root";
	Connection conn = null;
	
	public DBConnection() throws Exception{
		try{
			Class.forName(DBDRIVER);
			this.conn = DriverManager.getConnection(DBURL, DBUSER, DBPWD);
			System.out.println("yes");
		}catch(Exception e){
			
			throw e;
		}
	}
	
	public Connection getConnection(){
		return this.conn;
	}
	
	public void close() throws Exception{
		if(this.conn != null){
			try{
				this.conn.close();
			}catch(Exception e){
				throw e;
			}
		}
	}

}
