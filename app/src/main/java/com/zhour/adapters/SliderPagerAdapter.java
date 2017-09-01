package com.zhour.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhour.R;
import com.zhour.utils.APIConstants;
import com.zhour.utils.UImageLoader;

import java.util.ArrayList;

/**
 * Created by madhu on 23-Jul-17.
 */

public class SliderPagerAdapter extends PagerAdapter {
    private LayoutInflater layoutInflater;
    private Context mContext;
    private ArrayList<String> slideImages;

    public SliderPagerAdapter(Context mContext, ArrayList<String> slideImages) {
        this.mContext = mContext;
        this.slideImages = slideImages;
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.item_pager, container, false);
        //ImageModel imageModel = image_arraylist.get(position);
        ImageView iv_pager = (ImageView) view.findViewById(R.id.iv_pager);
        UImageLoader.URLpicLoading(iv_pager, APIConstants.HOME_URL + slideImages.get(position), null, R.drawable.logo);
       /* Picasso.with(activity.getApplicationContext())
                .load(imageModel.getUrl())     // optional
                .resize(200, 420)
                .into(iv_pager);*/
        container.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        return slideImages.size();
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
