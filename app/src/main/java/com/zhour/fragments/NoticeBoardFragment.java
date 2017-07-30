package com.zhour.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.NoticeBoardAdapter;
import com.zhour.models.NoticeBoardModel;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.ArrayList;


public class NoticeBoardFragment extends Fragment {
    public static final String TAG = NoticeBoardFragment.class.getSimpleName();
    private DashboardActivity parent;
    private View view;
    private ArrayList<NoticeBoardModel> noticeList;


    /* @BindView(R.id.lv_notice)*/
    ListView lv_notice;

    private NoticeBoardAdapter mNoticeBoardAdapter;

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
        view = inflater.inflate(R.layout.fragment_notice_board, container, false);
        // ButterKnife.bind(parent, view);
        inITUI();
        return view;
    }

    private void inITUI() {
        lv_notice = (ListView) view.findViewById(R.id.lv_notice);
        getNotifications();
        lv_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                NoticeBoardModel noticeBoardModel = (NoticeBoardModel) adapterView
                        .getAdapter().getItem(position);

                Bundle bundle = new Bundle();
                String imagePath = noticeBoardModel.getIcon();
                if (!Utility.isValueNullOrEmpty(imagePath)) {
                    bundle.putString(Constants.IMAGE_URL, imagePath);

                }
                bundle.putString(Constants.TEXT_DESC, noticeBoardModel.getDesc());
                Utility.navigateDashBoardFragment(new NoticeBoardDetailFragment(), NoticeBoardDetailFragment.TAG, bundle, parent);

            }
        });

        mNoticeBoardAdapter = new NoticeBoardAdapter(parent, noticeList);
        lv_notice.setAdapter(mNoticeBoardAdapter);


    }


    private void getNotifications() {
        noticeList = new ArrayList<>();

        NoticeBoardModel noticeBoardModel = new NoticeBoardModel();
        noticeBoardModel.setDate("26th July");
        noticeBoardModel.setDesc("Kalamindar Sale is Running Go to  near by stores");
        noticeBoardModel.setFavorite(true);
        noticeBoardModel.setIcon("https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg");

        NoticeBoardModel noticeBoardModel1 = new NoticeBoardModel();
        noticeBoardModel1.setDate("26th July");
        noticeBoardModel1.setDesc("Kalamindar Sale is Running Go to  near by stores");
        noticeBoardModel1.setFavorite(false);
        noticeBoardModel1.setIcon("https://lh3.googleusercontent.com/-s-AFpvgSeew/URquc6dF-JI/AAAAAAAAAbs/Mt3xNGRUd68/s1024/Backlit%252520Cloud.jpg");

        NoticeBoardModel noticeBoardModel2 = new NoticeBoardModel();
        noticeBoardModel2.setDate("26th July");
        noticeBoardModel2.setDesc("Kalamindar Sale is Running Go to  near by stores");
        noticeBoardModel2.setFavorite(true);
        noticeBoardModel2.setIcon("https://lh6.googleusercontent.com/-UBmLbPELvoQ/URqucCdv0kI/AAAAAAAAAbs/IdNhr2VQoQs/s1024/Apre%2525CC%252580s%252520la%252520Pluie.jpg");

        NoticeBoardModel noticeBoardModel3 = new NoticeBoardModel();
        noticeBoardModel3.setDate("26th July");
        noticeBoardModel3.setDesc("Kalamindar Sale is Running Go to  near by stores");
        noticeBoardModel3.setFavorite(false);
        noticeBoardModel3.setIcon("https://lh4.googleusercontent.com/--dq8niRp7W4/URquVgmXvgI/AAAAAAAAAbs/-gnuLQfNnBA/s1024/A%252520Song%252520of%252520Ice%252520and%252520Fire.jpg");

        NoticeBoardModel noticeBoardModel4 = new NoticeBoardModel();
        noticeBoardModel4.setDate("26th July");
        noticeBoardModel4.setDesc("Kalamindar Sale is Running Go to  near by stores");
        noticeBoardModel4.setFavorite(false);
        noticeBoardModel4.setIcon("https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg");


        noticeList.add(noticeBoardModel);
        noticeList.add(noticeBoardModel1);
        noticeList.add(noticeBoardModel2);
        noticeList.add(noticeBoardModel3);
        noticeList.add(noticeBoardModel4);

    }


}
