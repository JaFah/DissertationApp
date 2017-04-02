package com.clmain.dissertationapp.ui.customelements;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.clmain.dissertationapp.R;
import com.clmain.dissertationapp.ui.NewClimbFragment;

/**
 * Created by user on 01/04/2017.
 */

public class ClimbLogAdapter extends ArrayAdapter<String> {

    String[] date;
    String[] name;
    String[] location;
    String[] style;
    String[] grade;
    String[] comments;
    Context context;
    LayoutInflater inflater;

    public ClimbLogAdapter(Context context, int resource, String[] date, String[] name, String[] location, String[] style, String[] grade, String[] comments) {
        super(context, resource);

        this.context=context;
        this.date=date;
        this.name=name;
        this.location=location;
        this.style=style;
        this.grade=grade;
        this.comments=comments;
    }

    @Override
    public int getCount() {
        return date.length;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView==null) {
            inflater= (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_climb_log_entries, null);
        }

        final ViewHolder holder = new ViewHolder();

        holder.date = (TextView)convertView.findViewById(R.id.list_entry_date);
        holder.name = (TextView)convertView.findViewById(R.id.list_entry_name);
        holder.location = (TextView)convertView.findViewById(R.id.list_entry_location);
        holder.style = (TextView)convertView.findViewById(R.id.list_entry_style);
        holder.grade = (TextView)convertView.findViewById(R.id.list_entry_grade);
        holder.comments = (TextView)convertView.findViewById(R.id.list_entry_comments);
        holder.editButton = (Button)convertView.findViewById(R.id.list_entry_button_edit);

        //Bind data to views
        holder.date.setText(date[position]);
        holder.name.setText(name[position]);
        holder.location.setText(location[position]);
        holder.style.setText(style[position]);
        holder.grade.setText(grade[position]);
        holder.comments.setText(comments[position]);
        //Placeholder identify selection by position - potential issue if searching is implemented
        //as posts will no longer be in order by ID

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Click! " + position, Toast.LENGTH_SHORT).show();
                Fragment fragment = new NewClimbFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("climbID", (position+1));
                fragment.setArguments(bundle);
                final Context context = parent.getContext();
                FragmentManager fm = ((Activity) context).getFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();
                //TODO Animate Transition
                ft.replace(R.id.main_content, fragment, "new log");
                ft.addToBackStack(null).commit();

            }
        });

        return convertView;
    }

    public class ViewHolder {
        TextView date;
        TextView name;
        TextView location;
        TextView style;
        TextView grade;
        TextView comments;
        Button editButton;
    }
}
