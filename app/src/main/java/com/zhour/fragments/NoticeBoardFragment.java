package com.zhour.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.NoticeBoardAdapter;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.models.Model;
import com.zhour.models.NoticeBoardListModel;
import com.zhour.models.NoticeBoardModel;
import com.zhour.parser.NoticeBoardParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;


public class NoticeBoardFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = NoticeBoardFragment.class.getSimpleName();
    private DashboardActivity mParent;
    private View view;

    @BindView(R.id.lv_notice)
    ListView lv_notice;
    @BindView(R.id.tv_currently_blank)
    TextView tv_currently_blank;

    private NoticeBoardListModel mNoticeBoardListModel;
    private NoticeBoardAdapter mNoticeBoardAdapter;
    public static ArrayList<String> readIds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        readIds = new ArrayList();
        String mSharedPrefreanceString = Utility.getSharedPrefStringData(this.mParent, Constants.NOTIFICATION_READ_IDS);
        if (mSharedPrefreanceString != null) {
            String[] idsAfterSplit = mSharedPrefreanceString.split(",");
            for (String add : idsAfterSplit) {
                readIds.add(add);
            }
        }
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_notice_board, container, false);
        ButterKnife.bind(this, view);
        initUi();
        return view;
    }

    private void initUi() {
        getNotifications();
        setAlreadyOpenData();
    }

    private void setAlreadyOpenData() {

    }

    /**
     * Get Notice board data
     */
    private void getNotifications() {
        String communityID = Utility.getSharedPrefStringData(mParent, Constants.COMMUNITY_ID);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("communityid", communityID);

        NoticeBoardParser noticeBoardParser = new NoticeBoardParser();
        ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                APIConstants.GET_NOTICES, linkedHashMap,
                APIConstants.REQUEST_TYPE.POST, this, noticeBoardParser);
        Utility.execute(serverJSONAsyncTask);
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof NoticeBoardListModel) {
                mNoticeBoardListModel = (NoticeBoardListModel) model;
                if (!mNoticeBoardListModel.isError()) {
                    setDataToLayout();
                }
            }
        }
    }

    /**
     * This method is used to set Notice board data
     */
    private void setDataToLayout() {
        if (mNoticeBoardListModel != null && mNoticeBoardListModel.getNoticeBoardModels() != null && mNoticeBoardListModel.getNoticeBoardModels().size() > 0) {
            mNoticeBoardAdapter = new NoticeBoardAdapter(mParent, mNoticeBoardListModel.getNoticeBoardModels());
            lv_notice.setAdapter(mNoticeBoardAdapter);
            tv_currently_blank.setVisibility(View.GONE);
            lv_notice.setVisibility(View.VISIBLE);
        } else {
            tv_currently_blank.setVisibility(View.VISIBLE);
            lv_notice.setVisibility(View.GONE);
        }
    }

    @OnItemClick(R.id.lv_notice)
    void onItemClick(int position) {
        NoticeBoardModel noticeBoardModel = mNoticeBoardListModel.getNoticeBoardModels().get(position);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.TEXT_DESC, noticeBoardModel.getNoticedesc());
        bundle.putString(Constants.NOTIFICATION_ID, noticeBoardModel.getNoticeid());
        Utility.navigateDashBoardFragment(new NoticeBoardDetailFragment(), NoticeBoardDetailFragment.TAG, bundle, mParent);
    }
}
