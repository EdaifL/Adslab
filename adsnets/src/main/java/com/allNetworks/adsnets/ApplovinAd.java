package com.allNetworks.adsnets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.concurrent.TimeUnit;


public class ApplovinAd implements AdsManage {
    private MaxAppOpenAd appOpenAd;
    private static boolean IsOpenshowed = false;
    private MaxAdView adView;
    private MaxInterstitialAd interstitialAd;
    private Dialog dialog;
    private String LogTag = "Applovin";
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;
    private String OpenAppId,AppId;
    private String BannerUnit;
    private String Interstitial_Unit;
    private String Native_Unite;
    private String TAG = "Applovin";
    private static int Requests = 0;
    public static ApplovinAd applovinAd;
    public static ApplovinAd getInstance(NetworkUnitAd unitAd){
        if (applovinAd == null){
            applovinAd = new ApplovinAd();
            applovinAd.OpenAppId = unitAd.getOPEN_APP_ID();
            applovinAd.BannerUnit = unitAd.getBANNER_Id();
            applovinAd.AppId = unitAd.getAPP_ID();
            applovinAd.Interstitial_Unit = unitAd.getINTERSTITIAL_Id();
            applovinAd.Native_Unite = unitAd.getNATIVE_Id();
        }
        return applovinAd;
    }


    @Override
    public void init(Context context) {
        AppLovinSdk.getInstance( context ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( context);
    }



    @Override
    public void Show_OpenApp(Context context) {
        if (!IsOpenshowed && !OpenAppId.isEmpty()){
            dialog = new progessDialog(context);
            dialog.show();
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
        if (!BannerUnit.isEmpty()){
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
        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int heightPx = activity.getResources().getDimensionPixelSize( R.dimen.banner_height );
        adView.setLayoutParams( new ViewGroup.LayoutParams( width, heightPx ) );
        adView.setBackgroundColor(Color.WHITE);
        linearLayout.addView(adView);
        }

    }
    private int retryAttempt;
    @Override
    public void Show_Interstitial(Context context, Interstital interstital) {
        if (Requests +1 ==AdsUnites.interCounterShow) {
            dialog = new progessDialog(context);
            dialog.show();
            if (isLoaded) {
                interstitialAd.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        retryAttempt = 0;
                        interstitialAd.showAd(Interstitial_Unit);

                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        interstital.isShowed();

                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {

                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        interstital.fieldToShow();
                    }
                });
                interstitialAd.loadAd();
            } else {
                loadInter(context);
                MaxInterstitialAd interstitialAd2 = new MaxInterstitialAd(Interstitial_Unit, (Activity) context);
                interstitialAd2.setListener(new MaxAdListener() {
                    @Override
                    public void onAdLoaded(MaxAd ad) {
                        retryAttempt = 0;
                        interstitialAd2.showAd();
                    }

                    @Override
                    public void onAdDisplayed(MaxAd ad) {

                    }

                    @Override
                    public void onAdHidden(MaxAd ad) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        interstital.isShowed();
                        Requests =0;
                        isLoaded = false;


                    }

                    @Override
                    public void onAdClicked(MaxAd ad) {

                    }

                    @Override
                    public void onAdLoadFailed(String adUnitId, MaxError error) {
                        retryAttempt++;
                        long delayMillis = TimeUnit.SECONDS.toMillis((long) Math.pow(2, Math.min(6, retryAttempt)));

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                interstitialAd2.loadAd();
                            }
                        }, delayMillis);
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        interstital.fieldToShow();
                    }

                    @Override
                    public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        interstital.fieldToShow();
                    }
                });
                interstitialAd2.loadAd();
            }
        }else {
            Requests +=1;
            interstital.isShowed();
        }

    }
    private boolean isLoaded;
    @Override
    public boolean loadInter(Context context) {
        if (!Interstitial_Unit.isEmpty()){
            interstitialAd =new MaxInterstitialAd(Interstitial_Unit, (Activity) context);
            interstitialAd.setListener(new MaxAdListener() {
                @Override
                public void onAdLoaded(MaxAd ad) {
                    isLoaded = true;
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
                    retryAttempt++;
                    long delayMillis = TimeUnit.SECONDS.toMillis( (long) Math.pow( 2, Math.min( 6, retryAttempt ) ) );

                    new Handler().postDelayed( new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            interstitialAd.loadAd();
                        }
                    }, delayMillis );
                   isLoaded = false;
                }

                @Override
                public void onAdDisplayFailed(MaxAd ad, MaxError error) {

                }
            });
            interstitialAd.loadAd();}

        return isLoaded;
    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView) {
        if (!Native_Unite.isEmpty()){
        nativeAdLoader = new MaxNativeAdLoader( Native_Unite, context );
        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                if (imageView !=null){imageView.setVisibility(View.GONE);}
                nativeAd = ad;
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
        nativeAdLoader.loadAd();}

    }





    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {

    }

}
