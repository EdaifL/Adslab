package com.allNetworks.adslab;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.allNetworks.adsnets.Ads;
import com.allNetworks.adsnets.AdsManage;
import com.allNetworks.adsnets.AdsUnites;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();

        new AdsUnites(this, "https://imassine.store/api/application/com.Gittalng.LokLok", new AdsUnites.JsonListener() {
            @Override
            public void isloaded() {
                dialog.dismiss();
                Ads.ads.loadInter(MainActivity.this);
            }

            @Override
            public void fieldtoLoad(String error) {

            }
        });

        this.findViewById(R.id.Btn).setOnClickListener(v -> {
            Ads.ads.Show_Interstitial(this, new AdsManage.Interstital() {
                @Override
                public void isShowed() {
                    startActivity(new Intent(MainActivity.this,MainActivity.class));
                }

                @Override
                public void fieldToShow() {
                dialog.dismiss();
                }
            });
        });
    }
}