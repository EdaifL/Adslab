package com.allNetworks.adsnets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;
import com.yandex.mobile.ads.common.InitializationListener;
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAd;
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener;
import com.yandex.mobile.ads.nativeads.NativeAdLoader;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.template.NativeBannerView;

public class YandexClass implements AdsManage {
    private static YandexClass yandexAds;
    private final int Requests = 0;
    private  float Percentage;
    private ProgressDialog dialog;
    private String BannerUnit ="";
    private String Interstitial_Unit="";
    private String Native_Unite = "";
    private String NativeBanner_Unite = "";
    private static final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";
    public static YandexClass getInstance(AdsUnites config){
        if (yandexAds == null){
            yandexAds = new YandexClass();
            yandexAds.BannerUnit = config.getBanner_id();
            yandexAds.Interstitial_Unit= config.getInterstitial_id();
            yandexAds.Native_Unite = config.getNative_Id();
            yandexAds.NativeBanner_Unite = config.getNativeBanner_id();
        }
        return yandexAds;
    }
    @Override
    public void init(Context context) {
        MobileAds.initialize(context, new InitializationListener() {
            @Override
            public void onInitializationCompleted() {
                Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized");
            }
        });
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
        BannerAdView mBannerAdView = new BannerAdView(activity);
        mBannerAdView.setAdUnitId(BannerUnit);
        mBannerAdView.setAdSize(AdSize.stickySize(AdSize.FULL_SCREEN.getWidth()));
        AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.loadAd(adRequest);
        linearLayout.addView(mBannerAdView);
    }
    private InterstitialAd interstitialAd;
    @Override
    public void Show_Interstitial(Context context,  Intent MIntent) {
        initDialog(context);
        interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(Interstitial_Unit);
        AdRequest adRequest = new AdRequest.Builder().build();
        interstitialAd.loadAd(adRequest);
        interstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd.show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                interstitialAd.loadAd(adRequest);
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {
                if ( dialog !=null && dialog.isShowing()) {
                    dialog.dismiss(); }
                context.startActivity(MIntent);
            }

            @Override
            public void onAdClicked() {

            }

            @Override
            public void onLeftApplication() {

            }

            @Override
            public void onReturnedToApplication() {

            }

            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {

            }
        });
    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView) {
        if (!Native_Unite.isEmpty()){
        final NativeAdLoader nativeAdLoader = new NativeAdLoader(context);
        final NativeAdRequestConfiguration nativeAdRequestConfiguration =
                new NativeAdRequestConfiguration.Builder(Native_Unite).build();
        nativeAdLoader.loadAd(nativeAdRequestConfiguration);
        nativeAdLoader.setNativeAdLoadListener(new NativeAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull NativeAd nativeAd) {
                if (imageView!=null){
                    imageView.setVisibility(View.GONE);
                }
                final NativeBannerView nativeBannerView = new NativeBannerView(context);
                nativeBannerView.setAd(nativeAd);
                linearLayout.addView(nativeBannerView,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {

            }
        });}
    }

    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {

    }

    @Override
    public void Show_OpenApp(Context context) {

    }
}
