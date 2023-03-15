package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//用于连接数据库
public class MyConnection {
    public Connection ConnectionDBS(){
        Connection con=null;
        String driver="com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://localhost:3306/community";
        String user="root";
        String password="111111";
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }
}
