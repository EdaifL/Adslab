package com.allNetworks.adsnets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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



public class Adsadmob implements AdsManage{
    //region asabat
    private static Adsadmob admob;
    private InterstitialAd mInterstitialAd;
    private ProgressDialog dialog;
    private String BannerUnit ;
    private String InterstitialUnit ;
    private String NativeUnit ;
    private NativeAd nativeAd;
    private String appId;
    private  int Requests = 0;
    private  float Percentage;
    //endregion
    Activity activity;
    public static Adsadmob getInstance(AdsUnites config){
        if (admob ==null){
            admob =new Adsadmob();
            admob.BannerUnit = config.getBanner_id();
            admob.InterstitialUnit = config.getInterstitial_id();
            admob.NativeUnit = config.getNative_Id();
            admob.appId = config.getAppId();
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
    public void Show_OpenApp(Context context) {

    }

    @Override
    public void Show_Banner(Activity activity, LinearLayout linearLayout) {
        AdView adView = new AdView(activity);
        adView.setAdSize(AdSize.LARGE_BANNER);
        adView.setAdUnitId(BannerUnit);
        AdRequest adRequest = new AdRequest.Builder().build();
        //Todo check this out

        adView.loadAd(adRequest);
        linearLayout.addView(adView);
    }
    @Override
    public void Show_Interstitial(Context context, Intent MIntent) {
        initDialog(context);
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context,InterstitialUnit,adRequest, new InterstitialAdLoadCallback(){
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                if (dialog.isShowing())
                    dialog.dismiss();
                mInterstitialAd = interstitialAd;
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                    }

                    @Override
                    public void onAdDismissedFullScreenContent() {
                        if (dialog.isShowing()){
                            dialog.dismiss();
                        }

                            context.startActivity(MIntent);

                    }
                });
                mInterstitialAd.show((Activity) context);

            }
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                if (dialog.isShowing())
                    dialog.dismiss();

                    context.startActivity(MIntent);


            }
        });
    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout , ImageView imageView) {
     new nativeadmob().NativeAds(context,linearLayout,imageView);
  }
    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {



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
        public void NativeAds(Context context , LinearLayout linearLayout, ImageView imageView) {
            FrameLayout frameLayout = new FrameLayout(context);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            frameLayout.setLayoutParams(lp);
            linearLayout.addView(frameLayout);
            AdLoader.Builder builder = new AdLoader.Builder(context, NativeUnit);
            builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                @Override
                public void onNativeAdLoaded(NativeAd unifiedNativeAd) {
                    if (nativeAd != null) {
                        nativeAd.destroy();
                    }
                    if (imageView != null){imageView.setVisibility(View.GONE);}
                    nativeAd = unifiedNativeAd;

                    activity = (Activity)context;
                    NativeAdView adView = (NativeAdView) activity.getLayoutInflater().inflate(R.layout.native_ad_unified, null);

                    populateUnifiedNativeAdView(unifiedNativeAd, adView);
                    frameLayout.removeAllViews();
                    frameLayout.addView(adView);

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
