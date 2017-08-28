package com.zhour.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.SliderPagerAdapter;
import com.zhour.models.ImageModel;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

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

    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    private SliderPagerAdapter sliderPagerAdapter;
    ArrayList<ImageModel> slider_image_list;
    private TextView[] dots;
    int page_position = 0;


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
        ButterKnife.bind(this, view);
        inItUI();
        setTypeFace();
        return view;
    }

    private void setTypeFace() {
    }

    private void inItUI() {

        vp_slider = (ViewPager) view.findViewById(R.id.view_pager);
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);

        slider_image_list = new ArrayList<>();

        ImageModel imageModel = new ImageModel();
        imageModel.setUrl(R.drawable.zhour_add_one);
        ImageModel imageModel1 = new ImageModel();
        imageModel1.setUrl(R.drawable.zhour_add_two);
        ImageModel imageModel2 = new ImageModel();
        imageModel2.setUrl(R.drawable.zhour_add_three);
        ImageModel imageModel3 = new ImageModel();
        imageModel3.setUrl(R.drawable.zhour_add_four);
        slider_image_list.add(imageModel);
        slider_image_list.add(imageModel1);
        slider_image_list.add(imageModel2);
        slider_image_list.add(imageModel3);


        sliderPagerAdapter = new SliderPagerAdapter(parent, slider_image_list);
        vp_slider.setAdapter(sliderPagerAdapter);

        vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDots(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        addBottomDots(0);
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {
                if (page_position == slider_image_list.size()) {
                    page_position = 0;
                } else {
                    page_position = page_position + 1;
                }
                vp_slider.setCurrentItem(page_position, true);
            }
        };

        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 100, 3000);

    }

    @OnClick(R.id.ll_invite)
    public void inviteFragment() {
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.IS_PARTY_INVITE, true);
        Utility.navigateDashBoardFragment(new PartyAndIEventInviteFragment(), PartyAndIEventInviteFragment.TAG, bundle, getActivity());

    }

    /*EMERGENCY */

    @OnClick(R.id.ll_emergency)
    public void emergencyFragment() {
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new EmergencyFragment(), EmergencyFragment.TAG, bundle, getActivity());

    }
    /*ALIEN CAR*/

    @OnClick(R.id.ll_car)
    public void alienFragment() {
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new AlienCarFragment(), AlienCarFragment.TAG, bundle, getActivity());

    }
    /*COMPLAINTS*/

    @OnClick(R.id.ll_complaints)
    public void complaintsFragment() {
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new ComplaintsFragment(), ComplaintsFragment.TAG, bundle, getActivity());

    }
    /*MAID STATUS*/

    @OnClick(R.id.ll_maid)
    public void maidFragment() {
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new MaidStatusFragment(), MaidStatusFragment.TAG, bundle, getActivity());

    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[slider_image_list.size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(parent);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(Color.parseColor("#757575"));
            ll_dots.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(Color.parseColor("#00796B"));

    }

    /*NOTICE BOARD */
    @OnClick(R.id.ll_notice)
    public void gotoNoticeBoard() {
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new NoticeBoardFragment(), NoticeBoardFragment.TAG, bundle, parent);

    }

}
