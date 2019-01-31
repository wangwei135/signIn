package com.example.signIn.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.signIn.R;

public class SignInFragment extends Fragment {

    private String id;

    public static SignInFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id",id);
        SignInFragment fragment = new SignInFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        //获取参数,修改id
        Bundle bundle = getArguments();
        if (bundle!=null){
            this.id = bundle.getString("id");
        }

    }
}
