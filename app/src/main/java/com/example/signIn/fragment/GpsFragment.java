package com.example.signIn.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.signIn.R;
import com.example.signIn.ToastUtil;

public class GpsFragment extends Fragment {

    private String id;
    private Button btn_refresh;

    public static GpsFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("id",id);
        GpsFragment fragment = new GpsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gps,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //获取参数id
        Bundle bundle = getArguments();
        if (bundle!=null){
            this.id = bundle.getString("id");
            ToastUtil.showMsg(getContext(),this.id,false);
        }

        btn_refresh = view.findViewById(R.id.btn_refresh);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2019-1-27 0027  刷新坐标点击事件

            }
        });
    }
}
