package com.allNetworks.adsnets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.UnityAdsLoadOptions;
import com.unity3d.ads.UnityAdsShowOptions;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class UnityAd implements AdsManage {
    String AppId;
    private static UnityAd unity;
    private Dialog dialog;
    private String BannerUnit;
    private String InterstitialUnit;
    private String RewardVideoId;

    private static   int Requests = 0;
    private final Boolean TestMode = false;



    public static UnityAd getInstance(NetworkUnitAd unitAd) {
        if (unity == null) {
            unity = new UnityAd();
            unity.AppId = unitAd.getAPP_ID();
            unity.BannerUnit = unitAd.getBANNER_Id();
            unity.InterstitialUnit = unitAd.getINTERSTITIAL_Id();
            unity.RewardVideoId = unitAd.getRewardVideoId();
        }
        return unity;
    }

    @Override
    public void init(Context context) {

        UnityAds.initialize(context, AppId, TestMode);
    }
    @Override
    public void Show_OpenApp(Context context) {

    }
    @Override
    public void Show_Banner(Activity activity, LinearLayout linearLayout) {
        BannerView topBanner = new BannerView(activity, BannerUnit, new UnityBannerSize(320, 50));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.addView(topBanner);
        topBanner.setListener(new BAnnerAdsLoadListner());
        topBanner.load();

    }
    private boolean isLoaded;
    @Override
    public void Show_Interstitial(Context context, Interstital interstital) {
       if (Requests +1 ==AdsUnites.interCounterShow){
        dialog = new progessDialog(context);
        dialog.show();
        if (!isLoaded){
        loadInter(context);
        UnityAds.load(InterstitialUnit, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String s) {
                UnityAds.show((Activity) context, InterstitialUnit , new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                       if (dialog.isShowing()){ dialog.dismiss();}
                       interstital.fieldToShow();

                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                        interstital.isShowed();
                        if (dialog.isShowing()){dialog.dismiss(); }
                        isLoaded = false;
                        Requests =0;

                    }
                });
            }

            @Override
            public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {
               interstital.fieldToShow();

                if (dialog.isShowing()){dialog.dismiss(); }
            }
        });
        }else {
            UnityAds.show((Activity) context, InterstitialUnit , new UnityAdsShowOptions(), new IUnityAdsShowListener() {
                @Override
                public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                    if (dialog.isShowing()){ dialog.dismiss();}
                    interstital.fieldToShow();

                }

                @Override
                public void onUnityAdsShowStart(String placementId) {

                }

                @Override
                public void onUnityAdsShowClick(String placementId) {

                }

                @Override
                public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                    interstital.isShowed();
                    if (dialog.isShowing()){dialog.dismiss(); }
                    isLoaded = false;
                    Requests =0;

                }
            });
        }
       }else {
           Requests +=1;
           interstital.isShowed();
       }
    }
    @Override
    public boolean loadInter(Context context) {
        UnityAds.load(InterstitialUnit, new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String s) {
             isLoaded = true;
            }

            @Override
            public void onUnityAdsFailedToLoad(String s, UnityAds.UnityAdsLoadError unityAdsLoadError, String s1) {
                isLoaded = false;
            }
        });
        return isLoaded;
    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView) {

    }
    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {

    }
    @Override
    public void LoadReward(Context context) {
        UnityAds.load(RewardVideoId, new UnityAdsLoadOptions(), new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {

            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {

            }
        });
    }
    @Override
    public void Show_Reward(Context context, Reward rewardA) {
       dialog = new progessDialog(context);
       dialog.show();
        UnityAds.load(RewardVideoId, new UnityAdsLoadOptions(), new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                UnityAds.show((Activity) context, RewardVideoId, new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                        rewardA.FieldToreward(message);
                        if ( dialog.isShowing()){dialog.dismiss();}
                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                        rewardA.Rewarded();
                        LoadReward(context);
                        if (dialog.isShowing()){ dialog.dismiss();}
                    }
                });
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                rewardA.FieldToreward(message);
            }
        });
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
