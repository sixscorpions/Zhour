package com.zhour.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.models.MaidModel;
import com.zhour.models.Model;
import com.zhour.parser.MaidParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MaidStatusFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = MaidStatusFragment.class.getSimpleName();

    private DashboardActivity mParent;
    private View view;
    @BindView(R.id.tv_maid_name)
    TextView tv_maid_name;

    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_date)
    TextView tv_date;

    @BindView(R.id.tv_in_out)
    TextView tv_in_out;

    @BindView(R.id.btn_switch_in)
    TextView btn_switch_in;

    @BindView(R.id.iv_call)
    ImageView iv_call;

    private MaidModel mMaidModel;


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
        view = inflater.inflate(R.layout.fragment_maid_status, container, false);
        ButterKnife.bind(this, view);
        inItUI();
        return view;
    }

    private void inItUI() {
        /*GET PHONE PERMISSION*/
        askPermission();


        tv_in_out.setTypeface(Utility.setRobotoRegular(mParent));
        tv_time.setTypeface(Utility.setRobotoRegular(mParent));
        tv_maid_name.setTypeface(Utility.setRobotoRegular(mParent));
        getMaidInfo();
    }

    /*This method is used to get the maid info*/
    private void getMaidInfo() {

        String communityID = Utility.getSharedPrefStringData(mParent, Constants.COMMUNITY_ID);
        String residentID = Utility.getSharedPrefStringData(mParent, Constants.RESIDENT_ID);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("communityid", communityID);
        linkedHashMap.put("residentid", residentID);

        MaidParser maidParser = new MaidParser();
        ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                APIConstants.GET_MAID_INFO, linkedHashMap,
                APIConstants.REQUEST_TYPE.POST, this, maidParser);
        Utility.execute(serverJSONAsyncTask);
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof MaidModel) {
                mMaidModel = (MaidModel) model;
                if (!mMaidModel.getIsError()) {
                    setDataToLayout();
                }
            }
        }
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(mParent,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mParent,
                    Manifest.permission.CALL_PHONE)) {

                iv_call.setEnabled(true);

            } else {
                iv_call.setEnabled(false);

                ActivityCompat.requestPermissions(mParent,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
    }
    /*CALLING NUMBER*/

    @OnClick(R.id.iv_call)
    void callToMaid() {

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + tv_phone.getText().toString()));
        startActivity(intent);

    }

    /**
     * This method is used to set data after api get
     */
    private void setDataToLayout() {
        if (mMaidModel != null) {
            tv_maid_name.setText(mMaidModel.getStaffname());
            tv_phone.setText(mMaidModel.getContact1());
            if (Utility.isValueNullOrEmpty(mMaidModel.getIntime()) && Utility.isValueNullOrEmpty(mMaidModel.getOuttime())) {
                btn_switch_in.setText(Utility.getResourcesString(mParent, R.string.check_out_icon));
                btn_switch_in.setTextColor(Utility.getColor(mParent, R.color.yellow));
                tv_in_out.setText(Utility.getResourcesString(mParent, R.string.not_entered));
                btn_switch_in.setTypeface(Utility.getMaterialIconsRegular(mParent));
                tv_time.setVisibility(View.GONE);
                tv_date.setVisibility(View.GONE);
            } else if (Utility.isValueNullOrEmpty(mMaidModel.getOuttime()) && !Utility.isValueNullOrEmpty(mMaidModel.getIntime())) {
                btn_switch_in.setText(Utility.getResourcesString(mParent, R.string.check_in_icon));
                btn_switch_in.setTextColor(Utility.getColor(mParent, R.color.green));
                tv_in_out.setText(Utility.getResourcesString(mParent, R.string.in));
                btn_switch_in.setTypeface(Utility.getMaterialIconsRegular(mParent));
                tv_time.setText(Utility.displayTimeFormat(mMaidModel.getIntime()));
                tv_date.setText(Utility.displayDateFormat(mMaidModel.getIntime()));
                tv_time.setVisibility(View.VISIBLE);
                tv_date.setVisibility(View.VISIBLE);
            } else {
                btn_switch_in.setText(Utility.getResourcesString(mParent, R.string.check_out_icon));
                btn_switch_in.setTextColor(Utility.getColor(mParent, R.color.red));
                tv_in_out.setText(Utility.getResourcesString(mParent, R.string.out));
                btn_switch_in.setTypeface(Utility.getMaterialIconsRegular(mParent));
                tv_time.setText(Utility.displayTimeFormat(mMaidModel.getIntime()));
                tv_date.setText(Utility.displayDateFormat(mMaidModel.getIntime()));
                tv_time.setVisibility(View.VISIBLE);
                tv_date.setVisibility(View.VISIBLE);
            }

        }
    }
}
