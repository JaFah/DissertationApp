package com.clmain.dissertationapp.ui.dictionary;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import com.clmain.dissertationapp.R;

/**
 * A {@link Fragment} subclass, that displays the Climbing Dictionary.
 */
public class DictionaryFragment extends Fragment {
    int tab;
    TabHost tabs;
    android.support.v7.widget.Toolbar tool;

    TabHost tabHost;

    /**
     * Required empty public constructor
     */
    public DictionaryFragment() {
    }



    /**
     * Inflates layout for fragment
     * @param inflater - Layout Inflater used to inflate Layout
     * @param container - Parent view that fragment is atatched to.
     * @param savedInstanceState - Reconstrution Data if recreating fragment from previous state
     * @return Fragment's Layout View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    /**
     * Called when View is created. Sets up TabHost
     * @param view view returned by onCreateView
     * @param savedInstanceState bundle containing information for fragment recreation
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        setupTabHost();
    }

    /**
     * Caleed when Visible to User. Sets title, and populates dictionary ListViews
     */
    @Override
    public void onStart() {
        tabs =  (TabHost)getView().findViewById(R.id.tab_host_dictionary);
        tool = (android.support.v7.widget.Toolbar) getActivity().findViewById(R.id.main_toolbar);
        tool.setTitle(R.string.array_item_dictionary);

        populateDictionaryListViews();
        super.onStart();

    }

    /**
     * Called when fragment is paused. Saves currently selected tab, so when fragment is recreated
     * it can be set to be the same as when paused
     */
    @Override
    public void onPause() {
        super.onPause();
        tab =tabs.getCurrentTab();

    }

    /**
     * Called when activity is resumed. Sets tab and title to previous state if application was paused.
     */
    @Override
    public void onResume() {
        super.onResume();
        tabs.setCurrentTab(tab);
        tool.setTitle(R.string.array_item_dictionary);
    }

    /**
     * Sets up tab host, and populates them with Views
     */
    private void setupTabHost() {
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

        tabHost.addTab(spec);
    }

    /**
     * Populates dictionary listviews with String arrays, and sets click listeners.
     */
    private void populateDictionaryListViews() {
        ListView list = (ListView)getView().findViewById(R.id.list_tab_techniques);

        list.setAdapter(new DictionaryAdapter(getContext(),R.layout.list_dictionary_entry, getResources().getStringArray(R.array.dictionary_title_techniques)));
        list.setClickable(true);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
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

        };
        list.setOnItemClickListener(listener);
    }

}

