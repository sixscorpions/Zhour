package com.zhour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.fragments.AboutUsFragment;
import com.zhour.fragments.HomeFragment;
import com.zhour.fragments.PaymentFragment;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends BaseActivity {
    private DrawerLayout drawer_layout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.menu_icon)
    ImageView menu_icon;

    @BindView(R.id.iv_home)
    ImageView iv_home;

    @BindView(R.id.tv_home)
    TextView tv_home;

    @BindView(R.id.iv_about_us)
    ImageView iv_about_us;

    @BindView(R.id.tv_about)
    TextView tv_about;
    @BindView(R.id.iv_payment)
    ImageView iv_payment;

    @BindView(R.id.tv_payment)
    TextView tv_payment;
    @BindView(R.id.iv_logout)
    ImageView iv_logout;

    @BindView(R.id.tv_logout)
    TextView tv_logout;

    @BindView(R.id.tv_username)
    TextView tv_username;

    @BindView(R.id.tv_phone)
    TextView tv_phone;


    @BindView(R.id.ll_home)
    LinearLayout ll_home;
    @BindView(R.id.ll_about_us)
    LinearLayout ll_about_us;
    @BindView(R.id.ll_payment)
    LinearLayout ll_payment;
    @BindView(R.id.ll_logout)
    LinearLayout ll_logout;

    private NavigationView navigationView;
    private View headerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        initUI();
    }

    private void initUI() {
      /*  ToolbarUtils.setHomeToolBar(this,)*/
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        initNavigationDrawer();
        ll_home.performClick();

        /*USER NAME */
        tv_username.setTypeface(Utility.setRobotoRegular(this));
        String UserName = Utility.getSharedPrefStringData(this, Constants.USER_NAME);
        tv_username.setText(UserName);


         /*USER NAME */
        tv_phone.setTypeface(Utility.setRobotoRegular(this));
        String phoneNumber = Utility.getSharedPrefStringData(this, Constants.CONTACT_NUMBER);
        tv_phone.setText(phoneNumber);


    }

    @OnClick(R.id.menu_icon)
    public void toggelMenu() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            drawer_layout.openDrawer(GravityCompat.START);
        }
    }

    @OnClick(R.id.ll_home)
    public void navigatingHome() {
        iv_home.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_home));
        tv_home.setTextColor(Utility.getColor(this, R.color.yello_text));


        iv_about_us.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_about));
        tv_about.setTextColor(Utility.getColor(this, R.color.colorWhite));

        iv_payment.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_payment));
        tv_payment.setTextColor(Utility.getColor(this, R.color.colorWhite));

        iv_logout.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_logout));
        tv_logout.setTextColor(Utility.getColor(this, R.color.colorWhite));


        drawer_layout.closeDrawer(GravityCompat.START);
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new HomeFragment(), HomeFragment.TAG, bundle, this);

    }

    @OnClick(R.id.ll_about_us)
    public void navigateAboutUs() {
        iv_about_us.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_aboutus_yello));
        tv_about.setTextColor(Utility.getColor(this, R.color.yello_text));

        iv_home.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_home_white));
        tv_home.setTextColor(Utility.getColor(this, R.color.colorWhite));

        iv_payment.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_payment));
        tv_payment.setTextColor(Utility.getColor(this, R.color.colorWhite));

        iv_logout.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_logout));
        tv_logout.setTextColor(Utility.getColor(this, R.color.colorWhite));


        drawer_layout.closeDrawer(GravityCompat.START);
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new AboutUsFragment(), AboutUsFragment.TAG, bundle, this);

    }

    @OnClick(R.id.ll_payment)
    public void navigaPayment() {
        iv_payment.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_payment_yellow));
        tv_payment.setTextColor(Utility.getColor(this, R.color.yello_text));


        iv_home.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_home_white));
        tv_home.setTextColor(Utility.getColor(this, R.color.colorWhite));

        iv_about_us.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_about));
        tv_about.setTextColor(Utility.getColor(this, R.color.colorWhite));

        iv_logout.setImageDrawable(Utility.getDrawable(this, R.drawable.ic_logout));
        tv_logout.setTextColor(Utility.getColor(this, R.color.colorWhite));


        drawer_layout.closeDrawer(GravityCompat.START);
        Bundle bundle = new Bundle();
        Utility.navigateDashBoardFragment(new PaymentFragment(), PaymentFragment.TAG, bundle, this);

    }

    @OnClick(R.id.ll_logout)
    public void navigateToLogout() {
        Intent intent = new Intent(this, SignInActivity.class);
        Utility.setSharedPrefStringData(this, Constants.TOKEN, "");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();

    }


    private void initNavigationDrawer() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        headerview = navigationView.getHeaderView(0);
        // iv_profile_pic = (CircularImageView) headerview.findViewById(R.id.iv_dp_pic);

       /* ImageView iv_home = (ImageView) findViewById(R.id.iv_home);
        CircularImageView iv_profile_pic = (CircularImageView) headerview.findViewById(R.id.iv_dp_pic);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "I am Profile Pic", Toast.LENGTH_SHORT).show();

            }
        });*/

        /*CircularImageView iv_profile_pic = (CircularImageView) findViewById(R.id.iv_dp_pic);
        iv_profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, "I am Profile Pic", Toast.LENGTH_SHORT).show();

            }
        });
*/

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
