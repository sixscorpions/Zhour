package com.zhour.asynctasknew;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.BaseActivity;


/**
 * Created by madhu on 16-Dec-17.
 */

public class CustomDialog {

    /*SHOW PROGRESS BAR*/
    public static Dialog showProgressDialog(BaseActivity baseActivity, boolean cancelable) {

        Dialog mDialog = new Dialog(baseActivity, R.style.pb_dialog_style);



        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        View layout = View.inflate(baseActivity, R.layout.progressbar_custom, null);
        mDialog.setContentView(layout);

        TextView tvProgressMessage = (TextView) layout.findViewById(R.id.tvProgressMessage);
        tvProgressMessage.setText("Loading Please Wait....");
        tvProgressMessage.setVisibility(View.VISIBLE);

     // Dialog  progressDialog= null;

        if (baseActivity.mDialog != null) {
            baseActivity.mDialog.dismiss();
            baseActivity.mDialog = null;
        }

        baseActivity.mDialog = mDialog;

        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);
        mDialog.show();

        return mDialog;
    }

    /*HIDE THE PROGRESS DIALOG*/
    public static void hideProgressBar(BaseActivity parent) {
        if (parent.mDialog != null)
            parent.mDialog.dismiss();
    }
}
