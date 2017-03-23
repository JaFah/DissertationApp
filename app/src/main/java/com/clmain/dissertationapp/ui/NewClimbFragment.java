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

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.ClimbingLogbook;
import com.clmain.dissertationapp.db.DatabaseHelper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewClimbFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerFragment.DatePickerFragmentListener {

    public NewClimbFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_new_climb, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Spinner spinnerStyle = (Spinner)getView().findViewById(R.id.spinner_style);
        ArrayAdapter<CharSequence> StyleAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_style, android.R.layout.simple_spinner_dropdown_item);
        spinnerStyle.setAdapter(StyleAdapter);
        spinnerStyle.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> GradeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_grade, android.R.layout.simple_spinner_dropdown_item);
        Spinner spinnerGrade = (Spinner)getView().findViewById(R.id.spinner_grade);
        spinnerGrade.setAdapter(GradeAdapter);
        spinnerGrade.setOnItemSelectedListener(this);

        Button enter = (Button)getView().findViewById(R.id.button_enter);
        enter.setOnClickListener(this);
        Button date = (Button)getView().findViewById(R.id.button_date);
        date.setOnClickListener(this);
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
                EditText text;
                String date;
                String name;
                String location;
                String style;
                String grade;
                String comments;


                text = (EditText) getView().findViewById(R.id.edit_climb_name);
                name = text.getText().toString();

                Button button = (Button)getView().findViewById((R.id.button_date));
                if(button.getText().toString().equals("Enter Date")) {
                    date="";
                }else {
                    date=button.getText().toString();
                }
                System.out.println("Date PreDB: " + date);

                text = (EditText)getView().findViewById(R.id.edit_location);
                location = text.getText().toString();

                Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_style);
                style = spinner.getSelectedItem().toString();

                text = (EditText)getView().findViewById(R.id.edit_grade);
                grade = text.getText().toString();

                text = (EditText)getView().findViewById(R.id.edit_comments);
                comments = text.getText().toString();

                ClimbingLogbook log = new ClimbingLogbook();
                log.setName(name);
                log.setLocation(location);
                log.setDate(date);
                log.setStyle(style);
                log.setGrade(grade);
                log.setComments(comments);

                DatabaseHelper dbHelp = new DatabaseHelper(getContext());

                dbHelp.createClimbLogEntry(log);

                getFragmentManager().popBackStack();
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(Date date) {
        System.out.println("Date: " + DateFormat.getDateInstance(DateFormat.SHORT).format(date));
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





