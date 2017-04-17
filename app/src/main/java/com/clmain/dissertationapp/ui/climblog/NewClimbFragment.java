package com.clmain.dissertationapp.ui.climblog;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.ClimbingLogbook;
import com.clmain.dissertationapp.db.DatabaseHelper;
import java.text.DateFormat;
import java.util.Date;

/**
 * Fragment. USed to input climb data and create a new entry, or update a previous one in a  database.
 */
public class NewClimbFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, DatePickerFragment.DatePickerFragmentListener {

    //ID of climb to edit, -1 if new
    int climbID;
    Spinner styleSpinner;
    Spinner gradeSpinner;
    ArrayAdapter adapter;
    android.support.v7.widget.Toolbar tool;

    /**
     * Required empty public constructor
     */
    public NewClimbFragment() {
        //Required empty public constructor
    }

    //Fragment Life Cycle

    /**
     * Called on Fragmment creation.
     * Determinies whether to Fragment should create new log or update old one
     * @param savedInstanceState - Bundle containing data to update (if any)
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            climbID=bundle.getInt("climbID");

        }
    }

    /**
     * Inflates options menu
     * @param inflater - Menu Inflater
     * @param container - Parent View
     * @param savedInstanceState - Inherited Data from previous state
     * @return inflated View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_new_climb, container, false);
    }

    /**
     *Called after UI initialized, and sets up UI elements.
     * Populates with data from database if available, and sets title of App Bar
     * @param view - view returned by onCreateView
     * @param savedInstanceState - Data from previous state
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupUI();
        tool = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.main_toolbar);
        if (climbID== -1) {
            tool.setTitle(R.string.new_climb);

        }else {
            tool.setTitle(R.string.update_climb);
        }
    }

    /**
     * Called to inflate options menu
     * Inflates New Climb menu, or Update if appropriate
     * @param menu - menu to inflate
     * @param inflater - menu inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Adds menu to action bar
        menu.clear();
        inflater.inflate(R.menu.new_climb_menu, menu);
        MenuItem menuSave = menu.findItem(R.id.item_climb_save);
        MenuItem menuEdit= menu.findItem(R.id.item_climb_edit);
        if(climbID==-1)  {
            menuEdit.setVisible(false);
        }else {
            menuSave.setVisible(false);
        }

    }


    //Listeners

    /**
     * Listener for Fragment content Click Events.
     * Date - Displays date picker dialog
     * @param view - View that was clicked
     */
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.button_date:
                new DatePickerFragment();
                DatePickerFragment fragment = DatePickerFragment.newInstance(this);
                fragment.show(getFragmentManager(), "datePicker");
                break;

