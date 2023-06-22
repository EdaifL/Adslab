package com.allNetworks.adsnets;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeAdView;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.NativeBannerAdView;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

public class FaceAds implements AdsManage {

    private static FaceAds facebook;
    private InterstitialAd interstitialAd;
    private Dialog dialog;
    private NativeBannerAd nativeBannerAd;
    private RewardedVideoAd rewardedVideoAd;

    private NativeAd nativeAd;
    private AdView FBadView;
    private String BannerUnit ;
    private String Interstitial_Unit,RewardVideoId;
    private String Native_Unite = "";
    private String NativeBanner_Unite = "";
    private static   int Requests = 0;
    public static FaceAds getInstance(NetworkUnitAd unitAd) {
        if (facebook ==null){
            facebook = new FaceAds();
            facebook.BannerUnit = unitAd.getBANNER_Id();
            facebook.Interstitial_Unit = unitAd.getINTERSTITIAL_Id();
            facebook.Native_Unite = unitAd.getNATIVE_Id();
            facebook.NativeBanner_Unite = unitAd.getNATIVE_BANNER_Id();
            facebook.RewardVideoId = unitAd.getRewardVideoId();
        }
        return facebook;
    }

    @Override
    public void init(Context context) {
        AudienceNetworkAds.initialize(context);

    }



    @Override
    public void Show_OpenApp(Context context) {

    }

    @Override
    public void Show_Banner(Activity activity, LinearLayout linearLayout) {

        FBadView = new com.facebook.ads.AdView(activity,BannerUnit, AdSize.BANNER_HEIGHT_50);
        linearLayout.addView(FBadView);

        FBadView.loadAd();
    }
    private boolean Isloaded;
    @Override
    public void Show_Interstitial(Context context, Interstital interstital) {
       if (Requests +1 == AdsUnites.interCounterShow){
        dialog = new progessDialog(context);
        dialog.show();
        if (Isloaded){
            interstitialAd.show();
            interstitialAd.buildLoadAdConfig().withAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    if (dialog.isShowing())dialog.dismiss();
                    interstital.isShowed();
                    Isloaded =false;
                    Requests =0;
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    if (dialog.isShowing())dialog.dismiss();
                    interstital.fieldToShow();
                }

                @Override
                public void onAdLoaded(Ad ad) {

                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
        }else {
            loadInter(context);
            InterstitialAd interstitialAd2 = new InterstitialAd(context, Interstitial_Unit);
            interstitialAd2.buildLoadAdConfig().withAdListener(new InterstitialAdListener() {
                @Override
                public void onInterstitialDisplayed(Ad ad) {

                }

                @Override
                public void onInterstitialDismissed(Ad ad) {
                    if (dialog.isShowing()){ dialog.dismiss();}
                    interstital.isShowed();
                    Isloaded = false;
                    Requests =0;
                }

                @Override
                public void onError(Ad ad, AdError adError) {
                    if (dialog.isShowing()){ dialog.dismiss();}
                    interstital.fieldToShow();

                }

                @Override
                public void onAdLoaded(Ad ad) {
                  interstitialAd2.show();
                }

                @Override
                public void onAdClicked(Ad ad) {

                }

                @Override
                public void onLoggingImpression(Ad ad) {

                }
            });
            interstitialAd2.loadAd();
        }
       }else {
           Requests +=1;
           interstital.isShowed();
       }

    }

    @Override
    public boolean loadInter(Context context) {
        interstitialAd = new InterstitialAd(context, Interstitial_Unit);
        interstitialAd.buildLoadAdConfig().withAdListener(new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {

            }

            @Override
            public void onInterstitialDismissed(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                Isloaded = false;

            }

            @Override
            public void onAdLoaded(Ad ad) {
                Isloaded = true;
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });

        interstitialAd.loadAd();
        return Isloaded;
    }


