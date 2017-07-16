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
import com.zhour.models.PartyInviteModel;

import java.util.ArrayList;

/**
 * Created by madhu on 14-Jul-17.
 */

public class PartyInviteAdapter extends BaseAdapter {
    private ArrayList<PartyInviteModel> list;
    private DashboardActivity parent;
    private LayoutInflater layoutInflater;

    public PartyInviteAdapter(ArrayList<PartyInviteModel> list, DashboardActivity parent) {
        this.list = list;
        this.parent = parent;
        layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup group) {
        ViewHolder holder;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.list_item_invite, group, false);


            holder.iv_clock = (ImageView) convertView.findViewById(R.id.iv_clock);
            holder.iv_event = (ImageView) convertView.findViewById(R.id.iv_event);
            holder.iv_calander = (ImageView) convertView.findViewById(R.id.iv_calander);

            /*FIRST AND LAST NAME */
            holder.tv_description = (TextView) convertView.findViewById(R.id.tv_description);
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        PartyInviteModel partyInviteModel=  list.get(position);



        return convertView;
    }

    private class ViewHolder {

        private ImageView iv_clock;
        private ImageView iv_event;
        private ImageView iv_calander;
        private TextView tv_description;
        private TextView tv_date;
        private TextView tv_time;

    }
}
