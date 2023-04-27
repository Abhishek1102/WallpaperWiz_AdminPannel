package com.example.wallpaperwizfirebaseadmin.helper;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class Utils {
    public static void blackIconStatusBar(Activity activity, int color) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        //to change status bar icons color to black
//        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        //to change status bar icons color to white give visibility 0 and background color black or any
        activity.getWindow().getDecorView().setSystemUiVisibility(0);
        //to change status bar icons color give color parameter as black or white or any you want it
        activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, color));
    }
}
