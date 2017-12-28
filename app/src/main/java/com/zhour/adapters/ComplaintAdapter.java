package com.zhour.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.models.ComplaintListModel;
import com.zhour.models.ComplaintModel;
import com.zhour.models.Model;
import com.zhour.parser.ComplaintParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by shankar Pilli on 8/8/2017
 */

public class ComplaintAdapter extends BaseAdapter implements IAsyncCaller {
    private ArrayList<ComplaintListModel> complaintListModels;
    private DashboardActivity mParent;
    private LayoutInflater layoutInflater;

    private int clickedPositon = -1;

    public ComplaintAdapter(ArrayList<ComplaintListModel> complaintListModels, DashboardActivity mParent) {
        this.complaintListModels = complaintListModels;
        this.mParent = mParent;
        layoutInflater = (LayoutInflater) mParent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return complaintListModels.size();
    }

    @Override
    public Object getItem(int position) {
        return complaintListModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_complaint, group, false);
            holder = new ViewHolder();

            holder.tv_complaint_des = (TextView) convertView.findViewById(R.id.tv_complaint_des);
            holder.img_type = (ImageView) convertView.findViewById(R.id.img_type);
            holder.ll_name_contact = (LinearLayout) convertView.findViewById(R.id.ll_name_contact);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_contact_number = (TextView) convertView.findViewById(R.id.tv_contact_number);

            /*FIRST AND LAST NAME */
            holder.tv_flat_number = (TextView) convertView.findViewById(R.id.tv_flat_number);
            holder.tv_flat_number.setTypeface(Utility.setRobotoRegular(mParent));
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_date.setTypeface(Utility.setRobotoRegular(mParent));

            holder.ll_call = (LinearLayout) convertView.findViewById(R.id.ll_call);
            holder.ll_flat_date = (LinearLayout) convertView.findViewById(R.id.ll_flat_date);

            holder.tv_close = (TextView) convertView.findViewById(R.id.tv_close);

            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_status.setTypeface(Utility.setRobotoRegular(mParent));
            holder.img_status = (ImageView) convertView.findViewById(R.id.img_status);

            holder.tv_call = (TextView) convertView.findViewById(R.id.tv_call);
            holder.tv_call.setTypeface(Utility.getFontAwesomeWebFont(mParent));
            holder.tv_close.setTypeface(Utility.getFontAwesomeWebFont(mParent));

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        ComplaintListModel complaintListModel = complaintListModels.get(position);
        if (complaintListModel.getComplainttype().equalsIgnoreCase("83"))
            holder.img_type.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_electric));
        if (complaintListModel.getComplainttype().equalsIgnoreCase("82"))
            holder.img_type.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_pump));
        if (complaintListModel.getComplainttype().equalsIgnoreCase("81"))
            holder.img_type.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_apartment));
        if (complaintListModel.getComplainttype().equalsIgnoreCase("80"))
            holder.img_type.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_apartment));

        if (complaintListModel.getComplaintstatus().equalsIgnoreCase("Open")) {
            holder.tv_complaint_des.setText(complaintListModel.getComplaintdesc());
            holder.ll_name_contact.setVisibility(View.GONE);
            holder.tv_call.setVisibility(View.GONE);
            holder.img_status.setVisibility(View.VISIBLE);
            holder.ll_flat_date.setVisibility(View.VISIBLE);
            holder.tv_close.setVisibility(View.GONE);
            holder.tv_status.setText("Open");

            holder.tv_date.setText(complaintListModel.getUpdateddate().substring(0, complaintListModel.getUpdateddate().length() - 9));
            holder.tv_flat_number.setText(complaintListModel.getFlatnumber());

        } else if (complaintListModel.getComplaintstatus().equalsIgnoreCase("Closed")) {
            holder.tv_complaint_des.setText(complaintListModel.getComplaintdesc());
            holder.ll_name_contact.setVisibility(View.GONE);
            holder.tv_call.setVisibility(View.GONE);
            holder.img_status.setVisibility(View.VISIBLE);
            holder.ll_flat_date.setVisibility(View.VISIBLE);
            holder.tv_close.setVisibility(View.GONE);
            holder.tv_status.setText("Closed");

            holder.tv_date.setText(Utility.displayDateComplaintsFormat(complaintListModel.getUpdateddate()));
            holder.tv_flat_number.setText(complaintListModel.getFlatnumber());

        } else {
            holder.tv_complaint_des.setText(complaintListModel.getComplaintdesc());
            holder.ll_name_contact.setVisibility(View.VISIBLE);
            holder.tv_name.setText("Assigned to: " + complaintListModel.getStaffname());
            holder.tv_contact_number.setText(complaintListModel.getStaffcontact());
            holder.tv_call.setVisibility(View.VISIBLE);
            holder.ll_flat_date.setVisibility(View.GONE);
            holder.img_status.setVisibility(View.GONE);
            holder.tv_close.setVisibility(View.VISIBLE);
            holder.tv_status.setText("Call");
        }

        holder.ll_call.setId(position);
        holder.ll_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = v.getId();
                if (complaintListModels.get(pos).getComplaintstatus().equalsIgnoreCase("InProcess")) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + complaintListModels.get(pos).getStaffcontact()));
                    mParent.startActivity(intent);
                }
            }
        });

        holder.tv_close.setId(position);
        holder.tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = v.getId();
                if (complaintListModels.get(pos).getComplaintstatus().equalsIgnoreCase("InProcess")) {
                    showPickAlert(complaintListModels.get(pos));
                    clickedPositon = pos;
                }
            }
        });

        return convertView;
    }

    /**
     * This method is used to update the status
     *
     * @param complaintListModel
     */
    private void updateStatus(ComplaintListModel complaintListModel) {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("complaintid", complaintListModel.getComplaintid());
            linkedHashMap.put("complainttypeid", complaintListModel.getComplainttype());
            linkedHashMap.put("complaintdesc", complaintListModel.getComplaintdesc());
            linkedHashMap.put("complaintstatus", "Closed");
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

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof ComplaintModel) {
                ComplaintModel complaintModel = (ComplaintModel) model;
                if (!complaintModel.isError()) {
                    Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.complaint_closed_successfully));
                    complaintListModels.remove(clickedPositon);
                    notifyDataSetChanged();
                }
            }
        }
    }

    public class ViewHolder {
        private TextView tv_complaint_des;
        private ImageView img_type;
        private TextView tv_flat_number;
        private TextView tv_date;
        private TextView tv_status;
        private ImageView img_status;
        private TextView tv_call;

        private LinearLayout ll_flat_date;

        private TextView tv_name;
        private TextView tv_contact_number;
        private LinearLayout ll_name_contact;
        private LinearLayout ll_call;

        private TextView tv_close;
    }

    private void showPickAlert(final ComplaintListModel complaintListModel) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mParent);
        alertDialogBuilder.setMessage("Do you want to close this ticket?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        updateStatus(complaintListModel);
                    }
                });
        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
