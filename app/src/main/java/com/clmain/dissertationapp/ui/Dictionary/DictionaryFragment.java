package com.clmain.dissertationapp.ui.dictionary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.clmain.dissertationapp.R;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link DictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DictionaryFragment extends Fragment {
    int tab;
    TabHost tabs;
    android.support.v7.widget.Toolbar tool;

    TabHost tabHost;
    public DictionaryFragment() {
        // Required empty public constructor
    }

    public static DictionaryFragment newInstance() {
        DictionaryFragment fragment = new DictionaryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        tabHost=(TabHost)getView().findViewById(R.id.tab_host_dictionary);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.All);
        spec.setIndicator("All");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.Techniques);
        spec.setIndicator("Techniques");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab Three");
        spec.setContent(R.id.Equipment);
        spec.setIndicator("Equipment");
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Tab Four");
        spec.setContent(R.id.Jargon);
        spec.setIndicator("Jargon");

        TabWidget tabWidget = tabHost.getTabWidget();
        tabHost.addTab(spec);
    }

    private void displayDictionary() {
//        DatabaseHelper db = new DatabaseHelper(super.getContext());
//        List<Dictionary> entries = db.readAllDictionaryEntries();
//
//        FrameLayout layout = (FrameLayout)getView().findViewById(R.id.dictionary_content);
//        for(int i=0; i<entries.size(); i++) {
//            TextView title = new TextView(super.getContext());
//
//            System.out.println("Title: " + entries.get(i).getTitle() + ". Array Length: " + entries.size());
//
//            title.setText(entries.get(i).getTitle());
//            title.setId(i);
//            String tag = "title" + i;
//            title.setTag(tag);
//
//            if(i!=0) {
//                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)title.getLayoutParams();
//                //lp.addRule(RelativeLayout.BELOW,(TextView)findViewWithTag("description"+(i-1)));
//            }
//            layout.addView(title);
//
//            TextView description = new TextView(super.getContext());
//            description.setText(entries.get(i).getDescription());
//            RelativeLayout.LayoutParams lpd = (RelativeLayout.LayoutParams)description.getLayoutParams();
//            //lpd.addRule(RelativeLayout.BELOW, (TextView)getView().findViewWithTag("title"+i).getId());
//            layout.addView(description);
//
//
//            switch(entries.get(i).getTitle()) {
//                case "Crimp":
//                    ImageView img = new ImageView(super.getContext());
//                    img.setImageResource(R.mipmap.image_crimp);
//                    layout.addView(img);
//                    break;
//                case "Beta":
//                    break;
//                default:
//                    break;
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onStart() {
        displayDictionary();
        tool = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.main_toolbar);
        tool.setTitle(R.string.array_item_climbing_log);
        ListView list = (ListView)getView().findViewById(R.id.list_tab_techniques);
        //DictionaryAdapter adapter = new DictionaryAdapter(getContext(), R.layout.list_dictionary_entry, dictionary);

        list.setAdapter(new DictionaryAdapter(getContext(),R.layout.list_dictionary_entry, getResources().getStringArray(R.array.dictionary_title_techniques)));
        list.setClickable(true);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DictionaryEntryFragment fragment = new DictionaryEntryFragment();
                Bundle bundle = new Bundle();
                bundle.putString("title", getResources().getStringArray(R.array.dictionary_title_techniques)[position]);
                bundle.putInt("pos", position);
                fragment.setArguments(bundle);

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                ft.replace(R.id.main_content, fragment, "dictioanry entry");
                ft.addToBackStack(null).commit();
            }
        });
        super.onStart();

    }

    @Override
    public void onPause() {
        super.onPause();
        tabs = (TabHost)getView().findViewById(R.id.tab_host_dictionary);
        tab =tabs.getCurrentTab();

    }

    @Override
    public void onResume() {
        super.onResume();
        tabs = (TabHost)getView().findViewById(R.id.tab_host_dictionary);
        tabs.setCurrentTab(tab);
        tool.setTitle(R.string.array_item_dictionary);
    }

}

