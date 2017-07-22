package com.zhour.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.PartyInviteAdapter;
import com.zhour.models.PartyInviteModel;
import com.zhour.utils.Utility;

import java.util.ArrayList;


public class PartyAndIEventInviteFragment extends Fragment {
    public static final String TAG = "PartyAndIEventInviteFragment";
    private View view;
    private DashboardActivity parent;
    private ListView list_view;
    private ArrayList<PartyInviteModel> list;

    private TextView tv_party_invite;
    private TextView tv_event_invite;
    private ImageView iv_date;
    private EditText et_date;


    private LinearLayout ll_list_parent;
    private RelativeLayout rl_parent;


    private LinearLayout ll_phone;
    private ImageView iv_occations;

    private View  view_et_date;

    private PartyInviteAdapter partyInviteAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (DashboardActivity) getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_party_and_ievent_invite, container, false);

        inItUi();
        return view;
    }

    private void inItUi() {

        iv_date = (ImageView) view.findViewById(R.id.iv_date);
        et_date = (EditText) view.findViewById(R.id.et_date);
        list_view = (ListView) view.findViewById(R.id.list_view);
        tv_event_invite = (TextView) view.findViewById(R.id.tv_event_invite);
        tv_party_invite = (TextView) view.findViewById(R.id.tv_party_invite);
        ll_phone = (LinearLayout) view.findViewById(R.id.ll_phone);
        rl_parent = (RelativeLayout) view.findViewById(R.id.rl_parent);
        ll_list_parent = (LinearLayout) view.findViewById(R.id.ll_list_parent);

        view_et_date = view.findViewById(R.id.view_et_date);


        iv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_date.setImageDrawable(Utility.getDrawable(parent, R.drawable.ic_time_fill));

                tv_event_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_right));
                tv_event_invite.setTextColor(Utility.getColor(parent, R.color.colorPrimary));

                tv_party_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_left));
                tv_party_invite.setTextColor(Utility.getColor(parent, R.color.colorPrimary));


                rl_parent.setVisibility(View.GONE);
                ll_list_parent.setVisibility(View.VISIBLE);
                getListData();
            }
        });
        tv_event_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view_et_date.setVisibility(View.VISIBLE);
                iv_date.setImageDrawable(Utility.getDrawable(parent, R.drawable.ic_date));
                rl_parent.setVisibility(View.VISIBLE);
                ll_list_parent.setVisibility(View.GONE);
                et_date.setVisibility(View.VISIBLE);
                ll_phone.setVisibility(View.GONE);
                tv_event_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_fill_right));
                tv_event_invite.setTextColor(Utility.getColor(parent, R.color.colorWhite));

                tv_party_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_left));
                tv_party_invite.setTextColor(Utility.getColor(parent, R.color.colorPrimary));

            }
        });

        tv_party_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_et_date.setVisibility(View.GONE);
                iv_date.setImageDrawable(Utility.getDrawable(parent, R.drawable.ic_date));
                rl_parent.setVisibility(View.VISIBLE);
                ll_list_parent.setVisibility(View.GONE);
                et_date.setVisibility(View.GONE);
                ll_phone.setVisibility(View.VISIBLE);
                tv_party_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_fill_left));
                tv_party_invite.setTextColor(Utility.getColor(parent, R.color.colorWhite));

                tv_event_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_right));
                tv_event_invite.setTextColor(Utility.getColor(parent, R.color.colorPrimary));

            }
        });


    }

    private void getListData() {

        list = new ArrayList<>();


        PartyInviteModel partyInviteModel = new PartyInviteModel();
        partyInviteModel.setDesrciption("Hello every one ");
        partyInviteModel.setDate("May 23");
        partyInviteModel.setTime("7:00");

        PartyInviteModel partyInviteModel1 = new PartyInviteModel();
        partyInviteModel1.setDesrciption("Hello every one ");
        partyInviteModel1.setDate("May 23");
        partyInviteModel1.setTime("7:00");

        PartyInviteModel partyInviteModel2 = new PartyInviteModel();
        partyInviteModel2.setDesrciption("Hello every one ");
        partyInviteModel2.setDate("May 23");
        partyInviteModel2.setTime("7:00");

        PartyInviteModel partyInviteModel3 = new PartyInviteModel();
        partyInviteModel3.setDesrciption("Hello every one ");
        partyInviteModel3.setDate("May 23");
        partyInviteModel3.setTime("7:00");


        PartyInviteModel partyInviteModel4 = new PartyInviteModel();
        partyInviteModel4.setDesrciption("Hello every one ");
        partyInviteModel4.setDate("May 23");
        partyInviteModel4.setTime("7:00");

        list.add(partyInviteModel);
        list.add(partyInviteModel1);
        list.add(partyInviteModel2);
        list.add(partyInviteModel3);
        list.add(partyInviteModel4);

        partyInviteAdapter = new PartyInviteAdapter(list, parent);
        list_view.setAdapter(partyInviteAdapter);
    }


}
