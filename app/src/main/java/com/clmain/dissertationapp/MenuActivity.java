package com.clmain.dissertationapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.clmain.dissertationapp.fragments.MainMenuFragment;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.main_frame_layout, new MainMenuFragment());
        ft.commit();
    }


}
