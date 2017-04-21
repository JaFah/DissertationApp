package com.clmain.dissertationapp.ui.climblog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.clmain.dissertationapp.R;

/**
 * Adapter for ListView in CLimbLogFragment
 */

class ClimbLogAdapter extends ArrayAdapter<String> {
    private String[] date;
    private String[] name;
    private String[] location;
    private String[] style;
    private String[] grade;
    private String[] comments;
    private String[] height;
    private Context context;
    private final ViewHolder holder = new ViewHolder();

    ClimbLogAdapter(Context context, int resource, String[] date, String[] name, String[] location, String[] style, String[] grade, String[] comments, String[] heights) {
        super(context, resource);

        this.context=context;
        this.date=date;
        this.name=name;
        this.location=location;
        this.style=style;
        this.grade=grade;
        this.comments=comments;
        this.height=heights;
    }

    @Override
    public int getCount() {
        return date.length;
    }

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
        holder.date.setText(date[position]);
        holder.name.setText(name[position]);
        holder.location.setText(location[position]);
        holder.style.setText(style[position]);
        holder.grade.setText(grade[position]);
        holder.comments.setText(comments[position]);
        holder.height.setText(height[position]);
        return convertView;
    }

    void swapItems(String[] date, String[] name, String[] location, String[] style, String[] grade, String[] comments, String[] height) {
        for (String ignored : date) {
            this.date = date;
            this.name = name;
            this.location = location;
            this.style = style;
            this.grade = grade;
            this.comments = comments;
            this.height = height;
            notifyDataSetChanged();
        }
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
