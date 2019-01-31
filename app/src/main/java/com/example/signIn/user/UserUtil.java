package com.example.signIn.user;

import com.example.signIn.database.MySQL;

public class UserUtil {

    public static int STUDENT = 1;
    public static int TEACHER = 2;
    public static int ADMINISTRATION = 3;


    //查询数据库,判断是否登录成功
    public static boolean login(String userId,String password,int type){
        MySQL mysql = new MySQL();
        boolean ret = mysql.login(userId,password,type);
        return ret;
    }

    public static void main(String[] args) {
        MySQL mysql = new MySQL();
        boolean f = mysql.login("1","1",UserUtil.STUDENT);
        System.out.println(f);

        f = mysql.login("1","2",UserUtil.STUDENT);
        System.out.println(f);

        Student stu = findStudentById("15111204060");
        System.out.println(stu.name);
        System.out.println(stu.birthday);
        System.out.println(stu.major);
        System.out.println(stu.joinClassId);

    }


    //查询数据库,根据sId查找学生
    public static Student findStudentById(String sId) {
        return new MySQL().findStudentById(sId);
    }
}
