package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.utils.Constants;
import com.zhour.utils.UImageLoader;
import com.zhour.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeBoardDetailFragment extends Fragment {

    public static final String TAG = NoticeBoardDetailFragment.class.getSimpleName();
    private DashboardActivity parent;
    private View view;


    @BindView(R.id.iv_notice)
    ImageView iv_notice;

    @BindView(R.id.tv_description)
    TextView tv_description;

    private String imageUrl;
    private String description;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (DashboardActivity) getActivity();


        imageUrl = getArguments().getString(Constants.IMAGE_URL);
        description = getArguments().getString(Constants.TEXT_DESC);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_notice_board_detail, container, false);
        ButterKnife.bind(this, view);
        inITUI();
        return view;
    }

    private void inITUI() {

        tv_description.setTypeface(Utility.setRobotoRegular(parent));

        UImageLoader.URLpicLoading(iv_notice, imageUrl, null, R.drawable.ic_home_sample);
        tv_description.setText(description);


    }

}
