package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctask.ServerJSONAsyncTask;
import com.zhour.models.AboutUsModel;
import com.zhour.models.Model;
import com.zhour.parser.AboutUsParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AboutFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = AboutFragment.class.getSimpleName();
    private DashboardActivity mParent;
    private View view;


    @BindView(R.id.tv_api)
    TextView tv_api;
    @BindView(R.id.tv_version)
    TextView tv_version;
    @BindView(R.id.tv_message)
    TextView tv_message;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.tv_contact)
    TextView tv_contact;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        getAboutUsData();
    }

    private void getAboutUsData() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            AboutUsParser aboutUsParser = new AboutUsParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.BASE_URL, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, aboutUsParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof AboutUsModel) {
                AboutUsModel aboutUsModel = (AboutUsModel) model;
                tv_api.setText("   :  " + aboutUsModel.getAPI());
                tv_version.setText("   :  " + aboutUsModel.getVersion());
                tv_message.setText("   :  " + aboutUsModel.getMessage());
                tv_company.setText("   :  " + aboutUsModel.getCompany());
                tv_contact.setText("   :  " + aboutUsModel.getContact());
            }
        }
    }
}
