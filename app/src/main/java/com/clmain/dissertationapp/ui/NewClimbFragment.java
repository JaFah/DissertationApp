package com.clmain.dissertationapp.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.ClimbingLogbook;
import com.clmain.dissertationapp.db.DatabaseHelper;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewClimbFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerFragment.DatePickerFragmentListener {

    //ID of climb to edit, -1 if new
    private int climbID;

    public NewClimbFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            climbID=bundle.getInt("climbID");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new_climb, container, false);
        return view;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        Spinner spinnerStyle = (Spinner)getView().findViewById(R.id.spinner_style);
        Spinner spinnerGrade = (Spinner)getView().findViewById(R.id.spinner_grade);
        Button enter = (Button)getView().findViewById(R.id.button_enter);
        Button date = (Button)getView().findViewById(R.id.button_date);

        ArrayAdapter<CharSequence> StyleAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_style, android.R.layout.simple_spinner_dropdown_item);
        spinnerStyle.setAdapter(StyleAdapter);
        spinnerStyle.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> GradeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_grade, android.R.layout.simple_spinner_dropdown_item);

        spinnerGrade.setAdapter(GradeAdapter);
        spinnerGrade.setOnItemSelectedListener(this);

        enter.setOnClickListener(this);
        date.setOnClickListener(this);

        if(climbID!=-1) {
            //Load data from DB and display data
            System.out.println("Loading from DB");
            ClimbingLogbook log = new DatabaseHelper(getContext()).readClimbLogEntry(climbID);
            EditText name = (EditText)getView().findViewById(R.id.edit_climb_name);
            EditText location = (EditText)getView().findViewById(R.id.edit_location);
            EditText grade = (EditText)getView().findViewById(R.id.edit_grade);
            EditText comments = (EditText)getView().findViewById(R.id.edit_comments);
            TextView title = (TextView)getView().findViewById(R.id.text_climb_title);

            enter.setText(R.string.button_update);
            title.setText(R.string.text_view_edit_climb_title);

            if(!(log.getDate().equals(getResources().getString(R.string.db_no_date)))) {
                date.setText(log.getDate());
            }

            if(!(log.getName().equals(getResources().getString(R.string.db_no_name)))) {
                name.setText(log.getName());
            }

            if(!(log.getLocation().equals(getResources().getString(R.string.db_no_location)))) {
                location.setText(log.getLocation());
            }

            if(!(log.getGrade().equals(getResources().getString(R.string.db_no_grade)))) {
                grade.setText(log.getGrade());
            }

            comments.setText(log.getComments());

            switch(log.getGrade()) {
                case "UK Bouldering":
                    spinnerGrade.setSelection(0);
                    break;
                case "French":
                    spinnerGrade.setSelection(1);
                    break;
                case "British Trad A/T":
                    spinnerGrade.setSelection(2);
                    break;
                case "Other":
                    spinnerGrade.setSelection(3);
                    break;
            }

            switch(log.getStyle()) {
                case "Boulder - Indoor":
                    spinnerStyle.setSelection(0);
                    break;
                case "Boulder - Outdoor":
                    spinnerStyle.setSelection(1);
                    break;
                case "Sport - Indoor":
                    spinnerStyle.setSelection(2);
                    break;
                case "Sport - Outdoor":
                    spinnerStyle.setSelection(3);
                    break;
                case "Trad":
                    spinnerStyle.setSelection(4);
                    break;
                case "Other":
                    spinnerStyle.setSelection(5);
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }
    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    //Listeners
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_date:
                DatePickerFragment fragment = new DatePickerFragment().newInstance(this);
                fragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.button_enter:
                //unbound edit text, bound to text source, and reused
                EditText text;
                String date;
                String name;
                String location;
                String style;
                String grade;
                String comments;

                //Name
                text = (EditText) getView().findViewById(R.id.edit_climb_name);
                if(text.getText().toString().equals("")) {
                    name=getResources().getString(R.string.db_no_name);
                }else {
                    name = text.getText().toString();
                }


                //date
                Button button = (Button)getView().findViewById((R.id.button_date));
                if(button.getText().toString().equals("Enter Date")) {
                    date=getResources().getString(R.string.db_no_date);
                }else {
                    date=button.getText().toString();
                }

                //Location
                text = (EditText)getView().findViewById(R.id.edit_location);
                if(text.getText().toString().equals("")) {
                    location=getResources().getString(R.string.db_no_location);
                }else {
                    location = text.getText().toString();
                }

                //Style
                Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_style);
                style = spinner.getSelectedItem().toString();

                //Grading
                text = (EditText)getView().findViewById(R.id.edit_grade);
                if(text.getText().toString().equals("")) {
                    grade=getResources().getString(R.string.db_no_grade);
                }else {
                    grade = text.getText().toString();
                }

                //Comments
                text = (EditText)getView().findViewById(R.id.edit_comments);


                if(text.getText().toString().equals("")) {
                    comments = "No Comments";
                }else {
                    comments = text.getText().toString();
                }
                ClimbingLogbook log = new ClimbingLogbook();

                log.setName(name);
                log.setLocation(location);
                log.setDate(date);
                log.setStyle(style);
                log.setGrade(grade);
                log.setComments(comments);

                DatabaseHelper dbHelp = new DatabaseHelper(getContext());

                if(climbID!=-1) {
                    log.setID(climbID);
//                    System.out.println("ID: " + log.getID());
//                    System.out.println("Name: " + log.getName());
//                    System.out.println("Date: " + log.getDate());
//                    System.out.println("Location: " + log.getLocation());
//                    System.out.println("Style: " + log.getStyle());
//                    System.out.println("Grade: " + log.getGrade());
//                    System.out.println("Comments: " + log.getComments());
                    dbHelp.updateClimbLogEntry(log);
                }else {
                    dbHelp.createClimbLogEntry(log);
                }

                getFragmentManager().popBackStack();
                if(climbID!=-1) {
                    Toast.makeText(getContext(), "Climb Updated!", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(getContext(), "Climb Added!", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(Date date) {
        Button b = (Button)getView().findViewById(R.id.button_date);
        b.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(date));

    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        //System.out.println("Pos=" + pos +". Spinner: " + parent.getId());
        if(parent.getId()==R.id.spinner_style) {
            Spinner gradeSpin = (Spinner)getView().findViewById(R.id.spinner_grade);
            switch(parent.getSelectedItem().toString()) {
                case "Boulder - Indoor":
                    gradeSpin.setSelection(0);
                    break;
                case "Boulder - Outdoors":
                    gradeSpin.setSelection(0);
                    break;
                case "Sport - Indoor":
                    gradeSpin.setSelection(1);
                    break;
                case "Sport - Outdoor":
                    gradeSpin.setSelection(1);
                    break;
                case "Trad":
                    gradeSpin.setSelection(2);
                    break;
                default:
                    break;
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}





