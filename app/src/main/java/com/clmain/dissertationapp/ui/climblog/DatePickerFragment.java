package com.clmain.dissertationapp.ui.climblog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 23/03/2017.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private DatePickerFragmentListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedinstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        //Create a new instance of DatePickerDialog and return
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public interface DatePickerFragmentListener {
        public void onDateSet(Date date);
    }

    public DatePickerFragmentListener getDatePickerFragmentListener() {
        return this.listener;
    }

    public void setDatePickerListener(DatePickerFragmentListener listener) {
        this.listener = listener;
    }

    protected void notifyDatePickerListener(Date date) {
        if (this.listener != null) {
            this.listener.onDateSet(date);
        }
    }
    public static DatePickerFragment newInstance(DatePickerFragmentListener listener) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setDatePickerListener(listener);
        return fragment;
    }


    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date date = calendar.getTime();

        notifyDatePickerListener(date);
    }


}

