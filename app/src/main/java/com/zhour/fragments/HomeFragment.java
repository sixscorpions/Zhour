package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.utils.Utility;


public class HomeFragment extends Fragment {
    public static final String TAG = "HomeFragment";
    private DashboardActivity parent;
    private View view;

    private LinearLayout ll_invite;


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

        view = inflater.inflate(R.layout.fragment_home, container, false);
        inItUI();
        return view;
    }

    private void inItUI() {

        ll_invite = (LinearLayout) view.findViewById(R.id.ll_invite);
        ll_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Utility.navigateDashBoardFragment(new PartyAndIEventInviteFragment(), PartyAndIEventInviteFragment.TAG, bundle, getActivity());

            }
        });
    }

}
