package com.zhour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.models.Contact;
import com.zhour.utils.Utility;

import java.util.ArrayList;

/**
 * Created by madhu on 10-Aug-17.
 */

public class SelectedContactsAdapter extends BaseAdapter {
    private DashboardActivity parent;
    private LayoutInflater layoutInflater;
    private ArrayList<Contact> newList;


    public SelectedContactsAdapter(DashboardActivity parent, ArrayList<Contact> newList) {
        this.parent = parent;
        this.newList = newList;
        layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return newList.size();
    }

    @Override
    public Object getItem(int position) {
        return newList.get(position);
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
        Contact contact = newList.get(position);
        if (!Utility.isValueNullOrEmpty(contact.getPhoneNumber())) {

            if (!Utility.isValueNullOrEmpty(contact.getDisplayName()))
                viewHolder.tv_contact.setText(contact.getDisplayName());
            else
                viewHolder.tv_contact.setText(contact.getPhoneNumber());

        }
        return convertView;

    }

    private class ViewHolder {
        private TextView tv_contact;
        private TextView tv_contact_remove;

    }
}
