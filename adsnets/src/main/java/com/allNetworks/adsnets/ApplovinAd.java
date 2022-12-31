package com.allNetworks.adsnets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxAppOpenAd;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.mediation.nativeAds.MaxNativeAdViewBinder;
import com.applovin.sdk.AppLovinSdk;



public class ApplovinAd implements AdsManage {
    private MaxAppOpenAd appOpenAd;
    private static boolean IsOpenshowed = false;
    private MaxAdView adView;
    MaxInterstitialAd interstitialAd;
    ProgressDialog dialog;
    private String LogTag = "Applovin";
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd             nativeAd;
    private String OpenAppId;
    private String BannerUnit;
    private String Interstitial_Unit;
    private String Native_Unite;
    public static ApplovinAd applovinAd;
    public static ApplovinAd getInstance(AdsUnites adsUnites){
        if (applovinAd == null){
            applovinAd = new ApplovinAd();
            applovinAd.OpenAppId = adsUnites.getTagOpenAppId();
            applovinAd.BannerUnit = adsUnites.getBanner_id();
            applovinAd.Interstitial_Unit = adsUnites.getInterstitial_id();
            applovinAd.Native_Unite = adsUnites.getNative_Id();
        }
        return applovinAd;
    }


    @Override
    public void init(Context context) {
        AppLovinSdk.getInstance( context ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( context);
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
        if (!IsOpenshowed){
            initDialog(context);
            appOpenAd = new MaxAppOpenAd( OpenAppId, context);
            appOpenAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    if ( appOpenAd == null || !AppLovinSdk.getInstance( context ).isInitialized() ) return;

                    if ( appOpenAd.isReady() )
                    {
                        appOpenAd.showAd( OpenAppId );
                    }
                    else
                    {
                        appOpenAd.loadAd();
                    }
                }

                @Override
                public void onAdDisplayed(MaxAd ad) {
                    if (dialog.isShowing()){dialog.dismiss();}
                    IsOpenshowed =true;
                }

                @Override
                public void onAdHidden(MaxAd ad) {

                }

                @Override
                public void onAdClicked(MaxAd ad) {

                }

                @Override
                public void onAdLoadFailed(String adUnitId, MaxError error) {
                    appOpenAd.loadAd();
                    if (dialog.isShowing()){dialog.dismiss();}
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                    appOpenAd.loadAd();
                    if (dialog.isShowing()){dialog.dismiss();}
                }
            });
            appOpenAd.loadAd();
        }
    }

    @Override
    public void Show_Banner(Activity activity, LinearLayout linearLayout) {
        adView = new MaxAdView( BannerUnit, activity );
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int heightPx = activity.getResources().getDimensionPixelSize( R.dimen.banner_height );
                adView.setLayoutParams( new FrameLayout.LayoutParams( width, heightPx ) );
                adView.setBackgroundColor(Color.WHITE);
                adView.startAutoRefresh();
                linearLayout.addView(adView);

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });

        adView.loadAd();
    }

    @Override
    public void Show_Interstitial(Context context, Intent MIntent) {
        initDialog(context);
        interstitialAd =new MaxInterstitialAd(Interstitial_Unit, (Activity) context);
        interstitialAd.loadAd();
        interstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {
                interstitialAd.showAd(Interstitial_Unit);
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

                if (dialog.isShowing()){dialog.dismiss();}
                context.startActivity(MIntent);
                if (adView != null){adView = null;}

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                if (dialog.isShowing()){dialog.dismiss();}
                context.startActivity(MIntent);
                if (adView != null){adView = null;}
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                if (dialog.isShowing()){dialog.dismiss();}
                context.startActivity(MIntent);
                if (adView != null){adView = null;}
            }
        });
    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView) {
        nativeAdLoader = new MaxNativeAdLoader( Native_Unite, context );
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                if (imageView !=null){imageView.setVisibility(View.GONE);}
                nativeAd = ad;
                linearLayout.removeAllViews();
                linearLayout.addView( nativeAdView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900) );

            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {

            }
        } );
       MaxNativeAdView max =  new MaxNativeAdView(new MaxNativeAdViewBinder.Builder(R.layout.applovinnative )
                .setTitleTextViewId( R.id.title_text_view )
                .setBodyTextViewId( R.id.body_text_view )
                .setAdvertiserTextViewId( R.id.advertiser_textView )
                .setIconImageViewId( R.id.icon_image_view )
                .setMediaContentViewGroupId( R.id.media_view_container )
                .setOptionsContentViewGroupId( R.id.ad_options_view )
                .setCallToActionButtonId( R.id.cta_button )
                .build(),context);
        nativeAdLoader.loadAd();
    }





    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {

    }

}
