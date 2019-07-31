package com.quan.fems.trim.base;

import android.view.View;
import android.view.View.OnClickListener;

import java.util.Calendar;

public abstract class OnSingleClickListener implements OnClickListener {
    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    protected abstract void onSingleClick(View v);
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onSingleClick(v);
        }
    }
}
