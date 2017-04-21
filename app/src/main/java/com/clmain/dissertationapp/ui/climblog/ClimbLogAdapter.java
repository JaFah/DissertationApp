package com.clmain.dissertationapp.ui.climblog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.db.ClimbingLogbook;

import java.util.List;

/**
 * Adapter for ListView in CLimbLogFragment
 */

class ClimbLogAdapter extends ArrayAdapter<String> {
    private List<ClimbingLogbook> logs;
    private Context context;
    private final ViewHolder holder = new ViewHolder();

    ClimbLogAdapter(Context context, int resource, List<ClimbingLogbook> logs) {
        super(context, resource);

        this.context=context;
        this.logs=logs;
    }

    /**
     * Provides number of items in ListView.
     * As each value is always set in database, length of arrays will always match, so date was used.
     * @return number of date entries
     */
    @Override
    public int getCount() {
        return logs.size();
    }

    /**
     * Binds provided data to TextViews in layout, and returns created view
     * @param position - position in ListView
     * @param convertView - old view to reuse
     * @param parent - parent view to attach to
     * @return inflated view
     */
    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull final ViewGroup parent) {
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_climb_log_entries, null);
        }

        holder.date = (TextView)convertView.findViewById(R.id.list_entry_date);
        holder.name = (TextView)convertView.findViewById(R.id.list_entry_name);
        holder.location = (TextView)convertView.findViewById(R.id.list_entry_location);
        holder.style = (TextView)convertView.findViewById(R.id.list_entry_style);
        holder.grade = (TextView)convertView.findViewById(R.id.list_entry_grade);
        holder.comments = (TextView)convertView.findViewById(R.id.list_entry_comments);
        holder.height = (TextView)convertView.findViewById(R.id.list_entry_height);

        //Bind data to views
        holder.date.setText(logs.get(position).getDate());
        holder.name.setText(logs.get(position).getName());
        holder.location.setText(logs.get(position).getLocation());
        holder.style.setText(logs.get(position).getStyle());
        holder.grade.setText(logs.get(position).getGrade());
        holder.comments.setText(logs.get(position).getComments());
        holder.height.setText(logs.get(position).getHeight());

        return convertView;
    }

    /**
     * Called to update the values in the ListView
     */
    void swapItems(List<ClimbingLogbook> logs) {
        this.logs = logs;
        notifyDataSetChanged();
    }

    private class ViewHolder {
        TextView date;
        TextView name;
        TextView location;
        TextView style;
        TextView grade;
        TextView comments;
        TextView height;
    }


}
