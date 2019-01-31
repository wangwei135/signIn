package com.example.signIn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.signIn.user.Student;
import com.example.signIn.user.UserUtil;

public class LoginActivity extends AppCompatActivity {

    private EditText et_id,et_password;
    private Button btn_login;
    public static boolean autoLogin = false ; //是否自动登录
    private SharedPreferences sp; //本地保存用户,方便下次自动登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_id = findViewById(R.id.et_id);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);

        //获取上次的用户名和密码,自动填充用户密码
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE); //用户名和密码只允许为当前程序读写
        if (sp == null){
            System.out.println("sp是null********************");
        }else{
            System.out.println("sp不是null===================");
        }
        String userId = sp.getString("userId","");
        System.out.println("获取的用户ID是:"+userId);
        if(userId!=null && userId.length()>0){
            et_id.setText(userId);
            //先设置用户名,再设置密码
            String password = sp.getString("password","");
            System.out.println("获取的密码是:"+password);
            if(password!=null && password.length()>0){
                et_password.setText(password);
                //设置好之后,自动登录
                if(autoLogin)
                    new Thread(runnableLogin).start();
            }
        }

        //用户登录按钮添加点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                //建立一个线程来验证用户登录
                new Thread(runnableLogin).start();
            }
        });
    }


    //建立一个线程来验证用户登录
    Runnable runnableLogin = new Runnable() {
        @Override
        public void run() {
        //验证学生登录是否正确
        String userId = et_id.getText().toString();
        String password = et_password.getText().toString();
        boolean flag = UserUtil.login(userId,password,UserUtil.STUDENT);
        if ( flag ){
            //登录成功
            System.out.println("登录成功!");
            //保存用户名和密码,方便下一次登录
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("userId",userId);
            editor.putString("password",password);
            boolean save = editor.commit();
            if (save)
                System.out.println("保存成功"+userId+"和"+password);
            else
                System.out.println("保存失败****************");

            //发送消息,跳转到MainActivity界面
            myHandler.sendEmptyMessage(1);
            System.out.println("跳转完成================");
            //弹窗提示
            ToastUtil.showMsgInThread(LoginActivity.this,"登录成功",true);
        }else{
            //登录失败
            System.out.println("登录失败!");
            //清空密码输入框
            et_password.getText().clear();
            //弹窗提示,后面的代码不再执行
            ToastUtil.showMsgInThread(LoginActivity.this,"用户名和密码错误,登录失败",false);
        }
        }
    };

    //子线程中更新UI的handler
    Handler myHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    // 跳转到MainActivity界面,并且传递参数id过去
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("id",et_id.getText().toString());

                    intent.putExtras(bundle);
                    startActivity(intent);
                    LoginActivity.this.finish();//不可以回退到这个页面
                    System.out.println("跳转完成======================");
                    break;
            }
        }
    };
}
