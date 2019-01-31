package com.example.signIn.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.signIn.LoginActivity;
import com.example.signIn.MainActivity;
import com.example.signIn.R;
import com.example.signIn.ToastUtil;
import com.example.signIn.user.Student;
import com.example.signIn.user.UserUtil;

public class MyFragment extends Fragment {

    private TextView tv_id,tv_name,tv_sex,tv_birthday,tv_department,tv_major,tv_joinClassId;
    private Button btn_logout;
    private String id;
    private Student stu;

    public static MyFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_id = view.findViewById(R.id.tv_id);
        tv_name = view.findViewById(R.id.tv_name);
        tv_sex = view.findViewById(R.id.tv_sex);
        tv_birthday = view.findViewById(R.id.tv_birthday);
        tv_department = view.findViewById(R.id.tv_department);
        tv_major = view.findViewById(R.id.tv_major);
        tv_joinClassId = view.findViewById(R.id.tv_joinClassId);
        btn_logout = view.findViewById(R.id.btn_logout);

        //传递的参数,修改id
        Bundle bundle = getArguments();
        if(bundle!=null){
            this.id = bundle.getString("id");
            tv_id.setText(this.id);
            //获取用户信息,开始设置每一个TextView
            new Thread(runnableGetInfo).start();
        }

        //注销按钮,添加点击事件
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.autoLogin = false;
                Intent intent = new Intent(getContext(), LoginActivity.class);
                ((Activity) getContext()).finish(); //放入回收站,不能回退过来
                startActivity(intent);
            }
        });
    }

    //获取用户信息,设置每一个TextView
    Runnable runnableGetInfo = new Runnable() {
        @Override
        public void run() {
            stu = UserUtil.findStudentById(id);
            if (stu!=null){
                System.out.println("获取用户信息成功");
                //修改UI
                myHanlder.sendEmptyMessage(1);
                //弹窗
                ToastUtil.showMsgInThread(getContext(),"获取用户信息成功",true);
            }else{
                System.out.println("获取用户信息失败******************");
            }
        }
    };

    //当获取用户信息成功时,修改UI
    Handler myHanlder = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //修改UI
                    tv_name.setText(stu.name);
                    tv_sex.setText(stu.sex);
                    tv_birthday.setText(stu.birthday.toString());
                    tv_department.setText(stu.department);
                    tv_major.setText(stu.major);
                    tv_joinClassId.setText(stu.joinClassId);
                    break;
            }
        }
    };


}
