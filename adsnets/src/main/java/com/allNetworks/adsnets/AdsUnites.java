package com.allNetworks.adsnets;

import android.content.Context;
import android.util.Log;

import com.allNetworks.Tools.GuideModel;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdsUnites {
    private  static NetworkUnitAd Admob;
    private static   NetworkUnitAd Unity;
    private static   NetworkUnitAd Facebook;
    private static   NetworkUnitAd Applovin;
    public static int interCounterShow;
    private static NetworkUnitAd Yandex;
    private static JSONObject facebook,admob,yandex,applovin,unity;
    public static String OneSignalKey,newAplink;
    private boolean IsUnder,IsOnApp, isIsp;
    private ArrayList<String> IspList;
    public AdsUnites() {
    }
    public AdsUnites(Context Mycontext, String url,JsonListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //////////////////////////
                IspList = new ArrayList<>();
                OneSignalKey = response.optString("OneSignalKey");
                interCounterShow = response.optInt("InterCounter");
                IsUnder = response.optBoolean("under");
                IsOnApp = response.optBoolean("AppOn");
                JSONArray isp = response.optJSONArray("IspNet");
                facebook = response.optJSONObject("Facebook");
                admob = response.optJSONObject("Admob");
                yandex = response.optJSONObject("Yandex");
                applovin = response.optJSONObject("Applovin");
                unity = response.optJSONObject("Unity");
                newAplink = response.optString("NewApp");
                JSONArray GuideList = response.optJSONArray("Guide");
                if (GuideList != null){
                    Guide.Guides = getGuide(GuideList);
                }else {
                    Guide.Guides = new ArrayList<>();
                }
                if (admob != null) {
                    Admob = getAdUnite(admob);
                }
                if (facebook != null)
                    Facebook = getAdUnite(facebook);
                if (yandex != null) {
                    Yandex = getAdUnite(yandex);
                }
                if (applovin != null) {
                    Applovin = getAdUnite(applovin);
                }
                if (unity != null) {
                    Unity = getAdUnite(unity);
                }

                if (isp != null) {
                    for (int i = 0; i < isp.length(); i++) {
                        String d = isp.optString(i);
                       IspList.add(d);

                    }}
                    Ads.ads = Switch();
                    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
                    OneSignal.initWithContext(Mycontext);
                    OneSignal.setAppId(OneSignalKey);
                    OneSignal.promptForPushNotifications();
                if (IsUnder) {
                    listener.isUnder();
                }else if (!IsOnApp){
                    listener.isAppOff();
                }else {
                    new ConnectionInfo(new ConnectionInfo.ConnectionInfoListener() {
                        @Override
                        public void onConnectionInfoReceived(ArrayList<String> result) {
                            for (String isp: result) {
                                for (String json: IspList) {
                                    if (isp.contains(json)){
                                        isIsp =true;
                                        break;
                                    }else {
                                        isIsp = false;
                                    }
                                }
                                if (isIsp){
                                    break;
                                }
                            }
                            if (!isIsp){
                                try {
                                    Ads.ads.init(Mycontext);
                                } catch (IllegalArgumentException exception) {
                                    Ads.ads = Null.getInstance();
                                    Log.e("AdsError", exception.getMessage());
                                }

                                listener.isloaded();

                                }
                            else {
                                listener.isIspDeteceted();
                            }


                        }

                        @Override
                        public void onConnectionInfoError(String message) {

                        }
                    }).retrieveConnectionInfo();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if (error instanceof NetworkError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
                }
                Log.d("message", "onErrorResponse: "+message);
                listener.fieldtoLoad(message);
            }
        });
        Volley.newRequestQueue(Mycontext).add(request);}

    /////////////////////////////////////////////////////////
 public static AdsManage Switch(){
        if (yandex != null &&Yandex.isOn()){
            return YandexClass.getInstance(Yandex);
        } else if (facebook != null &&Facebook.isOn()){
            return FaceAds.getInstance(Facebook);
        }else if (admob != null && Admob.isOn()){
            return Adsadmob.getInstance(Admob);
        }else if (unity != null && Unity.isOn()){
            return UnityAd.getInstance(Unity);
        }else if (applovin != null && Applovin.isOn()) {
            return ApplovinAd.getInstance(Applovin);
        } else{
             throw new IllegalArgumentException("No networks Added");

        }
    }
    private NetworkUnitAd getAdUnite(JSONObject json){
        NetworkUnitAd unitAd = new NetworkUnitAd();
        unitAd.setOn(json.optBoolean("isOn"));
        unitAd.setAPP_ID(json.optString("AppId"));
        unitAd.setOPEN_APP_ID(json.optString("OpenApp"));
        unitAd.setBANNER_Id(json.optString("bannerId"));
        unitAd.setINTERSTITIAL_Id(json.optString("InterId"));
        unitAd.setNATIVE_Id(json.optString("NativeId"));
        unitAd.setNATIVE_BANNER_Id(json.optString("NativeBannerId"));
        unitAd.setRewardVideoId(json.optString("RewardId"));
        return unitAd;
    }

    private ArrayList<GuideModel> getGuide(JSONArray array){
        ArrayList < GuideModel> guides = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject guide = array.optJSONObject(i);
            String Title = guide.optString("Title");
            String Content = guide.optString("Content");
            String Img = guide.optString("Img");
            guides.add(new GuideModel(Title,Content,Img));

        }
        return guides;
    }

    public interface JsonListener{
        void isloaded();
        void fieldtoLoad(String error);
        void isUnder();
        void isAppOff();
        void isIspDeteceted();
    }


}
