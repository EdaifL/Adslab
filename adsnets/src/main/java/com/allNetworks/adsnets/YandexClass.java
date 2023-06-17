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

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
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
    private ProgressDialog dialog;
    private String BannerUnit ="";
    private String Interstitial_Unit="";
    private String Native_Unite = "";
    private static final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";
    public static YandexClass getInstance(NetworkUnitAd unitAd){
        if (yandexAds == null){
            yandexAds = new YandexClass();
            yandexAds.BannerUnit = unitAd.getBANNER_Id();
            yandexAds.Interstitial_Unit= unitAd.getINTERSTITIAL_Id();
            yandexAds.Native_Unite = unitAd.getNATIVE_Id();
        }
        return yandexAds;
    }
    @Override
    public void init(Context context) {
        MobileAds.initialize(context,()->{});
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
    private boolean isLoaded = false;

    @Override
    public void Show_Interstitial(Context context, Interstital interstital) {
        initDialog(context);
        if (isLoaded){
            interstitialAd.show();
            interstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
                @Override
                public void onAdLoaded() {

                }

                @Override
                public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                    interstital.fieldToShow();
                }

                @Override
                public void onAdShown() {

                }

                @Override
                public void onAdDismissed() {
                    if (dialog.isShowing()){dialog.dismiss();}
                    interstital.isShowed();
                    isLoaded = false;
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
        }else {
        loadInter(context);
            AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd interstitialAd2 = new InterstitialAd(context);
        interstitialAd2.setAdUnitId(Interstitial_Unit);

        interstitialAd2.loadAd(adRequest);
        interstitialAd2.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                interstitialAd2.show();
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                interstital.fieldToShow();
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {
                if (dialog.isShowing()){dialog.dismiss();}
                interstital.isShowed();
              isLoaded = false;
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
    }
    private InterstitialAd interstitialAd;
    @Override
    public boolean loadInter(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
         interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(Interstitial_Unit);

        interstitialAd.loadAd(adRequest);
        interstitialAd.setInterstitialAdEventListener(new InterstitialAdEventListener() {
            @Override
            public void onAdLoaded() {
                isLoaded = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                isLoaded  =false;
            }

            @Override
            public void onAdShown() {

            }

            @Override
            public void onAdDismissed() {

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
        return isLoaded;
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
