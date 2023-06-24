package com.allNetworks.adsnets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdOptions;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;


public class Adsadmob implements AdsManage{
    private static Adsadmob admob;
    private RewardedAd rewardedAd;
    private InterstitialAd mInterstitialAd;
    private Dialog dialog;
    private String BannerUnit ,InterstitialUnit ,NativeUnit,RewardVideoId;
    private NativeAd nativeAd;
    private AdRequest adRequest ;
    private String appId;
    private static   int Requests = 0;

    Activity activity;
    public static Adsadmob getInstance(NetworkUnitAd unitAd){
        if (admob ==null){
            admob =new Adsadmob();
            admob.BannerUnit = unitAd.getBANNER_Id();
            admob.InterstitialUnit = unitAd.getINTERSTITIAL_Id();
            admob.NativeUnit = unitAd.getNATIVE_Id();
            admob.appId = unitAd.getAPP_ID();
            admob.RewardVideoId = unitAd.getRewardVideoId();
        }
        return admob;
    }

    @Override
    public void init(Context context) {
        String TAG = "admob";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String myApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
            Log.d(TAG, "Name Found: " + myApiKey);
            ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", appId);
            String ApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");
            Log.d(TAG, "ReNamed Found: " + ApiKey);

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adRequest = new AdRequest.Builder().build();
    }


    @Override
    public void Show_OpenApp(Context context) {

    }

    @Override
    public void Show_Banner(Activity activity, LinearLayout linearLayout) {
        AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.LARGE_BANNER);
        adView.setAdUnitId(BannerUnit);
        //Todo check this out

