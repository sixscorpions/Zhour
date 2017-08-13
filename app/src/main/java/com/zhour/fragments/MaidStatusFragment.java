package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
    ImageView btn_switch_in;

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

    /**
     * This method is used to set data after api get
     */
    private void setDataToLayout() {
        if (mMaidModel != null) {
            tv_maid_name.setText(mMaidModel.getStaffname());
            tv_phone.setText(mMaidModel.getContact1());
            if (mMaidModel.getOuttime() == null) {
                btn_switch_in.setImageDrawable(Utility.getDrawable(mParent, R.drawable.check_in));
                tv_in_out.setText(Utility.getResourcesString(mParent,R.string.in));
            } else {
                btn_switch_in.setImageDrawable(Utility.getDrawable(mParent, R.drawable.check_out));
                tv_in_out.setText(Utility.getResourcesString(mParent,R.string.out));
            }
            tv_time.setText(Utility.displayTimeFormat(mMaidModel.getIntime()));
            tv_date.setText(Utility.displayDateFormat(mMaidModel.getIntime()));
        }
    }
}
