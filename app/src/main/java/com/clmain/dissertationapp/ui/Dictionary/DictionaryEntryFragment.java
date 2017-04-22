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
 * Fragent to display an entry from the dictionary
 */
public class DictionaryEntryFragment extends Fragment {
    String titleString;
    int pos;

    /**
     * Required empty Constructor
     */
    public DictionaryEntryFragment() {
        // Required empty public constructor
    }

    /**
     * Called on Fragment Creation.
     * retreives title from Bundle
     * retreives position of element selected to create fragment from Bundle
     * @param savedInstanceState Data to recreate fragment
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            titleString = bundle.getString("title");
            pos = bundle.getInt("pos");
        }

    }

    /**
     * Inflates Layout for Fragment
     * @param inflater - Layout Inflater
     * @param container - Parent View to attach fragment to
     * @param savedInstanceState - Data to reconstruct fragment from
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dictionary_entry, container, false);
    }

    /**
     * Called when fragment is visible to user.
     * Sets Content of View
     */
    @Override
    public void onStart() {
        super.onStart();
        TextView textView;

        textView = (TextView)getView().findViewById(R.id.dictionary_entry_title);
        textView.setText(titleString);
        setImg(titleString);
        textView = (TextView)getView().findViewById(R.id.dictionary_entry_definition);
        textView.setText(getResources().getStringArray(R.array.dictionary_definition_techniques)[pos]);
    }

    /**
     * Sets image value
     * @param title - Title, used to identify photo
     */
    private void setImg(String title) {
        ImageView image = (ImageView)getView().findViewById(R.id.dictionary_entry_image);
        final String[] titles = getResources().getStringArray(R.array.dictionary_definition_techniques);

        switch (title) {
            case "Crimp":
                image.setImageResource(R.mipmap.image_crimp);
                break;
            default:
                image.setImageResource(R.mipmap.ic_launcher);
                break;
        }
    }
}
