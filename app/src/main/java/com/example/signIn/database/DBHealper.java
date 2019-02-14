package com.example.signIn.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHealper {

    private static String driver = "com.mysql.jdbc.Driver"; // 驱动程序名
    private static String url = "jdbc:mysql://47.102.36.39:3306/student"; // URL指向要访问的数据库名, 其中student是数据库名,需要相应的修改
    private static String user = "root"; // MySQL配置时的用户名
    private static String password = "my password"; // MySQL登录密码

    static Connection connection;

    //返回连接对象
    public static Connection getConnection(){
        try{
            Class.forName(driver);
            connection = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("驱动jar包没有导入!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("连接失败!");
        }
        return connection;
    }


}
