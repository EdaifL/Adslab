package com.allNetworks.adsnets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Null implements AdsManage{
    public static Null aNull;
    public static Null getInstance(){
        if (aNull==null){
            aNull = new Null();

        }
        return aNull;
    }
    @Override
    public void init(Context context) {

    }

    @Override
    public void initDialog(Context context) {

    }

    @Override
    public void Show_OpenApp(Context context) {

    }

    @Override
    public void Show_Banner(Activity activity, LinearLayout linearLayout) {

    }

    @Override
    public void Show_Interstitial(Context context, Intent MIntent) {
        context.startActivity(MIntent);
    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView) {

    }

    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {

    }
}
