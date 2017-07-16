package com.zhour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity {

    public static final String TAG = SignInActivity.class.getSimpleName();

    @BindView(R.id.tv_user_login)
    TextView tv_user_login;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.tv_forgot_user_id)
    TextView tv_forgot_user_id;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTypeFace();
    }

    private void setTypeFace() {
        tv_user_login.setTypeface(Utility.setRobotoRegular(this));
        btn_login.setTypeface(Utility.setRobotoRegular(this));
        et_username.setTypeface(Utility.setLucidaSansItalic(this));
        et_password.setTypeface(Utility.setLucidaSansItalic(this));

        tv_forgot_user_id.setTypeface(Utility.setLucidaSansItalic(this));
        tv_forgot_password.setTypeface(Utility.setLucidaSansItalic(this));
    }


    @OnClick(R.id.btn_login)
    public void loginButtonFunction() {
        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
        startActivity(intent);
    }
}