            default:
                break;
        }
    }

    /**
     * Called when date is set in DatePickerDialog
     * sets text of Date button to selected date
     * @param date - date selected by user
     */
    @Override
    public void onDateSet(Date date) {
        Button b = (Button)getView().findViewById(R.id.button_date);
        b.setText(DateFormat.getDateInstance(DateFormat.SHORT).format(date));

    }

    /**
     * Listener for Options Items selection.
     *Save - Creates a new database entry from data in EditTexts
     * Update - Upadte current Database Row from data in EditTexts
     * @param item - Menu Item clicked
     * @return Successfully handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //unbound Views, bound to source, value taken, then reused
        DatabaseHelper dbHelp =new DatabaseHelper(getContext());
        ClimbingLogbook log = retreiveLogDataFromEditText();
        switch (item.getItemId()) {
            case R.id.item_climb_save:
                //Create New Entry
                dbHelp.createClimbLogEntry(log);

                getFragmentManager().popBackStack();
                Toast.makeText(getContext(), getResources().getString(R.string.toast_saved), Toast.LENGTH_SHORT).show();

                return true;
            case R.id.item_climb_edit:
                //Update Entry
                log.setID(climbID);
                dbHelp.updateClimbLogEntry(log);

                getFragmentManager().popBackStack();
                Toast.makeText(getContext(), getResources().getString(R.string.toast_updated), Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when Spinner item is selected.
     * Spinner Style item selected - Sets grade spinner content to correct grading type for style
     * @param parent - Adapter view where selection occured
     * @param view - View selected
     * @param pos - position of View in AdapterView
     * @param id - id of item selected
     */
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
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

    /**
     * Required. Handles non selection for Spinners.
     * @param parent - Adapter view where selection occured
     */
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Misc
    /**
     * Sets up UI elements
     * Bind Listeners and adapters to buttons and spinners where appropriate
     * Id recontructing from database data, sets content of EditTexts
     */
    private void setupUI() {
        //set Style Spinners
        styleSpinner = (Spinner)getView().findViewById(R.id.spinner_style);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_style, android.R.layout.simple_spinner_dropdown_item);
        styleSpinner.setAdapter(adapter);
        styleSpinner.setOnItemSelectedListener(this);

        //set grade Spinner
        gradeSpinner = (Spinner)getView().findViewById(R.id.spinner_grade);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.spinner_grade, android.R.layout.simple_spinner_dropdown_item);
        gradeSpinner.setAdapter(adapter);
        gradeSpinner.setOnItemSelectedListener(this);

        //set Date button Listener
        Button date = (Button)getView().findViewById(R.id.button_date);
        date.setOnClickListener(this);


        if(climbID!=-1) {
            //Load data from DB and display data
            ClimbingLogbook log = new DatabaseHelper(getContext()).readClimbLogEntry(climbID);
            EditText name = (EditText)getView().findViewById(R.id.edit_climb_name);
            EditText location = (EditText)getView().findViewById(R.id.edit_location);
            EditText grade = (EditText)getView().findViewById(R.id.edit_grade);
            EditText comments = (EditText)getView().findViewById(R.id.edit_comments);
            EditText height = (EditText)getView().findViewById(R.id.edit_height);


            //Set content of EditTexts from database
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

            if(!(log.getHeight().equals(getResources().getString(R.string.db_no_height)))) {
                height.setText(log.getHeight());
            }
            //Comments left blank in log, so no placeholder needs removing
            comments.setText(log.getComments());

            //Set spinner positions from database
            switch(log.getGrade()) {
                case "UK Bouldering":
                    gradeSpinner.setSelection(0);
                    break;
                case "French":
                    gradeSpinner.setSelection(1);
                    break;
                case "British Trad A/T":
                    gradeSpinner.setSelection(2);
                    break;
                case "Other":
                    gradeSpinner.setSelection(3);
                    break;
            }

            switch(log.getStyle()) {
                case "Boulder - Indoor":
                    styleSpinner.setSelection(0);
                    break;
                case "Boulder - Outdoor":
                    styleSpinner.setSelection(1);
                    break;
                case "Sport - Indoor":
                    styleSpinner.setSelection(2);
                    break;
                case "Sport - Outdoor":
                    styleSpinner.setSelection(3);
                    break;
                case "Trad":
                    styleSpinner.setSelection(4);
                    break;
                case "Other":
                    styleSpinner.setSelection(5);
                    break;
            }
        }
    }

    /**
     * Gets data inputted by user into EditTexts and Spinners.
     * @return ClimbingLogbook - Contains user inputted data
     */
    private ClimbingLogbook retreiveLogDataFromEditText() {
        EditText text;
        Spinner spinner;

        String date;
        String name;
        String location;
        String style;
        String grade;
        String height;
        String comments;
        ClimbingLogbook log;
        Button button = (Button)getView().findViewById((R.id.button_date));

        //Name
        text = (EditText) getView().findViewById(R.id.edit_climb_name);
        if(text.getText().toString().equals("")) {
            //If Blank, set Default
            name=getResources().getString(R.string.db_no_name);
        }else {
            name = text.getText().toString();
        }

        //date
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

        //Height
        text=(EditText)getView().findViewById(R.id.edit_height);
        if(text.getText().toString().equals("")) {
            height=getResources().getString(R.string.db_no_height);
        }else {
            height = text.getText().toString();
        }

        //Style
        spinner = (Spinner)getView().findViewById(R.id.spinner_style);
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
        log = new ClimbingLogbook();

        log.setName(name);
        log.setLocation(location);
        log.setDate(date);
        log.setStyle(style);
        log.setGrade(grade);
        log.setHeight(height);
        log.setComments(comments);

        return log;
    }
}