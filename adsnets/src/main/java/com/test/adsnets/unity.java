package com.test.adsnets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class unity implements AdsManage {
    String AppId;
    private static unity unity;
    private ProgressDialog dialog;
    private String BannerUnit;
    private String InterstitialUnit;
    private String NativeUnit;
    private final Boolean TestMode = false;



    public static unity getInstance(AdsUnites config) {
        if (unity == null) {
            unity = new unity();
            unity.AppId = config.getAppId();
            unity.BannerUnit = config.getBanner_id();
            unity.InterstitialUnit = config.getInterstitial_id();
        }
        return unity;
    }

    @Override
    public void init(Context context) {

        UnityAds.initialize(context, AppId, TestMode);
    }

    @Override
    public void initDialog(Context context) {
        dialog = new ProgressDialog(context, ProgressDialog.THEME_HOLO_LIGHT);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgressNumberFormat(null);
        dialog.setProgressPercentFormat(null);
        dialog.setIndeterminate(true);
        dialog.setMessage("Page Loading...");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    @Override
    public void Show_Banner(Activity activity, LinearLayout linearLayout) {
        BannerView topBanner = new BannerView(activity, BannerUnit, new UnityBannerSize(320, 50));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(topBanner);
        topBanner.setListener(new BAnnerAdsLoadListner());
        topBanner.load();

    }

    @Override
    public void Show_Interstitial(Context context,  Intent MIntent) {
        initDialog(context);
        UnityAds.load(InterstitialUnit, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String s) {
                UnityAds.show((Activity) context, InterstitialUnit , new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                        context.startActivity(MIntent);

                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                        context.startActivity(MIntent);
                        if (dialog.isShowing()){dialog.dismiss(); }

                    }
                });
            }

            @Override
            public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {
                context.startActivity(MIntent);
                if (dialog.isShowing()){dialog.dismiss(); }
            }
        });




    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView) {

    }

    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {

    }


    private class BAnnerAdsLoadListner implements BannerView.IListener {

        @Override
        public void onBannerLoaded(BannerView bannerView) {

        }

        @Override
        public void onBannerClick(BannerView bannerView) {

        }

        @Override
        public void onBannerFailedToLoad(BannerView bannerView, BannerErrorInfo bannerErrorInfo) {

        }

        @Override
        public void onBannerLeftApplication(BannerView bannerView) {

        }
    }
}
