package com.zhour.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;


public class AlienCarFragment extends Fragment {
    public static final String TAG = AlienCarFragment.class.getSimpleName();
    private DashboardActivity parent;
    private View view;

    private Button iv_get_details;


    private View mDialogView;
    private android.support.v7.app.AlertDialog alertDialog;

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
        /*PERMISSION FOR CALL*/
        askPermission();

        iv_get_details = (Button) view.findViewById(R.id.iv_get_details);
        iv_get_details.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                getCarparkingPopUp();

            }
        });
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(parent,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(parent,
                    Manifest.permission.CALL_PHONE)) {

            } else {

                ActivityCompat.requestPermissions(parent,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private void getCarparkingPopUp() {
        AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(parent);
        LayoutInflater inflater = parent.getLayoutInflater();
        mDialogView = inflater.inflate(R.layout.dialog_car_parking_popup, null);
        mAlertDialogBuilder.setView(mDialogView);


        alertDialog = mAlertDialogBuilder.create();
        try {
            alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        } catch (Exception e) {
            e.printStackTrace();

        }


        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(true);

        /*PROFILE USER AND IMAGE */

          /*TITLE*/
        ImageView iv_profile = (ImageView) mDialogView.findViewById(R.id.iv_profile);
        TextView tv_user_name = (TextView) mDialogView.findViewById(R.id.tv_user_name);
        tv_user_name.setTypeface(Utility.setRobotoRegular(parent));



        /*CLOSE BUTTON*/
        ImageView iv_cross = (ImageView) mDialogView.findViewById(R.id.iv_cross);
        iv_cross.setVisibility(View.VISIBLE);
        iv_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        /*PLOT ICON*/
        ImageView iv_plot = (ImageView) mDialogView.findViewById(R.id.iv_plot);
        TextView tv_plot_address = (TextView) mDialogView.findViewById(R.id.tv_plot_address);
        tv_plot_address.setTypeface(Utility.setRobotoRegular(parent));

        /*MOBILE ICON*/
        ImageView iv_mobile = (ImageView) mDialogView.findViewById(R.id.iv_mobile);
        TextView tv_mobile = (TextView) mDialogView.findViewById(R.id.tv_mobile);
        tv_plot_address.setTypeface(Utility.setRobotoRegular(parent));


        Button btn_call = (Button) mDialogView.findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "9177177728"));
                startActivity(intent);

            }
        });

        alertDialog.show();

    }


}