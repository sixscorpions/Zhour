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

import butterknife.BindView;
import butterknife.ButterKnife;


public class EmergencyFragment extends Fragment {

    @BindView(R.id.iv_emergency)
    ImageView iv_emergency;

    @BindView(R.id.iv_ambulence)
    ImageView iv_ambulence;

    @BindView(R.id.tv_fire)
    TextView tv_fire;

    @BindView(R.id.tv_ambulence)
    TextView tv_ambulence;


    public static final String TAG = EmergencyFragment.class.getSimpleName();
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
        view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ButterKnife.bind(this, view);
        inITUI();
        return view;
    }

    private void inITUI() {


    }

}
