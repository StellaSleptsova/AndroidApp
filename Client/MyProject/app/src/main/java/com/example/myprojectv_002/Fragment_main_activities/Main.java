package com.example.myprojectv_002.Fragment_main_activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.myprojectv_002.ClassesObject.UserInfo;
import com.example.myprojectv_002.MainActivity;
import com.example.myprojectv_002.R;

public class Main extends Fragment {

    private Button button_student;
    private Button button_teacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_main, container, false);

        button_student = (Button) v.findViewById(R.id.button_student);
        button_teacher = (Button) v.findViewById(R.id.button_teacher);
        button_student.setOnClickListener(onClickListenermain);
        button_teacher.setOnClickListener(onClickListenermain);

        return v;
    }

    View.OnClickListener onClickListenermain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button_student) {
               UserInfo.student_or_teacher = 0;
            } else {
                UserInfo.student_or_teacher = 1;
            }
            MainActivity.fragmentManager.beginTransaction().replace(R.id.activity_main, new Entry()).addToBackStack("main_stack").commit();
        }
    };
}
