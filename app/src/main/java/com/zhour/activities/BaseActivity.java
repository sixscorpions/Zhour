package com.zhour.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.zhour.R;
import com.zhour.fragments.HomeFragment;
import com.zhour.utils.Utility;

/**
 * Created by madhu on 05-Jul-17.
 */

public class BaseActivity extends AppCompatActivity {

    private int mClosePressCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    public void onBackPressed() {
        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 0) {
                FragmentManager.BackStackEntry backStackEntry = fragmentManager
                        .getBackStackEntryAt(fragmentManager
                                .getBackStackEntryCount() - 1);
                Utility.showLog("BackStackEntry Name", backStackEntry.getName());
                if (backStackEntry.getName().equalsIgnoreCase(HomeFragment.TAG)) {
                    mClosePressCount++;
                    if (mClosePressCount > 1) {
                        finishAffinity();
                    } else {
                        Utility.showToastMessage(getApplicationContext(), getResources().getString(R.string.press_again_to_exit));
                    }
                } else {
                    super.onBackPressed();
                }
            } else {
                super.onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            getFragmentManager().popBackStack();
        }
    }
}

