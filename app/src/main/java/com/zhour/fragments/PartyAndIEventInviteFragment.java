package com.zhour.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;


public class PartyAndIEventInviteFragment extends Fragment {
    private View view;
    private DashboardActivity parent;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (DashboardActivity) getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_party_and_ievent_invite, container, false);
        return view;
    }


}
