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

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewClimbFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

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


    //Listeners
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_date:
                DialogFragment fragment = new DatePickerFragment();
                fragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.button_enter:
                EditText text;
                //String date;
                String name;
                String location;
                String style;
                String grade;
                String comments;

                //TODO Date
                text = (EditText) getView().findViewById(R.id.edit_climb_name);
                name = text.getText().toString();

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
                log.setStyle(style);
                log.setGrade(grade);
                log.setComments(comments);

                DatabaseHelper dbHelp = new DatabaseHelper(getContext());

                dbHelp.createClimbLogEntry(log);
                //TODO Back to browse
                break;
            default:
                break;
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        System.out.println("Pos=" + pos +". Spinner: " + parent.getId());
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @Override
        public  Dialog onCreateDialog(Bundle savedinstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            //Create a new instance of DatePickerDialog and return
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }



        public void onDateSet(DatePicker view, int year, int month, int day) {
            System.out.println("Date: " + day + "/" + month + "/" + year);
//            String date=day + "/" + month + "/" + year;
//            Button enter = (Button)getParentFragment().getView().findViewById(R.id.button_date);
//            enter.setText(date);
        }


    }
}





