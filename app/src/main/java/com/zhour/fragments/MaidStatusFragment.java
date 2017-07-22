package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MaidStatusFragment extends Fragment {

    public static final String TAG = MaidStatusFragment.class.getSimpleName();

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

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_maid_status, container, false);
        inItUI();
        return view;
    }

    private void inItUI() {

    }

}
