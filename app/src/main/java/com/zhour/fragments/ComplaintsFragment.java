package com.zhour.fragments;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.ComplaintAdapter;
import com.zhour.adapters.SpinnerAdapter;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctask.ServerJSONAsyncTask;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.models.ComplaintListModel;
import com.zhour.models.ComplaintModel;
import com.zhour.models.ComplaintResponseModel;
import com.zhour.models.LookUpEventsTypeModel;
import com.zhour.models.Model;
import com.zhour.models.SpinnerModel;
import com.zhour.parser.ComplaintListParser;
import com.zhour.parser.ComplaintParser;
import com.zhour.parser.LookUpEventTypeParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ComplaintsFragment extends Fragment implements IAsyncCaller {

    public static final String TAG = ComplaintsFragment.class.getSimpleName();
    private DashboardActivity mParent;
    private View view;

    @BindView(R.id.et_complaints_types)
    EditText et_complaints_types;
    @BindView(R.id.et_complaint_description)
    EditText et_complaint_description;
    @BindView(R.id.tv_complaint_heading)
    TextView tv_complaint_heading;


    @BindView(R.id.ll_complaints)
    LinearLayout ll_complaints;
    @BindView(R.id.ll_list_parent)
    LinearLayout ll_list_parent;
    @BindView(R.id.list_view)
    ListView list_view;

    @BindView(R.id.iv_submit)
    Button iv_submit;

    @BindView(R.id.iv_complaint)
    ImageView iv_complaint;

    @BindView(R.id.iv_list)
    ImageView iv_list;

    private int position;


    private ComplaintAdapter complaintAdapter;

    private LookUpEventsTypeModel lookUpEventsTypeModel;
    private ComplaintResponseModel mComplaintResponseModel;
    private ArrayList<ComplaintListModel> complaintListModels;


    private int[] images;

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
        view = inflater.inflate(R.layout.fragment_complaints, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {

         /*GET PHONE PERMISSION*/
        askPermission();


        /*STATIC IMAGES ARRAY*/
        images = new int[]{R.drawable.ic_apartment, R.drawable.ic_apartment, R.drawable.ic_pump, R.drawable.ic_electric};

        getInviteTypes();
        ll_list_parent.setVisibility(View.GONE);
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

    @OnClick(R.id.iv_list)
    public void getComplaints() {
        iv_list.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_time_fill));
        iv_submit.setVisibility(View.GONE);
        ll_complaints.setVisibility(View.GONE);
        ll_list_parent.setVisibility(View.VISIBLE);
        getComplaintFromService();
    }

    @OnClick(R.id.tv_complaint_heading)
    public void showAddView() {
        iv_list.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_date));
        iv_submit.setVisibility(View.VISIBLE);
        ll_complaints.setVisibility(View.VISIBLE);
        ll_list_parent.setVisibility(View.GONE);
    }

    /**
     * This method is used to get data from the service
     */
    private void getComplaintFromService() {
        String communityID = Utility.getSharedPrefStringData(mParent, Constants.COMMUNITY_ID);
        String residentID = Utility.getSharedPrefStringData(mParent, Constants.RESIDENT_ID);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("complaintid", "0");
        linkedHashMap.put("communityid", communityID);
        linkedHashMap.put("residentid", residentID);

        ComplaintListParser complaintListParser = new ComplaintListParser();
        ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                APIConstants.GET_COMPLINTS, linkedHashMap,
                APIConstants.REQUEST_TYPE.POST, this, complaintListParser);
        Utility.execute(serverJSONAsyncTask);
    }

    private void getInviteTypes() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("entityname", "Complaint Types");
            LookUpEventTypeParser lookUpEventTypeParser = new LookUpEventTypeParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LOOKUP_DATA_BY_ENTITY_NAME, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, lookUpEventTypeParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method is used to add complaint
     */
    @OnClick(R.id.iv_submit)
    public void addContact() {
        if (isComplaintValidFields()) {
            saveComplaint("0", "Open");
        }
    }

    /**
     * This method is used to save the complaint
     */
    private void saveComplaint(String complaint_id, String complaint_status) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("complaintid", complaint_id);
            linkedHashMap.put("complainttypeid", getComplaintTypeId(et_complaints_types.getText().toString()));
            linkedHashMap.put("complaintdesc", et_complaint_description.getText().toString());
            linkedHashMap.put("complaintstatus", complaint_status);
            linkedHashMap.put("comments", "");
            linkedHashMap.put("communityid", Utility.getSharedPrefStringData(mParent, Constants.COMMUNITY_ID));
            linkedHashMap.put("residentid", Utility.getSharedPrefStringData(mParent, Constants.RESIDENT_ID));

            ComplaintParser mComplaintParser = new ComplaintParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.CREATE_OR_UPDATE_COMPLAINT, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, mComplaintParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getComplaintTypeId(String s) {
        String mComplaintTypeId = "";
        for (int i = 0; i < lookUpEventsTypeModel.getLookupNames().size(); i++) {
            if (lookUpEventsTypeModel.getLookUpModels().get(i).getLookupname().equals(s)) {
                mComplaintTypeId = lookUpEventsTypeModel.getLookUpModels().get(i).getLookupid();
            }
        }
        return mComplaintTypeId;
    }

    private boolean isComplaintValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_complaints_types.getText().toString())) {
            Utility.setSnackBar(mParent, et_complaints_types, "Please select complaints type");
            isValid = false;
        } else if (et_complaint_description.getText().toString().length() < 10) {
            Utility.setSnackBar(mParent, et_complaint_description, "Please write you description");
            isValid = false;
        }
        return isValid;
    }


    /*This method is used to set data to spinner*/
    @OnClick({R.id.et_complaints_types})
    void setInviteTypeData() {
        if (lookUpEventsTypeModel != null &&
                lookUpEventsTypeModel.getLookUpModels() != null &&
                lookUpEventsTypeModel.getLookUpModels().size() > 0)
            showSpinnerDialog(getActivity(), Utility.getResourcesString(mParent, R.string.complaints_types),
                    lookUpEventsTypeModel.getLookupNames(), 1);
    }

    public void showSpinnerDialog(final Context context, final String title,
                                  final ArrayList<SpinnerModel> itemsList, final int id) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);


        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        tv_title.setText(title);
        tv_title.setTextColor(context.getResources().getColor(R.color.colorWhite));
        builderSingle.setCustomTitle(view);


        final SpinnerAdapter adapter = new SpinnerAdapter(context, itemsList);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 1) {
                            position = which;
                            Utility.showLog("Position", "Position >>>>>" + position);
                            String text = mData.getTitle();
                            et_complaints_types.setText(text);
                            iv_complaint.setImageResource(images[position]);
                        }
                    }
                });
        builderSingle.show();
    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LookUpEventsTypeModel) {
                lookUpEventsTypeModel = (LookUpEventsTypeModel) model;
                if (!lookUpEventsTypeModel.isError()) {
                }
            } else if (model instanceof ComplaintModel) {
                ComplaintModel complaintModel = (ComplaintModel) model;
                if (!complaintModel.isError()) {
                    Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.complaint_raised_successfully));
                    clearDataForComplaint();
                }
            } else if (model instanceof ComplaintResponseModel) {
                mComplaintResponseModel = (ComplaintResponseModel) model;
                if (!mComplaintResponseModel.isError()) {
                    complaintListModels = new ArrayList<>();
                    complaintListModels = mComplaintResponseModel.getComplaintListModels();
                    setListData(complaintListModels);
                }
            }
        }
    }

    private void setListData(ArrayList<ComplaintListModel> complaintListModels) {
        complaintAdapter = new ComplaintAdapter(complaintListModels, mParent);
        list_view.setAdapter(complaintAdapter);
    }

    /**
     * This method is used to clear the data after raised.
     */
    private void clearDataForComplaint() {
        et_complaints_types.setText("");
        et_complaint_description.setText("");
    }
}
