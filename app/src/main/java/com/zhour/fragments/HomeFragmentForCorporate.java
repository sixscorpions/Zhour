package com.zhour.fragments;


import android.content.Intent;
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
import com.zhour.activities.ChooseCommunityActivity;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.SliderPagerAdapter;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.models.AuthenticateUserModel;
import com.zhour.models.BannerModel;
import com.zhour.models.CommunityUserModel;
import com.zhour.models.LogoutModel;
import com.zhour.models.Model;
import com.zhour.parser.AuthenticateUserParser;
import com.zhour.parser.BannerInfoParser;
import com.zhour.parser.LogoutParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HomeFragmentForCorporate extends Fragment implements IAsyncCaller {
    public static final String TAG = HomeFragmentForCorporate.class.getSimpleName();
    private DashboardActivity mParent;
    private View view;


    @BindView(R.id.tv_invite)
    TextView tv_invite;

    @BindView(R.id.tv_notice)
    TextView tv_notice;

    @BindView(R.id.tv_car)
    TextView tv_car;
    @BindView(R.id.tv_emergency)
    TextView tv_emergency;
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.iv_invite)
    ImageView iv_invite;
    @BindView(R.id.iv_notice)
    ImageView iv_notice;

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

    private ViewPager vp_slider;
    private LinearLayout ll_dots;
    private SliderPagerAdapter sliderPagerAdapter;
    private TextView[] dots;
    int page_position = 0;
    private BannerModel mBannerModel;
    private LogoutModel logoutModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;

        view = inflater.inflate(R.layout.fragment_home_corporate, container, false);
        ButterKnife.bind(this, view);
        inItUI();
        setTypeFace();
        return view;
    }

    private void setTypeFace() {
        getBannerInfo();
    }

    private void inItUI() {
        vp_slider = (ViewPager) view.findViewById(R.id.view_pager);
        ll_dots = (LinearLayout) view.findViewById(R.id.ll_dots);

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

    private void addBottomDots(int currentPage) {
        dots = new TextView[mBannerModel.getBannerUrls().size()];

        ll_dots.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(mParent);
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
        Utility.navigateDashBoardFragment(new NoticeBoardFragment(), NoticeBoardFragment.TAG, bundle, mParent);

    }

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof BannerModel) {
                mBannerModel = (BannerModel) model;
                if (!mBannerModel.isError()) {
                    setBannersData();
                } else if (mBannerModel.isError() && mBannerModel.getMessage().equalsIgnoreCase("Please login again!")) {
                    navigateToLogout();
                }
            } else if (model instanceof LogoutModel) {
                logoutModel = (LogoutModel) model;
                logout();
            } else if (model instanceof AuthenticateUserModel) {
                AuthenticateUserModel authenticateUserModel = (AuthenticateUserModel) model;

                Utility.setSharedPrefStringData(mParent, Constants.USER_NAME, authenticateUserModel.getUsername());
                Utility.setSharedPrefStringData(mParent, Constants.CONTACT_NUMBER, authenticateUserModel.getContactnumber());

                if (!authenticateUserModel.isError()) {
                    if (authenticateUserModel != null && authenticateUserModel.getCommunitiesList().size() > 1) {

                        ArrayList<CommunityUserModel> userList = authenticateUserModel.getCommunitiesList();
                        Intent intent = new Intent(mParent, ChooseCommunityActivity.class);
                        intent.putExtra(Constants.COMMUNITY_LIST, userList);
                        intent.putExtra(Constants.TOKEN, authenticateUserModel.getToken());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    } else {
                        Utility.setSharedPrefStringData(mParent, Constants.USER_ID, authenticateUserModel.getUserid());
                        Utility.setSharedPrefStringData(mParent, Constants.SESSION_ID, authenticateUserModel.getSesid());
                        Utility.setSharedPrefStringData(mParent, Constants.TOKEN, authenticateUserModel.getToken());

                        Utility.setSharedPrefStringData(mParent, Constants.ROLE_ID, authenticateUserModel.getCommunitiesList().get(0).getRoleid());
                        Utility.setSharedPrefStringData(mParent, Constants.ROLE_NAME, authenticateUserModel.getCommunitiesList().get(0).getRolename());
                        Utility.setSharedPrefStringData(mParent, Constants.COMMUNITY_ID, authenticateUserModel.getCommunitiesList().get(0).getCommunityid());
                        Utility.setSharedPrefStringData(mParent, Constants.COMMUNITY_NAME, authenticateUserModel.getCommunitiesList().get(0).getCommunityname());
                        Utility.setSharedPrefStringData(mParent, Constants.RESIDENT_ID, authenticateUserModel.getCommunitiesList().get(0).getResidentid());


                        Intent intent = new Intent(mParent, DashboardActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        mParent.finish();
                    }
                }
            }
        }
    }

    public void navigateToLogout() {

        String userID = Utility.getSharedPrefStringData(mParent, Constants.USER_ID);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("userid", userID);

        LogoutParser logoutParser = new LogoutParser();
        ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                APIConstants.LOGOUT, linkedHashMap,
                APIConstants.REQUEST_TYPE.POST, this, logoutParser);
        Utility.execute(serverJSONAsyncTask);

    }


    private void logout() {
        /*Intent intent = new Intent(mParent, SignInActivity.class);
        Utility.setSharedPrefStringData(mParent, Constants.TOKEN, "");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        mParent.finish();*/
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            JSONArray jsonArray = new JSONArray();
            linkedHashMap.put("contactnumber", Utility.getSharedPrefStringData(mParent, Constants.LOGIN_CONTACT_NUMBER));
            linkedHashMap.put("pwd", Utility.getSharedPrefStringData(mParent, Constants.LOGIN_PASS_WORD));
            AuthenticateUserParser authenticateUserParser = new AuthenticateUserParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.AUTHENTICATE_USER, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, authenticateUserParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to set the data of the banners
     */
    private void setBannersData() {
        if (mBannerModel != null && mBannerModel.getBannerUrls().size() > 0) {
            sliderPagerAdapter = new SliderPagerAdapter(mParent, mBannerModel.getBannerUrls());
            vp_slider.setAdapter(sliderPagerAdapter);
            vp_slider.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    //addBottomDots(position);

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            // addBottomDots(0);
            final Handler handler = new Handler();

            final Runnable update = new Runnable() {
                public void run() {
                    if (page_position == mBannerModel.getBannerUrls().size()) {
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
    }

    private void getBannerInfo() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            BannerInfoParser bannerInfoParser = new BannerInfoParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_BANNER_INFO, linkedHashMap,
                    APIConstants.REQUEST_TYPE.GET, this, bannerInfoParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
