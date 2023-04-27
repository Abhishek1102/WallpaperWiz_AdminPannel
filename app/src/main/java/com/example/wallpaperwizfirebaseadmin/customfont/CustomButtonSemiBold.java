package com.example.wallpaperwizfirebaseadmin.customfont;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatButton;

public class CustomButtonSemiBold extends AppCompatButton {

    public CustomButtonSemiBold(Context context) {
        super(context);
        applyCustomFont(context);
    }

    public CustomButtonSemiBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        applyCustomFont(context);
    }

    public CustomButtonSemiBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = com.example.wallpaperwizfirebaseadmin.customfont.FontCache.getTypeface("Roboto-Bold.ttf", context);
        setTypeface(customFont);
    }
}
