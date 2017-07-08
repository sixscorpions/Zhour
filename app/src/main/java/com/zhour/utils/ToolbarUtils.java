package com.zhour.utils;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhour.R;
import com.zhour.activities.BaseActivity;

/**
 * Created by madhu on 06-Jul-17.
 */

public class ToolbarUtils {

    /**
     * MAKE THE STATUS BAR TRANSLATE
     */
    public static void setTranslateStatusBar(BaseActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Utility.getColor(activity, R.color.transparent));
        }
    }
     /*TRANSLATE STATUS BAR*/

    public static void setTranslateStatusBar(AppCompatActivity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = context.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Utility.getColor(context, R.color.black_transparent));
        }
    }

    public static TextView setHomeToolBar(final BaseActivity parent, View view, String name) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            parent.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            Window window = parent.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Utility.getColor(parent, R.color.transparent));
        }

        ImageView tv_toolbar = (ImageView) view.findViewById(R.id.tv_toolbar);

        /*DONE*/
        TextView tv_toolbar_done = (TextView) view.findViewById(R.id.tv_toolbar_done);
        tv_toolbar_done.setVisibility(View.GONE);


        ImageView iv_toolbar_back = (ImageView) view.findViewById(R.id.iv_toolbar_back);
        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parent.onBackPressed();
            }
        });


        return tv_toolbar_done;
    }

}
