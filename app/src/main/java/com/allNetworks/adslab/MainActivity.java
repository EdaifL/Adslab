package com.allNetworks.adslab;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.allNetworks.adsnets.Ads;
import com.allNetworks.adsnets.AdsManage;
import com.allNetworks.adsnets.AdsUnites;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = this.findViewById(R.id.Btn);
        new AdsUnites(this, "https://imassine.store/api/application/com.Gittalng.LokLok", new AdsUnites.JsonListener() {
            @Override
            public void isloaded() {
                textView.setText("Is loaded and it's not a Isp selected");
                Ads.ads.LoadReward(MainActivity.this);
               textView.setOnClickListener(v -> {
                   Ads.ads.Show_Reward(MainActivity.this, new AdsManage.Reward() {
                       @Override
                       public void Rewarded() {
                           textView.setText("reward completed");
                           MainActivity.this.recreate();
                       }

                       @Override
                       public void FieldToreward(String erorrMassage) {

                       }
                   });
               });
            }

            @Override
            public void fieldtoLoad(String error) {

            }

            @Override
            public void isUnder() {
                textView.setText("it's Isp selected");
            }

            @Override
            public void isAppOff() {

            }
        });

    }
}