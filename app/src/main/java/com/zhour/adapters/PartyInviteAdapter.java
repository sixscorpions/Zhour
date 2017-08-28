package com.zhour.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.fragments.PartyAndIEventInviteFragment;
import com.zhour.models.InvitesModel;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.ArrayList;

/**
 * Created by madhu on 14-Jul-17.
 */

public class PartyInviteAdapter extends BaseAdapter {
    private ArrayList<InvitesModel> list;
    private DashboardActivity parent;
    private LayoutInflater layoutInflater;
    private InvitesModel invitesModel;
    private boolean is_Party_Invite;

    public PartyInviteAdapter(ArrayList<InvitesModel> list, DashboardActivity parent, boolean is_Party_Invite) {
        this.list = list;
        this.parent = parent;
        layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.is_Party_Invite = is_Party_Invite;
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
            convertView = layoutInflater.inflate(R.layout.list_item_invite, group, false);
            holder = new ViewHolder();

            holder.iv_clock = (ImageView) convertView.findViewById(R.id.iv_clock);
            holder.iv_calander = (ImageView) convertView.findViewById(R.id.iv_calander);

            /*FIRST AND LAST NAME */
            holder.tv_invitenote = (TextView) convertView.findViewById(R.id.tv_invitenote);
            holder.tv_invitenote.setTypeface(Utility.setRobotoRegular(parent));
            holder.tv_inviteType = (TextView) convertView.findViewById(R.id.tv_inviteType);
            holder.tv_invitenote.setTypeface(Utility.setRobotoRegular(parent));
            holder.tv_invitenote.setTypeface(Utility.setRobotoRegular(parent));
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_date.setTypeface(Utility.setRobotoRegular(parent));
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_time.setTypeface(Utility.setRobotoRegular(parent));

            holder.iv_clock = (ImageView) convertView.findViewById(R.id.iv_clock);
            holder.iv_calander = (ImageView) convertView.findViewById(R.id.iv_calander);


            holder.tv_edit = (TextView) convertView.findViewById(R.id.tv_edit);
            holder.tv_edit.setTypeface(Utility.getFontAwesomeWebFont(parent));
            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.DATE, invitesModel.getEventdate());
                    bundle.putString(Constants.TIME, invitesModel.getEventtime());
                    bundle.putString(Constants.INVITE_NOTE, invitesModel.getInvitenote());
                    bundle.putString(Constants.INVITE_TYPE, invitesModel.getInvitetype());
                    bundle.putString(Constants.VENUE, invitesModel.getVenue());
                    bundle.putBoolean(Constants.IS_PARTY_INVITE, is_Party_Invite);
                    Utility.navigateDashBoardFragment(new PartyAndIEventInviteFragment(), PartyAndIEventInviteFragment.TAG, bundle, parent);
                }
            });
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        invitesModel = list.get(position);

        holder.tv_invitenote.setText(Utility.capitalizeFirstLetter(invitesModel.getInvitenote()));
        holder.tv_date.setText(invitesModel.getEventdate());


        String inviteType = invitesModel.getInvitetype() + " Invitation";
        /*SET EVENT TIME */

        if (!Utility.isValueNullOrEmpty(invitesModel.getEventtime())) {
            holder.tv_time.setText(Utility.getTime(invitesModel.getEventtime()));
        }


        holder.tv_inviteType.setText(inviteType);


        return convertView;
    }

    public class ViewHolder {
        private TextView tv_invitenote;
        private ImageView iv_clock;
        private ImageView iv_calander;
        private TextView tv_date;
        private TextView tv_time;
        private TextView tv_inviteType;
        private TextView tv_edit;

    }
}
