package com.seven.fzuborrow.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.WindowManager;


public class ThemeUtil {
    public static void setDarkStatusIcon(Activity activity,boolean dark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = activity.getWindow().getDecorView();
            int vis = decorView.getSystemUiVisibility();
            if (dark) {
                vis = vis | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                vis = vis & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decorView.setSystemUiVisibility(vis);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().setStatusBarColor(Color.parseColor("#33000000"));
        }
    }
}
