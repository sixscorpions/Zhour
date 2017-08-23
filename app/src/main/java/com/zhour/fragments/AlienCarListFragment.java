package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.AlienCarListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AlienCarListFragment extends Fragment {
    public static final String TAG = AlienCarListFragment.class.getSimpleName();
    private DashboardActivity mParent;
    private View view;

    private AlienCarListAdapter alienCarListAdapter;
    @BindView(R.id.list_alien_car)
    ListView list_alien_car;

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
        view = inflater.inflate(R.layout.fragment_alien_car_list, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        alienCarListAdapter = new AlienCarListAdapter(mParent, AlienCarFragment.mAlienCarListModel.getAlienCarModels());
        list_alien_car.setAdapter(alienCarListAdapter);
    }

}