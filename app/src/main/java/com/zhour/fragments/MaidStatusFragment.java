package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MaidStatusFragment extends Fragment {

    public static final String TAG = MaidStatusFragment.class.getSimpleName();

    private DashboardActivity parent;
    private View view;
    @BindView(R.id.et_user_name)
    EditText et_user_name;

    @BindView(R.id.et_phone)
    EditText et_phone;


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
        ButterKnife.bind(this, view);
        inItUI();
        return view;
    }

    private void inItUI() {

    }

}
