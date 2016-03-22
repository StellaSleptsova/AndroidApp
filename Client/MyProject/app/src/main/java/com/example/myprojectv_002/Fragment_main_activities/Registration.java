package com.example.myprojectv_002.Fragment_main_activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myprojectv_002.R;

public class Registration extends Fragment {

    private Toolbar toolbar;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.act_registration, container, false);
        initToolbar();
        return v;
    }
    private void initToolbar() {
        toolbar=(Toolbar)v.findViewById(R.id.toolbar_reg);
        toolbar.setTitle("Регистрация");
    }
}
