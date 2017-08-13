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
import com.zhour.utils.Utility;


public class PaymentFragment extends Fragment {
    public static final String TAG = "PaymentFragment";
    private DashboardActivity parent;
    private View view;
    private TextView tv_sad;
    private ImageView iv_sad;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (DashboardActivity) getActivity();
       /* ButterKnife.bind(parent,view);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_payment, container, false);
        inItUI();
        return view;

    }

    private void inItUI() {
        iv_sad = (ImageView) view.findViewById(R.id.iv_sad);
        tv_sad = (TextView) view.findViewById(R.id.tv_sad);
        tv_sad.setTypeface(Utility.setRobotoRegular(parent));
        tv_sad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Utility.showOKOnlyDialog(parent, Utility.getResourcesString(parent, R.string.unauthorized_access),
                        Utility.getResourcesString(parent, R.string.app_name));*/
            }
        });
    }

}
