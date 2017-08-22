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

    @BindView(R.id.img_emergency)
    ImageView img_emergency;

    public static final String TAG = EmergencyFragment.class.getSimpleName();
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
        view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {

    }

}
