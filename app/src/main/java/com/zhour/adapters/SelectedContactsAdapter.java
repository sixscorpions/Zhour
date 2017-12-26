package com.zhour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.fragments.PartyAndIEventInviteFragment;
import com.zhour.models.Contact;
import com.zhour.utils.Utility;

import java.util.ArrayList;

/**
 * Created by madhu on 10-Aug-17.
 */

public class SelectedContactsAdapter extends BaseAdapter {
    private DashboardActivity parent;
    private LayoutInflater layoutInflater;
    //private ArrayList<Contact> newList;


    public SelectedContactsAdapter(DashboardActivity parent, ArrayList<Contact> newList) {
        this.parent = parent;
        // this.newList = newList;
        layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return PartyAndIEventInviteFragment.contactsListModel.size();
    }

    @Override
    public Object getItem(int position) {
        return PartyAndIEventInviteFragment.contactsListModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_added_contacts,
                    null);
            viewHolder = new ViewHolder();

            viewHolder.tv_contact = (TextView) convertView.findViewById(R.id.tv_contact);
            viewHolder.tv_contact.setTypeface(Utility.setRobotoRegular(parent));
            viewHolder.tv_contact_remove = (TextView) convertView.findViewById(R.id.tv_contact_remove);
            viewHolder.tv_contact_remove.setTypeface(Utility.getFontAwesomeWebFont(parent));
            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = PartyAndIEventInviteFragment.contactsListModel.get(position);
        if (!Utility.isValueNullOrEmpty(contact.getPhoneNumber())) {

            if (!Utility.isValueNullOrEmpty(contact.getDisplayName())) {
                viewHolder.tv_contact.setText(contact.getDisplayName());
                viewHolder.tv_contact_remove.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_contact.setText(contact.getPhoneNumber());
                viewHolder.tv_contact_remove.setVisibility(View.VISIBLE);
            }

        }
        viewHolder.tv_contact_remove.setId(position);
        viewHolder.tv_contact_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = v.getId();
                PartyAndIEventInviteFragment.contactsListModel.remove(pos);
                PartyAndIEventInviteFragment.addContactList.remove(pos);
                PartyAndIEventInviteFragment.tv_count.setText(String.format("%d", PartyAndIEventInviteFragment.contactsListModel.size()));
                if (PartyAndIEventInviteFragment.contactsListModel != null && PartyAndIEventInviteFragment.contactsListModel.size() == 0) {
                    PartyAndIEventInviteFragment.contactsListModel = null;
                    PartyAndIEventInviteFragment.addContactList = null;
                    PartyAndIEventInviteFragment.tv_count.setVisibility(View.GONE);
                    PartyAndIEventInviteFragment.mDialog.dismiss();
                } else {
                    PartyAndIEventInviteFragment.tv_count.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                }
            }
        });
        return convertView;

    }

    private class ViewHolder {
        private TextView tv_contact;
        private TextView tv_contact_remove;

    }
}
