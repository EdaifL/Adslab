package com.test.adsnets;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bytedance.sdk.openadsdk.api.PAGConstant;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAd;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerAdLoadListener;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerRequest;
import com.bytedance.sdk.openadsdk.api.banner.PAGBannerSize;
import com.bytedance.sdk.openadsdk.api.init.PAGConfig;
import com.bytedance.sdk.openadsdk.api.init.PAGSdk;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAd;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialAdLoadListener;
import com.bytedance.sdk.openadsdk.api.interstitial.PAGInterstitialRequest;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGImageItem;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAd;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdData;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeAdLoadListener;
import com.bytedance.sdk.openadsdk.api.nativeAd.PAGNativeRequest;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAd;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdInteractionListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenAdLoadListener;
import com.bytedance.sdk.openadsdk.api.open.PAGAppOpenRequest;

import java.util.ArrayList;
import java.util.List;


public class PangleAd implements AdsManage {
    private static PangleAd pangleAd;
    ProgressDialog dialog;
    static boolean openappShow = false;
    private String AppId;
    private String OpenAppId;
    private String BannerUnit ;
    private String InterstitialUnit ;
    private String NativeUnit ;
   public static PangleAd getInstance(AdsUnites config){
       if (pangleAd== null){
           pangleAd  = new PangleAd();
           pangleAd.AppId = config.getAppId();
           pangleAd.BannerUnit = config.getBanner_id();
           pangleAd.InterstitialUnit = config.getInterstitial_id();
           pangleAd.NativeUnit = config.getNative_Id();
       }
       return pangleAd;
   }

    private PAGConfig buildNewConfig(Context context) {
        return new PAGConfig.Builder()
                .appId(AppId)
                .debugLog(true)
                .supportMultiProcess(false)//If your app is a multi-process app, set this value to true
                .build();
    }

    public void ShowOpenApp(Activity activity){
        if (!openappShow){
        ProgressDialog dialog2 = new ProgressDialog(activity);
        dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog2.setMessage("Loading  Please wait...");
        dialog2.setIndeterminate(true);
        dialog2.setCanceledOnTouchOutside(false);
        dialog2.show();
        PAGAppOpenRequest request = new PAGAppOpenRequest();
        request.setTimeout(3000);
        PAGAppOpenAd.loadAd(OpenAppId, request, new PAGAppOpenAdLoadListener() {
            @Override
            public void onError(int code, String message) {
                dialog2.dismiss();
            }

            @Override
            public void onAdLoaded(PAGAppOpenAd appOpenAd) {
                appOpenAd.setAdInteractionListener(new PAGAppOpenAdInteractionListener(){

                    @Override
                    public void onAdShowed() {
                        openappShow = true;

                    }

                    @Override
                    public void onAdClicked() {

                    }

                    @Override
                    public void onAdDismissed() {
                        dialog2.dismiss();
                    }
                });
                appOpenAd.show(activity);
            }
        });
        }

    }
    static View nativeAdView;
    static List<View> creativeViewList = new ArrayList<>();
    static List<View> clickViewList = new ArrayList<>();
    static ImageView mDislike;
    private static void findADView(Context mContext, PAGNativeAdData adData){

        nativeAdView= LayoutInflater.from(mContext).inflate(R.layout.nativepandle, null);

        TextView mTitle = (TextView) nativeAdView.findViewById(R.id.ad_title);

        TextView mDescription = (TextView) nativeAdView.findViewById(R.id.ad_desc);

        ImageView mIcon = (ImageView) nativeAdView.findViewById(R.id.ad_icon);

        mDislike= (ImageView) nativeAdView.findViewById(R.id.ad_dislike);

        Button mCreativeButton = (Button) nativeAdView.findViewById(R.id.creative_btn);

        RelativeLayout mAdLogoView = (RelativeLayout) nativeAdView.findViewById(R.id.ad_logo);

        FrameLayout mImageOrVideoView = (FrameLayout) nativeAdView.findViewById(R.id.ad_video);

        mTitle.setText(adData.getTitle());

        mDescription.setText(adData.getDescription());
//icon
        PAGImageItem icon = adData.getIcon();
        if (icon != null && icon.getImageUrl() != null) {
            Glide.with(mContext).load(icon.getImageUrl()).into(mIcon);
        }
//set btn text
        mCreativeButton.setText(TextUtils.isEmpty(adData.getButtonText()) ? "Download" : adData.getButtonText());

//set pangle logo
        ImageView imageView = (ImageView) adData.getAdLogoView();
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        mAdLogoView.addView(imageView, lp);
        if (adData.getMediaView()!=null){
            mImageOrVideoView.addView(adData.getMediaView());}
        clickViewList.add(mCreativeButton);
        creativeViewList.add(mIcon);
        creativeViewList.add(mTitle);


    }

