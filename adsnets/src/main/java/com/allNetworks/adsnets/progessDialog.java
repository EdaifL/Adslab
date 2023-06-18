package com.allNetworks.adsnets;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

public class progessDialog extends Dialog {
    public progessDialog(@NonNull Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.loading,null);
        this.setContentView(view);
        this.setCancelable(false);

    }
}