        adView.loadAd(adRequest);
        linearLayout.addView(adView);
    }
    private boolean isloaded = false;
    @Override
    public void Show_Interstitial(Context context, Interstital interstital) {
        if (Requests+1 == AdsUnites.interCounterShow) {
            dialog = new progessDialog(context);
            dialog.show();
            if (isloaded) {
                mInterstitialAd.show((Activity) context);
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        isloaded = false;
                        interstital.isShowed();
                        Requests =0;
                    }
                });
            } else {
                loadInter(context);
                InterstitialAd.load(context, InterstitialUnit, adRequest, new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                        interstitialAd.show((Activity) context);
                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                                isloaded =false;
                                interstital.isShowed();
                                Requests =0;

                            }
                        });
                    }

                    @SuppressLint("SuspiciousIndentation")
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        interstital.fieldToShow();
                    }
                });
            }
        }
        else {
            Requests +=1;
            interstital.isShowed();
        }
    }

    @Override
    public boolean loadInter(Context context) {
        InterstitialAd.load(context,InterstitialUnit,adRequest, new InterstitialAdLoadCallback(){
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {

                mInterstitialAd = interstitialAd;
                isloaded =true;
            }
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                isloaded = false;
            }
        });
        return isloaded;
    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout ) {
     new nativeadmob().NativeAds(context,linearLayout);
  }
    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {



    }

    @Override
    public void Show_Reward(Context context, Reward rewardA) {
        dialog = new progessDialog(context);
        dialog.show();
        if (rewardedAd != null) {
            rewardedAd.show((Activity) context, rewardItem -> {});
            rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {


                }

                @Override
                public void onAdDismissedFullScreenContent() {

                    rewardedAd = null;
                    rewardA.Rewarded();
                    if (dialog.isShowing()){ dialog.dismiss();}
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    rewardedAd = null;
                    rewardA.FieldToreward(adError.getMessage());
                    if (dialog.isShowing()){ dialog.dismiss();}

                }

                @Override
                public void onAdImpression() {

                }

                @Override
                public void onAdShowedFullScreenContent() {

                }
            });

        } else {
            LoadReward(context);
            AdRequest adRequest = new AdRequest.Builder().build();
            RewardedAd.load(context, RewardVideoId,
                    adRequest, new RewardedAdLoadCallback() {
                        @Override
                        public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                            rewardA.FieldToreward(loadAdError.getMessage());
                        }

                        @Override
                        public void onAdLoaded(@NonNull RewardedAd ad) {
                            ad.show((Activity) context, rewardItem -> {});
                            ad.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdClicked() {


                                }

                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    rewardA.Rewarded();
                                    if (dialog.isShowing()){ dialog.dismiss();}

                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    rewardA.FieldToreward(adError.getMessage());
                                    if (dialog.isShowing()){ dialog.dismiss();}

                                }

                                @Override
                                public void onAdImpression() {

                                }

                                @Override
                                public void onAdShowedFullScreenContent() {

                                }
                            });
                        }
                    });

        }

    }

    @Override
    public void LoadReward(Context context) {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, RewardVideoId,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        rewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                        ServerSideVerificationOptions options = new ServerSideVerificationOptions
                                .Builder()
                                .setCustomData("SAMPLE_CUSTOM_DATA_STRING")
                                .build();
                        rewardedAd.setServerSideVerificationOptions(options);
                    }
                });

    }

    private class nativeadmob extends AppCompatActivity {
        private void populateUnifiedNativeAdView(NativeAd nativeAd, NativeAdView adView) {

            adView.setMediaView( adView.findViewById(R.id.ad_media));
            adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
            adView.setBodyView(adView.findViewById(R.id.ad_body));
            adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
            adView.setIconView(adView.findViewById(R.id.ad_app_icon));
            adView.setPriceView(adView.findViewById(R.id.ad_price));
            adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
            adView.setStoreView(adView.findViewById(R.id.ad_store));
            adView.setStarRatingView(adView.findViewById(R.id.ad_stars));

            ((TextView)adView.getHeadlineView()).setText(nativeAd.getHeadline());
            adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

            if (nativeAd.getBody() == null) {
                adView.getBodyView().setVisibility(View.INVISIBLE);
            } else {
                adView.getBodyView().setVisibility(View.VISIBLE);
                ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
            }
            if (nativeAd.getCallToAction() == null) {
                adView.getCallToActionView().setVisibility(View.INVISIBLE);
            } else {
                adView.getCallToActionView().setVisibility(View.VISIBLE);
                ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
            }
            if (nativeAd.getIcon() == null) {
                adView.getIconView().setVisibility(View.GONE);
            } else {
                ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
                adView.getIconView().setVisibility(View.VISIBLE);
            }
            if (nativeAd.getPrice() == null) {
                adView.getPriceView().setVisibility(View.INVISIBLE);
            } else {
                adView.getPriceView().setVisibility(View.VISIBLE);
                ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
            }
            if (nativeAd.getStore() == null) {
                adView.getStoreView().setVisibility(View.INVISIBLE);
            } else {
                adView.getStoreView().setVisibility(View.VISIBLE);
                ((TextView) adView.getStoreView()).setText(nativeAd.getStore());

            }
            if (nativeAd.getStarRating() == null) {
                adView.getStarRatingView().setVisibility(View.INVISIBLE);
            } else {
                ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
                adView.getStarRatingView().setVisibility(View.VISIBLE);
            }
            if (nativeAd.getAdvertiser() == null) {
                adView.getAdvertiserView().setVisibility(View.INVISIBLE);

            } else {
                ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
                adView.getAdvertiserView().setVisibility(View.VISIBLE);
            }
            adView.setNativeAd(nativeAd);
        }
        private void NativeBannerAdview(NativeAd nativeAd, NativeAdView adView){

        }
        public void NativeAds(Context context , LinearLayout linearLayout) {
            FrameLayout frameLayout = new FrameLayout(context);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            frameLayout.setLayoutParams(lp);

            AdLoader.Builder builder = new AdLoader.Builder(context, NativeUnit);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(NativeAd unifiedNativeAd) {
                    if (nativeAd != null) {
                        nativeAd.destroy();
                    }

                    nativeAd = unifiedNativeAd;

                    activity = (Activity)context;
                    NativeAdView adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ad_unified, null);

                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);
                    linearLayout.removeAllViews();
                    linearLayout.addView(frameLayout);

                }
            });

            NativeAdOptions adOptions = new NativeAdOptions.Builder().build();
            builder.withNativeAdOptions(adOptions);
            AdLoader adLoader = builder.withAdListener (new AdListener(){


            }).build();
            adLoader.loadAd(new AdRequest.Builder().build());

        }
        public void NativeBannerAds(Context context , LinearLayout linearLayout){

        }

    }

}
