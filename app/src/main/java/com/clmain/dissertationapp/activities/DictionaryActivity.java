package com.clmain.dissertationapp.activities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.clmain.dissertationapp.MenuActivity;
import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.Dictionary;
import com.clmain.dissertationapp.db.DictionaryDatabaseHelper;
import com.clmain.dissertationapp.db.DictionaryTags;

import java.util.List;

public class DictionaryActivity extends AppCompatActivity {

    private String[] navMenuTitles;
    private DrawerLayout navDrawerLayout;
    private ListView navListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        Toolbar toolbar = (Toolbar)findViewById(R.id.dictionary_toolbar);
        setSupportActionBar(toolbar);

        navMenuTitles = getResources().getStringArray(R.array.navMenu);
        navDrawerLayout = (DrawerLayout)findViewById(R.id.activity_dictionary);
        navListView = (ListView)findViewById(R.id.left_drawer);
        //TODO: Fix left hand navigation drawer
        //navDrawerLayout.setAdapter(new ArrayAdapter<String>(this, R.layout.activity_dictionary, navMenuTitles));

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
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
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
            //TODO: Implement database display
        }
    }
}
