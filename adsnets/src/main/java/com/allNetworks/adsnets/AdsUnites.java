package com.allNetworks.adsnets;

import android.content.Context;
import android.util.Log;

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

import org.json.JSONObject;

public class AdsUnites {

    private  static NetworkUnitAd Admob;
    private static   NetworkUnitAd Unity;
    private static   NetworkUnitAd Facebook;
    private static   NetworkUnitAd Applovin;
    public static int interCounterShow;
    private static NetworkUnitAd Yandex;
    private static JSONObject facebook,admob,yandex,applovin,unity;
    public static String OneSignalKey;
    private boolean IsUnder,IsOnApp;

    public AdsUnites() {
    }

    public AdsUnites(Context Mycontext, String url,JsonListener listener) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //////////////////////////

                OneSignalKey = response.optString("OneSignalKey");
                interCounterShow = response.optInt("InterCounter");
                IsUnder = response.optBoolean("under");
                IsOnApp = response.optBoolean("AppOn");
                if (IsOnApp || !IsUnder){
                 facebook = response.optJSONObject("Facebook");
                 admob = response.optJSONObject("Admob");
                 yandex = response.optJSONObject("Yandex");
                 applovin = response.optJSONObject("Applovin");
                 unity = response.optJSONObject("Unity");
                if (admob != null){
                    Admob = getAdUnite(admob);}
                if (facebook != null)
                    Facebook = getAdUnite(facebook);
                if (yandex != null){
                    Yandex = getAdUnite(yandex);}
                if (applovin != null){
                    Applovin = getAdUnite(applovin);}
                if (unity != null){
                    Unity = getAdUnite(unity);}

                try {
                    Ads.ads = Switch();
                    Ads.ads.init(Mycontext);
                }catch (IllegalArgumentException exception){
                    Ads.ads = Null.getInstance();
                    Log.e("AdsError",exception.getMessage());
                }
                listener.isloaded();
                } else if (IsUnder) {
                    listener.isUnder();
                }else if (!IsOnApp){
                    listener.isAppOff();
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
        return unitAd;


    }
    public interface JsonListener{
        void isloaded();
        void fieldtoLoad(String error);
        void isUnder();
        void isAppOff();
    }

}
