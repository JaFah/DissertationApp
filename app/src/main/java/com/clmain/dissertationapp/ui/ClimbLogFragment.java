package com.clmain.dissertationapp.ui;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.ClimbingLogbook;
import com.clmain.dissertationapp.db.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClimbLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClimbLogFragment extends Fragment {

    public ClimbLogFragment() {
        // Required empty public constructor
    }

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
    public void onStart() {
        super.onStart();
        loadEntries();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Adds menu to action bar
        menu.clear();
        inflater.inflate(R.menu.climb_log_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handles option selection from action bar
        switch (item.getItemId()) {
            case R.id.new_log_entry_button:
                Fragment fragment = new NewClimbFragment();
                FragmentManager fm = getFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();
                //TODO Animate Transition
                ft.replace(R.id.main_content, fragment, "new log");
                ft.addToBackStack(null).commit();
                return true;
            case R.id.button_delete:
                showDeleteDialog();
                return true;

            default:
                //input unrecognised
                return super.onOptionsItemSelected(item);
        }


    }

    private void loadEntries() {
        LinearLayout baselayout = (LinearLayout)getView().findViewById(R.id.layout_log);
        baselayout.removeAllViews();

        try {
            DatabaseHelper dbh = new DatabaseHelper(getContext());
            List<ClimbingLogbook> logs= dbh.readAllClimbLogEntries();

            for(int i=0; i<logs.size(); i++) {
                LinearLayout layout = new LinearLayout(getContext());
                layout.setOrientation(LinearLayout.VERTICAL);

                layout.setBackgroundColor(Color.WHITE);

                TextView name = new TextView(getContext());
                TextView date = new TextView(getContext());
                TextView location = new TextView(getContext());
                TextView style = new TextView(getContext());
                TextView grade = new TextView(getContext());
                TextView comments = new TextView(getContext());

                //Layout Boxes
                date.setTextSize(16);
                name.setTextSize(24);
                date.setTypeface(null, Typeface.ITALIC);
                name.setTypeface(null, Typeface.BOLD);
                location.setTextSize(18);
                location.setTypeface(null, Typeface.BOLD_ITALIC);
                style.setTextSize(16);
                grade.setTextSize(16);
                comments.setTextSize(16);

                System.out.println("Name  : " +logs.get(i).getName() );
                System.out.println("Date  : " +logs.get(i).getDate() );
                System.out.println("Loc   : " +logs.get(i).getLocation() );
                System.out.println("Style : " +logs.get(i).getStyle() );
                System.out.println("Grade :" +logs.get(i).getGrade() );
                System.out.println("Com   :" +logs.get(i).getComments() );

                //Set Text of Boxes
                if(logs.get(i).getDate()==null){
                    date.setText("No Date");
                }else {
                    date.setText(logs.get(i).getDate().toString());
                }

                if(logs.get(i).getName().equals("")) {
                    name.setText("Untitled Climb");
                }else {
                    name.setText(logs.get(i).getName());
                }

                if(logs.get(i).getLocation().equals("")) {
                    location.setText("Location Unknown");
                }else {
                    location.setText(logs.get(i).getLocation());
                }

                if(logs.get(i).getStyle().equals("")) {
                    style.setText("Style not specfied");
                }else {
                    style.setText(logs.get(i).getStyle());
                }

                if(logs.get(i).getGrade().equals("")) {
                    grade.setText("Ungraded Climb");
                } else {
                    grade.setText(logs.get(i).getGrade());
                }

                if(logs.get(i).getComments().equals("")) {

                }else {
                    comments.setText(logs.get(i).getComments());
                }

                //Load Layout
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                layout.addView(date, params);
                layout.addView(name, params);
                layout.addView(location, params);
                layout.addView(style, params);
                layout.addView(grade, params);
                layout.addView(comments, params);

                LinearLayout baseLayout = (LinearLayout)getView().findViewById(R.id.layout_log);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(5,5,5,5);

                baseLayout.addView(layout, layoutParams);
            }
        }catch(Exception e) {
            System.out.println("Error (Database Empty?) : " + e);
        }
    }

    private void showDeleteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_are_you_sure);
        builder.setMessage(R.string.message_delete);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DatabaseHelper(getContext()).deleteAllClimbLogEntries();
                loadEntries();
                dialog.dismiss();
            }
        });
          builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  dialog.dismiss();
              }
          });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