    @Override
    public void init(Context context) {
        PAGConfig pAGInitConfig = buildNewConfig(context);
        PAGConfig.setChildDirected(PAGConstant.PAGChildDirectedType.PAG_CHILD_DIRECTED_TYPE_CHILD);//Set the configuration of COPPA
        PAGConfig.getChildDirected();
        PAGConfig.setGDPRConsent(PAGConstant.PAGGDPRConsentType.PAG_GDPR_CONSENT_TYPE_CONSENT);//Set the configuration of GDPR
        PAGConfig.getGDPRConsent();//get the value of GDPR
        PAGConfig.setDoNotSell(PAGConstant.PAGDoNotSellType.PAG_DO_NOT_SELL_TYPE_NOT_SELL);//Set the configuration of CCPA
        PAGConfig.getDoNotSell();
        PAGSdk.init(context, pAGInitConfig, new PAGSdk.PAGInitCallback() {
            @Override
            public void success() {
                //load pangle ads after this method is triggered.
                Log.i("PangleTAG", "pangle init success: ");
            }

            @Override
            public void fail(int code, String msg) {
                Log.i("PangleTAG", "pangle init fail: " + code);
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
        PAGBannerRequest request = new PAGBannerRequest(PAGBannerSize.BANNER_W_320_H_50);
        PAGBannerAd.loadAd(BannerUnit, request,
                new PAGBannerAdLoadListener() {
                    @Override
                    public void onError(int i, String s) {
                        Log.e("PangleTAG",s);
                    }

                    @Override
                    public void onAdLoaded(PAGBannerAd pagBannerAd) {
                        if (pagBannerAd != null) {
                            linearLayout.addView(pagBannerAd.getBannerView());
                        }
                    }
                });
    }

    @Override
    public void Show_Interstitial(Context context, Intent MIntent) {
        initDialog(context);
        PAGInterstitialRequest request = new PAGInterstitialRequest();
        PAGInterstitialAd.loadAd(InterstitialUnit,
                request,
                new PAGInterstitialAdLoadListener() {
                    @Override
                    public void onError(int code, String message) {
                        Log.e("LOG",message);
                        if (dialog.isShowing()){ dialog.dismiss();}
                        context.startActivity(MIntent);
                    }

                    @Override
                    public void onAdLoaded(PAGInterstitialAd interstitialAd) {
                        if (interstitialAd != null) {
                            interstitialAd.setAdInteractionListener(new PAGInterstitialAdInteractionListener() {
                                @Override
                                public void onAdShowed() {
                                    Log.e("PangleTAG","AdsShowed");
                                }

                                @Override
                                public void onAdClicked() {

                                }

                                @Override
                                public void onAdDismissed() {
                                    Log.e("PangleTAG","Ads Dismissed");
                                    if (dialog.isShowing()){ dialog.dismiss();}
                                    context.startActivity(MIntent);

                                }
                            });
                            interstitialAd.show((Activity) context);

                        }
                    }

                });

    }

    @Override
    public void Show_Native(Context context, LinearLayout linearLayout, ImageView imageView) {
        PAGNativeRequest request = new PAGNativeRequest();
        PAGNativeAd.loadAd(NativeUnit, request, new PAGNativeAdLoadListener() {
            @Override
            public void onError(int code, String message) {
                Log.e("PangleTAG",message);
            }

            @Override
            public void onAdLoaded(PAGNativeAd pagNativeAd) {
                Log.e("PangleTAG","NativeadsLoaded");
                if (imageView != null){imageView.setVisibility(View.GONE);}
                findADView(context,pagNativeAd.getNativeAdData());


                pagNativeAd.registerViewForInteraction((ViewGroup) nativeAdView, clickViewList, creativeViewList, mDislike, new PAGNativeAdInteractionListener() {
                    @Override
                    public void onAdShowed() {

                    }

                    @Override
                    public void onAdClicked() {

                    }

                    @Override
                    public void onAdDismissed() {

                    }
                });
                linearLayout.addView(nativeAdView);
            }
        });
    }

    @Override
    public void Show_NativeBanner(Context context, LinearLayout linearLayout) {

    }
}
