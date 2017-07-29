package com.zhour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.ChooseCommunityActivity;
import com.zhour.models.CommunityUserModel;
import com.zhour.utils.Utility;

import java.util.ArrayList;

/**
 * Created by madhu on 29-Jul-17.
 */

public class CommunitiesAdapter extends BaseAdapter {
    private ChooseCommunityActivity parent;
    private ArrayList<CommunityUserModel> commnitiesList;
    private LayoutInflater layoutInflater;


    public CommunitiesAdapter(ChooseCommunityActivity parent, ArrayList<CommunityUserModel> commnitiesList) {
        this.parent = parent;
        this.commnitiesList = commnitiesList;
        layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return commnitiesList.size();
    }

    @Override
    public Object getItem(int position) {
        return commnitiesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, viewGroup, false);
            viewHolder = new ViewHolder();

            viewHolder.iv_community = (ImageView) convertView.findViewById(R.id.iv_community);
            viewHolder.tv_community_name = (TextView) convertView.findViewById(R.id.tv_community_name);
            viewHolder.tv_community_name.setTypeface(Utility.setRobotoRegular(parent));
            viewHolder.tv_community = (TextView) convertView.findViewById(R.id.tv_community);
            viewHolder.tv_community.setTypeface(Utility.setRobotoRegular(parent));
            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }
        CommunityUserModel communityUserModel = commnitiesList.get(position);

        viewHolder.tv_community_name.setText(communityUserModel.getCommunityname());
        viewHolder.tv_community.setText(communityUserModel.getRolename());

        return convertView;
    }

    private class ViewHolder {
        private ImageView iv_community;
        private TextView tv_community_name;
        private TextView tv_community;

    }
}
