package com.clmain.dissertationapp.ui.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.clmain.dissertationapp.R;

/**
 * Created by user on 06/04/2017.
 */

public class DictionaryAdapter extends ArrayAdapter<String> {

    String[] arrayTitle;
    LayoutInflater inflater;
    Context context;

    public DictionaryAdapter(Context context, int resources, String[] title) {
        super(context, resources);
        this.context=context;
        this.arrayTitle=title;
    }
    @Override
    public int getCount() {
        return arrayTitle.length;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_dictionary_entry, null);
        }

        TextView title = (TextView) convertView.findViewById(R.id.list_dictionary_text_title);
        title.setText(arrayTitle[position]);
        return convertView;
    }
}
