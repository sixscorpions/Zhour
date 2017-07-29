package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;


public class AlienCarFragment extends Fragment {
    public static final String TAG = AlienCarFragment.class.getSimpleName();
    private DashboardActivity parent;
    private View view;

    private Button iv_get_details;

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
        view = inflater.inflate(R.layout.fragment_alien_car, container, false);
        inITUI();
        return view;
    }

    private void inITUI() {
        iv_get_details = (Button) view.findViewById(R.id.iv_get_details);
        iv_get_details.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
              //  getCarparkingPopUp();

            }
        });
    }

    private void getCarparkingPopUp() {
         View mDialogView;

        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(parent);
        LayoutInflater inflater = parent.getLayoutInflater();
        mDialogView = inflater.inflate(R.layout.dialog_car_parking_popup, null);
        mAlertDialogBuilder.setView(mDialogView);

        final AlertDialog alertDialog = mAlertDialogBuilder.create();



    }


}
