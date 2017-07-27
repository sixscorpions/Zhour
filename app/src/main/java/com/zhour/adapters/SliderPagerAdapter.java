package com.zhour.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zhour.R;
import com.zhour.models.ImageModel;

import java.util.ArrayList;

/**
 * Created by madhu on 23-Jul-17.
 */

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    Activity activity;
    ArrayList<ImageModel> image_arraylist;

    public SliderPagerAdapter(Activity activity, ArrayList<ImageModel> image_arraylist) {
        this.activity = activity;
        this.image_arraylist = image_arraylist;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_pager, container, false);

        ImageModel imageModel = image_arraylist.get(position);
        ImageView iv_pager = (ImageView) view.findViewById(R.id.iv_pager);
        Picasso.with(activity.getApplicationContext())
                .load(imageModel.getUrl())
                .placeholder(R.drawable.ic_diwali) // optional
                .error(R.drawable.ic_diwali)         // optional
                .into(iv_pager);
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return image_arraylist.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}