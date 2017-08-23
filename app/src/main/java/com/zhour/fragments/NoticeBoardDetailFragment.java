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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoticeBoardDetailFragment extends Fragment {

    public static final String TAG = NoticeBoardDetailFragment.class.getSimpleName();
    private DashboardActivity mParent;
    private View view;


    @BindView(R.id.iv_notice)
    ImageView iv_notice;

    @BindView(R.id.tv_description)
    TextView tv_description;

    private String imageUrl;
    private String description;
    private String mNotificationId;
    private ArrayList<String> readIds;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();


        imageUrl = getArguments().getString(Constants.IMAGE_URL);
        description = getArguments().getString(Constants.TEXT_DESC);
        mNotificationId = getArguments().getString(Constants.NOTIFICATION_ID);

        readIds = new ArrayList();
        String mSharedPrefreanceString = Utility.getSharedPrefStringData(mParent, Constants.NOTIFICATION_READ_IDS);
        if (mSharedPrefreanceString != null) {
            String[] idsAfterSplit = mSharedPrefreanceString.split(",");
            for (String add : idsAfterSplit) {
                readIds.add(add);
            }
        }
        if (readIds != null && readIds.contains(mNotificationId)) {
            return;
        }
        if (readIds == null || readIds.size() <= 0 || readIds.contains(mNotificationId)) {
            Utility.setSharedPrefStringData(mParent, Constants.NOTIFICATION_READ_IDS, mNotificationId);
            return;
        }
        Utility.setSharedPrefStringData(mParent, Constants.NOTIFICATION_READ_IDS, mSharedPrefreanceString + "," + mNotificationId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_notice_board_detail, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        tv_description.setTypeface(Utility.setRobotoRegular(mParent));
        UImageLoader.URLpicLoading(iv_notice, imageUrl, null, R.drawable.ic_home_sample);
        tv_description.setText(description);


    }

}
