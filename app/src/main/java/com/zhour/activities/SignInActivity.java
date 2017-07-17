package com.zhour.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctask.ServerJSONAsyncTask;
import com.zhour.models.AuthenticateUserModel;
import com.zhour.models.Model;
import com.zhour.parser.AuthenticateUserParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import org.json.JSONArray;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseActivity implements IAsyncCaller {

    public static final String TAG = SignInActivity.class.getSimpleName();

    @BindView(R.id.tv_user_login)
    TextView tv_user_login;

    @BindView(R.id.btn_login)
    Button btn_login;

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setTypeFace();
    }

    private void setTypeFace() {
        context = SignInActivity.this;
        tv_user_login.setTypeface(Utility.setRobotoRegular(this));
        btn_login.setTypeface(Utility.setRobotoRegular(this));
        et_username.setTypeface(Utility.setLucidaSansItalic(this));
        et_password.setTypeface(Utility.setLucidaSansItalic(this));
        tv_forgot_password.setTypeface(Utility.setLucidaSansItalic(this));
        tv_forgot_password.setPaintFlags(tv_forgot_password.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }


    @OnClick(R.id.btn_login)
    public void loginButtonFunction() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            JSONArray jsonArray = new JSONArray();
            linkedHashMap.put("contactnumber", et_username.getText().toString());
            linkedHashMap.put("pwd", et_password.getText().toString());
            AuthenticateUserParser authenticateUserParser = new AuthenticateUserParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    this, Utility.getResourcesString(this, R.string.please_wait), true,
                    APIConstants.AUTHENTICATE_USER, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, authenticateUserParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof AuthenticateUserModel) {
                AuthenticateUserModel authenticateUserModel = (AuthenticateUserModel) model;
                if (!authenticateUserModel.isError()) {
                    Utility.setSharedPrefStringData(context, Constants.USER_ID, authenticateUserModel.getUserid());
                    Utility.setSharedPrefStringData(context, Constants.SESSION_ID, authenticateUserModel.getSesid());
                    Utility.setSharedPrefStringData(context, Constants.USER_NAME, authenticateUserModel.getUsername());
                    Utility.setSharedPrefStringData(context, Constants.CONTACT_NUMBER, authenticateUserModel.getUsername());
                    Utility.setSharedPrefStringData(context, Constants.ROLE_NAME, authenticateUserModel.getRolename());
                    Utility.setSharedPrefStringData(context, Constants.COMMUNITY_ID, authenticateUserModel.getCommunityid());
                    Utility.setSharedPrefStringData(context, Constants.COMMUNITY_NAME, authenticateUserModel.getCommunityname());
                    Utility.setSharedPrefStringData(context, Constants.RESIDENT_ID, authenticateUserModel.getResidentid());
                    Utility.setSharedPrefStringData(context, Constants.TOKEN, authenticateUserModel.getToken());

                    Intent intent = new Intent(context, DashboardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }
}
