package sop.dao.impl;

import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class SQLHelper {
	//定义三个变量
	private static Connection ct=null;
	private static PreparedStatement ps=null;
	private static ResultSet rs=null;
	private static CallableStatement cs=null;
	
	//连接数据库的用户名，密码，url，驱动
	//说明：在实际开发中，我们往往把这些变量写到一个外部文件中
	//当程序启动时，我们读入这些配置信息。java.util.Properites
	private static String username;
	private static String password;
	private static String driver;
	private static String url;

	//使用静态块加载驱动(驱动只需要加载一次)
	static{
//使用Properties类，来读取配置文件
		Properties pp=new Properties();
		FileInputStream fis=null;
		try {
			fis=new FileInputStream("D:/workspaceForMars/SOP/src/dbinfo.properties");
			//让pp与dbinfo.properties文件关联起来
			pp.load(fis);
			//获取dbinfo.properties文件内信息
			setUsername((String) pp.getProperty("username"));
			setPassword((String) pp.getProperty("password"));
			driver=(String) pp.getProperty("driver");
			setUrl((String) pp.getProperty("url"));
			
			//获得驱动
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(fis!=null){
					fis.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			fis=null;
		}
	}

	public static Connection getCt() {
		return ct;
	}

	public static void setCt(Connection ct) {
		SQLHelper.ct = ct;
	}

	public static PreparedStatement getPs() {
		return ps;
	}

	public static void setPs(PreparedStatement ps) {
		SQLHelper.ps = ps;
	}

	public static ResultSet getRs() {
		return rs;
	}

	public static void setRs(ResultSet rs) {
		SQLHelper.rs = rs;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		SQLHelper.username = username;
	}

	public static String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		SQLHelper.password = password;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		SQLHelper.url = url;
	}
	
	
	//调用存储过程的方法
	public static void executeProcedure(String sql,String[] parameters){
		try {
			ct=DriverManager.getConnection(url,username,password);
			cs=ct.prepareCall(sql);
			if(parameters!=null){
				for(int i=0;i<parameters.length;i++){
					cs.setString(i+1, parameters[i]);
					System.out.println(parameters[i]);
				}
			}
			//执行
			cs.execute();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	//调用展示全部的存储过程的方法
	public static void executeListAllProcedure(String sql,String id){
		try {
			ct=DriverManager.getConnection(url,username,password);
			cs=ct.prepareCall(sql);
			cs.setString(1, id);
			//给第二个?注册
			cs.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR);
			//执行
			cs.execute();
			rs=(ResultSet)cs.getObject(2);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
	//调用展示全部的存储过程的方法
	public static void executeListAllProcedure2(String sql){
		try {
			ct=DriverManager.getConnection(url,username,password);
			cs=ct.prepareCall(sql);
			cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
			//执行
			cs.execute();
			rs=(ResultSet)cs.getObject(1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}
	
		//完成查询任务
		public static ResultSet executeQuery(String sql,String[] parameters){
			try {
				//根据实际情况我们对sql语句?赋值
				//得到连接
				ct=DriverManager.getConnection(url,username,password);
				//创建ps对象，得到sql语句对象
				ps=ct.prepareStatement(sql);
				//如果parameters不为null，才赋值
				if(parameters!=null){
					for(int i=0;i<parameters.length;i++){
						ps.setString(i+1, parameters[i]);
					}
				}
				rs=ps.executeQuery();
			} catch (SQLException e) {
				e.printStackTrace();
				//抛出运行异常
				throw new RuntimeException(e.getMessage());
			} 
			return rs;
		}

		//统一的curd操作
		public static void executeUpdate(String sql,String[] parameters){
			try {
				ct=DriverManager.getConnection(url,username,password);
				ps=ct.prepareStatement(sql);
				if(parameters!=null){
					for(int i=0;i<parameters.length;i++){
						ps.setString(i+1, parameters[i]);
					}
				}
				//执行
				ps.executeUpdate();

			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e.getMessage());
			}
		}
		

	
	//把关闭资源写成函数
		public static void close(){
			//关闭资源
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				rs=null;
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ps=null;
			}
			if(ct!=null){
				try {
					ct.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ct=null;
			}
			if(cs!=null){
				try {
					cs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				cs=null;
			}
		}

		public static CallableStatement getCs() {
			return cs;
		}

		public static void setCs(CallableStatement cs) {
			SQLHelper.cs = cs;
		}
		


}
