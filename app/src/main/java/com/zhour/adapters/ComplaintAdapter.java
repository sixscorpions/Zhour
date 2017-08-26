package com.zhour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.models.ComplaintListModel;
import com.zhour.utils.Utility;

import java.util.ArrayList;

/**
 * Created by shankar Pilli on 8/8/2017
 */

public class ComplaintAdapter extends BaseAdapter {
    private ArrayList<ComplaintListModel> complaintListModels;
    private DashboardActivity mParent;
    private LayoutInflater layoutInflater;

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

            /*FIRST AND LAST NAME */
            holder.tv_flat_number = (TextView) convertView.findViewById(R.id.tv_flat_number);
            holder.tv_flat_number.setTypeface(Utility.setRobotoRegular(mParent));
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_date.setTypeface(Utility.setRobotoRegular(mParent));

            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            holder.tv_status.setTypeface(Utility.setRobotoRegular(mParent));
            holder.img_status = (ImageView) convertView.findViewById(R.id.img_status);
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


        holder.tv_complaint_des.setText(complaintListModel.getComplaintdesc());
        holder.tv_date.setText(Utility.displayDateComplaintsFormat(complaintListModel.getUpdateddate()));

        holder.tv_flat_number.setText(complaintListModel.getFlatnumber());
        holder.tv_status.setText(complaintListModel.getComplaintstatus());

        return convertView;
    }

    public class ViewHolder {
        private TextView tv_complaint_des;
        private ImageView img_type;
        private TextView tv_flat_number;
        private TextView tv_date;
        private TextView tv_status;
        private ImageView img_status;

    }
}
