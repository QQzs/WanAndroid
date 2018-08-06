package com.zs.demo.wanandroid.utils;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

import com.zs.demo.wanandroid.R;

public class LoadingDialog extends AlertDialog {

    public LoadingDialog(Context context) {
        super(context, R.style.loading_dialog);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);

    }

    TextView text;
    ImageView mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.loading_layout);
        setCanceledOnTouchOutside(false);

        text = (TextView) findViewById(R.id.text);
        mProgressBar = (ImageView) findViewById(R.id.progressbar);
        try {
            ((AnimationDrawable) mProgressBar.getDrawable()).start();
        } catch (Exception e) {
        }
    }

    public void setText(String txt) {
        text.setText(txt);
    }

}
