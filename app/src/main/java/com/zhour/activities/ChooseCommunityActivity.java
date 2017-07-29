package com.zhour.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.zhour.R;
import com.zhour.adapters.CommunitiesAdapter;
import com.zhour.models.CommunityUserModel;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChooseCommunityActivity extends BaseActivity {


    @BindView(R.id.iv_tool_icon)
    ImageView iv_tool_icon;

    /* @BindView(R.id.lv_communities)*/
    ListView lv_communities;

    private CommunitiesAdapter mAdapter;
    private String token;

    private ArrayList<CommunityUserModel> commnitiesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_community);
        ButterKnife.bind(this);
        commnitiesList = (ArrayList<CommunityUserModel>) getIntent().getSerializableExtra(Constants.COMMUNITY_LIST);
        token = getIntent().getStringExtra(Constants.TOKEN);
        initUi();
    }

    private void initUi() {
        lv_communities = (ListView) findViewById(R.id.lv_communities);
        getCommunitiesData();
    }

    private void getCommunitiesData() {
        mAdapter = new CommunitiesAdapter(this, commnitiesList);
        lv_communities.setAdapter(mAdapter);

        lv_communities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                CommunityUserModel communityUserModel = (CommunityUserModel)
                        adapterView.getAdapter().getItem(position);

                Utility.setSharedPrefStringData(getApplicationContext(), Constants.TOKEN, token);
                Utility.setSharedPrefStringData(getApplicationContext(), Constants.USER_ID, communityUserModel.getUserid());
                Utility.setSharedPrefStringData(getApplicationContext(), Constants.ROLE_ID, communityUserModel.getRoleid());
                Utility.setSharedPrefStringData(getApplicationContext(), Constants.ROLE_NAME, communityUserModel.getRolename());
                Utility.setSharedPrefStringData(getApplicationContext(), Constants.COMMUNITY_ID, communityUserModel.getCommunityid());
                Utility.setSharedPrefStringData(getApplicationContext(), Constants.COMMUNITY_NAME, communityUserModel.getCommunityname());
                Utility.setSharedPrefStringData(getApplicationContext(), Constants.RESIDENT_ID, communityUserModel.getResidentid());

                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();


            }
        });
    }

    public void navigateToDashBoard() {


    }
/*
    @OnItemClick(R.id.lv_communities)
    public void navigateToDashBoard() {


    }*/
}
