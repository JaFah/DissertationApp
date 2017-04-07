package com.clmain.dissertationapp.ui.climblog;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.ActionMode;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link ClimbLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClimbLogFragment extends Fragment  {

    ListView listView;
    String[] dates;
    String[] names;
    String[] locations;
    String[] styles;
    String[] grades;
    String[] comments;


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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        getEntries();
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_sorter);
        spinner.setAdapter(ArrayAdapter.createFromResource(getContext(), R.array.spinner_sorter, android.R.layout.simple_spinner_dropdown_item));
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        listView = (ListView)getView().findViewById(R.id.layout_list_climb_log);
        getEntries();
        final ClimbLogAdapter adapter =new ClimbLogAdapter(getContext(), R.layout.list_climb_log_entries, dates, names, locations, styles, grades, comments);
        listView.setAdapter(adapter);
        if(dates.length==0) {
            //Empty Database
            databaseIsEmpty();
        }else {
            listView.setClickable(true);
            final Boolean[] selected = new Boolean[listView.getCount()];
            for (int i = 0; i < listView.getCount(); i++) {
                selected[i] = false;
            }

            listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    selected[position] = !selected[position];
                    System.out.println("Position: " + position + ", State: " + selected[position]);
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    android.support.v7.widget.Toolbar tool = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.main_toolbar);
                    tool.setVisibility(View.GONE);
                    for (int i = 0; i < listView.getCount(); i++) {
                        //Disable buttons
                    }
                    mode.setTitle(R.string.delete);
                    MenuInflater inflater = getActivity().getMenuInflater();
                    inflater.inflate(R.menu.delete_menu, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return true;
                }

                @Override
                public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
                    if (item.getItemId() == R.id.button_delete) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(R.string.title_are_you_sure);
                        builder.setMessage(R.string.message_delete_all);
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
                                if(dates.length==0) {
                                    databaseIsEmpty();
                                }
                                ((ClimbLogAdapter) listView.getAdapter()).swapItems(dates, names, locations, styles, grades, comments);
                                //listView.setAdapter(new ClimbLogAdapter(getContext(), R.layout.list_climb_log_entries, dates, names, locations, styles, grades, comments));
                                dialog.dismiss();
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
                public void onDestroyActionMode(ActionMode mode) {
                    android.support.v7.widget.Toolbar tool = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.main_toolbar);
                    tool.setVisibility(View.VISIBLE);
                    getEntries();
                    listView.setAdapter(new ClimbLogAdapter(getContext(), R.layout.list_climb_log_entries, dates, names, locations, styles, grades, comments));
                }
            });
        }

        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        getEntries();
        ((ClimbLogAdapter)listView.getAdapter()).swapItems(dates, names, locations, styles, grades, comments);
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
                Bundle bundle = new Bundle();
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
                        ((ClimbLogAdapter)listView.getAdapter()).swapItems(dates, names, locations, styles, grades, comments);
                        listView.setAdapter(new ClimbLogAdapter(getContext(), R.layout.list_climb_log_entries, dates, names, locations, styles, grades, comments));
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



    private void getEntries() {
        DatabaseHelper dbh = new DatabaseHelper(getContext());
        List<ClimbingLogbook> logs= dbh.readAllClimbLogEntries();
        names = new String[logs.size()];
        dates = new String[logs.size()];
        locations = new String[logs.size()];
        styles = new String[logs.size()];
        grades = new String[logs.size()];
        comments = new String[logs.size()];

        for(int i=0; i<logs.size(); i++) {
            dates[i]=logs.get(i).getDate();
            names[i]=logs.get(i).getName();
            locations[i]=logs.get(i).getLocation();
            styles[i]=logs.get(i).getStyle();
            grades[i]=logs.get(i).getGrade();
            comments[i]=logs.get(i).getComments();
        }
    }

    private void databaseIsEmpty() {
        TextView text = (TextView)getView().findViewById(R.id.text_sorter_title);
        Spinner spinner = (Spinner)getView().findViewById(R.id.spinner_sorter);
        text.setVisibility(View.GONE);
        spinner.setVisibility(View.GONE);

        TextView firstText = new TextView(getContext());
        firstText.setText("To Start, click new ");
        firstText.setTextSize(24);
        firstText.setTypeface(null, Typeface.BOLD);
        firstText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout layout = (LinearLayout)getView().findViewById(R.id.layout_cLimb_log_root);
        layout.addView(firstText);
    }

}
