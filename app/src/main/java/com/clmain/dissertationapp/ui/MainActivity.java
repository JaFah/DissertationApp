package com.clmain.dissertationapp.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.ui.dictionary.DictionaryFragment;
import com.clmain.dissertationapp.ui.climblog.ClimbLogFragment;

/**
 * The base activity for the application.
 * Content is swapped in and out of here through the use of fragments.
 */
public class MainActivity extends AppCompatActivity {

    private String[] navMenuTitles;
    private DrawerLayout navDrawerLayout;
    private ListView navListView;
    private ActionBarDrawerToggle navDrawerToggle;
    ListView navDrawerList;
    boolean toggle;

    MenuInflater inflater;


    //Activity Flow Methods

    /**
     * Called when Activity is created. Override sets content XML,
     * creates an App Bar, and the Default Fragment is loaded
     *
     * @param savedInstanceState - Bundle Containing Dynamic date for activity recreation, saved from onPause
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        setupAppBar();
        displayDefaultContent();

    }

    /**
     * Called after Activity creation, synchronises state of nav drawer with drawerLayout
     *
     * @param savedInstanceState - - Bundle Containing Dynamic date for activity recreation, saved from onPause
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        navDrawerToggle.syncState();
    }

    //UI Creation

    /**
     * sets the default fragment(Climbing Log) that is loaded when the program first starts.
     */
    private void displayDefaultContent() {
        //Set default window
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.main_content, new ClimbLogFragment()).commit();

        navListView.setItemChecked(0, true);
        setTitle(R.string.array_item_climbing_log);
    }

    //App Bar

    /**
     * Creates an App Bar, and sets up the navigation panel
     */
    private void setupAppBar() {
        final Toolbar toolbar = (Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        //String to hold names for List Items
        navMenuTitles = getResources().getStringArray(R.array.navMenu);
        navDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer);

        navListView = (ListView)findViewById(R.id.left_drawer);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }catch(NullPointerException e) {
            //Action Bar not enabled
            System.out.println("Action Bar not set: " + e);
        }


        navListView.setAdapter(new ArrayAdapter<>(this, R.layout.list_nav_drawer, navMenuTitles));
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

        //set Status Bar Colour
        Window window = getWindow();
        window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
    }

    //OptionsMenu

    /**
     * Called when options menu is created. Inflates the main menu.
     *
     * @param menu - Menu to inflate
     * @return true - the menu was inflated successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Prepares the navigation panel menu.
     *
     * @param menu - Drawer Menu
     * @return Drawer state (Open/Closed)
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        navDrawerList=(ListView)findViewById(R.id.left_drawer);
        boolean drawerOpen = navDrawerLayout.isDrawerOpen(navDrawerList);

        return !drawerOpen;
    }

    //Navigation Drawer

    /**
     * Used to notify the DrawerToggle of a change in state
     * @param newConfig the new Configuration of the Drawer Toggle
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navDrawerToggle.onConfigurationChanged(newConfig);
    }


    //Listeners
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            toggle=true;
            selectItem(position);
        }

        /**
         * called when selection is made from navigation drawer, and handles the response.
         * Replaces current Fragment with selection.
         * @param position - position of selection in list
         */
        private void selectItem(int position) {

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

    /**
     * Called when an options menu item is selected.
     */
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


}


