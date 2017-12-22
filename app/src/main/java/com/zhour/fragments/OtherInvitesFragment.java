package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;


public class OtherInvitesFragment extends Fragment {

    private DashboardActivity mParent;
    private View view;


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
        view = inflater.inflate(R.layout.fragment_other_invites, container, false);
        iniTUI();


        // Inflate the layout for this fragment
        return view;
    }

    private void iniTUI() {



    }

}
