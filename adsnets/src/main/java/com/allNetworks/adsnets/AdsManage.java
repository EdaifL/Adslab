package com.allNetworks.adsnets;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;

public interface AdsManage {
    void init(Context context);
    void initDialog(Context context);
    void Show_OpenApp(Context context);
    void Show_Banner(Activity activity, LinearLayout linearLayout);
    void Show_Interstitial(Context context, Intent MIntent);
    void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView);
    void Show_NativeBanner(Context context, LinearLayout linearLayout );

}
