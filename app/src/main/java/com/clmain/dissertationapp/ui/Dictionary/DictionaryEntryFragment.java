package com.clmain.dissertationapp.ui.dictionary;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.clmain.dissertationapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DictionaryEntryFragment extends Fragment {
    String titleString;
    int pos;

    public DictionaryEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            titleString = bundle.getString("title");
            pos = bundle.getInt("pos");
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary_entry, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        TextView textView;

        try {
            textView = (TextView)getView().findViewById(R.id.dictionary_entry_title);
            textView.setText(titleString);
            setImg(titleString);
            textView = (TextView)getView().findViewById(R.id.dictionary_entry_definition);
            textView.setText(getResources().getStringArray(R.array.dictionary_definition_techniques)[pos]);

        }catch (Exception e) {
            textView = (TextView)getView().findViewById(R.id.dictionary_entry_title);
            textView.setText("Oops, something went wrong. See Logs");
            System.out.println(e);
        }


    }

    private void setImg(String title) {
        ImageView image = (ImageView)getView().findViewById(R.id.dictionary_entry_image);
        final String[] titles = getResources().getStringArray(R.array.dictionary_definition_techniques);

        switch (title) {
            case "Arete (Technique)":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Barndoor":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Beta":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Crimp":
                image.setImageResource(R.mipmap.image_crimp);
                break;
            case "Crux":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Fist Jam":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Flagging":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Heel Hook":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Rock Over":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Smearing":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
            case "Toe Hook":
                image.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }
}
