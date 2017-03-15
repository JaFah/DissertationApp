package com.clmain.dissertationapp.ui;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.clmain.dissertationapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClimbLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClimbLogFragment extends Fragment {

    public ClimbLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ClimbLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ClimbLogFragment newInstance(String param1, String param2) {
        ClimbLogFragment fragment = new ClimbLogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.climb_log_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_log_entry_button:
                Fragment fragment = new NewClimbFragment();
                FragmentManager fm = getFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_content, fragment, "new log");
                ft.addToBackStack(null).commit();
                return true;

            default:
                //input unrecognised
                return super.onOptionsItemSelected(item);
        }


    }

}
