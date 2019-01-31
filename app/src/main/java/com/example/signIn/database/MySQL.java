package com.example.signIn.database;

import com.example.signIn.user.Student;
import com.example.signIn.user.UserUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL {

    public boolean fun() {
        boolean flag=true;
        Connection conn = DBHealper.getConnection();
        try {
            String sql = "INSERT INTO student(sName,sPassword) VALUES(?,?);";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "wei");
            ps.setString(2, "123456");

            int m = ps.executeUpdate();
            if (m != 0) {
                System.out.println("插入成功");
            } else {
                System.out.println("插入失败");
                flag = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            flag = false;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return flag;
    }

    //判断是否登录成功
    public boolean login(String userId,String password,int type){

        if(userId!=null && userId.length()>0){
            Connection conn = DBHealper.getConnection();
            if(conn==null){
                System.out.println("连接中断**********");
                return false;
            }

            String sql;
            //如果是学生
            if (type==UserUtil.STUDENT){
                sql = "select sPassword from student where sId='"+userId+"'";
            }
            //如果是教师
            else if (type==UserUtil.TEACHER){
                sql = "select tPassword from teacher where tId='"+userId+"'";
            }
            //如果是管理员登录
            else if (type==UserUtil.ADMINISTRATION){
                //暂不支持管理员登录功能
                return false;
            }else{
                return false;
            }
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String str = rs.getString(1);
                    if(str.equals(password)) {
                        return true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                //关闭连接
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    //查询数据库,根据sId查找学生
    public Student findStudentById(String sId){
        if (sId != null && sId.length() > 0) {
            Connection conn = DBHealper.getConnection();
            Student stu;
            String sql = "SELECT sName,sSex,sBirthday,sDepartment,sMajor,joinClassId FROM `student` WHERE sId='" + sId + "';";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    stu = new Student();
                    stu.id = sId;
                    stu.name = rs.getString("sName");
                    stu.sex = rs.getString("sSex");
                    stu.birthday = rs.getDate("sBirthday");
                    stu.department = rs.getString("sDepartment");
                    stu.major = rs.getString("sMajor");
                    stu.joinClassId = rs.getString("joinClassId");
                    return stu;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void main(String[] args) {
        MySQL mysql = new MySQL();
        boolean f = mysql.login("1","1",UserUtil.STUDENT);
        System.out.println(f);

        f = mysql.login("1","2",UserUtil.STUDENT);
        System.out.println(f);

        MySQL m2 = new MySQL();
        f = m2.login("1","1",UserUtil.STUDENT);
        System.out.println(f);

        f = m2.login("1","2",UserUtil.STUDENT);
        System.out.println(f);
    }
}
