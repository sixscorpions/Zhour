package com.zhour.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zhour.R;
import com.zhour.activities.DashboardActivity;
import com.zhour.adapters.ContactsAdapter;
import com.zhour.adapters.PartyInviteAdapter;
import com.zhour.adapters.SpinnerAdapter;
import com.zhour.aynctask.IAsyncCaller;
import com.zhour.aynctask.ServerJSONAsyncTask;
import com.zhour.aynctaskold.ServerIntractorAsync;
import com.zhour.models.Contact;
import com.zhour.models.EventInviteSuccessModel;
import com.zhour.models.InvitesModel;
import com.zhour.models.LookUpEventsTypeModel;
import com.zhour.models.Model;
import com.zhour.models.PartyInviteModel;
import com.zhour.models.PartyInviteSuccessModel;
import com.zhour.models.SpinnerModel;
import com.zhour.parser.EventInviteSuccessParser;
import com.zhour.parser.LookUpEventTypeParser;
import com.zhour.parser.PartyInviteParser;
import com.zhour.parser.PartyInviteSuccessParser;
import com.zhour.utils.APIConstants;
import com.zhour.utils.Constants;
import com.zhour.utils.Utility;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private DashboardActivity mParent;

    @BindView(R.id.list_view)
    ListView list_view;

    @BindView(R.id.tv_party_invite)
    TextView tv_party_invite;

    @BindView(R.id.tv_event_invite)
    TextView tv_event_invite;

    @BindView(R.id.iv_date)
    ImageView iv_date;

    @BindView(R.id.tv_phone_book)
    TextView tv_phone_book;

    @BindView(R.id.btn_submit)
    Button btn_submit;

    @BindView(R.id.et_date)
    EditText et_date;

    @BindView(R.id.et_party_time)
    EditText et_party_time;

    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.tv_circle)
    TextView tv_circle;

    @BindView(R.id.tv_add)
    TextView tv_add;

    @BindView(R.id.tv_count)
    TextView tv_count;


    @BindView(R.id.ll_list_parent)
    LinearLayout ll_list_parent;

    @BindView(R.id.rl_parent)
    RelativeLayout rl_parent;
    @BindView(R.id.ll_party_invite)
    LinearLayout ll_party_invite;
    @BindView(R.id.iv_occasions)
    ImageView iv_occations;

    @BindView(R.id.et_party_date)
    EditText et_party_date;
    @BindView(R.id.et_event_invite_types)
    EditText et_event_invite_types;
    @BindView(R.id.et_event_venue)
    EditText et_event_venue;

    @BindView(R.id.et_invite_types)
    EditText et_invite_types;
    @BindView(R.id.et_venue)
    EditText et_venue;
    @BindView(R.id.et_invite_note)
    EditText et_invite_note;

    @BindView(R.id.scroll_view)
    ScrollView scroll_view;

    @BindView(R.id.tv_no_of_contacts)
    TextView tv_no_of_contacts;


    public static ArrayList<Contact> contactsListModel;
    public static ArrayList<Contact> addContactList = new ArrayList<>();

    private ArrayList<Contact> newList;
    private ArrayList<InvitesModel> inviteEventList;

    private Cursor mCursor;
    private Set<Contact> result;
    private LookUpEventsTypeModel lookUpEventsTypeModel;
    private PartyInviteModel partyInviteModel;
    private ContactsAdapter mAdapter;
    private PartyInviteAdapter partyInviteAdapter;


    private boolean isPartyInvite = false;
    private boolean isEventInvite = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParent = (DashboardActivity) getActivity();
        Utility.setTranslateStatusBar(mParent);
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
        tv_circle.setTypeface(Utility.setFontAwesomeWebfont(mParent));
        tv_add.setTypeface(Utility.setFontAwesomeWebfont(mParent));
        tv_phone_book.setTypeface(Utility.setFontAwesomeWebfont(mParent));
        askPermission();
        getInviteTypes();
    }

    private void getInviteTypes() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("entityname", "Event%20Types");
            LookUpEventTypeParser lookUpEventTypeParser = new LookUpEventTypeParser();
            ServerJSONAsyncTask serverJSONAsyncTask = new ServerJSONAsyncTask(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.GET_LOOKUP_DATA_BY_ENTITY_NAME, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, lookUpEventTypeParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(mParent,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mParent,
                    Manifest.permission.READ_CONTACTS)) {

            } else {

                ActivityCompat.requestPermissions(mParent,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        Constants.MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @OnClick(R.id.iv_date)
    public void eventsAndParty() {
        iv_date.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_time_fill));

        tv_event_invite.setBackground(Utility.getDrawable(mParent, R.drawable.rectangel_edit_right));
        tv_event_invite.setTextColor(Utility.getColor(mParent, R.color.colorPrimary));

        tv_party_invite.setBackground(Utility.getDrawable(mParent, R.drawable.rectangel_edit_left));
        tv_party_invite.setTextColor(Utility.getColor(mParent, R.color.colorPrimary));


        rl_parent.setVisibility(View.GONE);
        ll_list_parent.setVisibility(View.VISIBLE);
        btn_submit.setVisibility(View.GONE);
        scroll_view.setVisibility(View.GONE);


        getInviteService();
    }


    @OnClick(R.id.tv_phone_book)
    public void phoneBook() {

        final Dialog dialog = new Dialog(mParent);
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
                if (newList.size() > 0) {
                    contactsListModel = new ArrayList<>();

                    for (int i = 0; i < newList.size(); i++) {
                        Contact contact = newList.get(i);
                        if (contact.ismContacts_Flow()) {
                            contact.setmContacts_Flow(true);
                            contact.getDisplayName();
                            contact.getPhoneNumber();
                            contactsListModel.add(contact);
                            mAdapter.updateAdapter(newList);
                        }
                    }
                    if (contactsListModel.size() > 0 && contactsListModel.size() != 0) {
                        tv_count.setText("" + contactsListModel.size());
                    } else {
                        tv_count.setText("" + contactsListModel.size());


                    }
                }
                //Utility.showToastMessage(getActivity(), "SELECTED CONTACTS" + contactsListModel.size());
                dialog.dismiss();
            }
        });
        tv_et_search_image.setTypeface(Utility.getMaterialIconsRegular(mParent));

        mCursor = mParent.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        Set<Contact> contacts = getContacts();

        if (newList == null) {
            newList = new ArrayList<>(new HashSet<>(contacts));
        }

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

        mAdapter = new ContactsAdapter(mParent, newList, TAG);
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
        int res = mParent.checkCallingOrSelfPermission(permission);
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

    @OnClick(R.id.tv_add)
    public void addContact() {
        if (isAddContactValidFields()) {

            updateData();

        }

    }

    private boolean isAddContactValidFields() {
        boolean isValid = true;
        if (Utility.isValueNullOrEmpty(et_phone.getText().toString())) {
            Utility.setSnackBar(mParent, et_phone, "Please enter mobile number");
            isValid = false;
        } else if (et_phone.getText().toString().length() < 10) {
            Utility.setSnackBar(mParent, et_phone, "Please enter valid mobile number");
            isValid = false;
        }
        return isValid;
    }


    @OnClick(R.id.et_date)
    public void getEventDate() {
        Utility.hideSoftKeyboard(mParent, et_party_date);
        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(mParent, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                int month = monthOfYear + 1;
                et_date.setText("" + month + "/" + dayOfMonth + "/" + year);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();

    }


    @OnClick(R.id.tv_event_invite)
    public void eventInvite() {
        isEventInvite = true;
        isPartyInvite = false;
        Utility.hideSoftKeyboard(mParent, tv_event_invite);
        scroll_view.setVisibility(View.VISIBLE);
        btn_submit.setVisibility(View.VISIBLE);
        et_event_invite_types.setVisibility(View.VISIBLE);
        et_event_venue.setVisibility(View.VISIBLE);
        iv_date.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_date));
        rl_parent.setVisibility(View.VISIBLE);
        ll_list_parent.setVisibility(View.GONE);
        et_date.setVisibility(View.VISIBLE);
        ll_party_invite.setVisibility(View.GONE);
        tv_event_invite.setBackground(Utility.getDrawable(mParent, R.drawable.rectangel_edit_fill_right));
        tv_event_invite.setTextColor(Utility.getColor(mParent, R.color.colorWhite));

        tv_party_invite.setBackground(Utility.getDrawable(mParent, R.drawable.rectangel_edit_left));
        tv_party_invite.setTextColor(Utility.getColor(mParent, R.color.colorPrimary));
        clearDataForEvent();
    }

    @OnClick(R.id.tv_party_invite)
    public void partyInvite() {
        isPartyInvite = true;
        isEventInvite = false;
        Utility.hideSoftKeyboard(mParent, tv_party_invite);
        scroll_view.setVisibility(View.VISIBLE);
        btn_submit.setVisibility(View.VISIBLE);
        iv_date.setImageDrawable(Utility.getDrawable(mParent, R.drawable.ic_date));
        rl_parent.setVisibility(View.VISIBLE);
        ll_list_parent.setVisibility(View.GONE);
        et_date.setVisibility(View.GONE);
        et_event_invite_types.setVisibility(View.GONE);
        et_event_venue.setVisibility(View.GONE);
        ll_party_invite.setVisibility(View.VISIBLE);
        tv_party_invite.setBackground(Utility.getDrawable(mParent, R.drawable.rectangel_edit_fill_left));
        tv_party_invite.setTextColor(Utility.getColor(mParent, R.color.colorWhite));

        tv_event_invite.setBackground(Utility.getDrawable(mParent, R.drawable.rectangel_edit_right));
        tv_event_invite.setTextColor(Utility.getColor(mParent, R.color.colorPrimary));

        clearDataAndShowToast();
    }

    @OnClick(R.id.btn_submit)
    public void submit() {
        if (isPartyInvite) {
            if (isValidFields()) {
                savePartyInvite();
            }
        } else if (isEventInvite) {
            if (isValidEventInviteFields()) {
                saveEventInvite();
            }
        }
    }

    /*GET INVITE LIST SERVICE CALL*/
    private void getInviteService() {

        String communityID = Utility.getSharedPrefStringData(mParent, Constants.COMMUNITY_ID);
        String residentID = Utility.getSharedPrefStringData(mParent, Constants.RESIDENT_ID);

        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("communityid", communityID);
        linkedHashMap.put("residentid", residentID);

        PartyInviteParser partyInviteParser = new PartyInviteParser();
        ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                APIConstants.GET_INVITES, linkedHashMap,
                APIConstants.REQUEST_TYPE.POST, this, partyInviteParser);
        Utility.execute(serverJSONAsyncTask);

    }


    /**
     * This method is used to save party invite
     */
    private void savePartyInvite() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("invitetypeid", getInviteTypeId(et_invite_types.getText().toString()));
            linkedHashMap.put("eventdate", et_party_date.getText().toString());
            linkedHashMap.put("eventtime", et_party_time.getText().toString());
            linkedHashMap.put("invitenote", et_invite_note.getText().toString());
            linkedHashMap.put("venue", et_venue.getText().toString());
            linkedHashMap.put("communityid", Utility.getSharedPrefStringData(mParent, Constants.COMMUNITY_ID));
            linkedHashMap.put("residentid", Utility.getSharedPrefStringData(mParent, Constants.RESIDENT_ID));

            JSONArray jsonArray = new JSONArray();
            JSONObject jsonObject = new JSONObject();
            if (contactsListModel != null && contactsListModel.size() > 0) {
                for (int i = 0; i < contactsListModel.size(); i++) {
                    Contact c = contactsListModel.get(i);
                    String num = c.getPhoneNumber().trim();
                    String phNo = num.replaceAll("[()\\s-]+", "");
                    jsonObject.put(c.displayName, phNo);

                }
            }

            jsonArray.put(jsonObject);
            linkedHashMap.put("contacts", jsonArray.toString());

            PartyInviteSuccessParser partyInviteSuccessParser = new PartyInviteSuccessParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.SAVE_INVITE, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, partyInviteSuccessParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to save event invite
     */
    private void saveEventInvite() {
        try {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("invitetypeid", getInviteTypeId(et_event_invite_types.getText().toString()));
            linkedHashMap.put("eventdate", et_date.getText().toString());
            linkedHashMap.put("eventtime", "00:00");
            linkedHashMap.put("invitenote", et_invite_note.getText().toString());
            linkedHashMap.put("venue", et_event_venue.getText().toString());
            linkedHashMap.put("communityid", Utility.getSharedPrefStringData(mParent, Constants.COMMUNITY_ID));
            linkedHashMap.put("residentid", Utility.getSharedPrefStringData(mParent, Constants.RESIDENT_ID));

            JSONArray jsonArray = new JSONArray();
            linkedHashMap.put("contacts", jsonArray.toString());

            EventInviteSuccessParser mEventInviteSuccessParser = new EventInviteSuccessParser();
            ServerIntractorAsync serverJSONAsyncTask = new ServerIntractorAsync(
                    mParent, Utility.getResourcesString(mParent, R.string.please_wait), true,
                    APIConstants.SAVE_INVITE, linkedHashMap,
                    APIConstants.REQUEST_TYPE.POST, this, mEventInviteSuccessParser);
            Utility.execute(serverJSONAsyncTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getInviteTypeId(String s) {
        String mInviteTypeId = "";
        for (int i = 0; i < lookUpEventsTypeModel.getLookupNames().size(); i++) {
            if (lookUpEventsTypeModel.getLookUpModels().get(i).getLookupname().equals(s)) {
                mInviteTypeId = lookUpEventsTypeModel.getLookUpModels().get(i).getLookupid();
            }
        }
        return mInviteTypeId;
    }

    /**
     * This method is used check is the field are valid
     */
    private boolean isValidFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(et_invite_types.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_invite_types, "Please select invite type");
            et_invite_types.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_party_date.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_party_date, "Please select date");
            et_party_date.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_party_time.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_party_time, "Please select time");
            et_party_date.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_venue.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_venue, "Please enter venue");
            et_party_date.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_invite_note.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_invite_note, "Please enter invite note");
            et_party_date.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    /**
     * This method is used check is the field are valid
     */
    private boolean isValidEventInviteFields() {
        boolean isValidated = false;
        if (Utility.isValueNullOrEmpty(et_date.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_date, "Please select date");
            et_date.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_invite_note.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_invite_note, "Please enter invite note");
            et_party_date.requestFocus();
        } else if (Utility.isValueNullOrEmpty(et_event_venue.getText().toString().trim())) {
            Utility.setSnackBar(mParent, et_event_venue, "Please enter venue");
            et_party_date.requestFocus();
        } else {
            isValidated = true;
        }
        return isValidated;
    }

    @OnClick(R.id.et_party_date)
    public void getDate() {
        Utility.hideSoftKeyboard(mParent, et_party_date);

        Calendar c = Calendar.getInstance();
        DatePickerDialog dpd = new DatePickerDialog(mParent, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                int month = monthOfYear + 1;
                et_party_date.setText("" + month + "/" + dayOfMonth + "/" + year);

            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        dpd.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dpd.show();


    }

    @OnClick(R.id.et_party_time)
    public void getTime() {
        Utility.hideSoftKeyboard(mParent, et_party_time);
        Calendar c = Calendar.getInstance();
        TimePickerDialog timePickerDialog = new TimePickerDialog(mParent, new TimePickerDialog.OnTimeSetListener() {
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
            } else if (model instanceof PartyInviteModel) {
                partyInviteModel = (PartyInviteModel) model;
                if (!partyInviteModel.isError()) {
                    inviteEventList = new ArrayList<>();
                    setListData(partyInviteModel.getInvitesList());

                }
            } else if (model instanceof PartyInviteSuccessModel) {
                PartyInviteSuccessModel partyInviteSuccessModel = (PartyInviteSuccessModel) model;
                if (!partyInviteSuccessModel.isError()) {
                    Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.invitation_sent_successfully));
                    clearDataAndShowToast();
                }
            } else if (model instanceof EventInviteSuccessModel) {
                EventInviteSuccessModel eventInviteSuccessModel = (EventInviteSuccessModel) model;
                if (!eventInviteSuccessModel.isError()) {
                    Utility.showToastMessage(mParent, Utility.getResourcesString(mParent, R.string.invitation_sent_successfully));
                    clearDataForEvent();
                }
            }
        }
    }

    /**
     * This method is used to clear the data
     */
    private void clearDataAndShowToast() {
        et_invite_types.setText("");
        et_party_date.setText("");
        et_party_time.setText("");
        et_venue.setText("");
        et_invite_note.setText("");
        et_phone.setText("");
        contactsListModel = null;
    }

    /**
     * This method is used to clear the data
     */
    private void clearDataForEvent() {
        et_event_invite_types.setText("");
        et_date.setText("");
        et_invite_note.setText("");
        et_event_venue.setText("");
        contactsListModel = null;
    }

    /**
     * SET INVITES DATA
     */
    private void setListData(ArrayList<InvitesModel> invitesList) {

        if (invitesList.size() > 0 && invitesList != null) {
            for (int i = 0; i < invitesList.size(); i++) {
                InvitesModel invitesModel = invitesList.get(i);
                inviteEventList.add(invitesModel);

            }

            partyInviteAdapter = new PartyInviteAdapter(inviteEventList, mParent);
            list_view.setAdapter(partyInviteAdapter);

        }


    }

    /*This method is used to set data to spinner*/
    @OnClick({R.id.et_invite_types, R.id.et_event_invite_types})
    void setInviteTypeData() {
        if (lookUpEventsTypeModel != null &&
                lookUpEventsTypeModel.getLookUpModels() != null &&
                lookUpEventsTypeModel.getLookUpModels().size() > 0)
            showSpinnerDialog(getActivity(), Utility.getResourcesString(mParent, R.string.invite_type),
                    lookUpEventsTypeModel.getLookupNames(), 1);
    }

    public void showSpinnerDialog(final Context context, final String title,
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
                            if (isPartyInvite) {
                                et_invite_types.setText(text);
                            } else if (isEventInvite) {
                                et_event_invite_types.setText(text);
                            }
                        }
                    }
                });
        builderSingle.show();
    }

    @SuppressLint("DefaultLocale")
    private void updateData() {
        if (contactsListModel != null) {
            int size = contactsListModel.size() - 1;
            Contact contact = contactsListModel.get(size);
            contact.setPhoneNumber(et_phone.getText().toString());
            contactsListModel.add(contact);
            Utility.showLog("Size Of Contacts ", "" + size);
        } else {
            Utility.showLog("Size Of Contacts ", "" + (contactsListModel != null ? contactsListModel.size() : 0));
        }
        tv_count.setText(String.format("%d", contactsListModel.size()));
        tv_no_of_contacts.setVisibility(View.VISIBLE);
        tv_no_of_contacts.setText(String.format("%d", contactsListModel.size()));
        et_phone.setText("");

    }


}
