package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.models.ComplaintModel;
import com.zhour.models.Model;
import com.zhour.parser.ComplaintParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.LinkedHashMap;

public class ComplaintsFragment extends Fragment implements IAsyncCaller{

    public static final String TAG = ComplaintsFragment.class.getSimpleName();
    private DashboardActivity parent;
    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_complaints, container, false);
        inITUI();
        return view;
    }

    private void inITUI() {


        getComplints();
    }

    private void getComplints() {

        String residentID = Utility.getSharedPrefStringData(parent, Constants.RESIDENT_ID);
        String communityID = Utility.getSharedPrefStringData(parent, Constants.COMMUNITY_ID);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("complaintid", 0);
        linkedHashMap.put("residentid", residentID);
        linkedHashMap.put("communityid", communityID);

        ComplaintParser complaintParser = new ComplaintParser();
        ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                parent, Utility.getResourcesString(parent, R.string.please_wait), true,
                APIConstants.GET_COMPLINTS, linkedHashMap,
                APIConstants.REQUEST_TYPE.POST, this, complaintParser);
        Utility.execute(serverJSONAsyncTask);

    }

    @Override
    public void onComplete(Model model) {
        if (model instanceof ComplaintModel){

        }

    }
}
