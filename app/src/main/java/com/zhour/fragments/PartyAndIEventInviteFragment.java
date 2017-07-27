package com.zhour.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.ContactsAdapter;
import com.zhour.adapters.PartyInviteAdapter;
import com.zhour.adapters.SpinnerAdapter;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctask.ServerJSONAsyncTask;
import com.zhour.models.AuthenticateUserModel;
import com.zhour.models.Contact;
import com.zhour.models.LookUpEventsTypeModel;
import com.zhour.models.Model;
import com.zhour.models.PartyInviteModel;
import com.zhour.models.SpinnerModel;
import com.zhour.parser.AuthenticateUserParser;
import com.zhour.parser.LookUpEventTypeParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PartyAndIEventInviteFragment extends Fragment implements IAsyncCaller {
    public static final String TAG = PartyAndIEventInviteFragment.class.getSimpleName();
    private View view;
    private DashboardActivity parent;

    @BindView(R.id.list_view)
    ListView list_view;

    @BindView(R.id.tv_party_invite)
    TextView tv_party_invite;

    @BindView(R.id.tv_event_invite)
    TextView tv_event_invite;

    @BindView(R.id.iv_date)
    ImageView iv_date;

    @BindView(R.id.iv_phone_book)
    ImageView iv_phone_book;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.et_date)
    EditText et_date;

    @BindView(R.id.et_party_time)
    EditText et_party_time;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.ll_code_phone)
    LinearLayout ll_code_phone;

    private ArrayList<PartyInviteModel> list;

    @BindView(R.id.ll_list_parent)
    LinearLayout ll_list_parent;

    @BindView(R.id.rl_parent)
    RelativeLayout rl_parent;
    @BindView(R.id.ll_party_invite)
    LinearLayout ll_party_invite;
    @BindView(R.id.iv_occations)
    ImageView iv_occations;

    @BindView(R.id.view_et_date)
    View view_et_date;


    @BindView(R.id.et_party_date)
    EditText et_party_date;

    @BindView(R.id.et_invite_types)
    EditText et_invite_types;

    public static ArrayList<String> contactsListModel = new ArrayList<>();

    private PartyInviteAdapter partyInviteAdapter;
    private Cursor mCursor;
    private Set<Contact> result;
    private LookUpEventsTypeModel lookUpEventsTypeModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parent = (DashboardActivity) getActivity();
        Utility.setTranslateStatusBar(parent);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null)
            return view;
        view = inflater.inflate(R.layout.fragment_party_and_ievent_invite, container, false);
        ButterKnife.bind(this, view);
        inItUI();
        return view;
    }

    private void inItUI() {
        askPermission();
        getInviteTypes();
    }

    private void getInviteTypes() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("entityname", "Event%20Types");
            LookUpEventTypeParser lookUpEventTypeParser = new LookUpEventTypeParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    parent, Utility.getResourcesString(parent, R.string.please_wait), true,
                    APIConstants.GET_LOOKUP_DATA_BY_ENTITY_NAME, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, lookUpEventTypeParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(parent,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(parent,
                    Manifest.permission.READ_CONTACTS)) {

            } else {

                ActivityCompat.requestPermissions(parent,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        Constants.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
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

    @OnClick(R.id.iv_date)
    public void eventsAndParty() {
        iv_date.setImageDrawable(Utility.getDrawable(parent, R.drawable.ic_time_fill));

        tv_event_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_right));
        tv_event_invite.setTextColor(Utility.getColor(parent, R.color.colorPrimary));

        tv_party_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_left));
        tv_party_invite.setTextColor(Utility.getColor(parent, R.color.colorPrimary));


        rl_parent.setVisibility(View.GONE);
        ll_list_parent.setVisibility(View.VISIBLE);
        btn_submit.setVisibility(View.GONE);
        getListData();
    }

    @OnClick(R.id.iv_phone_book)
    public void phoneBook() {

        final Dialog dialog = new Dialog(parent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_contacts);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(true); // can dismiss alert screen when user click back buttonon
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        EditText et_search = (EditText) dialog.findViewById(R.id.et_search);
        TextView tv_et_search_image = (TextView) dialog.findViewById(R.id.tv_et_search_image);
        ListView ll_contacts = (ListView) dialog.findViewById(R.id.ll_contacts);
        TextView tv_pick = (TextView) dialog.findViewById(R.id.tv_pick);

        tv_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contactsListModel.size() > 0) {
                    for (String mNum : contactsListModel) {
                        et_phone.setText("");
                        et_phone.setText(mNum);
                    }
                }
                //Utility.showToastMessage(getActivity(), "SELECTED CONTACTS" + contactsListModel.size());
                contactsListModel.clear();
                dialog.dismiss();
            }
        });
        tv_et_search_image.setTypeface(Utility.getMaterialIconsRegular(parent));

        mCursor = parent.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        Set<Contact> contacts = getContacts();
        ArrayList<Contact> newList = new ArrayList<>(new HashSet<>(contacts));

        Collections.sort(newList, new Comparator<Contact>() {
            @Override
            public int compare(Contact lhs, Contact rhs) {
                char lhsFirstLetter = TextUtils.isEmpty(lhs.displayName) ? ' ' : lhs.displayName.charAt(0);
                char rhsFirstLetter = TextUtils.isEmpty(rhs.displayName) ? ' ' : rhs.displayName.charAt(0);
                int firstLetterComparison = Character.toUpperCase(lhsFirstLetter) - Character.toUpperCase(rhsFirstLetter);
                if (firstLetterComparison == 0)
                    return lhs.displayName.compareTo(rhs.displayName);
                return firstLetterComparison;
            }
        });

        final ContactsAdapter mAdapter = new ContactsAdapter(parent, newList, TAG);
        ll_contacts.setAdapter(mAdapter);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.show();


    }

    private boolean checkContactsReadPermission() {
        String permission = "android.permission.READ_CONTACTS";
        int res = parent.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private Set<Contact> getContacts() {
        if (checkContactsReadPermission()) {
            result = new TreeSet<>();
            if (mCursor != null) {
                Log.e("count", "" + mCursor.getCount());
                while (mCursor.moveToNext()) {
                    String name = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String image_uri = mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                    if (!name.equals(phoneNumber)) {
                        Contact contact = new Contact(name, phoneNumber, image_uri);
                        contact.setCheckBox(false);
                        result.add(contact);
                    }
                }
            } else {
                Utility.showLog("hello", "Hello");
            }
        }
        return result;
    }


    @OnClick(R.id.et_date)
    public void getEventDate() {
        Utility.hideSoftKeyboard(parent, et_party_date);
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(parent, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                int month = monthOfYear + 1;
                et_date.setText("" + year + "-" + month + "-" + dayOfMonth);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();

    }


    @OnClick(R.id.tv_event_invite)
    public void eventInvite() {
        btn_submit.setVisibility(View.VISIBLE);
        view_et_date.setVisibility(View.VISIBLE);
        iv_date.setImageDrawable(Utility.getDrawable(parent, R.drawable.ic_date));
        rl_parent.setVisibility(View.VISIBLE);
        ll_list_parent.setVisibility(View.GONE);
        et_date.setVisibility(View.VISIBLE);
        ll_party_invite.setVisibility(View.GONE);
        tv_event_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_fill_right));
        tv_event_invite.setTextColor(Utility.getColor(parent, R.color.colorWhite));

        tv_party_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_left));
        tv_party_invite.setTextColor(Utility.getColor(parent, R.color.colorPrimary));

    }

    @OnClick(R.id.tv_party_invite)
    public void partyInvite() {
        Utility.hideSoftKeyboard(parent, tv_party_invite);
        btn_submit.setVisibility(View.VISIBLE);
        view_et_date.setVisibility(View.GONE);
        iv_date.setImageDrawable(Utility.getDrawable(parent, R.drawable.ic_date));
        rl_parent.setVisibility(View.VISIBLE);
        ll_list_parent.setVisibility(View.GONE);
        et_date.setVisibility(View.GONE);
        ll_party_invite.setVisibility(View.VISIBLE);
        tv_party_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_fill_left));
        tv_party_invite.setTextColor(Utility.getColor(parent, R.color.colorWhite));

        tv_event_invite.setBackground(Utility.getDrawable(parent, R.drawable.rectangel_edit_right));
        tv_event_invite.setTextColor(Utility.getColor(parent, R.color.colorPrimary));
    }

    @OnClick(R.id.btn_submit)
    public void submit() {

    }

    @OnClick(R.id.et_party_date)
    public void getDate() {
        Utility.hideSoftKeyboard(parent, et_party_date);

        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(parent, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                int month = monthOfYear + 1;
                et_party_date.setText("" + year + "-" + month + "-" + dayOfMonth);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();


    }

    @OnClick(R.id.et_party_time)
    public void getTime() {
        Utility.hideSoftKeyboard(parent, et_party_time);
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(parent, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                et_party_time.setText(selectedHour + ":" + selectedMinute);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);
        timePickerDialog.show();
    }


    @Override
    public void onComplete(Model model) {
        if (model != null) {
            if (model instanceof LookUpEventsTypeModel) {
                lookUpEventsTypeModel = (LookUpEventsTypeModel) model;
                if (!lookUpEventsTypeModel.isError()) {
                }
            }
        }
    }

    /*This method is used to set data to spinner*/
    @OnClick(R.id.et_invite_types)
    void setInviteTypeData() {
        if (lookUpEventsTypeModel != null &&
                lookUpEventsTypeModel.getLookUpModels() != null &&
                lookUpEventsTypeModel.getLookUpModels().size() > 0)
            showSpinnerDialog(getActivity(), Utility.getResourcesString(parent, R.string.invite_type),
                    et_invite_types, lookUpEventsTypeModel.getLookupNames(), 1);
    }

    public void showSpinnerDialog(final Context context, final String title, final EditText et_spinner,
                                  ArrayList<SpinnerModel> itemsList, final int id) {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);

        /*CUSTOM TITLE*/
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_alert_dialog_title, null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_alert_dialog_title);
        RelativeLayout dialog_back_ground = (RelativeLayout) view.findViewById(R.id.dialog_back_ground);
        dialog_back_ground.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        tv_title.setText(title);
        tv_title.setTextColor(context.getResources().getColor(R.color.colorWhite));
        builderSingle.setCustomTitle(view);


        final SpinnerAdapter adapter = new SpinnerAdapter(context, itemsList);
        builderSingle.setAdapter(adapter,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SpinnerModel mData = (SpinnerModel) adapter.getItem(which);
                        if (id == 1) {
                            String text = mData.getTitle();
                            et_spinner.setText(text);
                        }
                    }
                });
        builderSingle.show();
    }
}
