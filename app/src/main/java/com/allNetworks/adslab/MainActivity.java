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


    }
}