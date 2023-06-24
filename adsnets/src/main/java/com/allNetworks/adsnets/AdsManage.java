package com.allNetworks.adsnets;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;

public interface AdsManage {
    void init(Context context);
    void Show_OpenApp(Context context);
    void Show_Banner(Activity activity, LinearLayout linearLayout);
    void Show_Interstitial(Context context , Interstital interstital);
    boolean loadInter(Context context);
    void Show_Native(Context context, LinearLayout linearLayout);
    void Show_NativeBanner(Context context, LinearLayout linearLayout );
    void LoadReward(Context context);
    void Show_Reward(Context context,Reward rewardA);

    public interface Interstital{
        void isShowed();
        void fieldToShow();


    }
    public interface Reward{
         void Rewarded();
         void FieldToreward(String erorrMassage);
    }

}
