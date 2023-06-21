package com.allNetworks.adsnets;

public class NetworkUnitAd {
    private  boolean isOn;
    private  String BANNER_Id ;
    private  String NATIVE_BANNER_Id ;
    private  String NATIVE_Id ;
    private  String INTERSTITIAL_Id ;
    private  String APP_ID;
    private  String OPEN_APP_ID;
    private String RewardVideoId;

    public NetworkUnitAd() {
    }

    public NetworkUnitAd(boolean isOn, String BANNER_Id, String NATIVE_BANNER_Id, String NATIVE_Id, String INTERSTITIAL_Id, String APP_ID, String OPEN_APP_ID, String rewardVideoId) {
        this.isOn = isOn;
        this.BANNER_Id = BANNER_Id;
        this.NATIVE_BANNER_Id = NATIVE_BANNER_Id;
        this.NATIVE_Id = NATIVE_Id;
        this.INTERSTITIAL_Id = INTERSTITIAL_Id;
        this.APP_ID = APP_ID;
        this.OPEN_APP_ID = OPEN_APP_ID;
        this.RewardVideoId = rewardVideoId;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public String getBANNER_Id() {
        return BANNER_Id;
    }

    public void setBANNER_Id(String BANNER_Id) {
        this.BANNER_Id = BANNER_Id;
    }

    public String getNATIVE_BANNER_Id() {
        return NATIVE_BANNER_Id;
    }

    public void setNATIVE_BANNER_Id(String NATIVE_BANNER_Id) {
        this.NATIVE_BANNER_Id = NATIVE_BANNER_Id;
    }

    public String getRewardVideoId() {
        return RewardVideoId;
    }

    public void setRewardVideoId(String rewardVideoId) {
        RewardVideoId = rewardVideoId;
    }

    public String getNATIVE_Id() {
        return NATIVE_Id;
    }

    public void setNATIVE_Id(String NATIVE_Id) {
        this.NATIVE_Id = NATIVE_Id;
    }

    public String getINTERSTITIAL_Id() {
        return INTERSTITIAL_Id;
    }

    public void setINTERSTITIAL_Id(String INTERSTITIAL_Id) {
        this.INTERSTITIAL_Id = INTERSTITIAL_Id;
    }

    public String getAPP_ID() {
        return APP_ID;
    }

    public void setAPP_ID(String APP_ID) {
        this.APP_ID = APP_ID;
    }

    public String getOPEN_APP_ID() {
        return OPEN_APP_ID;
    }

    public void setOPEN_APP_ID(String OPEN_APP_ID) {
        this.OPEN_APP_ID = OPEN_APP_ID;
    }

    @Override
    public String toString() {
        return "NetworkUnitAd{" +
                "isOn=" + isOn +
                ", BANNER_Id='" + BANNER_Id + '\'' +
                ", NATIVE_BANNER_Id='" + NATIVE_BANNER_Id + '\'' +
                ", NATIVE_Id='" + NATIVE_Id + '\'' +
                ", INTERSTITIAL_Id='" + INTERSTITIAL_Id + '\'' +
                ", APP_ID='" + APP_ID + '\'' +
                ", OPEN_APP_ID='" + OPEN_APP_ID + '\'' +
                ", RewardVideoId='" + RewardVideoId + '\'' +
                '}';
    }

}
