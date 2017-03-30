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
import android.nfc.Tag;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

    public static ClimbLogFragment newInstance() {
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
        List<RelativeLayout> logEntries = getListItems();
        //TODO add listView
        //ListView listView = (ListView)getView().findViewById();

        super.onStart();
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

            case R.id.button_edit:
                Toast.makeText(getContext(), "Edit is not yet implemented", Toast.LENGTH_SHORT).show();
                return true;
            default:
                //input unrecognised
                return super.onOptionsItemSelected(item);
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

    private List getListItems() {
        DatabaseHelper dbh = new DatabaseHelper(getContext());
        List<ClimbingLogbook> logs= dbh.readAllClimbLogEntries();
        List<RelativeLayout> entryLayouts = new ArrayList<RelativeLayout>();

        for(int i=0; i<logs.size(); i++) {
            //Layout to contain log entry information
            final RelativeLayout entryLayout = new RelativeLayout(getContext());

            entryLayout.setId(i);
            entryLayout.setBackgroundColor(Color.WHITE);

            TextView name = new TextView(getContext());
            TextView date = new TextView(getContext());
            TextView location = new TextView(getContext());
            TextView style = new TextView(getContext());
            TextView grade = new TextView(getContext());
            TextView comments = new TextView(getContext());
            Button editButton = new Button(getContext());

            //Identifiers
            name.setId(View.generateViewId());
            date.setId(View.generateViewId());
            location.setId(View.generateViewId());
            style.setId(View.generateViewId());
            grade.setId(View.generateViewId());
            comments.setId(View.generateViewId());

            //Style Text
            date.setTextSize(16);
            name.setTextSize(24);
            date.setTypeface(null, Typeface.ITALIC);
            name.setTypeface(null, Typeface.BOLD);
            location.setTextSize(18);
            location.setTypeface(null, Typeface.BOLD_ITALIC);
            style.setTextSize(16);
            grade.setTextSize(16);
            comments.setTextSize(16);
            editButton.setText(R.string.button_edit);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int id = entryLayout.getId();
                    }
                });

            System.out.println("Name  : " +logs.get(i).getName() );
            System.out.println("Date  : " +logs.get(i).getDate() );
            System.out.println("Style : " +logs.get(i).getStyle() );
            System.out.println("Grade :" +logs.get(i).getGrade() );
            System.out.println("Com   :" +logs.get(i).getComments() );

            //Set Text of Boxes
            if(logs.get(i).getDate().equals("")){
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

            //Create Layout Params
            RelativeLayout.LayoutParams dateParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams nameParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams locationParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams styleParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams gradeParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams commentsParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            //Position Elements
            dateParams.removeRule(RelativeLayout.ALIGN_PARENT_LEFT);
            dateParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            dateParams.addRule(RelativeLayout.RIGHT_OF, name.getId());
            nameParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            locationParams.addRule(RelativeLayout.BELOW, name.getId());
            styleParams.addRule(RelativeLayout.BELOW, location.getId());
            gradeParams.addRule(RelativeLayout.RIGHT_OF, style.getId());
            gradeParams.setMargins(60,0,0,0);
            gradeParams.addRule(RelativeLayout.BELOW, location.getId());
            commentsParams.addRule(RelativeLayout.BELOW, style.getId());

            entryLayout.addView(date, dateParams);
            entryLayout.addView(name, nameParams);
            entryLayout.addView(location, locationParams);
            entryLayout.addView(style, styleParams);
            entryLayout.addView(grade, gradeParams);
            entryLayout.addView(comments, commentsParams);
            LinearLayout baseLayout = (LinearLayout)getView().findViewById(R.id.layout_log);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(5,5,5,5);

           //baseLayout.addView(entryLayout, layoutParams);
            entryLayouts.add(entryLayout);
        }
    return entryLayouts;
    }
}
