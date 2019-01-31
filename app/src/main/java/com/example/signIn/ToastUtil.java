package com.example.signIn;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

//显示Toast的小插件
public class ToastUtil {
    public static Toast toast;
    public static void showMsg(Context context,String msg, boolean shortTime){
        if(toast!=null) {
            toast.cancel();
        }
        toast = Toast.makeText(context,msg,shortTime==true?Toast.LENGTH_SHORT:Toast.LENGTH_LONG);
        toast.show();
    }

    //在子线程中弹窗
    public static void showMsgInThread(Context context,String msg, boolean shortTime){
        //解决在子线程中不能弹出提示的方法
        Looper.prepare();
        ToastUtil.showMsg(context,msg,shortTime);
        Looper.loop();
        //注意：写在Looper.loop()之后的代码不会被执行
    }
}
