package com.clmain.dissertationapp.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.Dictionary;
import com.clmain.dissertationapp.db.DatabaseHelper;
import com.clmain.dissertationapp.db.DictionaryTags;
import com.clmain.dissertationapp.ui.dictionary.DictionaryFragment;
import com.clmain.dissertationapp.ui.climblog.ClimbLogFragment;

public class MainActivity extends AppCompatActivity {

    private String[] navMenuTitles;
    private DrawerLayout navDrawerLayout;
    private ListView navListView;
    private ActionBarDrawerToggle navDrawerToggle;

    MenuInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //String to hold names for List Items
        navMenuTitles = getResources().getStringArray(R.array.navMenu);
        navDrawerLayout = (DrawerLayout)findViewById(R.id.activity_main);

        navListView = (ListView)findViewById(R.id.left_drawer);

        navListView.setAdapter(new ArrayAdapter<String>(this,R.layout.list_nav_drawer, navMenuTitles));
        navListView.setOnItemClickListener(new DrawerItemClickListener());

        //debug_populateDb();

        //Set default window
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.main_content, new ClimbLogFragment()).commit();

        navListView.setItemChecked(0, true);
        setTitle(R.string.arrayItem_climbing_log);


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case  R.id.about_button :
                //TODO Implement 'About'
                Toast.makeText(this, "'About' is not implemented yet", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void debug_populateDb() {
        Dictionary dictionary = new Dictionary();

        dictionary.setTitle("Crimp");
        dictionary.setDescription("Very narrow hand hold where only the tips of the fingers have purchase");
        dictionary.setImageLocation("@mipmap/image_crimp.png");

        DatabaseHelper ddb = new DatabaseHelper(this);
        ddb.createDictionaryEntry(dictionary, new DictionaryTags());

        dictionary.setTitle("Beta");
        dictionary.setTitle("Advice on how to complete a route");
        dictionary.setImageLocation("");
        ddb.createDictionaryEntry(dictionary, new DictionaryTags());



    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, view);
        }

        private void selectItem(int position, View view) {

            Fragment frag;
            FragmentManager fragmentManager;

            switch(position) {
                case 0:
                    //Climbing Log
                    frag = new ClimbLogFragment();
                    break;
                case 1:
                    //Dictionary
                    frag = new DictionaryFragment();
                    break;
                case 2:
                    //Gear Log
                    frag = new GearLogFragment();
                    break;
                case 3:
                    //Guide
                default:
                    //broken
                    frag = new Fragment();
                    break;
            }
            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.main_content, frag).commit();

            navListView.setItemChecked(position, true);
            setTitle(navMenuTitles[position]);
            navDrawerLayout.closeDrawer(navListView);
        }
    }


}


