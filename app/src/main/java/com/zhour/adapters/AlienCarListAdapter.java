package com.zhour.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.models.AlienCarModel;
import com.zhour.utils.Utility;

import java.util.ArrayList;

/**
 * Created by madhu on 29-Jul-17.
 */

public class AlienCarListAdapter extends BaseAdapter {
    private DashboardActivity mParent;
    private ArrayList<AlienCarModel> alienCarModels;
    private LayoutInflater layoutInflater;
    private View mDialogView;
    private android.support.v7.app.AlertDialog alertDialog;


    public AlienCarListAdapter(DashboardActivity mParent, ArrayList<AlienCarModel> alienCarModels) {
        this.mParent = mParent;
        this.alienCarModels = alienCarModels;
        layoutInflater = (LayoutInflater) mParent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return alienCarModels.size();
    }

    @Override
    public Object getItem(int position) {
        return alienCarModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_alien_car, viewGroup, false);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_name.setTypeface(Utility.setRobotoRegular(mParent));
            holder.tv_flat = (TextView) convertView.findViewById(R.id.tv_flat);
            holder.tv_flat.setTypeface(Utility.setRobotoRegular(mParent));
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AlienCarModel noticeBoardModel = (AlienCarModel) alienCarModels.get(position);
        holder.tv_name.setText(noticeBoardModel.getResidentname());
        holder.tv_flat.setText("" + noticeBoardModel.getFlatnumber());
        convertView.setId(position);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = v.getId();
                setDataToLayout(alienCarModels.get(position));
            }
        });

        return convertView;
    }

    private class ViewHolder {
        private TextView tv_name;
        private TextView tv_flat;
    }

    /**
     * This method is used to set data to the layout
     */
    private void setDataToLayout(final AlienCarModel mAlienCarModel) {
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
                //Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mAlienCarModel.getContact1()));
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "7013688846"));
                mParent.startActivity(intent);
            }
        });

        /**Static data*/
        tv_user_name.setText("Ramakrishna");
        tv_plot_address.setText("Flat No: 61101");
        tv_mobile.setText("7013688846");

        alertDialog.show();
    }
}
