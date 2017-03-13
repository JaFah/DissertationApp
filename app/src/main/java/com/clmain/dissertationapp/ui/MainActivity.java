package com.clmain.dissertationapp.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
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

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.Dictionary;
import com.clmain.dissertationapp.db.DictionaryDatabaseHelper;
import com.clmain.dissertationapp.db.DictionaryTags;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] navMenuTitles;
    private DrawerLayout navDrawerLayout;
    private ListView navListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        navMenuTitles = getResources().getStringArray(R.array.navMenu);
        navDrawerLayout = (DrawerLayout)findViewById(R.id.activity_main);
        navListView = (ListView)findViewById(R.id.left_drawer);

        navListView.setAdapter(new ArrayAdapter<String>(this,R.layout.list_nav_drawer, navMenuTitles));
        navListView.setOnItemClickListener(new DrawerItemClickListener());

        debug_populateDb();
        displayDictionary();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case  R.id.dictionary_button :
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

        DictionaryDatabaseHelper ddb = new DictionaryDatabaseHelper(this);
        System.out.println("Probably going to crash here");
        ddb.createDictionaryEntry(dictionary, new DictionaryTags());

        dictionary.setTitle("Beta");
        dictionary.setTitle("Advice on how to complete a route");
        dictionary.setImageLocation("");
        ddb.createDictionaryEntry(dictionary, new DictionaryTags());
    }

    private void displayDictionary() {
        DictionaryDatabaseHelper ddb = new DictionaryDatabaseHelper(this);

        List<Dictionary> entries = ddb.readAllDictionaryEntries();
        for(int i=0;i<entries.size();i++) {

        }
    }


    /**
     * Created by user on 12/03/2017.
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position, view);
        }

        private void selectItem(int position, View view) {
            System.out.println("Button Pressed, pos="+position);

            Fragment frag;
            FragmentManager fragmentManager;

            switch(position) {
                case 0:
                    //Dictionary
                    frag = new DictionaryFragment();
                    //Bundle args = new Bundle();
                    //args.putInt(DictionaryFragment.ARGUMENT, POSITION);
                    //frag.setArguments(args);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();

                    navListView.setItemChecked(position, true);
                    setTitle(navMenuTitles[position]);
                    navDrawerLayout.closeDrawer(navListView);
                    break;
                case 1:
                    //Gear Log
                    frag = new GearLogFragment();
                    //Bundle args = new Bundle();
                    //args.putInt(DictionaryFragment.ARGUMENT, POSITION);
                    //frag.setArguments(args);
                    fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, frag).commit();

                    navListView.setItemChecked(position, true);
                    setTitle(navMenuTitles[position]);
                    navDrawerLayout.closeDrawer(navListView);

                    break;
                default:
                    //broken
                    break;
            }
        }
    }


}


