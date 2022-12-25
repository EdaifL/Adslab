package com.test.adsnets;

public class AdsUnites {
    private static String TAG_NETWORK ;
    private static String TAG_BANNER ;
    private static String TAG_NATIVE_BANNER ;
    private static String TAG_NATIVE ;
    private static String TAG_INTERSTITIAL ;
    private static String TAG_APP_ID ;

    public AdsUnites(String TAG_NETWORK, String TAG_BANNER, String TAG_NATIVE_BANNER, String TAG_NATIVE, String TAG_INTERSTITIAL, String TAG_APP_ID) {
        this.TAG_NETWORK = TAG_NETWORK;
        this.TAG_BANNER = TAG_BANNER;
        this.TAG_NATIVE_BANNER = TAG_NATIVE_BANNER;
        this.TAG_NATIVE = TAG_NATIVE;
        this.TAG_INTERSTITIAL = TAG_INTERSTITIAL;
        this.TAG_APP_ID = TAG_APP_ID;

    }


    public String getAdNetwork() {
       return TAG_NETWORK;
    }

    public String getBanner_id() {
       return TAG_BANNER;
    }
    public String getNativeBanner_id() {
       return TAG_NATIVE_BANNER;
    }
    public String getInterstitial_id() {
        return TAG_INTERSTITIAL;
    }
    public String getNative_Id() {
       return TAG_NATIVE;
    }
    public String getAppId(){
        return TAG_APP_ID;
    }

    public AdsUnites() {
    }

    /////////////////////////////////////////////////////////
 public static AdsManage Switch(){
       AdsUnites config = new AdsUnites();
        if (config.getAdNetwork().toLowerCase().equals("yandex")){
            return YandexClass.getInstance(config);
        } else if (config.getAdNetwork().toLowerCase().equals("facebook")){
            return FaceAds.getInstance(config);
        }else if (config.getAdNetwork().toLowerCase().equals("admob")){
            return Adsadmob.getInstance(config);
        }else if (config.getAdNetwork().toLowerCase().equals("unity")){
            return unity.getInstance(config);
        }else if (config.getAdNetwork().toLowerCase().equals("pangle")){
            return PangleAd.getInstance(config);
        } else{
            throw  new IllegalArgumentException("Not Valid Ads  Network");
        }
    }

}
