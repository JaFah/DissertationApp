package com.clmain.dissertationapp.ui.climblog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.support.v7.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.ClimbingLogbook;
import com.clmain.dissertationapp.db.DatabaseHelper;
import com.clmain.dissertationapp.ui.dictionary.DictionaryAdapter;

import java.util.List;

/**
 *
 */
public class ClimbLogFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ListView listView;
    List<ClimbingLogbook> logs;
    android.support.v7.widget.Toolbar tool;

    /**
     * Required Empty Constructor
     */
    public ClimbLogFragment() {
        // Required empty public constructor
    }

    /**
     * Inflates Fragment View and retreives climb logs from Database.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return inflated View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        getEntries();
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    /**
     * Called after View Created. Sets up UI elements and displays ListView.
     * @param view - View that has been created
     * @param savedInstanceState - data to load from previous state
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupUI();
        //Setup ListView
        displayListView();
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Called when fragment returns from pause. Updates ListView Entries and resets title
     */
    @Override
    public void onResume() {
        super.onResume();
        getEntries();
        ((ClimbLogAdapter)listView.getAdapter()).swapItems(logs);
        tool.setTitle(R.string.array_item_climbing_log);
    }

    /**
     * Clears previous option menu and seets new one
     * @param menu - menu to inflate
     * @param inflater - Menu Inflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //Adds menu to action bar
        menu.clear();
        inflater.inflate(R.menu.climb_log_menu, menu);
    }

    /**
     * called to prepare options menu. hides the menu if navigation pane is open
     * @param menu - Menu to be prepared
     */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        DrawerLayout drawerLayout = (DrawerLayout)getActivity().findViewById(R.id.main_drawer);
        ListView drawerList = (ListView)getActivity().findViewById(R.id.left_drawer);


        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);

        if(menu.findItem(R.id.button_delete_all)!=null) {
            //If one is null, other will be
            menu.findItem(R.id.button_delete_all).setVisible(!drawerOpen);
            menu.findItem(R.id.button_new_log_entry).setVisible(!drawerOpen);

        }


    }

    /**
     * Caleed whn options menu is selected.
     * @param item - item that was selected
     * @return boolean - action handled?
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handles option selection from action bar
        switch (item.getItemId()) {
            case R.id.button_new_log_entry:
                //New Climb
                Fragment fragment = new NewClimbFragment();
                Bundle bundle = new Bundle();
                //-1 denotes a new climb, rather than update a previously existing one
                bundle.putInt("climbID", -1);
                fragment.setArguments(bundle);
                FragmentManager fm = getFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();
                //TODO Animate Transition
                ft.replace(R.id.main_content, fragment, "new log");
                ft.addToBackStack(null).commit();
                return true;
            case R.id.button_delete_all:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.title_are_you_sure);
                builder.setMessage(R.string.message_delete_all);
                builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DatabaseHelper(getContext()).deleteAllClimbLogEntries();
                        getEntries();
                        displayListView();
                        ((ClimbLogAdapter)listView.getAdapter()).swapItems(logs);
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
                return true;
            default:
                //input unrecognised
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * retrieve dictionary entries from database
     */
    private void getEntries() {
        DatabaseHelper dbh = new DatabaseHelper(getContext());
        logs= dbh.readAllClimbLogEntries();

    }

    /**
     * Sets up UI elements
     */
    private void setupUI() {
        //Setup Spinners
        Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_sorter);
        spinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.spinner_sorter, R.layout.spinner_sorter_text));
        spinner.setOnItemSelectedListener(this);

        //Setup Toolbar
        tool = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.main_toolbar);
        tool.setTitle(R.string.array_item_climbing_log);
        listView = (ListView)getView().findViewById(R.id.layout_list_climb_log);
    }

    /**
     * Checks whether to display ListView.
     * If empty, hides listview/sorter and replaces with a hint to user
     * If not empty, listView displayed
     */
    private void displayListView() {
        final ClimbLogAdapter adapter =new ClimbLogAdapter(getContext(), R.layout.list_climb_log_entries, logs);
        listView.setAdapter(adapter);

        TextView text = (TextView)getView().findViewById(R.id.text_sorter_title);
        Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_sorter);
        final ListView listView = (ListView)getView().findViewById(R.id.layout_list_climb_log);
        LinearLayout layout = (LinearLayout)getView().findViewById(R.id.layout_sorter);

        if(logs.size()==0) {
            //Display Hint
            layout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.white));
            spinner.setVisibility(View.GONE);
            text.setText(R.string.log_tutorial);
            listView.setVisibility(View.GONE);
        }else {
            //Display ListView
            layout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
            spinner.setVisibility(View.VISIBLE);
            listView.setVisibility(View.VISIBLE);
            text.setText(R.string.text_view_sorter_title);

            setupListView(adapter);
        }
    }

    /**
     * Populates ListView with data from database.
     * Sets Listeners for ListView
     * @param adapter - Adapter for listView
     */
    private void setupListView(final ClimbLogAdapter adapter) {
        listView.setClickable(true);

        //Used to determine whether each item is selected
        final Boolean[] selected = new Boolean[listView.getCount()];
        for (int i = 0; i < listView.getCount(); i++) {
            selected[i] = false;
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Load ClimbFragment with data from selection
                Fragment fragment = new NewClimbFragment();
                Bundle bundle = new Bundle();

                bundle.putInt("climbID", (position+1));
                fragment.setArguments(bundle);

                final Context context = parent.getContext();
                FragmentManager fm = ((Activity) context).getFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();
                //TODO Animate Transition
                ft.replace(R.id.main_content, fragment, "new log");
                ft.addToBackStack(null).commit();

            }
        });

        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {

                listView.setAdapter(new ClimbLogAdapter(getContext(), R.layout.list_climb_log_entries,logs));

                mode.setTitle(R.string.delete);

                MenuInflater inflater = getActivity().getMenuInflater();
                inflater.inflate(R.menu.delete_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return true;
            }

            @Override
            public boolean onActionItemClicked(final android.view.ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.button_delete) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.title_are_you_sure);
                    builder.setMessage(R.string.message_delete_selection);
                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i = 0; i < selected.length; i++) {
                                System.out.println("selected: " + selected[i]);
                                if (selected[i]) {
                                    System.out.println("Deleting: " + selected[i] + i);
                                    new DatabaseHelper(getContext()).deleteCimbLogEntry(i);
                                }
                            }
                            getEntries();
                            ((ClimbLogAdapter) listView.getAdapter()).swapItems(logs);
                            dialog.dismiss();
                            displayListView();
                            mode.finish();

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
                return true;
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
                //tool.setVisibility(View.VISIBLE);
                getEntries();
                ((ClimbLogAdapter) listView.getAdapter()).swapItems(logs);
            }

            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                selected[position] = !selected[position];
                System.out.println("Position: " + position + ", State: " + selected[position]);
            }
        });
    }

    /**
     * Called when selection is made by spinner
     * Not implemented - Displays 'Not implemented' Toast
     * @param parent - AdapterView where selection was made
     * @param view - View within adapter that made selection
     * @param position - position of view within adapter
     * @param id - row id of selection
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getContext(), "Sorting not Implemented", Toast.LENGTH_SHORT ).show();
    }

    /**
     * Required non selection Method
     * @param parent AdapterView with no selection
     */
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
