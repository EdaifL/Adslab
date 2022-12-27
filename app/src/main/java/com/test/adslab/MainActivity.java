package com.test.adslab;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.test.adsnets.AdsManage;
import com.test.adsnets.AdsUnites;

public class MainActivity extends AppCompatActivity {
    AdsManage adsManage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AdsUnites("admob","ca-app-pub-3940256099942544/6300978111","","ca-app-pub-3940256099942544/1044960115","ca-app-pub-3940256099942544/8691691433","");
         adsManage = AdsUnites.Switch();
         adsManage.init(this);
         adsManage.Show_Native(this,this.findViewById(R.id.nativev),null);

    }
}