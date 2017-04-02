package com.clmain.dissertationapp.ui.customelements;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.Checkable;

/**
 * Created by user on 02/04/2017.
 */

public class RelativeLayoutCheckable extends RelativeLayout implements Checkable {

    private boolean checked;

    public RelativeLayoutCheckable(Context context) {
        super(context);
    }

    public RelativeLayoutCheckable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RelativeLayoutCheckable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked=checked;
        refreshDrawableState();
    }

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void toggle() {
        checked = !checked;
        refreshDrawableState();

    }

//    public static final int[] CHECKED_STATE = {R.attr.state_checked};
//    @Override
//    protected int[] onCreateDrawableState(int extraSpace) {
//        int[] states =  super.onCreateDrawableState(extraSpace + 1);
//        if (checked){
//            mergeDrawableStates(states, CHECKED_STATE);
//        }
//        return states;
//    }
}
