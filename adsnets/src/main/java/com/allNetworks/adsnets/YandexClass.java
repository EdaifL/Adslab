package com.allNetworks.adsnets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import com.yandex.mobile.ads.common.MobileAds;
import com.yandex.mobile.ads.interstitial.InterstitialAd;
import com.yandex.mobile.ads.interstitial.InterstitialAdEventListener;
import com.yandex.mobile.ads.nativeads.NativeAd;
import com.yandex.mobile.ads.nativeads.NativeAdLoadListener;
import com.yandex.mobile.ads.nativeads.NativeAdLoader;
import com.yandex.mobile.ads.nativeads.NativeAdRequestConfiguration;
import com.yandex.mobile.ads.nativeads.template.NativeBannerView;
import com.yandex.mobile.ads.rewarded.Reward;
import com.yandex.mobile.ads.rewarded.RewardedAd;
import com.yandex.mobile.ads.rewarded.RewardedAdEventListener;

public class YandexClass implements AdsManage {
    private static YandexClass yandexAds;
    private Dialog dialog;
    private RewardedAd rewardedAd;
    private String BannerUnit ,Interstitial_Unit,Native_Unite , RewardVideoId;
    private static   int Requests = 0;
    private static final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";
    public static YandexClass getInstance(NetworkUnitAd unitAd){
        if (yandexAds == null){
            yandexAds = new YandexClass();
            yandexAds.BannerUnit = unitAd.getBANNER_Id();
            yandexAds.Interstitial_Unit= unitAd.getINTERSTITIAL_Id();
            yandexAds.Native_Unite = unitAd.getNATIVE_Id();
            yandexAds.RewardVideoId = unitAd.getRewardVideoId();
        }
        return yandexAds;
    }
    @Override
    public void init(Context context) {
        MobileAds.initialize(context,()->{});
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
        if(Requests +1 == AdsUnites.interCounterShow){
        dialog = new progessDialog(context);
        dialog.show();
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
                    Requests =0;
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
                Requests =0;
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
        }else {
            Requests +=1;
            interstital.isShowed();
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
    public void Show_Native(Context context, LinearLayout linearLayout) {
        if (!Native_Unite.isEmpty()){
        final NativeAdLoader nativeAdLoader = new NativeAdLoader(context);
        final NativeAdRequestConfiguration nativeAdRequestConfiguration =
                new NativeAdRequestConfiguration.Builder(Native_Unite).build();
        nativeAdLoader.loadAd(nativeAdRequestConfiguration);
        nativeAdLoader.setNativeAdLoadListener(new NativeAdLoadListener() {
            @Override
            public void onAdLoaded(@NonNull NativeAd nativeAd) {
               linearLayout.removeAllViews();
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
    public void LoadReward(Context context) {
        final AdRequest adRequest = new AdRequest.Builder().build();
        rewardedAd = new RewardedAd(context);
        rewardedAd.setAdUnitId(RewardVideoId);
        rewardedAd.loadAd(adRequest);

    }

    @Override
    public void Show_Reward(Context context, Reward rewardA) {
        dialog = new progessDialog(context);
        dialog.show();
        if (rewardedAd.isLoaded()){
            rewardedAd.show();
            rewardedAd.setRewardedAdEventListener(new RewardedAdEventListener() {
                @Override
                public void onAdLoaded() {

                }

                @Override
                public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                    rewardA.FieldToreward(adRequestError.getDescription());
                    if (dialog.isShowing()){ dialog.dismiss();}

                }

                @Override
                public void onAdShown() {

                }

                @Override
                public void onAdDismissed() {
                    if (dialog.isShowing()){ dialog.dismiss();}
                    rewardedAd = null;
                    LoadReward(context);
                }

                @Override
                public void onRewarded(@NonNull com.yandex.mobile.ads.rewarded.Reward reward) {
                    rewardA.Rewarded();
                    if (dialog.isShowing()){ dialog.dismiss();}
                    rewardedAd = null;
                    LoadReward(context);
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
            final AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd rewardedAd1 = new RewardedAd(context);
            rewardedAd1.setAdUnitId(RewardVideoId);
            rewardedAd1.loadAd(adRequest);
            rewardedAd1.setRewardedAdEventListener(new RewardedAdEventListener() {
                @Override
                public void onAdLoaded() {
                        rewardedAd1.show();
                }

                @Override
                public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
                    rewardA.FieldToreward(adRequestError.getDescription());
                    if (dialog.isShowing()){ dialog.dismiss();}

                }

                @Override
                public void onAdShown() {

                }

                @Override
                public void onAdDismissed() {
                    if (dialog.isShowing()){ dialog.dismiss();}
                }

                @Override
                public void onRewarded(@NonNull com.yandex.mobile.ads.rewarded.Reward reward) {
                    rewardA.Rewarded();
                    if (dialog.isShowing()){ dialog.dismiss();}
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

    @Override
    public void Show_OpenApp(Context context) {

    }
}
