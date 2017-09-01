package com.zhour.fragments;


import android.Manifest;
import android.content.Context;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.vision.text.TextRecognizer;
import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.models.AlienCarListModel;
import com.zhour.models.AlienCarModel;
import com.zhour.models.Model;
import com.zhour.parser.AlienCarParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AlienCarFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = AlienCarFragment.class.getSimpleName();
    private DashboardActivity mParent;
    private View view;

    public static EditText et_vehicle_number;

    @BindView(R.id.tv_scanner)
    TextView tv_scanner;
    private Context mContext;

    private View mDialogView;
    public static AlienCarListModel mAlienCarListModel;
    private android.support.v7.app.AlertDialog alertDialog;
    private Uri imageUri;


    private static final String SAVED_INSTANCE_URI = "uri";
    private static final String SAVED_INSTANCE_RESULT = "result";
    private TextRecognizer detector;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();

        if (savedInstanceState != null) {
            imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
            et_vehicle_number.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
        }
        detector = new TextRecognizer.Builder(mParent).build();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_alien_car, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        et_vehicle_number = (EditText) view.findViewById(R.id.et_vehicle_number);
        /*PERMISSION FOR CALL*/
        askPermission();
        //  askPermissionForScanner();
        tv_scanner.setTypeface(Utility.getFontAwesomeWebFont(mParent));
    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(mParent,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mParent,
                    Manifest.permission.CALL_PHONE)) {

            } else {

                ActivityCompat.requestPermissions(mParent,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.MY_PERMISSIONS_REQUEST_CALL_PHONE);
            }
        }
    }

    private void askPermissionForScanner() {
        if (ContextCompat.checkSelfPermission(mParent,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mParent,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {

                ActivityCompat.requestPermissions(mParent,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }
    }

    /**
     * This method is used to get the vehicle info
     */
    @OnClick(R.id.iv_get_details)
    void getVehicleInfo() {

        if (isValidFields() && isValidDigitFields()) {
            callServiceToGetVehicleInfo();
        }

    }

    /*CAR NUMBER SCANNER*/
    @OnClick(R.id.tv_scanner)
    void getNumberScanner() {
    /*    takePicture();*/
        ActivityCompat.requestPermissions(mParent, new
                String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constants.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

    }

    /**
     * Get Vehicle info
     */
    private void callServiceToGetVehicleInfo() {
        String communityID = Utility.getSharedPrefStringData(mParent, Constants.COMMUNITY_ID);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("communityid", communityID);
        linkedHashMap.put("vehiclenumber", et_vehicle_number.getText().toString().toUpperCase());

        AlienCarParser alienCarParser = new AlienCarParser();
        ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                APIConstants.GET_VEHICLE_INFO, linkedHashMap,
                APIConstants.REQUEST_TYPE.POST, this, alienCarParser);
        Utility.execute(serverJSONAsyncTask);
    }


    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(et_vehicle_number.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_vehicle_number, "Please enter vehicle number");
            et_vehicle_number.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    private boolean isValidDigitFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_vehicle_number.getText().toString())) {
            Utility.setSnackBar(mParent, et_vehicle_number, "Please write code");
            isValid = false;
        } else if (et_vehicle_number.getText().toString().length() < 4) {
            Utility.setSnackBar(mParent, et_vehicle_number, "Code must be 4 digit");
            isValid = false;
        }
        return isValid;
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof AlienCarListModel) {
                mAlienCarListModel = (AlienCarListModel) model;
                et_vehicle_number.setText("");
                if (!mAlienCarListModel.getIsError()) {
                    if (mAlienCarListModel.getAlienCarModels().size() == 0) {
                        Utility.setSnackBar(mParent, view, Utility.getResourcesString(mParent, R.string.vehicle_not_found));
                    } else {
                        setDataToLayout();
                    }
                }
            }
        }
    }

    /**
     * This method is used to set data to the layout
     */
    private void setDataToLayout() {
        if (mAlienCarListModel.getAlienCarModels().size() == 1) {
            final AlienCarModel mAlienCarModel = mAlienCarListModel.getAlienCarModels().get(0);

            AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(mParent);
            LayoutInflater inflater = mParent.getLayoutInflater();
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
            tv_user_name.setTypeface(Utility.setRobotoRegular(mParent));
            tv_user_name.setText(mAlienCarModel.getResidentname());


            TextView tv_type = (TextView) mDialogView.findViewById(R.id.tv_type);
            tv_type.setTypeface(Utility.setRobotoRegular(mParent));

            TextView tv_type_of_vehicle = (TextView) mDialogView.findViewById(R.id.tv_type_of_vehicle);
            tv_type_of_vehicle.setTypeface(Utility.setRobotoRegular(mParent));

            if (!Utility.isValueNullOrEmpty(mAlienCarModel.getVehtype()))
                tv_type.setText(mAlienCarModel.getVehtype());


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
            tv_plot_address.setTypeface(Utility.setRobotoRegular(mParent));
            tv_plot_address.setText("Flat No: " + mAlienCarModel.getFlatnumber());

        /*MOBILE ICON*/
            ImageView iv_mobile = (ImageView) mDialogView.findViewById(R.id.iv_mobile);
            TextView tv_mobile = (TextView) mDialogView.findViewById(R.id.tv_mobile);
            tv_mobile.setTypeface(Utility.setRobotoRegular(mParent));
            tv_mobile.setText(mAlienCarModel.getContact1());


            Button btn_call = (Button) mDialogView.findViewById(R.id.btn_call);
            btn_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mAlienCarModel.getContact1()));
                    startActivity(intent);
                }
            });

            alertDialog.show();
        } else {
            Utility.navigateDashBoardFragment(new AlienCarListFragment(), AlienCarListFragment.TAG, null, mParent);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
            outState.putString(SAVED_INSTANCE_RESULT, et_vehicle_number.getText().toString());
        }
        super.onSaveInstanceState(outState);
    }


}