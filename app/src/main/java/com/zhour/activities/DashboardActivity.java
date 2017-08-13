package com.zhour.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.zhour.R;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.fragments.AboutUsFragment;
import com.zhour.fragments.HomeFragment;
import com.zhour.fragments.PaymentFragment;
import com.zhour.models.LogoutModel;
import com.zhour.models.Model;
import com.zhour.parser.LogoutParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DashboardActivity extends BaseActivity implements IAsyncCaller {
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
    private LogoutModel logoutModel;
    private TextRecognizer detector;
    Uri imageUri;

    public String vehicleNumberText;

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

        String userID = Utility.getSharedPrefStringData(this, Constants.USER_ID);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("userid", userID);

        LogoutParser logoutParser = new LogoutParser();
        ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                this, Utility.getResourcesString(this, R.string.please_wait), true,
                APIConstants.LOGOUT, linkedHashMap,
                APIConstants.REQUEST_TYPE.POST, this, logoutParser);
        Utility.execute(serverJSONAsyncTask);

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

    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LogoutModel) {
                logoutModel = (LogoutModel) model;
                /*if (!logoutModel.isError()) {*/
                logout();
                // }
            }
        }
    }

    private void logout() {
        Intent intent = new Intent(this, SignInActivity.class);
        Utility.setSharedPrefStringData(this, Constants.TOKEN, "");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.PHOTO_REQUEST && resultCode == RESULT_OK) {
            launchMediaScanIntent();
            try {
                Bitmap bitmap = decodeBitmapUri(this, imageUri);
                if (detector.isOperational() && bitmap != null) {
                    Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                    SparseArray<TextBlock> textBlocks = detector.detect(frame);
                    String blocks = "";
                    String lines = "";
                    String words = "";
                    for (int index = 0; index < textBlocks.size(); index++) {
                        //extract scanned text blocks here
                        TextBlock tBlock = textBlocks.valueAt(index);
                        blocks = blocks + tBlock.getValue() + "\n" + "\n";
                        for (Text line : tBlock.getComponents()) {
                            //extract scanned text lines here
                            lines = lines + line.getValue() + "\n";
                            for (Text element : line.getComponents()) {
                                //extract scanned text words here
                                words = words + element.getValue() + ", ";
                            }
                        }
                    }
                    if (textBlocks.size() == 0) {
                        vehicleNumberText = "Scan Failed: Found nothing to scan";

                    } else {
                        /*tvScanText.setText(tvScanText.getText() + blocks + "\n");*/

                        vehicleNumberText = blocks + "\n";
                       /* vehicleNumberText = "---------" + "\n";
                        vehicleNumberText = "Lines: " + "\n";
                        vehicleNumberText = lines + "\n";
                        vehicleNumberText = "---------" + "\n";
                        vehicleNumberText = "Words: " + "\n";
                        vehicleNumberText = words + "\n";
                        vehicleNumberText = "---------" + "\n";*/

                    }
                } else {
                    vehicleNumberText = "Could not set up the detector!";
                }
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT)
                        .show();
                Log.e("Tag", e.toString());
            }
        }
    }
      /*DECODE BIMAP URI*/

    private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
        int targetW = 600;
        int targetH = 600;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        return BitmapFactory.decodeStream(ctx.getContentResolver()
                .openInputStream(uri), null, bmOptions);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (imageUri != null) {
            outState.putString(Constants.SAVED_INSTANCE_URI, imageUri.toString());
            outState.putString(Constants.SAVED_INSTANCE_RESULT,vehicleNumberText);
        }
        super.onSaveInstanceState(outState);
    }

    private void launchMediaScanIntent() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        mediaScanIntent.setData(imageUri);
        this.sendBroadcast(mediaScanIntent);
    }

}
