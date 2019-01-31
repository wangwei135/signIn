package com.example.signIn;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.signIn.fragment.GpsFragment;
import com.example.signIn.fragment.MyFragment;
import com.example.signIn.fragment.SignInFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //存放3个page页面
    public static ViewPager mViewPager;
    //底部导航栏
    public BottomNavigationView mBnv;
    //全局变量
    public static String id;

    MenuItem menuItem;
    List<Fragment> list_fragment = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取传递过来的参数
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        mViewPager = findViewById(R.id.viewPager);
        //底部导航栏添加选中事件
        mBnv = findViewById(R.id.bnv);
        mBnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //选中 我的 item_my
                if(item.getItemId()==R.id.item_my){
                    ToastUtil.showMsg(MainActivity.this,"我的",true);
                    mViewPager.setCurrentItem(0);
                }
                //选中 定位 item_gps
                else if(item.getItemId()==R.id.item_gps){
                    ToastUtil.showMsg(MainActivity.this,"定位",true);
                    mViewPager.setCurrentItem(1);
                }
                //选中 签到 item_signIn
                else if (item.getItemId()==R.id.item_signIn){
                    ToastUtil.showMsg(MainActivity.this,"签到",true);
                    mViewPager.setCurrentItem(2);
                }
                return true;
            }
        });

        //viewPager设置滚动动画效果
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                // TODO: 2019-1-27 0027  设置动画效果
                //设置滚动动画效果
            }

            @Override
            public void onPageSelected(int i) {
                if(menuItem!= null){
                    menuItem.setChecked(false);
                }
                else{
                    mBnv.getMenu().getItem(i);
                }
                menuItem = mBnv.getMenu().getItem(i);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //这里设置添加几个页面fragment
        list_fragment.add(MyFragment.newInstance(id));
        list_fragment.add(GpsFragment.newInstance(id));
        list_fragment.add(SignInFragment.newInstance(id));
        BottomViewAdapter adapter = new BottomViewAdapter(getSupportFragmentManager(), list_fragment);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3); //设置缓存的个数

    }
}
