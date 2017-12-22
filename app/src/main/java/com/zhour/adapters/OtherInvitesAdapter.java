package com.zhour.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.fragments.NoticeBoardFragment;
import com.zhour.models.Contact;
import com.zhour.models.Model;
import com.zhour.models.NoticeBoardModel;
import com.zhour.models.OtherInvitesModel;
import com.zhour.models.PartyInviteSuccessModel;
import com.zhour.parser.PartyInviteSuccessParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.UImageLoader;
import com.zhour.utils.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * Created by madhu on 29-Jul-17.
 */

public class OtherInvitesAdapter extends BaseAdapter implements IAsyncCaller {
    private DashboardActivity parent;
    private ArrayList<OtherInvitesModel> otherInvitesModels;
    private LayoutInflater layoutInflater;
    private AlertDialog alertDialog;

    public OtherInvitesAdapter(DashboardActivity parent, ArrayList<OtherInvitesModel> otherInvitesModels) {
        this.parent = parent;
        this.otherInvitesModels = otherInvitesModels;
        layoutInflater = (LayoutInflater) parent.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return otherInvitesModels.size();
    }

    @Override
    public Object getItem(int position) {
        return otherInvitesModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item, viewGroup, false);
            holder = new ViewHolder();
            holder.ll_root_layout = (LinearLayout) convertView.findViewById(R.id.ll_root_layout);
            holder.iv_invite = (ImageView) convertView.findViewById(R.id.iv_invite);
            holder.tv_invite = (TextView) convertView.findViewById(R.id.tv_invite);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OtherInvitesModel otherInvitesModel = otherInvitesModels.get(position);
        holder.tv_invite.setText(otherInvitesModel.getInvitedisplayname());
        UImageLoader.URLpicLoading(holder.iv_invite, APIConstants.HOME_URL + otherInvitesModel.getMobicon(), null, R.drawable.logo);

        holder.ll_root_layout.setId(position);
        holder.ll_root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = v.getId();
                getDialog(otherInvitesModels.get(pos));
            }
        });
        return convertView;
    }

    @Override
    public void onComplete(Model model) {
        if (model instanceof PartyInviteSuccessModel) {
            PartyInviteSuccessModel partyInviteSuccessModel = (PartyInviteSuccessModel) model;
            if (!partyInviteSuccessModel.isError()) {
                Utility.showToastMessage(parent, Utility.getResourcesString(parent, R.string.invitation_sent_successfully));
                alertDialog.dismiss();
            }
        }
    }

    public void getDialog(final OtherInvitesModel otherInvitesModel) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(parent);
        LayoutInflater inflater = parent.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_other_invite, null);

        ImageView iv_car = (ImageView) dialogView.findViewById(R.id.iv_car);
        Button btn_confirm = (Button) dialogView.findViewById(R.id.btn_confirm);

        TextView tv_please_sub = (TextView) dialogView.findViewById(R.id.tv_please_sub);
        tv_please_sub.setTypeface(Utility.setRobotoRegular(parent));

        TextView tv_cab_entry = (TextView) dialogView.findViewById(R.id.tv_cab_entry);
        tv_cab_entry.setTypeface(Utility.setRobotoRegular(parent));
        tv_cab_entry.setText(otherInvitesModel.getInvitename());

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInvite(otherInvitesModel);
            }
        });
        Button btn_cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        UImageLoader.URLpicLoading(iv_car, APIConstants.HOME_URL + otherInvitesModel.getMobicon(), null, R.drawable.logo);
        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.show();

    }


    String formattedDate;
    private Date formatedTime;
    String formatedTimeString;

    private void saveInvite(OtherInvitesModel otherInvitesModel) {

        Calendar c = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        formattedDate = df.format(c.getTime());
        formatedTime = Calendar.getInstance().getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dfForTime = new SimpleDateFormat("HH:mm");
        formatedTimeString = dfForTime.format(Calendar.getInstance().getTime());

        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();

            linkedHashMap.put("invitetypeid", otherInvitesModel.getInviteid());
            linkedHashMap.put("eventdate", formattedDate);
            linkedHashMap.put("eventtime", formatedTimeString);
            linkedHashMap.put("invitenote", otherInvitesModel.getInvitedesc());
            linkedHashMap.put("venue", Utility.getSharedPrefStringData(parent, Constants.APARTMENT_NAME) + ", " + "Flat no :"
                    + Utility.getSharedPrefStringData(parent, Constants.APARTMENT_FLAT_NO));
            linkedHashMap.put("communityid", Utility.getSharedPrefStringData(parent, Constants.COMMUNITY_ID));
            linkedHashMap.put("residentid", Utility.getSharedPrefStringData(parent, Constants.RESIDENT_ID));
            linkedHashMap.put("contacts", "");
            PartyInviteSuccessParser partyInviteSuccessParser = new PartyInviteSuccessParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    parent, Utility.getResourcesString(parent, R.string.please_wait), true,
                    APIConstants.SAVE_INVITE, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, partyInviteSuccessParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ViewHolder {
        private LinearLayout ll_root_layout;
        private ImageView iv_invite;
        private TextView tv_invite;

    }
}
