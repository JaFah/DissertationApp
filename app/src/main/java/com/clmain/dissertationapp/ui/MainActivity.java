package com.clmain.dissertationapp.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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
    private ListView navDrawerList;
    private ActionMenuItemView menuItem;
    boolean toggle;

    MenuInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //String to hold names for List Items
        navMenuTitles = getResources().getStringArray(R.array.navMenu);
        navDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer);

        navListView = (ListView)findViewById(R.id.left_drawer);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);


        navListView.setAdapter(new ArrayAdapter<String>(this,R.layout.list_nav_drawer, navMenuTitles));
        navListView.setOnItemClickListener(new DrawerItemClickListener());

        navDrawerToggle = new ActionBarDrawerToggle(this, navDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            CharSequence title;

            public void onDrawerOpened(View view) {
                toggle=false;
                title = getSupportActionBar().getTitle();
                super.onDrawerOpened(view);
                getSupportActionBar().setTitle(R.string.drawer_title);
                invalidateOptionsMenu();

            }
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                System.out.println("Drawer closed. Toggle=" + toggle);
                if(!toggle) {
                    getSupportActionBar().setTitle(title);
                }
                invalidateOptionsMenu();
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Toast.makeText(getBaseContext(), "Home", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        super.onOptionsItemSelected(item);
                        return true;
                }

            }

        };

        navDrawerToggle.setDrawerIndicatorEnabled(true);
        navDrawerLayout.addDrawerListener(navDrawerToggle);
        navDrawerToggle.syncState();


        //Set default window
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.main_content, new ClimbLogFragment()).commit();

        navListView.setItemChecked(0, true);
        setTitle(R.string.array_item_climbing_log);

        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navDrawerToggle.onConfigurationChanged(newConfig);
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

        if(navDrawerToggle.onOptionsItemSelected(item) && item.getItemId()==R.id.home) {
            return true;
        }
        switch(item.getItemId()) {
            case  R.id.button_about :
                //TODO Implement 'About'
                Toast.makeText(this, "'About' is not implemented yet", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        navDrawerList=(ListView)findViewById(R.id.left_drawer);
        boolean drawerOpen = navDrawerLayout.isDrawerOpen(navDrawerList);

        if(drawerOpen) {
            return false;
        } else {
            return true;
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
            toggle=true;
            System.out.println("Item Clicked. Toggle=" + toggle);
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
                    frag = new GuideFragment();
                    break;
                case 4:
                    //Camera
                    frag = new CameraFragment();
                    break;
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


