package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private DashboardActivity parent;
    private View view;


    @BindView(R.id.tv_invite)
    TextView tv_invite;

    @BindView(R.id.tv_notice)
    TextView tv_notice;

    @BindView(R.id.tv_car)
    TextView tv_car;
    @BindView(R.id.tv_maid)
    TextView tv_maid;
    @BindView(R.id.tv_complaints)
    TextView tv_complaints;
    @BindView(R.id.tv_emergency)
    TextView tv_emergency;
    @BindView(R.id.iv_maid)
    ImageView iv_maid;
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.iv_invite)
    ImageView iv_invite;
    @BindView(R.id.iv_notice)
    ImageView iv_notice;
    @BindView(R.id.iv_message)
    ImageView iv_message;
    @BindView(R.id.iv_smile)
    ImageView iv_smile;

    @BindView(R.id.iv_emergency)
    ImageView iv_emergency;

    @BindView(R.id.ll_invite)
    LinearLayout ll_invite;

    @BindView(R.id.ll_emergency)
    LinearLayout ll_emergency;
    @BindView(R.id.ll_notice)
    LinearLayout ll_notice;
    @BindView(R.id.ll_car)
    LinearLayout ll_car;

    @BindView(R.id.ll_maid)
    LinearLayout ll_maid;


    @BindView(R.id.ll_complaints)
    LinearLayout ll_complaints;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (DashboardActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;

        view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this,view);
        inItUI();
        setTypeFace();
        return view;
    }

    private void setTypeFace() {
    }

    private void inItUI() {

    }

    @OnClick(R.id.ll_invite)
    public void inviteFragment() {
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new PartyAndIEventInviteFragment(), PartyAndIEventInviteFragment.TAG, bundle, getActivity());

    }

    @OnClick(R.id.ll_maid)
    public void maidFragment() {
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new MaidStatusFragment(), MaidStatusFragment.TAG, bundle, getActivity());

    }

}
