package com.clmain.dissertationapp.ui.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.clmain.dissertationapp.R;

/**
 * Used to populate Dictionary ListViews with Views
 */

public class DictionaryAdapter extends ArrayAdapter<String> {

    String[] arrayTitle;
    LayoutInflater inflater;
    Context context;

    /**
     * Constructor. sets Local variables
     * @param context - App Context
     * @param resource - Resource Id to use for ListView items
     * @param title - Array of text to bind to TextViews
     */
    public DictionaryAdapter(Context context, int resource, String[] title) {
        super(context, resource);
        this.context=context;
        this.arrayTitle=title;
    }

    /**
     * get total number of items in ListView
     * @return length of ListView
     */
    @Override
    public int getCount() {
        return arrayTitle.length;
    }

    /**
     * Inflates the view to display
     * @param position - Row position
     * @param convertView - View to be reused
     * @param parent - Parent View
     * @return
     */
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
