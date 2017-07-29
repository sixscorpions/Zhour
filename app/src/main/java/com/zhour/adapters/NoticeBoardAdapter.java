package com.zhour.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zhour.R;
import com.zhour.activities.DashboardActivity;
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
            convertView.setTag(holder);

            holder.iv_notice = (ImageView) convertView.findViewById(R.id.iv_notice);
            holder.iv_favorite = (ImageView) convertView.findViewById(R.id.iv_favorite);

            holder.tv_header = (TextView) convertView.findViewById(R.id.tv_header);
            holder.tv_header.setTypeface(Utility.setRobotoRegular(parent));
            holder.tv_date = (TextView) convertView.findViewById(R.id.tv_date);
            holder.tv_header.setTypeface(Utility.setRobotoRegular(parent));


        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NoticeBoardModel noticeBoardModel = noticeList.get(position);

        UImageLoader.URLpicLoading(holder.iv_notice, noticeBoardModel.getIcon(), null, R.drawable.ic_home_sample);

        holder.tv_header.setText(noticeBoardModel.getDesc());
        holder.tv_date.setText(noticeBoardModel.getDate());

        Bitmap largeIcon = BitmapFactory.decodeFile(noticeBoardModel.getIcon());




        if (noticeBoardModel.isFavorite())
            holder.iv_favorite.setImageDrawable(Utility.getDrawable(parent, R.drawable.ic_favorite));
        else
            holder.iv_favorite.setImageDrawable(Utility.getDrawable(parent, R.drawable.ic_un_favorite));

        return convertView;
    }

    private class ViewHolder {

        private ImageView iv_notice;
        private TextView tv_header;
        private TextView tv_date;
        private ImageView iv_favorite;

    }
}
