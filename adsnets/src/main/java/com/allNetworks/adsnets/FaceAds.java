package com.allNetworks.adsnets;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

public class FaceAds implements AdsManage {

    private static FaceAds facebook;
    private InterstitialAd interstitialAd;
    private ProgressDialog dialog;
    private NativeBannerAd nativeBannerAd;
    private NativeAd nativeAd;
    private AdView FBadView;
    private String BannerUnit ="";
    private String Interstitial_Unit="";
    private String Native_Unite = "";
    private String NativeBanner_Unite = "";
    public static FaceAds getInstance(NetworkUnitAd unitAd) {
        if (facebook ==null){
            facebook = new FaceAds();
            facebook.BannerUnit = unitAd.getBANNER_Id();
            facebook.Interstitial_Unit = unitAd.getINTERSTITIAL_Id();
            facebook.Native_Unite = unitAd.getNATIVE_Id();
            facebook.NativeBanner_Unite = unitAd.getNATIVE_BANNER_Id();
        }
        return facebook;
    }

    @Override
    public void init(Context context) {
        AudienceNetworkAds.initialize(context);

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

        FBadView = new com.facebook.ads.AdView(activity,BannerUnit, AdSize.BANNER_HEIGHT_50);
        linearLayout.addView(FBadView);

        FBadView.loadAd();
    }
    private boolean Isloaded;
    @Override
    public void Show_Interstitial(Context context, Interstital interstital) {
        initDialog(context);
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



    private class FBInterstitialAdListener implements InterstitialAdListener {

        Context mContext;

        Intent mIntent;

        private FBInterstitialAdListener(Context nActivity, Intent nIntent) {
            mContext = nActivity;

            mIntent = nIntent;
        }

        @Override
        public void onAdClicked(Ad ad) {
        }

        @Override
        public void onInterstitialDisplayed(Ad ad) {
        }

        @Override
        public void onLoggingImpression(Ad ad) {
        }

        @Override
        public void onInterstitialDismissed(Ad ad) {

            if ( dialog !=null && dialog.isShowing()) {
                dialog.dismiss(); }
            mContext.startActivity(mIntent);
        }
        @Override
        public void onError(Ad ad, AdError adError) {

            if ( dialog !=null && dialog.isShowing()) {
                dialog.dismiss();
            }

           mContext.startActivity(mIntent);
        }

        @Override
        public void onAdLoaded(Ad ad) {
            if (interstitialAd.isAdLoaded())
                interstitialAd.show();
        }
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

}