    @Override
    public void Show_Native(final Context context, final LinearLayout linearLayout , final ImageView imageView) {

        nativeAd = new NativeAd(context, Native_Unite);


        nativeAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (imageView != null){imageView.setVisibility(View.GONE);}
                NativeAdViewAttributes viewAttributes = new NativeAdViewAttributes(context)
                        .setBackgroundColor(Color.WHITE)
                        .setTitleTextColor(Color.BLACK)
                        .setDescriptionTextColor(Color.DKGRAY)
                        .setButtonColor(Color.BLACK)
                        .setButtonBorderColor(Color.WHITE)
                        .setButtonTextColor(Color.WHITE);
                View nativeAdView = NativeAdView.render(context, nativeAd, viewAttributes);
                linearLayout.addView(nativeAdView, new LinearLayout.LayoutParams(MATCH_PARENT, 800));
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }

        });
        nativeAd.loadAd();


    }

    @Override
    public void Show_NativeBanner(final Context context, final LinearLayout linearLayout ) {


        nativeBannerAd = new NativeBannerAd(context, NativeBanner_Unite);
        nativeBannerAd.buildLoadAdConfig().withAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                NativeAdViewAttributes viewAttributes = new NativeAdViewAttributes(context)
                        .setBackgroundColor(Color.WHITE)
                        .setTitleTextColor(Color.BLACK)
                        .setDescriptionTextColor(Color.DKGRAY)
                        .setButtonColor(Color.BLACK)
                        .setButtonBorderColor(Color.WHITE)
                        .setButtonTextColor(Color.WHITE);
                View adView = NativeBannerAdView.render(context, nativeBannerAd, NativeBannerAdView.Type.HEIGHT_100, viewAttributes);
                linearLayout.addView(adView);
            }

            @Override
            public void onAdClicked(Ad ad) {

            }

            @Override
            public void onLoggingImpression(Ad ad) {

            }
        });
        nativeBannerAd.loadAd();

    }

    @Override
    public void LoadReward(Context context) {
        rewardedVideoAd = new RewardedVideoAd(context, RewardVideoId);
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(new RewardedVideoAdListener() {
                            @Override
                            public void onRewardedVideoCompleted() {

                            }

                            @Override
                            public void onRewardedVideoClosed() {

                            }

                            @Override
                            public void onError(Ad ad, AdError adError) {

                            }

                            @Override
                            public void onAdLoaded(Ad ad) {

                            }

                            @Override
                            public void onAdClicked(Ad ad) {

                            }

                            @Override
                            public void onLoggingImpression(Ad ad) {

                            }
                        })
                        .build());
    }

    private RewardedVideoAd rewardedVideoAd1;
    @Override
    public void Show_Reward(Context context, Reward rewardA) {
            if (rewardedVideoAd != null){
               dialog = new progessDialog(context);
                rewardedVideoAd.loadAd(rewardedVideoAd.buildLoadAdConfig().withAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoCompleted() {
                        rewardA.Rewarded();
                        rewardedVideoAd = null;
                        if (dialog.isShowing()){ dialog.dismiss();}
                    }

                    @Override
                    public void onRewardedVideoClosed() {

                        if (dialog.isShowing()){ dialog.dismiss();}

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        rewardA.FieldToreward(adError.getErrorMessage());
                        if (dialog.isShowing()){ dialog.dismiss();}

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        rewardedVideoAd.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                }).build());
            }else {
                 rewardedVideoAd1 = new RewardedVideoAd(context,RewardVideoId);
                rewardedVideoAd1.loadAd(rewardedVideoAd1.buildLoadAdConfig().withAdListener(new RewardedVideoAdListener() {
                    @Override
                    public void onRewardedVideoCompleted() {
                        rewardA.Rewarded();
                        rewardedVideoAd1 = null;
                        if (dialog.isShowing()){ dialog.dismiss();}
                    }

                    @Override
                    public void onRewardedVideoClosed() {

                        if (dialog.isShowing()){ dialog.dismiss();}

                    }

                    @Override
                    public void onError(Ad ad, AdError adError) {
                        rewardA.FieldToreward(adError.getErrorMessage());
                        if (dialog.isShowing()){ dialog.dismiss();}

                    }

                    @Override
                    public void onAdLoaded(Ad ad) {
                        rewardedVideoAd1.show();
                    }

                    @Override
                    public void onAdClicked(Ad ad) {

                    }

                    @Override
                    public void onLoggingImpression(Ad ad) {

                    }
                }).build());

            }
    }

}
