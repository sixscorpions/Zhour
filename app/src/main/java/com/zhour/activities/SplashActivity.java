package com.zhour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.zhour.R;
import com.zhour.utils.ToolbarUtils;

public class SplashActivity extends BaseActivity {
    public static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_splash);
        ToolbarUtils.setTranslateStatusBar(this);

        Handler mSplashHandler = new Handler();
        Runnable action = new Runnable() {
            @Override
            public void run() {
                navigateToSignIn();
                /*if (Utility.isValueNullOrEmpty(Utility.getSharedPrefStringData(SplashActivity.this, Constants.APP_PREF))) {
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    navigateToSignIn();
                }*/

            }
        };
        mSplashHandler.postDelayed(action, SPLASH_TIME_OUT);
    }

    public void navigateToSignIn() {
        Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();


    }


}




