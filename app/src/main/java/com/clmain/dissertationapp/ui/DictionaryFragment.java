package com.clmain.dissertationapp.ui;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.Dictionary;
import com.clmain.dissertationapp.db.DatabaseHelper;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link DictionaryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DictionaryFragment extends Fragment {

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

    private void displayDictionary() {
        DatabaseHelper db = new DatabaseHelper(super.getContext());
        List<Dictionary> entries = db.readAllDictionaryEntries();

        FrameLayout layout = (FrameLayout)getView().findViewById(R.id.dictionary_content);
        for(int i=0; i<entries.size(); i++) {
            TextView title = new TextView(super.getContext());

            System.out.println("Title: " + entries.get(i).getTitle() + ". Array Length: " + entries.size());

            title.setText(entries.get(i).getTitle());
            title.setId(i);
            String tag = "title" + i;
            title.setTag(tag);

            if(i!=0) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams)title.getLayoutParams();
                //lp.addRule(RelativeLayout.BELOW,(TextView)findViewWithTag("description"+(i-1)));
            }
            layout.addView(title);

            TextView description = new TextView(super.getContext());
            description.setText(entries.get(i).getDescription());
            RelativeLayout.LayoutParams lpd = (RelativeLayout.LayoutParams)description.getLayoutParams();
            //lpd.addRule(RelativeLayout.BELOW, (TextView)getView().findViewWithTag("title"+i).getId());
            layout.addView(description);


            switch(entries.get(i).getTitle()) {
                case "Crimp":
                    ImageView img = new ImageView(super.getContext());
                    img.setImageResource(R.mipmap.image_crimp);
                    layout.addView(img);
                    break;
                case "Beta":
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        displayDictionary();

    }



}
