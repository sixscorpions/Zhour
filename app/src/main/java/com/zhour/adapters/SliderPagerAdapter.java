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
    // ArrayList<ImageModel> image_arraylist;
    int[] slideImages;

    public SliderPagerAdapter(Activity activity,  int[] slideImages) {
        this.activity = activity;
        this.slideImages = slideImages;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.item_pager, container, false);

        //ImageModel imageModel = image_arraylist.get(position);
        ImageView iv_pager = (ImageView) view.findViewById(R.id.iv_pager);

        iv_pager.setImageResource(slideImages[position]);

        // UImageLoader.URLpicLoading(iv_pager,imageModel.getUrl(),null,R.drawable.ic_about);
       /* Picasso.with(activity.getApplicationContext())
                .load(imageModel.getUrl())     // optional
                .resize(200, 420)
                .into(iv_pager);*/
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return slideImages.length;
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
