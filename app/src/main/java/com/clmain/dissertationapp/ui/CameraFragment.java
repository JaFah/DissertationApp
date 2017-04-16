package com.clmain.dissertationapp.ui;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.clmain.dissertationapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFragment extends Fragment {
    android.support.v7.widget.Toolbar tool;

    public CameraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tool = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.main_toolbar);
        tool.setTitle(R.string.array_item_climb_camera);

        super.onViewCreated(view, savedInstanceState);
    }
}
