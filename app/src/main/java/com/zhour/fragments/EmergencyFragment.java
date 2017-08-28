package com.zhour.fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


public class EmergencyFragment extends Fragment {

    @BindView(R.id.img_emergency)
    ImageView img_emergency;

    @BindView(R.id.content)
    com.skyfishjy.library.RippleBackground rippleBackground;

    public static final String TAG = EmergencyFragment.class.getSimpleName();
    private DashboardActivity mParent;
    private View view;
    private boolean isEmergency = false;

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
        view = inflater.inflate(R.layout.fragment_emergency, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        isEmergency = true;
        final Handler handler = new Handler();
        img_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isEmergency) {
                    rippleBackground.startRippleAnimation();
                    isEmergency = false;
                } else {
                    rippleBackground.stopRippleAnimation();
                    isEmergency = true;
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        callEmergency();
                    }
                }, 3000);
            }
        });
    }

    /**
     * This method is used to call emergency
     */
    private void callEmergency() {

    }

}
