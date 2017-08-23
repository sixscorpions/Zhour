package com.zhour.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.fragments.NoticeBoardFragment;
import com.zhour.models.NoticeBoardModel;
import com.zhour.utils.UImageLoader;
import com.zhour.utils.Utility;

import java.util.ArrayList;

/**
 * Created by madhu on 29-Jul-17.
 */

public class NoticeBoardAdapter extends BaseAdapter {
    private DashboardActivity parent;
    private ArrayList<NoticeBoardModel> noticeList;
    private LayoutInflater layoutInflater;


    public NoticeBoardAdapter(DashboardActivity parent, ArrayList<NoticeBoardModel> noticeList) {
        this.parent = parent;
        this.noticeList = noticeList;
        layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return noticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return noticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_notice_board_item, viewGroup, false);
            holder = new ViewHolder();
            holder.iv_notice = (ImageView) convertView.findViewById(R.id.iv_notice);
            holder.iv_favorite = (ImageView) convertView.findViewById(R.id.iv_favorite);
            holder.ll_text_layout = (LinearLayout) convertView.findViewById(R.id.ll_text_layout);
            holder.tv_header = (TextView) convertView.findViewById(R.id.tv_header);
            holder.tv_header.setTypeface(Utility.setRobotoRegular(parent));
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_header.setTypeface(Utility.setRobotoRegular(parent));
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NoticeBoardModel noticeBoardModel = (NoticeBoardModel) this.noticeList.get(position);
        holder.tv_header.setText(noticeBoardModel.getNoticetitle());
        holder.tv_date.setText("" + noticeBoardModel.getSentdate());
        if (NoticeBoardFragment.readIds == null || !NoticeBoardFragment.readIds.contains(noticeBoardModel.getNoticeid())) {
            holder.ll_text_layout.setBackgroundColor(Utility.getColor(this.parent, R.color.dialogBgColor));
        } else {
            holder.ll_text_layout.setBackgroundColor(Utility.getColor(this.parent, R.color.white));
        }
        return convertView;
    }

    private class ViewHolder {

        private ImageView iv_notice;
        private TextView tv_header;
        private TextView tv_date;
        private ImageView iv_favorite;
        private LinearLayout ll_text_layout;

    }
}
