package dp_2020L038;


import java.sql.*;

/***
 * 
 * @author zhakiDapeng
 * @version 1.0
 * 连接数据库的类
 */
public class JDBC_Connection {
	static final String DB_URL = "jdbc:mysql://localhost/demo";
	static final String USER = "root";
	static final String PWD = "dapeng";

	public static Connection connectJDBS() {
		Connection conn = null;
		try {
			// 打开一个连接。
			conn = DriverManager.getConnection(DB_URL, USER, PWD);
			return conn;
		} catch (Exception ex) {
			System.out.println("在连接到JDBS时出现了错误!");
			return null;
		}
	}
}
