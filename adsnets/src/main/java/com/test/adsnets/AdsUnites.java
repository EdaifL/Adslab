package com.test.adsnets;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

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
    private static String TAG_NETWORK ;
    private static String TAG_BANNER ;
    private static String TAG_NATIVE_BANNER ;
    private static String TAG_NATIVE ;
    private static String TAG_INTERSTITIAL ;
    private static String TAG_APP_ID ;
    private static String TAG_OPEN_APP_ID;


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

    public AdsUnites(Context Mycontext, String url) {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //////////////////////////

                JSONObject ads = response.optJSONObject("Ads_Config");
                TAG_NETWORK  = ads.optString("Network");
                TAG_BANNER =  ads.optString("BannerId");
                TAG_INTERSTITIAL = ads.optString("InterId");
                TAG_NATIVE = ads.optString("NativeId");
                TAG_NATIVE_BANNER = ads.optString("NativeBannerId");
                TAG_APP_ID = ads.optString("AppId");
                TAG_OPEN_APP_ID = ads.optString("OpenAppId");
                Ads.ads = Switch();
                Ads.ads.init(Mycontext);


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

                new AlertDialog.Builder(Mycontext)
                        .setTitle("Error")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = Mycontext.getPackageManager().
                                        getLaunchIntentForPackage(Mycontext.getPackageName());
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Mycontext.startActivity(i);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Activity activity = (Activity) Mycontext;
                                activity.finishAffinity();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert).setCancelable(false)
                        .show();
            }
        });
        Volley.newRequestQueue(Mycontext).add(request);}


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
