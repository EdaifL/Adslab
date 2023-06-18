package com.allNetworks.adsnets;

import android.app.Activity;
import android.content.Context;
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
    public void Show_OpenApp(Context context) {

    }

    @Override
    public void Show_Banner(Activity activity, LinearLayout linearLayout) {

    }

    @Override
    public void Show_Interstitial(Context context, Interstital interstital) {
            interstital.isShowed();
    }

    @Override
    public boolean loadInter(Context context) {
        return false;
    }



    @Override
    public void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView) {

    }

    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {

    }
}
